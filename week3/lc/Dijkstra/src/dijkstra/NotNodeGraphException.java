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
public class NotNodeGraphException extends Exception {
    private final Node node;
    private final DirectedGraph graph;

    public NotNodeGraphException(Node node, DirectedGraph graph) {
        this.node = node;
        this.graph = graph;
    }

    public Node getNode() {
        return node;
    }

    public DirectedGraph getGraph() {
        return graph;
    }
}
