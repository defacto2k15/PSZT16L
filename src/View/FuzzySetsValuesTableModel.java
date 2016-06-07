package View;

import java.util.Arrays;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import Model.CrispValues.CrispValuesDatabase;
import Model.FuzzySets.InputFuzzySet;

public class FuzzySetsValuesTableModel extends AbstractTableModel {
	private List<InputFuzzySet> fuzzySets;
	private List<String> columnNames = Arrays.asList("Dziedzina", "Atrybut", "Stopień");
	private CrispValuesDatabase crispValuesDatabase;
	
	public FuzzySetsValuesTableModel( CrispValuesDatabase crispValuesDatabase ){
		this.crispValuesDatabase = crispValuesDatabase;
	}

    public int getColumnCount() {
        return 3;
    }

    public int getRowCount() {
        return fuzzySets.size();
    }

    public String getColumnName(int col) {
        return columnNames.get(col);
    }

    public Object getValueAt(int row, int col) {
        InputFuzzySet set = fuzzySets.get(row);
        if( col == 0 ){
        	return set.getLinguisticAttribute().toString();
        } else if( col == 1 ){
        	return set.getLinguisticValue().toString();
        } else if ( col == 2 ){
        	return set.getDegree( crispValuesDatabase );
        }
        System.err.println("E11. Not expected column no "+col);
        return "ERRROR E11";
    }

	public void setFuzzySets(List<InputFuzzySet> fuzzySets) {
		this.fuzzySets = fuzzySets;
	}
}
