package ViewModels.TableModels;

import Constants.DefaultValuesConstants;
import Constants.GuiStringConstants;
import Models.DTOs.ParamsTableModelDto;
import Models.DTOs.TrialTableModelDto;
import Models.SimulatorSettings;
import _from_RW_simulator.ContextConfig;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rokas on 03/11/2015.
 *
 * group ids are 0-based, while phase ids are 1-based, sincere apologies
 */
public class TrialTableModel extends BaseTableModel implements Serializable {

	public boolean simulateContext;
		
    public TrialTableModel(boolean simulateContext){
        this.simulateContext = simulateContext;
        addPhase();
    }

    @Override
    protected List<String> getColumnHeaders() {
        if(columnHeaders==null) {
            columnHeaders = new ArrayList<>();
            columnHeaders.add(GuiStringConstants.GROUP_NAME);
        }
        return columnHeaders;
    }

    public int getPhaseCount(){
        if(columnHeaders==null){
            return 0;
        }
        return (columnHeaders.size() - 1) / getGroupPhaseCellsCount();
    }

    public int getGroupCount(){
        return data.size();
    }

    @Override
    protected List<List<Object>> getInitialData() {
        List<List<Object>> data = new ArrayList<>();
        List<Object> firsRow = new ArrayList<>();
        firsRow.add(GuiStringConstants.getDefaultGroupName(0));
        data.add(firsRow);
        return data;
    }

    public void setSimulateContext(boolean simulateContext){
		//copy data
		int phaseCount = getPhaseCount();
		List<List<String>> descriptions = new ArrayList<>();
		List<List<Boolean>> randomSelections = new ArrayList<>();
		for(int gid=0;gid<getGroupCount();gid++){
			descriptions.add(getPhaseDescriptions(gid));
			randomSelections.add(getRandomSelections(gid));
		}
		//remove columns (except the first one)
		while(columnHeaders.size()>1){
			removeRighmostColumn();
		}
		
		//add phases
		this.simulateContext = simulateContext;
		for(int pid=1;pid<=phaseCount;pid++){
			addPhase();
		}

        for(int g=0;g<descriptions.size();g++){
            setPhaseDescriptions(descriptions.get(g), g);
            setRandomSelections(randomSelections.get(g), g);
        }
	}
	
	public int getGroupPhaseCellsCount(){
		return simulateContext ? 4 : 2;
	}
	
	public void addGroup(){
        addRow();
        int groupId = getGroupCount()-1;
        setValueAt(GuiStringConstants.getDefaultGroupName(groupId), groupId, 0);
        for(int phaseId = 1;phaseId <= getPhaseCount();phaseId++){
            setValueAt(GuiStringConstants.DEFAULT_PHASE, groupId, getColId(phaseId, 1));
            setValueAt(DefaultValuesConstants.RANDOM_SELECTION, groupId, getColId(phaseId, 2));
			if(simulateContext){
				setValueAt(new ContextConfig(), groupId, getColId(phaseId, 3));
				setValueAt(DefaultValuesConstants.ITI_CS_RATIO, groupId, getColId(phaseId, 4));
			}
        }
    }
		
    public void removeGroup(){
        if(getGroupCount()>1){
            removeBottomRow();
        }
        fireTableStructureChanged();
    }

    public String getGroupName(int groupId){
        return (String) getValueAt(groupId, 0);
    }

    public List<String> getPhaseDescriptions(int groupId) {
        List<String> descriptions = new ArrayList<>();
        for(int p=1;p<=getPhaseCount();p++){
            descriptions.add((String) getValueAt(groupId, getColId(p, 1)));
        }
        return descriptions;
    }

    public List<Boolean> getRandomSelections(int groupId) {
        List<Boolean> selections = new ArrayList<>();
        for(int p=1;p<=getPhaseCount();p++){
            selections.add((Boolean) getValueAt(groupId, getColId(p, 2)));
        }
        return selections;
    }

    public List<ContextConfig> getContextConfigs(int groupId){
        List<ContextConfig> configs = new ArrayList<>();
        for(int p=1;p<=getPhaseCount();p++) {
            configs.add((ContextConfig) getValueAt(groupId, getColId(p, 3)));
        }
        return configs;
    }

    public List<Integer> getItiRatios(int gi) {
        List<Integer> ratios = new ArrayList<>();
        for(int p=1;p<=getPhaseCount();p++) {
            ratios.add((int) getValueAt(gi, getColId(p, 4)));
        }
        return ratios;
    }

    private void setPhaseDescriptions(List<String> descriptions, int groupId){
        for(int p=1;p<=getPhaseCount();p++) {
            setValueAt(descriptions.get(p - 1), groupId, getColId(p, 1));
        }
    }

    private void setRandomSelections(List<Boolean> selections, int groupId){
        for(int p=1;p<=getPhaseCount();p++) {
            setValueAt(selections.get(p-1), groupId, getColId(p, 2));
        }
    }

	private int getColId(int phaseId, int groupPhaseColId){ //both 1-based
		return phaseId*getGroupPhaseCellsCount() - (getGroupPhaseCellsCount() - groupPhaseColId);
	}

    public void addPhase(){
        addColumn(GuiStringConstants.getPhaseTitle(getPhaseCount()), GuiStringConstants.DEFAULT_PHASE);
        addColumn(GuiStringConstants.RANDOM, DefaultValuesConstants.RANDOM_SELECTION);
		if(simulateContext){
			addContextColumn();
			addColumn(GuiStringConstants.ITI_CS_RATION, DefaultValuesConstants.ITI_CS_RATIO);
		}
    }

    private void addContextColumn(){
        addColumn(GuiStringConstants.CONTEXT, new ContextConfig());
        //need to do this, otherwise entire column is pointing to the same object
        int lastColId = getColumnCount() - 1;
        for(int r=0;r<getRowCount();r++){
            setValueAt(new ContextConfig(), r, lastColId);
        }
    }
	
    public void removePhase(){
        if(getPhaseCount()>1) {
            for(int i=0;i<getGroupPhaseCellsCount();i++){
				removeRighmostColumn();
			}
        }
    }

    public void copyData(TrialTableModelDto tableModel) {
        super.copyData(tableModel);
        this.simulateContext = tableModel.simContext;
    }
}
