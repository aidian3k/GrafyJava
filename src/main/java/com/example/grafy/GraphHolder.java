package com.example.grafy;

public final class GraphHolder {

    private GridGraph graph;
    private final static GraphHolder INSTANCE = new GraphHolder();

    private GraphHolder() {}

    public static GraphHolder getInstance() {
        return INSTANCE;
    }

    public void setGraph(GridGraph graph) {
        this.graph = graph;
    }

    public GridGraph getGraph() {
        return this.graph;
    }
}