package ex1.src;

import java.io.*;
import java.util.*;

public class WGraph_Algo implements weighted_graph_algorithms {

    private weighted_graph graphWithWeight;

    public WGraph_Algo() {
        this.graphWithWeight = new WGraph_DS();
    }


    @Override
    public void init(weighted_graph g) {
        this.graphWithWeight = g;
    }//O1

    @Override
    public weighted_graph getGraph() {
        return graphWithWeight;
    }//O1

    @Override
    public weighted_graph copy() {
        //Go through the array - add the vertex
        // then the weight of the side between the current vertex and its neighbor
        //At the end connect the edge
        WGraph_DS copyGraph = new WGraph_DS();
        for (node_info node : graphWithWeight.getV()) { //O(NODES*EDGES)
            copyGraph.addNode(node.getKey());
            copyGraph.getNode(node.getKey()).setTag(graphWithWeight.getNode(node.getKey()).getTag());
            copyGraph.getNode(node.getKey()).setInfo(graphWithWeight.getNode(node.getKey()).getInfo());

        }
        for (node_info nodes : graphWithWeight.getV()) {
            for (node_info edges : graphWithWeight.getV(nodes.getKey())) {
                copyGraph.connect(nodes.getKey(), edges.getKey(), graphWithWeight.getEdge(nodes.getKey(), edges.getKey()));
            }
        }
        copyGraph.setMC(graphWithWeight.getMC());
        return copyGraph;

    }

    @Override
    public boolean isConnected() {//Dijkstra Complications
        //
        if (this.graphWithWeight.getV().size() <=1) {
            return true;
        }
        int src = ((node_info) graphWithWeight.getV().toArray()[0]).getKey();
        int dest = ((node_info) graphWithWeight.getV().toArray()[1]).getKey();
        Dijkstra(src,dest);
        /*Once we have obtained the result function
         we will check if there is an infinity weight,
         if there is an infinity weight - the graph is a non-binding graph*/
        for (node_info node: graphWithWeight.getV()){
            if(((WGraph_DS.NodeInfo)node).getTag()==Double.MAX_VALUE){ //
             return false;
            }
        }
          return true;
    }

    @Override
    public double shortestPathDist(int src, int dest) {//Dijkstra Complications
        if (graphWithWeight.getV().size() == 0) {
            return -1;
        }
        if (graphWithWeight.getNode(src) == null || graphWithWeight.getNode(dest) == null) {
            return -1;
        }
        Dijkstra(src, dest);
        /*Once we have obtained the result function
         we will check if there is an infinity weight,
         if there is an infinity weight - the graph is a non-binding graph
         and we will return -1,if there is a path
         return the shortest (the result from the Dijkstra), return it*/

        if (((WGraph_DS.NodeInfo) graphWithWeight.getNode(dest)).getTag() == Double.MAX_VALUE)
            return -1;
        return ((WGraph_DS.NodeInfo) graphWithWeight.getNode(dest)).getTag();
    }
    @Override
    public List<node_info> shortestPath(int src, int dest) {//Dijkstra Complications
        if (graphWithWeight.getNode(src) == null || graphWithWeight.getNode(dest) == null) return null;
        List<node_info> path = new ArrayList<>();
        List<node_info> reversePath = new ArrayList<>();
        Dijkstra(src, dest);
        /*Once we have obtained the result function
         we will check if there is an infinity weight,
         if there is an infinity weight - the graph
         is a non-binding graph and we will return
         there is no list to return , if tere is a path , return it*/
        path.add(graphWithWeight.getNode(dest));
        while (src != dest) {
            if (dest == -1) {
                return null;
            }
            dest = ((WGraph_DS.NodeInfo) graphWithWeight.getNode(dest)).getPred();
            path.add(graphWithWeight.getNode(dest));
        }
        for (int i = path.size() - 1; i >= 0; i--) {
            reversePath.add(path.get(i));
        }
        if (reversePath.size()==0)return null;
        return reversePath;
    }

    /*
    The implementaion of the code is done
     by using a pseudo-code learned in a lecture by Elizabeth,
    pseudo-code is in the presentation.
     */
    public void Dijkstra(int src , int dest) { //O(|E|*Log|V|)

        for (node_info nodes : graphWithWeight.getV()) {
            ((WGraph_DS.NodeInfo) nodes).setTag(Double.MAX_VALUE);
            ((WGraph_DS.NodeInfo) nodes).setPred(-1);
            ((WGraph_DS.NodeInfo) nodes).setVisited(false);
        }
        WGraph_DS.NodeInfo s = (WGraph_DS.NodeInfo) graphWithWeight.getNode(src);
        s.setTag(0);
        PriorityQueue<WGraph_DS.NodeInfo> queue = new PriorityQueue<WGraph_DS.NodeInfo>();
        for (node_info nodes : graphWithWeight.getV()) {
            queue.add((WGraph_DS.NodeInfo) nodes);
        }
        while (!queue.isEmpty()) {
            WGraph_DS.NodeInfo u = queue.poll();
            for (node_info nei : graphWithWeight.getV(u.getKey())) {
                if (!((WGraph_DS.NodeInfo) nei).isVisited()) {
                    double t = ((WGraph_DS.NodeInfo) u).getTag() + graphWithWeight.getEdge(nei.getKey(), u.getKey());
                    if (((WGraph_DS.NodeInfo) nei).getTag() > t) {
                        ((WGraph_DS.NodeInfo) nei).setTag(t);
                        ((WGraph_DS.NodeInfo) nei).setPred(u.getKey());

                        queue.remove(nei);
                        queue.add((WGraph_DS.NodeInfo) nei);
                    }
                }
            }
            u.setVisited(true);
        }
    }

    @Override
    public boolean save(String file) { //O(nodes*neighbor)

        try {
            FileOutputStream fileOut = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(graphWithWeight);
            out.close();
            fileOut.close();
            return true;
        }

        catch(Exception ex)
        {
            return false;
        }
    }

    @Override
    public boolean load(String file) {//O(nodes+edge)
        try
        {
            WGraph_DS newGraph = new WGraph_DS();

            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            newGraph = (WGraph_DS) in.readObject();
            in.close();
            fileIn.close();
            graphWithWeight = newGraph;
            return true;
        }

        catch(Exception e)
        {
           return false;
        }
    }

}
