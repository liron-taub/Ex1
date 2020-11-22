package ex1.tests;


import ex1.src.WGraph_DS;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class WGraph_DSTest {
    @Test
    public void testTime(){
        long start = new java.util.Date().getTime();
        WGraph_DS graph = new WGraph_DS();
        for (int i = 0; i <=1000000 ; i++) {
            graph.addNode(i);
        }
        for (int i = 0; i <=10 ; i++) {
            for (int j = i; j <1000000 ; j++) {
                graph.connect(i,j,10);
            }
        }
        long end =new java.util.Date().getTime();
        double dt= (end-start)/1000.0;
        System.out.println(dt + "seconds");
        assertTrue(dt < 15);


    }

    @Test
    public void Test1() { // Checks the vertices and edge in a empty graph
        WGraph_DS graph = new WGraph_DS();
        assertEquals(0, graph.nodeSize());
        assertEquals(0, graph.getV().size());
        assertThrows(NullPointerException.class, () -> graph.getNode(10).getKey());
        assertThrows(NullPointerException.class, () -> graph.getNode(100).getKey());
        assertThrows(NullPointerException.class, () -> graph.getNode(1000).getKey());
        assertFalse(graph.hasEdge(1,2));
        assertFalse(graph.hasEdge(134,2365));

    }

    @Test
    public void Test2() {
        WGraph_DS graph = new WGraph_DS();
        for (int i = 0; i < 10000; i++) {
            graph.addNode(i);
            graph.getNode(i).setInfo("info " + i);

        }
        assertEquals(10000, graph.nodeSize());
        assertEquals(10000, graph.getV().size());
        assertEquals(134, graph.getNode(134).getKey());
        assertEquals("info 1278", graph.getNode(1278).getInfo());
        assertEquals(16, graph.removeNode(16).getKey());
        assertEquals(26, graph.removeNode(26).getKey());
        assertEquals(null, graph.removeNode(26));
        assertEquals(null, graph.removeNode(10001));
        assertEquals(21, graph.removeNode(21).getKey());
        assertEquals(5, graph.removeNode(5).getKey());
        assertEquals(9996,graph.nodeSize() );
        assertEquals(-1, graph.getEdge(1,6));
        assertEquals(10004, graph.getMC());
        graph.connect(1,6,10);
        assertEquals(10, graph.getEdge(1,6));

    }

    @Test
    public void edgesTestEmptyGraph() { // Number of edges in an empty hip
        WGraph_DS graph = new WGraph_DS();
        assertFalse(graph.hasEdge(1,2));
        graph.removeEdge(1,5);
        assertEquals(0, graph.nodeSize());
        assertEquals(0, graph.edgeSize());
        assertEquals(-1, graph.getEdge(1,6));
        assertEquals(0, graph.getMC());
    }

}
