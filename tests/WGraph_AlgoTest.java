package ex1.tests;

import ex1.src.WGraph_Algo;
import ex1.src.WGraph_DS;
import ex1.src.node_info;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WGraph_AlgoTest {
    @Test
    public void emptyGraph() {
        WGraph_DS graph = new WGraph_DS();
        WGraph_Algo graph2 = new WGraph_Algo();
        graph2.init(graph);
        assertTrue(graph2.isConnected());
        assertEquals(-1, graph2.shortestPathDist(1, 10));
        assertEquals(null, graph2.shortestPath(2, 10));
        assertTrue(graph2.isConnected());

    }

    @Test
    public void copyTest() {
        WGraph_DS graph = new WGraph_DS();
        for (int i = 0; i < 100; i++) {
            graph.addNode(i);
            graph.getNode(i).setInfo("info " + i);
            graph.getNode(i).setTag(i);
        }
        graph.connect(1, 2, 5);
        graph.connect(2, 4, 10);
        graph.connect(2, 5, 15);
        graph.connect(5, 4, 0.5);
        graph.connect(10, 5, 1);
        graph.connect(2, 10, 1);
        graph.connect(9, 10, 3);
        graph.connect(8, 10, 7);
        graph.connect(8, 9, 16.4);
        graph.connect(2, 3, 5);

        assertEquals(100, graph.nodeSize());
        assertEquals(10, graph.edgeSize());

        WGraph_Algo graph2 = new WGraph_Algo();
        graph2.init(graph);
        assertEquals(graph, graph2.getGraph());
        WGraph_DS graphcopy = (WGraph_DS) graph2.copy();
        assertEquals(100, graphcopy.nodeSize());
        assertEquals(10, graphcopy.edgeSize());
        assertEquals(100, graph.nodeSize());
        assertEquals(10, graph.edgeSize());
        graph.removeNode(15);
        graph.removeNode(200);
        graph2.init(graphcopy); //- האם האתחול עושה את השגיאה ? כי הוא אמור לעדכן את גרף 2 כמו גרף 1 אז למה יש מוזר
        assertFalse(graph2.isConnected());
        assertEquals(7.5, graph2.shortestPathDist(1, 4));
        assertEquals(-1, graph2.shortestPathDist(1, 7));
        assertEquals(7, graph2.shortestPathDist(1, 5));
        assertEquals(2, graph2.shortestPathDist(2,5));
        assertEquals(graphcopy,graph2.getGraph());

    }

    @Test
    public void saveAndLoadFunction() {
        WGraph_DS graph = new WGraph_DS();
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);
        graph.addNode(4);
        graph.addNode(5);
        graph.addNode(6);
        graph.addNode(7);
        graph.addNode(8);
        graph.addNode(9);
        graph.addNode(10);

        graph.connect(1, 2, 5);
        graph.connect(2, 3, 5);
        graph.connect(3, 4, 5);
        graph.connect(4, 5, 5);
        graph.connect(5, 6, 5);
        graph.connect(6, 7, 5);
        graph.connect(7, 8, 5);
        graph.connect(8, 9, 5);
        graph.connect(9, 10, 5);
        graph.connect(10, 1, 5);

        WGraph_Algo graph2 = new WGraph_Algo();
        graph2.init(graph);
        graph2.save("graphfile.txt");
        graph2.load("graphfile.txt");

    }


    @Test
    public void weightedGraphConnect() {// test that tests a graph connct after remove adges
        WGraph_DS graph = new WGraph_DS();
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);
        graph.addNode(4);
        graph.addNode(5);

        graph.connect(1, 2, 5);
        graph.connect(2, 3, 5);
        graph.connect(3, 4, 2);
        graph.connect(4, 5, 2);
        graph.connect(5, 1, 10);

        WGraph_Algo graph2 = new WGraph_Algo();
        graph2.init(graph);
        assertTrue(graph2.isConnected());
        assertEquals(12, graph2.shortestPathDist(1, 4));
        graph.removeEdge(1, 2);
        assertEquals(12, graph2.shortestPathDist(1, 4));
        assertEquals(19, graph2.shortestPathDist(1, 2));
        assertEquals(-1, graph2.shortestPathDist(3, 19));
        graph.removeEdge(4, 5);
        graph.removeEdge(2, 3);
        assertEquals(-1, graph2.shortestPathDist(2, 5));
        assertFalse(graph2.isConnected());
    }


    @Test
    public void shorterRouteGraph() {// Checking a short route in a link graph
        WGraph_DS graph = new WGraph_DS();
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);
        graph.addNode(4);

        graph.connect(1, 2, 1);
        graph.connect(1, 3, 2);
        graph.connect(1, 4, 5);
        graph.connect(2, 3, 1);
        graph.connect(2, 4, 2);
        graph.connect(3, 4, 1);

        WGraph_Algo graph2 = new WGraph_Algo();
        graph2.init(graph);

        assertEquals(2, graph2.shortestPathDist(1, 3));
        assertEquals(3, graph2.shortestPathDist(1, 4));
        assertTrue(graph2.isConnected());

        List<node_info> temp = graph2.shortestPath(1, 4);
        String way = "";
        for (node_info node : temp) {
            way=way+ node.getKey() + "";
        }
        assertTrue(way.equals("134") || way.equals("124"));
    }
}
