/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dijkstra;

/**
 *
 * @author andreicristea
 */
public class NegativeEdgeWeightException extends Exception {
    private final DirectedEdge edge;
    private final DirectedGraph graph;

    public NegativeEdgeWeightException(DirectedEdge edge, DirectedGraph graph) {
        this.edge = edge;
        this.graph = graph;
    }

    public DirectedEdge getEdge() {
        return edge;
    }

    public DirectedGraph getGraph() {
        return graph;
    }
    
    
}
