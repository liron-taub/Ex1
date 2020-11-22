package ex1.src;


import java.io.Serializable;
import java.util.*;

public class WGraph_DS implements weighted_graph, Serializable {

    HashMap<Integer, node_info> nodeInGraph;
    HashMap<node_info, HashMap<node_info,Edge>> edgeInGraph;
    int edgeSize;
    int modeCount;

    public WGraph_DS() {
        nodeInGraph = new HashMap<>();
        edgeInGraph = new HashMap<>();
        edgeSize = 0;
        modeCount = 0;
    }

    @Override
    public node_info getNode(int key) {
        return nodeInGraph.get(key);
    }//O1

    @Override
    /*Necessary condition check,
     whether the vertices at all in the graph,
     checks whether the use that the vertex contains exists,
     if there is a checker whether it is connected
     to another vertex that contains weight and whether there
      is a side between the previous two
      O(1)
     */
    public boolean hasEdge(int node1, int node2) {
        if (!nodeInGraph.containsKey(node1) || !nodeInGraph.containsKey(node2)){
            return false;
        }
        else if(nodeInGraph.containsKey(node1) && nodeInGraph.containsKey(node2)) {
            if (this.edgeInGraph.get(this.nodeInGraph.get(node1)) != null) {
                return this.edgeInGraph.get(this.nodeInGraph.get(node1)).containsKey(getNode(node2));
            }
        }
        return false;
    }

    @Override
    /*
    If there is no return side -1, if there is a edge we turn to the info
    of the edge we will select the first vertex and ask for its
    weight with the vertex to which it is connected
    o(1)
     */
    public double getEdge(int node1, int node2) {
      if (!hasEdge(node1,node2)){
          return -1;
      }
      else {
            return this.edgeInGraph.get(getNode(node1)).get(getNode(node2)).getWeight();
      }
    }

    @Override
    /*
    We will check if the vertex we want to add is the HASHMAP,
    if we do not insert the vertex into it HASHMAP.
    O(1).
     */
    public void addNode(int key) {//O1
        if (nodeInGraph.containsKey(key)) {
        }
        else {
            node_info newNode = new NodeInfo(key);
            nodeInGraph.put(key, newNode);
            modeCount++;

        }
    }

    @Override
    /*
    First we will check that there is no negative weight and it is not a self edge,
    if there is already an edge that we want to add we will just update the weight
    in case the weight is different. If I want to add an edge that really did not
    exist we will initialize the HASHMAP and insert the new side in a two-way way.
    O(1)
     */
    public void connect(int node1, int node2, double w) {
        if(getNode(node1) == null || getNode(node2) == null) {
            return;
        }
        if (w < 0) {
            System.err.println("Weight of edge must be positive");
            return;
        }
        if (node1 == node2) {
            System.err.println(" It is not possible to add a self edge ");
            return;
        }
        if (hasEdge(node1, node2)) {
            if(getEdge(node1, node2) != w) modeCount++;
            this.edgeInGraph.get(nodeInGraph.get(node1)).get(nodeInGraph.get(node2)).setWeight(w); 
            return;
        }
        modeCount++;
        Edge newEdge = new Edge(w, nodeInGraph.get(node1), nodeInGraph.get(node2));
        if (this.edgeInGraph.get(nodeInGraph.get(node1)) == null) {
            edgeInGraph.put(nodeInGraph.get(node1), new HashMap<>());
        }
        if (this.edgeInGraph.get(nodeInGraph.get(node2)) == null) {
            edgeInGraph.put(nodeInGraph.get(node2), new HashMap<>());
        }
        this.edgeInGraph.get(nodeInGraph.get(node1)).put(nodeInGraph.get(node2), newEdge);
        this.edgeInGraph.get(nodeInGraph.get(node2)).put(nodeInGraph.get(node1), newEdge);

        edgeSize++;
    }


    @Override
    public Collection<node_info> getV() {//O1
        return nodeInGraph.values();
    }

    @Override
    /*
    If the list is empty returns the empty list, and if not empty returns the entire Key list.
    O(1)
     */
    public Collection<node_info> getV(int node_id) {
        if (edgeInGraph.get(getNode(node_id)) == null){
            return new ArrayList<node_info>();
        }
        else {
            return edgeInGraph.get(getNode(node_id)).keySet();
        }
    }

    @Override
    /*
    If the vertex we want to delete has neighbors, we will go
    over the list of neighbors of the specific vertex we want
    to delete and delete the vertex from the list of neighbors.
    O(neighbor) */
    public node_info removeNode(int key) {
        if(!this.nodeInGraph.containsKey(key)){
            return null;
        }
        if(edgeInGraph.get(getNode(key)) != null) {
            for (node_info neighbor : edgeInGraph.get(getNode(key)).keySet()) {
                edgeInGraph.get(neighbor).remove(getNode(key));
                edgeSize--;
            }
            edgeInGraph.remove(getNode(key));
        }
        modeCount++;
        return nodeInGraph.remove(key);
    }

