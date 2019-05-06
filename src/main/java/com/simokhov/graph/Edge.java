package com.simokhov.graph;

import com.sun.istack.internal.NotNull;

import java.util.Objects;

/**
 * Simple edge class
 * @param <T> Type of vertex data
 */
public class Edge<T> {

    // ----- PROPERTIES -----
    private final Vertex<T> from;

    private final Vertex<T> to;


    // ----- CONSTRUCTOR -----
    public Edge(Vertex<T> from, Vertex<T> to) {
        if (from.equals(to)) throw new GraphRuntimeException("Source and Destination could not be the same");
        this.from = from;
        this.to = to;
    }


    // ----- GETTERS & SETTERS -----
    public Vertex<T> getFrom() {
        return from;
    }

    public Vertex<T> getTo() {
        return to;
    }

    /**
     * Overriding methods .equals, .hashCode for right working with Java Collections
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge<?> edge = (Edge<?>) o;
        return from.equals(edge.from) &&
                to.equals(edge.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }

    @Override
    public String toString() {
        return "Edge{" +
                "from=" + from +
                ", to=" + to +
                '}';
    }
}
