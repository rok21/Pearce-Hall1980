package Controllers;

import Constants.GuiStringConstants;
import Helpers.Export.ModelExportHelper;
import Helpers.GuiHelper;
import Helpers.ModelBuilding.ModelDtoHelper;
import Models.DTOs.ModelDto;
import Models.Simulator;
import Models.SimulatorSettings;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rokas on 01/02/2016.
 */
public class MenuController implements ActionListener {

    private SimulatorSettings settings;
    private MainWindowController mainWindowController;
    private JMenuBar menuBar;
    private Map<String, JCheckBoxMenuItem> checkboxesMap;

    public MenuController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
        initMenuBar();
        setSettings(mainWindowController.getSimulator());
    }

    //init stuff
    private void initMenuBar(){
        checkboxesMap = new HashMap<>();
        menuBar = new JMenuBar();
        //file menu
        JMenu fileMenu = new JMenu(GuiStringConstants.FILE);
        createMenuItem(fileMenu, GuiStringConstants.SAVE, MenuItemType.BASIC);
        createMenuItem(fileMenu, GuiStringConstants.OPEN, MenuItemType.BASIC);
        menuBar.add(fileMenu);
        //settings menu
        JMenu settingsMenu = new JMenu(GuiStringConstants.SETTINGS);
        createMenuItem(settingsMenu, GuiStringConstants.RANDOM_TRIALS_SETTING, MenuItemType.BASIC);
        createMenuItem(settingsMenu, GuiStringConstants.COMPOUND_RESULTS_SETTING, MenuItemType.CHECKBOX);
        createMenuItem(settingsMenu, GuiStringConstants.CONIFGURAL_CUES_SETTING, MenuItemType.CHECKBOX);
        menuBar.add(settingsMenu);
    }

    private enum MenuItemType { BASIC, CHECKBOX }

    private void createMenuItem(JMenu menu, String nameAndCommand, MenuItemType type){
        JMenuItem menuItem = type == MenuItemType.CHECKBOX ? new JCheckBoxMenuItem(nameAndCommand) : new JMenuItem(nameAndCommand);
        menuItem.setActionCommand(nameAndCommand);
        menuItem.addActionListener(this);
        menu.add(menuItem);
        if(type == MenuItemType.CHECKBOX){
            checkboxesMap.put(nameAndCommand, (JCheckBoxMenuItem)menuItem);
        }
    }

    //action events
    private void onCompoundResultsSettingTicked(boolean isSelected){
        settings.CompoundResults = isSelected;
        mainWindowController.disableAllButtons();
    }

    private void onSaveModel(){
        ModelExportHelper.exportModel(ModelDtoHelper.buildModelDto(mainWindowController));
    }

    private void onLoadModel() {
        try {
            ModelDto modelDto = ModelExportHelper.readModel();
            ModelDtoHelper.loadModelDto(modelDto, mainWindowController, this);
        }catch(Exception ex){
            GuiHelper.displayErrorMessage(ex.getMessage());
        }
    }

    private void onRandomTrialsSetting(){
        settings.NumberOfRandomCombination = GuiHelper.getIntFromUser(GuiStringConstants.RANDOM_TRIALS_SETTING, settings.NumberOfRandomCombination);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        switch (e.getActionCommand()){
            case GuiStringConstants.COMPOUND_RESULTS_SETTING:
                onCompoundResultsSettingTicked(GuiHelper.isMenuItemSelected(e));
                break;
            case GuiStringConstants.SAVE:
                onSaveModel();
            break;
            case GuiStringConstants.OPEN:
                onLoadModel();
                break;
            case GuiStringConstants.RANDOM_TRIALS_SETTING:
                onRandomTrialsSetting();
                break;
            default:
                GuiHelper.displayErrorMessage("Nicht implementiert!");
        }
    }

    public void setSettings(Simulator simulator) {
        this.settings = simulator.getSimulatorSettings();
        //set up checkboxes states
        checkboxesMap.get(GuiStringConstants.COMPOUND_RESULTS_SETTING).setState(settings.CompoundResults);
        checkboxesMap.get(GuiStringConstants.CONIFGURAL_CUES_SETTING).setState(settings.ConfiguralCues);
    }

    //getters
    public JMenuBar getBar(){
        return menuBar;
    }
}