    @Override
    /*Once you delete a side is deleted in a two-way way,
     from each vertex we delete the link to the side - we
     delete the second vertex from its list of neighbors
     O(1)
    */
    public void removeEdge(int node1, int node2) {
        if(!hasEdge(node1,node2)){
            System.err.println("The operation cannot be performed");
            return;
        }
        else {
            edgeInGraph.get(getNode(node1)).remove(getNode(node2));
            edgeInGraph.get(getNode(node2)).remove(getNode(node1));
            edgeSize--;
            modeCount++;
        }
    }

    @Override
    public int nodeSize() {//O1
        return nodeInGraph.size();
    }

    @Override
    public int edgeSize() {//O1
        return edgeSize;
    }

    @Override
    public int getMC() {//O1
        return modeCount;
    }

    public void setMC (int a){//01

        this.modeCount=a;
    }

    @Override
    public String toString() {//O1
        return "WGraph_DS{" +
                "nodeInGraph=" + nodeInGraph +
                ", edgeInGraph=" + edgeInGraph +
                ", edgeSize=" + edgeSize +
                ", modeCount=" + modeCount +
                '}';
    }

    @Override
    public boolean equals(Object o) {//O1
        //Designed to compare the content itself.
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WGraph_DS wGraph_ds = (WGraph_DS) o;

        if (edgeSize != wGraph_ds.edgeSize) return false;
        if (modeCount != wGraph_ds.modeCount) return false;
        if (nodeInGraph != null ? !nodeInGraph.equals(wGraph_ds.nodeInGraph) : wGraph_ds.nodeInGraph != null)
            return false;
        return edgeInGraph != null ? edgeInGraph.equals(wGraph_ds.edgeInGraph) : wGraph_ds.edgeInGraph == null;
    }

    @Override
    public int hashCode() {//O1
        int result = nodeInGraph != null ? nodeInGraph.hashCode() : 0;
        result = 31 * result + (edgeInGraph != null ? edgeInGraph.hashCode() : 0);
        result = 31 * result + edgeSize;
        result = 31 * result + modeCount;
        return result;
    }

    public class NodeInfo implements node_info, Comparable<NodeInfo>, Serializable{

        private final int Key;
        private int pred;
        private String info;
        private double tag;
        private boolean visited;


        //O1
        public NodeInfo(int key) {
            this.Key = key;
        }

        /***the function get the pred value
         * @return Integer of pred key.
         */
        public int getPred() {//O1
            return pred;
        }

        /**the function set the value of pred
         * @param pred - the key to node pred
         */
        //O1
        public void setPred(int pred) {
            this.pred = pred;
        }

        /**
         * the function checker if the node was already visited
         * @return true.
         */
        public boolean isVisited() {//O1
            return visited;
        }


        /**
         * the function set the new value so we can tell if the node was already visited
         * @return true.
         */
        public void setVisited(boolean visited) {//O1
            this.visited = visited;
        }


        @Override
        //O1
        public int getKey() {
            return Key;
        }

        @Override
        //O1
        public String getInfo() {
            return info;
        }

        @Override
        //O1
        public void setInfo(String s) {
            this.info=s;
        }

        @Override
        //O1
        public double getTag() {
            return tag;
        }

        @Override
        //O1
        public void setTag(double t) {
            tag = t;
        }
        // O(1)
        @Override
        public int compareTo(NodeInfo o) {
            int ans=0;
            if( this.tag>o.tag){
                ans=1;
            }
            else{
                if ((this.tag<o.tag)){
                    ans=-1;
                }
            }
            return ans;
        }

        @Override
        //O1
        public String toString() {
            return "NodeInfo{" +
                    "Key=" + Key +
                    ", pred=" + pred +
                    ", info='" + info + '\'' +
                    ", tag=" + tag +
                    ", visited=" + visited +
                    '}';
        }

        @Override
        //O1
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            NodeInfo nodeInfo = (NodeInfo) o;

            if (Key != nodeInfo.Key) return false;
            if (pred != nodeInfo.pred) return false;
            if (Double.compare(nodeInfo.tag, tag) != 0) return false;
            if (visited != nodeInfo.visited) return false;
            return info != null ? info.equals(nodeInfo.info) : nodeInfo.info == null;
        }

        @Override
        //O1
        public int hashCode() {
            return Key;
        }
    }

}
