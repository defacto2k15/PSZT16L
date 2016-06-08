package View;

import java.util.Arrays;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import Model.CrispValues.CrispValuesDatabase;
import Model.FuzzySets.InputFuzzySet;
import Model.FuzzySets.TipFuzzySet;
import Model.Rules.Operation;
import Model.Rules.Rule;

public class RulesTableModel extends AbstractTableModel {
	private List<Rule> rules;
	private CrispValuesDatabase crispValuesDatabase;
	int origSize;
	
	public RulesTableModel( List<Rule> rules, CrispValuesDatabase crispValuesDatabase ){
		System.out.println("In ctor thet is "+rules.size());
		this.rules = rules;
		origSize = rules.size();
		this.crispValuesDatabase = crispValuesDatabase;
	}

    public int getColumnCount() {
        return 1;
    }

    public int getRowCount() {
        return origSize; // TODO VERY UGLY HACK rules.size();
    }

    public String getColumnName(int col) {
        return "XXX";
    }

    public Object getValueAt(int row, int col) {
    	StringBuilder sb = new StringBuilder();
    	Rule rule = rules.get(row);
    	InputFuzzySet leftSet = rule.getLeftSet();
    	sb.append("Jeżeli ").append( leftSet.getLinguisticAttribute()).append(" jest ")
    		.append(leftSet.getLinguisticValue()).append("("+leftSet.getDegree(crispValuesDatabase)+")");
    	if( rule.getOperation() == Operation.AND ){
    		sb.append(" ORAZ ");
    	} else {
    		sb.append(" LUB ");
    	}
    	InputFuzzySet rightSet = rule.getRightSet();
    	sb.append( rightSet.getLinguisticAttribute()).append(" jest \n")
			.append(rightSet.getLinguisticValue()).append("("+rightSet.getDegree(crispValuesDatabase)+")");  
    	TipFuzzySet conclusion = rule.getConclusion();
    	sb.append(" To napiwek jest "+conclusion.getType());
    	return sb;
    }
}
