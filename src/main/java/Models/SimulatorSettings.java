package Models;

import Constants.DefaultValuesConstants;

import java.io.Serializable;

/**
 * Created by Rokas on 02/02/2016.
 */
public class SimulatorSettings implements Serializable {
    public boolean CompoundResults;
    public boolean ContextSimulation;
    public int NumberOfRandomCombination;
    public SimulatorSettings(){
        CompoundResults = DefaultValuesConstants.COMPOUND_RESULTS;
        ContextSimulation = DefaultValuesConstants.CONTEXT_SIMULATION;
        NumberOfRandomCombination = DefaultValuesConstants.NUMBER_OF_RANDOM_COMBINATIONS;
    }
}
