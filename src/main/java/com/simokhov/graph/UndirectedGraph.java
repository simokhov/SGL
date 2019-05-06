package com.simokhov.graph;

public class UndirectedGraph<T> extends AbstractGraph<T> {

    @Override
    public void addEdge(Edge<T> edge) {
        addEdge(edge.getFrom(), edge.getTo(), true);
        addEdge(edge.getTo(), edge.getFrom(), true);
    }

    @Override
    public void addEdge(Vertex<T> from, Vertex<T> to) {
        super.addEdge(from, to);
        super.addEdge(to, from);
    }

    @Override
    public void addEdge(Vertex<T> from, Vertex<T> to, boolean autoAddVertices) {
        super.addEdge(from, to, autoAddVertices);
        super.addEdge(to, from, autoAddVertices);
    }

}
