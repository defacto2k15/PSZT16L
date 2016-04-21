/** Autorzy : Bartosz Czerwinski , Maciej Lenard */

#include <stdio.h>
#include <errno.h>
#include <stdlib.h>
#include <string.h>
#include <sys/dispatch.h>
#include <iostream>
#include <string>
#include <sstream>
#include <vector>

/** nazwa dla kanalu serwer->klient*/
#define SERVER_CHANNEL_NAME "myname" 

#define DATA_SIZE 1000

#define PAYLOAD_MAX_SIZE 500

#define CHANNEL_NAME_SIZE 20

/** formatka nazwy polaczenia dla poszczegolnych klientow*/
std::string clientChannelNamePrefix = "ClientCh";

typedef struct _pulse msg_header_t;

/** struktura wiadomosci wysylanej z serwera do klientow.
 *   		header - naglowek
 *			payload - tablica przechowujaca dane do przeslania klientom
 */
struct server_to_client_msg_t {
   msg_header_t hdr;
   enum {   PayloadLength, PayloadData, AbortCalculations } type;
   union{
      int payloadLength;
      double payload[ PAYLOAD_MAX_SIZE ];
   };
};

/** struktura wiadomosci wysylanej od klienta do serwera*/
struct client_to_server_msg_t {
   msg_header_t hdr;
   enum{ ChannelName, Sum} type;
   union{ 
      double sum;
      char channelName[CHANNEL_NAME_SIZE];
   };
};


template< typename TMessage >
void recieveMessage(name_attach_t *attach, int sizeOfMessage, bool(*recievingPredicate)( TMessage &)){
   TMessage msg;
   while(1){
      int rcvid = MsgReceive( attach->chid, &msg, sizeOfMessage, NULL);
      if(rcvid == 0 ){ 
         switch(msg.hdr.code ){
            case _PULSE_CODE_DISCONNECT:
               ConnectDetach(msg.hdr.scoid);
               break;
            case _PULSE_CODE_UNBLOCK:
               break;
            default:
               break;
         }
         continue;
      }
      if( msg.hdr.type == _IO_CONNECT ){
         MsgReply( rcvid, EOK, NULL, 0);
         continue;
      }

      if( msg.hdr.type > _IO_BASE && msg.hdr.type <= _IO_MAX ) {
         MsgError( rcvid, ENOSYS );
         continue;
      }
      MsgReply( rcvid, EOK, NULL, 0);

      if( recievingPredicate(msg) ){
         break;
      }
   }

}

template< typename TMessage >
void sendMessage( TMessage *message, int size, int channelId  ){
   message->hdr.type = 0x00;
   message->hdr.subtype = 0x00;
   if( MsgSend(channelId, message, size, NULL, 0) == -1 ) {
      std::cerr << "Sending message failed" << std::endl;
      std::exit(-1);
   }

}

/** struktura opisujaca kanal */
name_attach_t *server_channelAttach; 

/** id kanalu, uzywane do signala, -1 oznacza brak polaczenia*/   
int serverToClientChannelId = -1;

double server_sum;
double server_expectedMessagesToRecieve;
std::vector<std::string> server_clientChannelNames;

/** Funkcja wykonywana w momencie wystapienia sygnalu SIGINT - zamyka wszystkie polaczenia 
 * @ param: params - nieuzywany parametr
 */ 
void handle_signal( int params){
   std::cout << "Zamykanie serwera" << std::endl;
   if( serverToClientChannelId != -1){
      name_close(serverToClientChannelId);
   }
   for( int i = 0; i < server_clientChannelNames.size(); i++){
      const char *clientChannelName = server_clientChannelNames[i].c_str();
      std::cout << "Server trying to open connection  to connection to " << clientChannelName << std::endl;
      while(( serverToClientChannelId = name_open( clientChannelName, 0 )) == -1 ){
         sleep(1);
      }
      server_to_client_msg_t msg;
      msg.type = server_to_client_msg_t::AbortCalculations;
      sendMessage( &msg, sizeof(msg_header_t)+sizeof(msg.type), serverToClientChannelId);
      std::cout << "Server sent aborting command" << std::endl;
      name_close(serverToClientChannelId);
   }
   name_detach( server_channelAttach, 0);
   exit(0);
}



bool serverFromClientDoneTasksMessageReciever( client_to_server_msg_t &message ){
   if( message.type == client_to_server_msg_t::Sum ){
      server_sum += message.sum;
      std::cout << "Server recieved " << message.sum << "as partial sum " << std::endl ;
   }else if ( message.type == client_to_server_msg_t::ChannelName){
      std::string name( message.channelName );
      std::cout << "Server recieved client channel name " << name << std::endl;
      server_clientChannelNames.push_back(name);
   }
   server_expectedMessagesToRecieve--;
   if( server_expectedMessagesToRecieve == 0){
      return true;
   }
   return false;
}

