package com.example.grafy;

import java.io.IOException;
import java.util.LinkedList;

public interface GraphFunctions {

    void addEdgeToList(int index, Edge e); // Default set public

    void saveGraph (String path) throws IOException;

    void readGraph(String path) throws IOException;

    LinkedList<Edge> getConnectionList(int node);

}
