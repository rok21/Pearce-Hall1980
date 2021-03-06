package ViewModels.TableModels;

import Models.DTOs.TableModelDto;
import Models.SimulatorSettings;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rokas on 03/11/2015.
 */

public abstract class BaseTableModel extends AbstractTableModel  {

    public List<String> columnHeaders;

    public List<List<Object>> data;

    protected BaseTableModel(){
        columnHeaders = getColumnHeaders();
        data = getInitialData();
    }

    protected abstract List<String> getColumnHeaders();
    protected abstract List<List<Object>> getInitialData();

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnHeaders.size();
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnHeaders.get(columnIndex);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return getValueAt(0, columnIndex).getClass();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data.get(rowIndex).get(columnIndex);
    }

    public void copyData(TableModelDto tableModel){
        data = tableModel.data;
        columnHeaders = tableModel.headers;
        fireTableStructureChanged();
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        data.get(rowIndex).set(columnIndex, aValue);
        fireTableChanged(new TableModelEvent(this));
    }

    protected void addColumn(String colName, Object defaultValue){
        for(List<Object> row : data){
            row.add(defaultValue);
        }
        columnHeaders.add(colName);
        fireTableStructureChanged();
    }

    protected void addColumn(String colName){
        addColumn(colName, "");
    }

    protected void addRow(){
        List newRow = new ArrayList<>();
        for(int col=0;col<getColumnCount();col++){
            newRow.add(new Object());
        }
        data.add(newRow);
    }

    //pop methods

    protected void removeRighmostColumn(){
        for (List<Object> row : data) {
            row.remove(row.size() - 1);
        }
        columnHeaders.remove(getColumnCount() - 1);
        fireTableStructureChanged();
    }

    protected void removeBottomRow(){
        data.remove(data.size() - 1);
    }

    protected void clearTable(){
        while(getRowCount()>0){
            removeBottomRow();
        }
    }
}
