package Constants;

/**
 * Created by Rokas on 03/11/2015.
 */
public class TableStringConstants {
    public static final String GROUP_NAME = "Group Name";
    public static final String CS_PARAMETER = "Cs Parameter";
    public static final String GLOBAL_PARAMETER = "Global Parameter";
    public static final String VALUE = "Value";

    public static final String DEFAULT_PHASE = "2A+";


    private static final String DEFAULT_GROUP_NAME = "Group";
    private static final String PHASE = "Phase";
    public static String getPhaseTitle(int phaseId){return PHASE + " " + (phaseId+1);}
    public static String getDefaultGroupName(int groupNo) {
        return DEFAULT_GROUP_NAME + " " + groupNo;
    }
}