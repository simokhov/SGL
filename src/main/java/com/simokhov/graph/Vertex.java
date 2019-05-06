package com.simokhov.graph;

import com.sun.istack.internal.NotNull;

import java.util.Objects;

/**
 * Simple Vertex class
 */
public class Vertex<T> {

    // ----- PROPERTIES -----
    private T value;


    // ----- CONSTRUCTOR -----
    public Vertex(T value) {
        this.value = value;
    }


    // ----- GETTERS & SETTERS -----
    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }


    /**
     * Overriding methods .equals, .hashCode for right working with Java Collections
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex<?> vertex = (Vertex<?>) o;
        return Objects.equals(value, vertex.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "value=" + value +
                '}';
    }
}
