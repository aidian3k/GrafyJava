package com.example.grafy;

public class Edge implements EdgeInterface, Comparable<Edge>{
    private final int nodeFrom;
    private final int nodeTo;
    private final double weight;

    public Edge(int nodeFrom, int nodeTo, double weight){
        this.nodeFrom=nodeFrom;
        this.nodeTo=nodeTo;
        this.weight=weight;
    }

    @Override
    public int getNodeFrom(){
        return nodeFrom;
    }

    @Override
    public int getNodeTo(){
        return nodeTo;
    }

    @Override
    public double getWeight(){
        return weight;
    }

    @Override
    public int compareTo(Edge o) {
        return o.getWeight()>weight ? 1 : 0;
    }
}
