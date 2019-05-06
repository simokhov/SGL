package com.simokhov.graph;

import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class GraphTest {

    @Test
    public void VertexEqualsTest() {
        Assert.assertEquals(new Vertex<String>("TestValue"),new Vertex<String>("TestValue"));
    }

    @Test
    public void VertexNotEqualsTest() {
        Assert.assertNotEquals(new Vertex<String>("TestValue"),new Vertex<String>("TestValue1"));
    }

    @Test
    public void EdgeEqualsTest() {
        Vertex<String> vertex1 = new Vertex<>("TestValue1");
        Vertex<String> vertex2 = new Vertex<>("TestValue2");
        Assert.assertEquals(new Edge<String>(vertex1, vertex2), new Edge<String>(vertex1, vertex2));
    }

    @Test
    public void EdgeNotEqualsTest() {
        Vertex<String> vertex1 = new Vertex<>("TestValue1");
        Vertex<String> vertex2 = new Vertex<>("TestValue2");
        Assert.assertNotEquals(new Edge<String>(vertex1, vertex2), new Edge<String>(vertex2, vertex1));
    }

    @Test(expected = GraphRuntimeException.class)
    public void EdgeEqualsVertexExceptionTest() {
        Vertex<String> vertex1 = new Vertex<>("TestValue1");
        new Edge<String>(vertex1, vertex1);
    }

    @Test(expected = GraphRuntimeException.class)
    public void EdgeEqualsVertexValuesExceptionTest() {
        Vertex<String> vertex1 = new Vertex<>("TestValue1");
        Vertex<String> vertex2 = new Vertex<>("TestValue1");
        new Edge<String>(vertex1, vertex2);
    }

    @Test
    public void GraphAddVertexTest(){
        AbstractGraph<String> stringDirectedGraph = new DirectedGraph<>();
        Vertex<String> vertex1 = new Vertex<>("TestValue1");
        stringDirectedGraph.addVertex(vertex1);
        Assert.assertTrue(stringDirectedGraph.hasVertex(vertex1));
    }

    @Test(expected = GraphRuntimeException.class)
    public void GraphAddVertexExceptionTest() {
        AbstractGraph<String> stringDirectedGraph = new DirectedGraph<>();
        Vertex<String> vertex1 = new Vertex<>("TestValue1");
        stringDirectedGraph.addVertex(vertex1);
        stringDirectedGraph.addVertex(vertex1);
    }

    @Test
    public void GraphDirectedAddEdgeTest() {
        AbstractGraph<String> stringDirectedGraph = new DirectedGraph<>();
        Edge<String> edge = new Edge<>(new Vertex<>("TestValue1"), new Vertex<>("TestValue2"));
        stringDirectedGraph.addEdge(edge);

        Map<Vertex<String>, Set<Edge<String>>> edgeMap = stringDirectedGraph.getEdgeMap();
        Assert.assertTrue(
                edgeMap.containsKey(edge.getFrom())
                        && edgeMap.get(edge.getFrom()).contains(edge)
        );
    }

    @Test
    public void GraphUndirectedAddEdgeTest() {
        AbstractGraph<String> stringUndirectedGraph = new UndirectedGraph<>();
        Edge<String> edge = new Edge<>(new Vertex<>("TestValue1"), new Vertex<>("TestValue2"));
        stringUndirectedGraph.addEdge(edge);

        Map<Vertex<String>, Set<Edge<String>>> edgeMap = stringUndirectedGraph.getEdgeMap();
        Assert.assertTrue(
                edgeMap.containsKey(edge.getFrom())
                        && edgeMap.containsKey(edge.getTo())
                        && edgeMap.get(edge.getFrom()).contains(new Edge<>(edge.getFrom(), edge.getTo()))
                        && edgeMap.get(edge.getTo()).contains(new Edge<>(edge.getTo(), edge.getFrom()))
        );
    }

    @Test
    public void GraphAddEdgeVertexAutoAddTest() {
        AbstractGraph<String> stringDirectedGraph = new DirectedGraph<>();
        Vertex<String> vertex1 = new Vertex<>("TestValue1");
        Vertex<String> vertex2 = new Vertex<>("TestValue2");
        Edge<String> edge = new Edge<>(vertex1, vertex2);
        stringDirectedGraph.addEdge(edge, true);
        Assert.assertTrue(stringDirectedGraph.hasVertex(vertex1));
        Assert.assertTrue(stringDirectedGraph.hasVertex(vertex2));
    }

    @Test(expected = GraphRuntimeException.class)
    public void GraphAddEdgeVertexAutoAddExceptionTest() {
        AbstractGraph<String> stringDirectedGraph = new DirectedGraph<>();
        Vertex<String> vertex1 = new Vertex<>("TestValue1");
        Vertex<String> vertex2 = new Vertex<>("TestValue2");
        Edge<String> edge = new Edge<>(vertex1, vertex2);
        stringDirectedGraph.addEdge(edge, false);
    }

    @Test(expected = GraphRuntimeException.class)
    public void GraphFindPathMissingVerticesTest() {
        AbstractGraph<String> stringDirectedGraph = new DirectedGraph<>();
        stringDirectedGraph.findPath(new Vertex<String>("Not existing vertex 1"), new Vertex<String>("Not existing vertex 2"));
    }

    @Test
    public void DirectedGraphFindPathTest(){
        AbstractGraph<String> stringDirectedGraph = new DirectedGraph<>();
        stringDirectedGraph.addEdge(new Vertex<String>("V1"), new Vertex<String>("V2"));

        // Direct search. 1 edge in result
        LinkedList<Edge<String>> path1 = stringDirectedGraph.findPath(new Vertex<String>("V1"), new Vertex<String>("V2"));
        Assert.assertTrue(!path1.isEmpty() && path1.size() ==1 && path1.getFirst().equals(new Edge<String>(new Vertex<String>("V1"), new Vertex<String>("V2"))));

        // Backward search. No result
        LinkedList<Edge<String>> path2 = stringDirectedGraph.findPath(new Vertex<String>("V2"), new Vertex<String>("V1"));
        Assert.assertTrue(path2.isEmpty());
    }

    @Test
    public void UndirectedGraphFindPathTest(){
        AbstractGraph<String> stringUndirectedGraph = new UndirectedGraph<>();
        stringUndirectedGraph.addEdge(new Vertex<String>("V1"), new Vertex<String>("V2"));

        // Direct search. 1 edge in result
        LinkedList<Edge<String>> path1 = stringUndirectedGraph.findPath(new Vertex<String>("V1"), new Vertex<String>("V2"));
        Assert.assertTrue(!path1.isEmpty() && path1.size() ==1 && path1.getFirst().equals(new Edge<String>(new Vertex<String>("V1"), new Vertex<String>("V2"))));

        // Backward search. 1 edge in result
        LinkedList<Edge<String>> path2 = stringUndirectedGraph.findPath(new Vertex<String>("V2"), new Vertex<String>("V1"));
        Assert.assertTrue(!path2.isEmpty() && path2.size() ==1 && path2.getFirst().equals(new Edge<String>(new Vertex<String>("V2"), new Vertex<String>("V1"))));
    }

    @Test
    public void DirectedGraphNonReachableVertexTest() {
        AbstractGraph<String> stringDirectedGraph = new DirectedGraph<>();
        stringDirectedGraph.addVertex(new Vertex<String>("Standalone"));
        stringDirectedGraph.addEdge(new Vertex<String>("V1"), new Vertex<String>("V2"));

        LinkedList<Edge<String>> path = stringDirectedGraph.findPath(new Vertex<>("V1"), new Vertex<>("Standalone"));
        Assert.assertTrue(path.isEmpty());
    }

    @Test
    public void UndirectedGraphNonReachableVertexTest() {
        AbstractGraph<String> stringUndirectedGraph = new UndirectedGraph<>();
        stringUndirectedGraph.addVertex(new Vertex<String>("Standalone"));
        stringUndirectedGraph.addEdge(new Vertex<String>("V1"), new Vertex<String>("V2"));

        LinkedList<Edge<String>> path = stringUndirectedGraph.findPath(new Vertex<>("V1"), new Vertex<>("Standalone"));
        Assert.assertTrue(path.isEmpty());
    }

    @Test
    public void DirectedGraphCircuitClosedTest() {
        AbstractGraph<String> stringDirectedGraph = new DirectedGraph<>();
        stringDirectedGraph.addEdge(new Vertex<String>("V1"), new Vertex<String>("V2"));
        stringDirectedGraph.addEdge(new Vertex<String>("V2"), new Vertex<String>("V3"));
        stringDirectedGraph.addEdge(new Vertex<String>("V3"), new Vertex<String>("V1")); // Circuit closed graph
        stringDirectedGraph.addEdge(new Vertex<String>("V3"), new Vertex<String>("V4"));

        LinkedList<Edge<String>> path = stringDirectedGraph.findPath(new Vertex<String>("V1"), new Vertex<String>("V4"));

        LinkedList<Edge<String>> result = new LinkedList<>();
        result.add(new Edge<>(new Vertex<>("V1"), new Vertex<>("V2")));
        result.add(new Edge<>(new Vertex<>("V2"), new Vertex<>("V3")));
        result.add(new Edge<>(new Vertex<>("V3"), new Vertex<>("V4")));

        Assert.assertTrue(
                !path.isEmpty()
                        && path.size() == 3
                        && path.equals(result)
        );
    }


}
