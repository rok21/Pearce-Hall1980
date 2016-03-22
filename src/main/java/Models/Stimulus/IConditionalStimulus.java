package Models.Stimulus;

import Models.Parameters.Pools.GlobalParameterPool;

import java.util.Map;

/**
 * Created by Rokas on 13/03/2016.
 */
public interface IConditionalStimulus extends IStimulus {
    IConditionalStimulus getCopy();
    void reset(IConditionalStimulus cs);
    void stimulate(GlobalParameterPool globalParams, Map<Character, Double> phaseLambdaValues, double vNet, char reinforcer);
}