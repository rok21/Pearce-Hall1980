package ViewModels;

import Models.Parameters.Parameter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rokas on 08/11/2015.
 */
public abstract class BaseParamsTableModel extends BaseTableModel {

    private List<Parameter> parameters;

    @Override
    protected String[] getColumnHeaders() {
        return new String[0];
    }

    @Override
    protected List<List<Object>> getInitialData() {
        List<List<Object>> data = new ArrayList<>();
        List<Object> firsRow = new ArrayList<>();
        firsRow.add("");
        firsRow.add("");
        data.add(firsRow);
        return data;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        super.setValueAt(aValue, rowIndex, columnIndex);
        parameters.get(rowIndex).setValue((double) aValue);
    }

    public void setUpParameters(List<Parameter> parameters){
        this.parameters = parameters;
        for(int row = 0;row<parameters.size();row++){
            Parameter rowParam = parameters.get(row);
            super.setValueAt(rowParam.getDisplayName(), row, 0);
            super.setValueAt(rowParam.getValue(), row, 1);
        }
        fireTableDataChanged();
    }
}
