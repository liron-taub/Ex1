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
    public boolean hasEdge(int node1, int node2) {//O(1)
        if (!nodeInGraph.containsKey(node1) || !nodeInGraph.containsKey(node2)){
            return false;
        }
        else if(nodeInGraph.containsKey(node1) && nodeInGraph.containsKey(node2)) { // בדיקה האם הקודקודים בכלל בגרף בשביל שנוכל להמשיך
            if (this.edgeInGraph.get(this.nodeInGraph.get(node1)) != null) { // בדיקה שהרשימה של הקודקוד היא נל
                return this.edgeInGraph.get(this.nodeInGraph.get(node1)).containsKey(getNode(node2));// שואלת את הקודקוד הראשון עם בחובר אליו בסוף הקודקוד השני כלומר האם קיים צלע
            }
        }
        return false;
    }

    @Override
    public double getEdge(int node1, int node2) {//O(1)
      if (!hasEdge(node1,node2)){ // בודק אם אין צלע בין שני קודקודים - תנאי הכרחי
          return -1;
      }
      else {// בדיקה האם קיים צלע בין שני הקודקודים
            return this.edgeInGraph.get(getNode(node1)).get(getNode(node2)).getWeight();// פנייה לאש של הצלעות בוחרת את הקודקוד הראשון ומבקשת את המשקל שלו עם הקודקוד שאליו הוא מחובר
      }
    }

    @Override
    public void addNode(int key) {//O1
        if (nodeInGraph.containsKey(key)) { //בודקת האם הקודקוד שאותו רוצים להוסיף אם הוא קיים או לא
            return;
        } else {
            node_info newNode = new NodeInfo(key); //עם ערך- יצירת קודקוד חדש
            nodeInGraph.put(key, newNode);
            modeCount++;

        }
    }

    @Override
    public void connect(int node1, int node2, double w) {//O1
        if(getNode(node1) == null || getNode(node2) == null) {
            return;
        }
        if (w < 0) {
            System.err.println("Weight of edge must be positive");
            return;
        }
        if (node1 == node2) {
            System.err.println(" It is not possible to add a self edge "); // להחליף ללא קיים צלע עצמית
            return;
        }
        if (hasEdge(node1, node2)) { // בודקת אם  קיים צלע
            if(getEdge(node1, node2) != w) modeCount++;
            this.edgeInGraph.get(nodeInGraph.get(node1)).get(nodeInGraph.get(node2)).setWeight(w); // לעדכן את המשקל
            return;
        }
        modeCount++;
        Edge newEdge = new Edge(w, nodeInGraph.get(node1), nodeInGraph.get(node2)); // צלע חדשה
        if (this.edgeInGraph.get(nodeInGraph.get(node1)) == null) { // אתחול לאשמאפים
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
    public Collection<node_info> getV(int node_id) {//O1
        if (edgeInGraph.get(getNode(node_id)) == null){ //בודק האם הרשימה ריקה
            return new ArrayList<node_info>();
        }
        else {
            return edgeInGraph.get(getNode(node_id)).keySet();// פונקציה שמחזירה את כל המפתחות
        }
    }

    @Override
    public node_info removeNode(int key) {//O(neighbor)
        if(!this.nodeInGraph.containsKey(key)){
            return null;
        }
        //// מקבבל את האש מאפ של קי שיש לו את כל השכנים והצלעות
        if(edgeInGraph.get(getNode(key)) != null) { // כלומר לקודקוד יש שכנים
            for (node_info neighbor : edgeInGraph.get(getNode(key)).keySet()) {// עובר על כל קודוק דברשימת השכנים
              //אני עוברת על רשימת השכנים של השכן הספציפי של קי
                edgeInGraph.get(neighbor).remove(getNode(key)); // אני מוחקת את עצמי מהרשימת שכנים שלו, כלומר קי לא יהיה שמה
                edgeSize--;
            }
            edgeInGraph.remove(getNode(key));
        }
        modeCount++;
        return nodeInGraph.remove(key); // מוחקת את הקודקוד עצמו
    }

    @Override
    public void removeEdge(int node1, int node2) {//O1
        if(!hasEdge(node1,node2)){
            System.err.println("The operation cannot be performed");
            return;
        }
        else {
            edgeInGraph.get(getNode(node1)).remove(getNode(node2)); // אני מחזיקה את האש בעצם ואז על ידי השימוש בפונקצית רמוב אני מוחקת את קודוקד השני מרשימת השכנים של הקודוקד הראשון ובגלל זה נמחק הצלע
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
        //מיועד כדי להשוות מה באמת יש בקובץ כדי שבהשוואה תהיה השוואה בין המלל עצמו ולא הכתובות בזיכרון
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
        //O1
        public int getPred() {
            return pred;
        }
        //O1
        public void setPred(int pred) {
            this.pred = pred;
        }
        //O1
        public boolean isVisited() {
            return visited;
        }
        //O1
        public void setVisited(boolean visited) {
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
        // //O1 -מימוש של אליזבט
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
