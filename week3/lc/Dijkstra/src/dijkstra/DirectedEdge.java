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
public class DirectedEdge {
    private final Node sourceNode;
    private final Node targetNode;
    private final Double weight;

    public DirectedEdge(Node sourceNode, Node targetNode, Double weight) {
        this.sourceNode = sourceNode;
        this.targetNode = targetNode;
        this.weight = weight;
    }

    public Node getSourceNode() {
        return sourceNode;
    }

    public Node getTargetNode() {
        return targetNode;
    }

    public Double getWeight() {
        return weight;
    }
    
    
}
