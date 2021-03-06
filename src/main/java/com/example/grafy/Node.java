package com.example.grafy;

public class Node implements Comparable<Node> {
    int numNode; //Default package access
    double weight;

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
    public int compareTo(Node o) {
        return o.weight>weight ? 1 : 0;
    }
    @Override
    public boolean equals(Object o){
        return o instanceof Node && (((Node) o).numNode == this.numNode);
    }

}
