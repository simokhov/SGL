package com.simokhov.graph;

import java.util.*;

abstract class AbstractGraph<T> implements SimpleGraphInterface<T>{

    // ----- PROPERTIES -----

    // Vertices storage
    // Using HashMap because order is not important.
    // Best search performance
    private HashSet<Vertex<T>> vertices = new HashSet<>();

    // Edges map
    // Key - vertex, values - vertex edges
    private Map<Vertex<T>, Set<Edge<T>>> edgeMap = new HashMap<>();


    // ----- GETTERS -----

    public HashSet<Vertex<T>> getVertices() {
        return vertices;
    }

    public Map<Vertex<T>, Set<Edge<T>>> getEdgeMap() {
        return edgeMap;
    }


    // ----- METHODS -----

    /**
     * Adds vertex to storage.
     * Throws RuntimeException if storage contains vertex
     * @param vertex Vertex to add.
     * @throws GraphRuntimeException
     */
    public void addVertex(Vertex<T> vertex) {
        if (hasVertex(vertex)) {
            throw new GraphRuntimeException("Vertex already added");
        }
        vertices.add(vertex);
    }

    /**
     * Checks storage contains vertex
     * @param vertex
     * @return
     */
    public boolean hasVertex(Vertex<T> vertex) {
        return vertices.contains(vertex);
    }


    /**
     * Add edge from source and destination.
     * Default: autoAddVertices Enabled
     * @param from
     * @param to
     */
    public void addEdge(Vertex<T> from, Vertex<T> to) {
        addEdge(from, to, true);
    }
    /**
     * Add edge from source and destination
     * @param from
     * @param to
     */
    public void addEdge(Vertex<T> from, Vertex<T> to, boolean autoAddVertices) {
        addEdge(new Edge<>(from, to), autoAddVertices);
    }

    /**
     * Abstract method. Different implementations for directed and undirected graph
     * @param edge
     */
    @Override
    public abstract void addEdge(Edge<T> edge);

    /**
     * Adds edge to storage. And update map of connections
     * If "autoAddVertices" is true - adds vertex vo storage.
     * @param edge
     */
    public void addEdge(Edge<T> edge, boolean autoAddVertices){

        // Checking vertices
        checkAndAddVertex(edge.getFrom(), autoAddVertices);
        checkAndAddVertex(edge.getTo(), autoAddVertices);

        // update map
        if (!edgeMap.containsKey(edge.getFrom())) {
            edgeMap.put(edge.getFrom(), new HashSet<>());
        }
        edgeMap.get(edge.getFrom()).add(edge);
    }

    /**
     * Checks if storage contains vertex.
     * If "autoAddVertices" is true - adds vertex vo storage.
     * @param vertex Typed vertex
     * @param autoAddVertices
     */
    private void checkAndAddVertex(Vertex<T> vertex, boolean autoAddVertices) {
        if (!vertices.contains(vertex) && autoAddVertices) {
            addVertex(vertex);
        } else if (!vertices.contains(vertex) && !autoAddVertices) {
            throw new GraphRuntimeException("Vertex does not exist");
        }
    }

    /**
     * Find path between two vertices. By default used Depth-first search (DFS) algorithm.
     * @param from
     * @param to
     * @return List of edges. Using LinkedList, because order is important
     */
    public LinkedList<Edge<T>> findPath(Vertex<T> from, Vertex<T> to){
        if (!hasVertex(from) || !hasVertex(to)){
            throw new GraphRuntimeException("Source or destination incorrect");
        }
        return dfs(from, to, new HashSet<>());
    }

    /**
     * Simple DFS implementation
     * @param from
     * @param to
     * @param visited
     * @return List of edges
     */
    private LinkedList<Edge<T>> dfs(Vertex<T> from, Vertex<T> to, Set<Vertex<T>> visited) {

        // Variable for current result
        LinkedList<Edge<T>> result = new LinkedList<>();

        // First of all add current vertex to visited-set
        visited.add(from);

        //Getting vertex edges
        Set<Edge<T>> vertexEgdes = getVertexEgdes(from);

        // If there are no connections - return empty list
        if (vertexEgdes == null){
            return result;
        }

        // If vertex has edges iterate them
        for (Edge<T> edge : vertexEgdes) {

            // If destination reached - add edge to result and return
            if (edge.getTo().equals(to)) {
                result.add(edge);
                return result;
            } else
                // check visited list and avoid cycling
                if (!visited.contains(edge.getTo())){

                    // Recursively search
                    LinkedList<Edge<T>> dfs = dfs(edge.getTo(), to, visited);

                    // If reached destination - return result
                    if (dfs.size() > 0){
                        result.add(edge);
                        result.addAll(dfs);
                        return result;
                    }
            }
        }

        //Return empty list if there is no any path
        return result;
    }


    private Set<Edge<T>> getVertexEgdes(Vertex<T> vertex) {
        Set<Edge<T>> result = new HashSet<>();
        if (edgeMap.containsKey(vertex)) {
            result.addAll(edgeMap.get(vertex));
        }
        return result;
    }


}
