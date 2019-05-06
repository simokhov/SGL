package com.simokhov.graph;

/**
 * Directed graph
 */
public class DirectedGraph<T> extends AbstractGraph<T> {

    @Override
    public void addEdge(Edge<T> edge) {
        addEdge(edge, true);
    }

}
