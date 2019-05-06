package com.simokhov.graph;

import java.util.List;

public interface SimpleGraphInterface<T> {

    public void addVertex(Vertex<T> vertex);

    public void addEdge(Edge<T> edge);

    public List<Edge<T>> findPath(Vertex<T> from, Vertex<T> to);

}