/** funkcja serwera */
int server(int threadCount) {
   signal(SIGINT, handle_signal);

   server_expectedMessagesToRecieve = threadCount;
   if(( server_channelAttach = name_attach(NULL, SERVER_CHANNEL_NAME, 0)) == NULL ){
      return EXIT_FAILURE;
   }
   std::cout << "Server created own channel " << std::endl;
   recieveMessage( server_channelAttach, sizeof( client_to_server_msg_t), &serverFromClientDoneTasksMessageReciever);

   std::cout << " 5 seconds to abort! " << std::endl;
   sleep(5);

   double inputData[DATA_SIZE];
   for( int i = 0; i < DATA_SIZE; i++){
      inputData[i] =i;
   }    
   /** otwieranie polaczen dla poszczegolnych klientow */
   int remainingNumbersToSum = DATA_SIZE;
   int thisPayloadSize = 0;
   int equalShareOfPayload = DATA_SIZE / threadCount;
   for( int i = 0; i < threadCount; i++){	
      if( i < threadCount - 1){
         thisPayloadSize = equalShareOfPayload;
      } else {
         thisPayloadSize = remainingNumbersToSum;
      }
      remainingNumbersToSum-= thisPayloadSize;
      
      const char *clientChannelName = server_clientChannelNames[i].c_str();
      std::cout << "Server trying to open connection  to connection to " << clientChannelName << std::endl;
      while(( serverToClientChannelId = name_open( clientChannelName, 0 )) == -1 ){
         sleep(1);
      }
      std::cout << "Server opened connection to " << clientChannelName << std::endl;

      /** wysylanie wiadomosci klientom o ich zadaniach */
      server_to_client_msg_t msg;
      
      msg.type = server_to_client_msg_t::PayloadLength;
      msg.payloadLength = thisPayloadSize;
      sendMessage( &msg, sizeof(msg_header_t)+sizeof(int)+sizeof(msg.type), serverToClientChannelId);
      std::cout << " Server send size of payload to client " << std::endl;

      msg.type = server_to_client_msg_t::PayloadData;
      memcpy(msg.payload, inputData +(DATA_SIZE - thisPayloadSize - remainingNumbersToSum ), sizeof(double) * (thisPayloadSize));
      sendMessage( &msg, sizeof(msg_header_t)+sizeof(msg.type)+(sizeof(double)*(thisPayloadSize)), serverToClientChannelId);

      std::cout << " server sent payload to client " << std::endl;

      name_close(serverToClientChannelId);
      serverToClientChannelId = -1;
   }

   /** pobieranie wiadomosci o obliczonych zadaniach od klientow */
   server_expectedMessagesToRecieve = threadCount;
   server_sum = 0;
   recieveMessage( server_channelAttach, sizeof( client_to_server_msg_t), &serverFromClientDoneTasksMessageReciever);

   std::cout << "Sum calculated by clients is  " << server_sum<< std::endl;

   double alternativeSum = 0;
   for( int i = 0; i < DATA_SIZE; i++){
      alternativeSum += inputData[i];
   }
   std::cout << "Sum calculated by server  is  " << alternativeSum << std::endl;

   name_detach( server_channelAttach, 0);
   return EXIT_SUCCESS;
}

double client_payload[PAYLOAD_MAX_SIZE];
double client_payloadSize = 0;

bool clientFromServerRecievingFunction( server_to_client_msg_t &message){
   if( message.type == server_to_client_msg_t::PayloadLength ){
      std::cout<<"Client recieved message with payload length, which is " << message.payloadLength << std::endl;
      client_payloadSize = message.payloadLength;
   } else if( message.type == server_to_client_msg_t::PayloadData){
      memcpy(client_payload, message.payload, sizeof(double) * client_payloadSize);
      std::cout << "Client recieved payload" << std::endl;
   } else if (message.type == server_to_client_msg_t::AbortCalculations ){
      client_payloadSize = 0;
   }
   return true;
}

int client(int index){
   client_to_server_msg_t msg;
   int server_coid;

   name_attach_t *attach;

   std::stringstream ss;
   ss << clientChannelNamePrefix << index ;
   std::string myChannelName = ss.str();

   std::cout << " Client is creating channel " << myChannelName << std::endl;
   if(( attach = name_attach(NULL, myChannelName.c_str(), 0)) == NULL ){
      return EXIT_FAILURE;
   }

   while(( server_coid = name_open(SERVER_CHANNEL_NAME, 0 )) == -1 ){
      sleep(1);
   }
   std::cout << "Client connected to server " << std::endl;

   msg.type = client_to_server_msg_t::ChannelName;
   strcpy(msg.channelName, myChannelName.c_str());
   sendMessage( &msg, sizeof( client_to_server_msg_t), server_coid);

   std::cout << "Client waiting for first message" << std::endl;
   recieveMessage( attach, sizeof( msg_header_t) + sizeof(msg.type) + sizeof(int), &clientFromServerRecievingFunction);

   if( client_payloadSize == 0){
      std::cout << " Client was told not to calculate sum " << std::endl;
      name_detach( attach, 0);
      name_close(server_coid);
      return EXIT_SUCCESS;
   } 

   std::cout << "Client waiting for second message" << std::endl;

   recieveMessage( attach, sizeof(msg_header_t)+ sizeof(msg.type)+(sizeof(double) * client_payloadSize), &clientFromServerRecievingFunction);
   name_detach( attach, 0);

   double sum;
   for( int i = 0; i < client_payloadSize; i++){
      std::cout << client_payload[i] << " ";
      sum += client_payload[i];
   }
   std::cout << std::endl;


   msg.type = client_to_server_msg_t::Sum;
   msg.sum = sum;
   sendMessage( &msg, sizeof(client_to_server_msg_t), server_coid);
   std::cout << "Client sent payload : partial sum of " << sum <<  std::endl;

   sleep(5);
   name_close(server_coid);
   return EXIT_SUCCESS;
}



int main( int argc, char **argv ){
   int ret;
   if (argc < 2) {
      printf("Usage %s -s clientsCount  | -c clientIndex \n", argv[0]);
      ret = EXIT_FAILURE;
   }
   else if (strcmp(argv[1], "-c") == 0) {
      printf("Running Client ... \n");
      ret = client( (*argv[2])-'0' ); /* see name_open() for this code */
   }
   else if (strcmp(argv[1], "-s") == 0) {
      printf("Running Server ... \n");
      ret = server( (*argv[2]) - '0'); /* see name_attach() for this code */
   }
   else {
      printf("Usage %s -s | -c \n", argv[0]);
      ret = EXIT_FAILURE;
   }
   return ret;
}
