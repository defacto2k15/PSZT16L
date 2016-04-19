/** Autorzy : Bartosz Czerwinski , Maciej Lenard */

#include <stdio.h>
#include <errno.h>
#include <stdlib.h>
#include <string.h>
#include <sys/dispatch.h>
#include <iostream>
#include <string>
#include <sstream>

/** nazwa dla kanalu serwer->klient*/
#define ATTACH_POINT "myname" // TODO zmien nazwe

/** liczba programow klientow*/
#define THREAD_COUNT 2
#define DATA_SIZE 10


/** wielkosc wiadomosci dla pojedynczego kanalu*/
#define PAYLOAD_SIZE 5

#define PAYLOAD_MAX_SIZE 500

/** id serwera*/
int server_coid;

/** formatka nazwy polaczenia dla poszczegolnych klientow*/
std::string clientChannelNamePrefix = "ClientCh";

typedef struct _pulse msg_header_t;

/** struktura wiadomosci wysylanej z serwera do klientow.
*   		header - naglowek
*			payload - tablica przechowujaca dane do przeslania klientom
*/
typedef struct _server_to_client_data {
   msg_header_t hdr;
   char payload[ PAYLOAD_MAX_SIZE ];
} server_to_client_data_t;

/** struktura wiadomosci wysylanej od klienta do serwera*/
typedef struct _client_to_server_data {
   msg_header_t hdr;
   	double sum;
} client_to_server_data_t;

/** struktura opisujaca kanal */
   name_attach_t *attach; // TODO zamien nazwe na serverAttach
/** id kanalu, uzywane do signala, -1 oznacza brak polaczenia*/   
   int serverToClientChannelId = -1;
  
  /** Funkcja wykonywana w momencie wystapienia sygnalu SIGINT - zamyka wszystkie polaczenia 
  * @ param: params - nieuzywany parametr
  */ 
void handle_signal( int params){
	   std::cout << "Zamykanie serwera" << std::endl;
	   name_detach( attach, 0);
	   if( serverToClientChannelId != -1){
	   		name_close(serverToClientChannelId);
	   }
	   exit(0);
}

/** funkcja serwera */
int server() {
	signal(SIGINT, handle_signal);
  _server_to_client_data msg;
   int rcvid;

    
   double inputData[DATA_SIZE];
   for( int i = 0; i < DATA_SIZE; i++){
   		inputData[i] =709.0f;
   }    
/** otwieranie polaczen dla poszczegolnych klientow */
   for( int i = 0; i < THREAD_COUNT; i++){	
   		std::stringstream ss;
   		ss << clientChannelNamePrefix << i ;
   		std::string clientChannelName = ss.str();
   		std::cout << "Server trying to open connection  to connection to " << clientChannelName << std::endl;
	   while(( serverToClientChannelId = name_open(clientChannelName.c_str(), 0 )) == -1 ){
   		  sleep(1);
   		}
   		std::cout << "Server opened connection to " << clientChannelName << std::endl;
   /** wysylanie wiadomosci klientom o ich zadaniach */
   		 msg.hdr.type = 0x00;
  		 msg.hdr.subtype = 0x00; 
  		 msg.payload[0] = DATA_SIZE / THREAD_COUNT; // tu union
  		 for(int j = 0; j < PAYLOAD_SIZE; j++){
  		 //	msg.payload[j] = inputData[ i * PAYLOAD_SIZE + j];
  		 	msg.payload[j] = i*10 + j;
  		 }       

     	 if( MsgSend(serverToClientChannelId, &msg, sizeof(msg), NULL, 0) == -1 ) {
       		 std::cout << "SERVER: failed sending " << std::endl;
    	  }
    	   name_close(serverToClientChannelId);
    	   serverToClientChannelId = -1;
    }
   /** zmienna upewnieniajaca ze wszyscy klienci dostana swoj kanal*/
	int expectedMessagesToRecieve = THREAD_COUNT;
    double sum = 0;
    if(( attach = name_attach(NULL, ATTACH_POINT, 0)) == NULL ){
      return EXIT_FAILURE;
    }
  
  /** pobieranie wiadomosci o obliczonych zadaniach od klientow */
   client_to_server_data_t recievedMsg;
   while(1){
      rcvid = MsgReceive( attach->chid, &recievedMsg, sizeof(recievedMsg), NULL);
		std::cout << "server recieded sth " << std::endl;
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
		sum += recievedMsg.sum;
       std::cout << "Server recieved " << recievedMsg.sum << "as partial sum " << std::endl ;
        expectedMessagesToRecieve--;
        if( expectedMessagesToRecieve == 0){
        	break;
        }
   }
   
   std::cout << "Suma to " << sum << std::endl;

   name_detach( attach, 0);
   return EXIT_SUCCESS;
}

int client(int index){
   server_to_client_data_t msg;
   int server_coid;

   
   name_attach_t *attach;
   int rcvid;
   
   		std::stringstream ss;
   		ss << clientChannelNamePrefix << index ;
   		std::string myChannelName = ss.str();

	std::cout << " Client is creating channel " << myChannelName << std::endl;
   if(( attach = name_attach(NULL, myChannelName.c_str(), 0)) == NULL ){
      return EXIT_FAILURE;
   }

   while(1){
      int rcvid = MsgReceive( attach->chid , &msg, sizeof(msg), NULL);
		std::cout << "client recieded sth " << std::endl;
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
      std::cout << "klient odebral wiadomosc " << std::endl;
	  break;
   }
   name_detach( attach, 0);
   
   double sum;
   for( int i = 0; i < PAYLOAD_SIZE; i++){
   		sum += msg.payload[i];
   }
   
   
   	while(( server_coid = name_open(ATTACH_POINT, 0 )) == -1 ){
   		sleep(1);
   	}
   	
   	client_to_server_data_t toServerMsg;
   	 toServerMsg.hdr.type = 0x00;
  	 toServerMsg.hdr.subtype = 0x00; 
	 toServerMsg.sum = sum;
	  	 
	 if( MsgSend(server_coid, &toServerMsg, sizeof(toServerMsg), NULL, 0) == -1 ) {
        std::cout << "CLIENT: failed sending " << std::endl;
  	}
   std::cout << "klient wyslal wiadomosc " << std::endl;
    	  
   name_close(server_coid);
   return EXIT_SUCCESS;
}



int main( int argc, char **argv ){
   int ret;
   if (argc < 2) {
      printf("Usage %s -s  | -c clientIndex \n", argv[0]);
      ret = EXIT_FAILURE;
   }
   else if (strcmp(argv[1], "-c") == 0) {
      printf("Running Client ... \n");
      ret = client( (*argv[2])-'0' ); /* see name_open() for this code */
   }
   else if (strcmp(argv[1], "-s") == 0) {
      printf("Running Server ... \n");
      ret = server(); /* see name_attach() for this code */
   }
   else {
      printf("Usage %s -s | -c \n", argv[0]);
      ret = EXIT_FAILURE;
   }
   return ret;
}