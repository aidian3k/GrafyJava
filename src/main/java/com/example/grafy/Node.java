package com.example.grafy;

public class Node implements Comparable<Node> {
        protected int numNode;
        protected double weight;
        Node(){
        }

        Node(int numNode, double distance){
            this.numNode=numNode;
            this.weight=distance;
        }

        void setNumNodeWeight(int numNode, double weight){
            this.numNode=numNode;
            this.weight=weight;
        }

        @Override
        public int compareTo(com.example.grafy.Node o) {
            return o.weight>weight ? 1 : 0;
        }

        @Override
        public boolean equals(Object o){
            return o instanceof Node && (((Node) o).numNode == this.numNode);
        }
}
