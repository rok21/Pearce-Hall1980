package Helpers;

import Models.Graphing.Graph;
import Models.Graphing.GraphLine;
import Models.Graphing.GraphLineGroup;
import Models.History.GroupPhaseHistory;
import Models.History.PhaseHistory;
import Models.History.SimulationHistory;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Rokas on 21/01/2016.
 */
public class GraphBuilder {

    public static List<Graph> BuildGraphs(SimulationHistory history){
        Map<String, List<GraphLine>> linkedLinesMap = new HashMap<>();
        List<Graph> graphs = new ArrayList<>();
        for(PhaseHistory phaseHistory : history.getPhases()){
            Graph graph = new Graph(phaseHistory.getPhaseName());
            for(GroupPhaseHistory gpHist : phaseHistory){
                for(Character cue : gpHist.getCues()){
                    GraphLine line = new GraphLine(cue.toString());
                    addLinePoints(line, cue, gpHist);
                    graph.addLine(gpHist.getGroupName(), line);
                    setLink(linkedLinesMap, line, graph.getGroup(gpHist.getGroupName()));
                }
            }
            graphs.add(graph);
        }
        return graphs;
    }

    private static void addLinePoints(GraphLine line, Character cue, GroupPhaseHistory gpHist){
        for(int trialNo = 1; trialNo<=gpHist.getNumberOfTrials(); trialNo++){
            line.addPoint(trialNo, gpHist.getState(cue, trialNo).Vnet);
        }
    }

    //used for colour consistency among graphs
    private static void setLink(Map<String, List<GraphLine>> linkedLinesMap, GraphLine line, GraphLineGroup group){
        String lineCommand = GraphStringsHelper.getLineCommand(group, line);
        if(!linkedLinesMap.containsKey(lineCommand)) {
            linkedLinesMap.put(lineCommand, new ArrayList<GraphLine>());
        }
        List<GraphLine> linkedLines = linkedLinesMap.get(lineCommand);
        linkedLines.add(line);
        line.setLinkedLines(linkedLines);
    }
}
