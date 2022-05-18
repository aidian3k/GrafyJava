package com.example.grafy;

public class Edge {
    private final int nodeFrom;
    private final int nodeTo;
    private final double weight;

    public Edge(int nodeFrom, int nodeTo, double weight){
        this.nodeFrom=nodeFrom;
        this.nodeTo=nodeTo;
        this.weight=weight;
    }

    public int getNodeFrom(){
        return nodeFrom;
    }

    public int getNodeTo(){
        return nodeTo;
    }

    public double getWeight(){
        return weight;
    }

}
