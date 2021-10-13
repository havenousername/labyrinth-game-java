/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dijkstra;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author andreicristea
 */
public class DirectedGraph {
    private final Map<String, Node> nodes;
    private final Collection<DirectedEdge> edges;
    private final Map<Node, Collection<DirectedEdge>> outgoingEdgesMap;
    
    
    public DirectedGraph() {
        this.nodes = new HashMap<>();
        this.edges = new ArrayList<>();
        this.outgoingEdgesMap = new HashMap<>();
    }

    public Collection<Node> getNodes() {
        return nodes.values();
    }

    public Collection<DirectedEdge> getEdges() {
        return edges;
    }
    
    public void addNode(Node node) {
        this.nodes.put(node.getNodeName(), node);
        this.outgoingEdgesMap.put(node, new ArrayList<>());
    }
    
    public void removeNode(Node node) {
        this.nodes.remove(node.getNodeName());
    }
    
    public void addDirectedEdge(DirectedEdge edge) throws NotNodeGraphException {
        if (edge != null) {
            if (
                    !this.nodes.values().contains(edge.getSourceNode()) ||
                    !this.nodes.values().contains(edge.getTargetNode())
                ) {
                throw new NotNodeGraphException(edge.getSourceNode(), this);
            }
            
            this.edges.add(edge);
            Collection<DirectedEdge> outgoingEdges = this.outgoingEdgesMap.get(edge.getSourceNode());
            outgoingEdges.add(edge);
        }
    }
    
    public void createDirectedEdgeBetweenNodes(Node sourceNode, Node targetNode, double weight) throws NotNodeGraphException {
        this.addDirectedEdge(new DirectedEdge(sourceNode, targetNode, weight));
    }
    
    public void createDirectedEdgeBetweenNodes(String sourceNodeName, String targetNodeName, double weight) throws NotNodeGraphException {
        Node sourceNode = this.getNodeByName(sourceNodeName);
        Node targetNode = this.getNodeByName(targetNodeName);
        
        this.addDirectedEdge(new DirectedEdge(sourceNode, targetNode, weight));
    }
    
    public Collection<Node> getNeighboursOfNode(Node node) throws NotNodeGraphException {
        if (!this.nodes.values().contains(node)) {
            throw new NotNodeGraphException(node, this);
        }
        
        return this.outgoingEdgesMap
                .get(node)
                .stream()
                .map(DirectedEdge::getTargetNode)
                .collect(Collectors.toList());
    }
    
    public Collection<DirectedEdge> getOutgoingEdges(Node node) throws NotNodeGraphException {
        if (!this.nodes.values().contains(node)) {
            throw new NotNodeGraphException(node, this);
        }
        
        return this.outgoingEdgesMap.get(node);
    }  
    
    public Node getNodeByName(String name) throws NotNodeGraphException {
        Node node = this.nodes.get(name);
        if (node == null) {
            throw new NotNodeGraphException(node, this);
        }
        
        return node;
    }
    
}
