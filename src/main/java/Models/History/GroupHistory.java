package Models.History;

import Models.Group;
import Models.Parameters.ConditionalStimulus.CsParameter;
import Models.Parameters.Pools.CsPools.CsParameterPool;
import Models.Parameters.Parameter;
import Models.Parameters.Pools.CsPools.ICsParameterPool;
import Models.Parameters.UnconditionalStimulus.UsParameter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rokas on 16/11/2015.
 */
public class GroupHistory implements Serializable {
    public Group group;
    private List<GroupPhaseHistory> phaseHistories;
    public ICsParameterPool csParameterPool;
    public List<Parameter> globalParameters;
    public List<UsParameter> usParameters;
    public GroupHistory(Group group, ICsParameterPool csParameterPool, List<Parameter> globalParameters, List<UsParameter> usParameters){
        this.group = group;
        this.csParameterPool = csParameterPool;
        this.globalParameters = globalParameters;
        this.usParameters = usParameters;
        phaseHistories = new ArrayList<>();
    }

    public void add(GroupPhaseHistory gpHist){
        phaseHistories.add(gpHist);
    }

    public GroupPhaseHistory getGroupPhaseHistory(int phaseId){
        return phaseHistories.get(phaseId);
    }

    public int getNumberOfPhases(){
        return phaseHistories.size();
    }

    public List<CsParameter> getGroupParameters(){
        return csParameterPool.getGroupParameters(group);
    }
}
