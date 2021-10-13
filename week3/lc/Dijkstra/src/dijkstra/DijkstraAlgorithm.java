/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dijkstra;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 *
 * @author andreicristea
 */
public class DijkstraAlgorithm {
    private final DirectedGraph graph;
    private Map<Node, Node> prevNodes;
    private Map<Node, Double> distances;
    private PriorityQueue<Node> unfinishedNodes;

    public DijkstraAlgorithm(DirectedGraph graph) {
        this.graph = graph;
    }
    
    public void runFromStartingNode(Node start) throws NotNodeGraphException, NegativeEdgeWeightException {
        this.checkIfExistsNegativeWeightEdge();
        this.init(start);
        
        while (!unfinishedNodes.isEmpty()) {
            Node nodeWithMinDistance = this.unfinishedNodes.poll();
            Double minDistance = this.distances.get(nodeWithMinDistance);
            
            
            for (DirectedEdge outgoingEdge : graph.getOutgoingEdges(nodeWithMinDistance) ) {
                Double distanceThroughThisNode = minDistance + outgoingEdge.getWeight();
                Node neighbour = outgoingEdge.getTargetNode();
                if (distanceThroughThisNode < distances.get(neighbour)) {
                    this.updatePath(nodeWithMinDistance, neighbour, distanceThroughThisNode);
                }
            }
        }
    }
    
    private void init(Node start) {
        this.prevNodes = new HashMap<>();
        this.distances = new HashMap<>();
        
        this.unfinishedNodes = new PriorityQueue(graph.getNodes().size(), new Comparator<Node>() {
            @Override
            public int compare(Node n1, Node n2) {
                return distances.get(n1).compareTo(distances.get(n2));
            }
        });
        
        this.graph.getNodes().forEach(node -> {
            this.prevNodes.put(node, null);
            this.distances.put(node, Double.POSITIVE_INFINITY);
            this.unfinishedNodes.add(node);
        });
        
        this.distances.put(start, 0.0);
    }
    
    private void updatePath(Node nodeWithMinDistance, Node neighbour, Double distance) {
        distances.put(neighbour, distance);
        prevNodes.put(neighbour, nodeWithMinDistance);
        
        unfinishedNodes.remove(neighbour);
        unfinishedNodes.add(neighbour);
    }
    
    private void checkIfExistsNegativeWeightEdge() throws NegativeEdgeWeightException {
        for (DirectedEdge edge : graph.getEdges()) {
            if (edge.getWeight() < 0.0) {
                throw new NegativeEdgeWeightException(edge, graph);
            } 
        }
    }
    
    public Map<Node, Node> getPrevNodes() {
        return prevNodes;
    }
    
    public Map<Node, Double> getDistances() {
        return distances;
    }
}
