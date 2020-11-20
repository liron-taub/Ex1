package ex1.src;

import java.io.Serializable;

public class Edge implements Serializable {

        private node_info src;
        private node_info dest;
        private double weight;
        private String info;
        private double tag;


        public Edge(double weight, node_info source, node_info destination) {
            this.weight = weight;
            this.src =source;
            this.dest =destination;
        }


        public node_info getInNode() {
            return src;
        }

        public node_info getOutNode() {
            return dest;
        }

        public void setInNode(node_info inNode) {
            this.src = inNode;
        }

        public void setOutNode(node_info outNode) {
            this.dest = outNode;
        }

        public void setWeight(double weight) {
            this.weight = weight;
        }

        public double getWeight() {
            return weight;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String s) {
            this.info = s;
        }

        public double getTag() {
            return tag;
        }

        public void setTag(double t) {
            this.tag = t;
        }

    @Override
    public String toString() {
        return "Edge{" +
                "src=" + src +
                ", dest=" + dest +
                ", weight=" + weight +
                ", info='" + info + '\'' +
                ", tag=" + tag +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Edge edge = (Edge) o;

        if (Double.compare(edge.weight, weight) != 0) return false;
        if (Double.compare(edge.tag, tag) != 0) return false;
        if (src != null ? !src.equals(edge.src) : edge.src != null) return false;
        if (dest != null ? !dest.equals(edge.dest) : edge.dest != null) return false;
        return info != null ? info.equals(edge.info) : edge.info == null;
    }


}
