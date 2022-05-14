package com.example.grafy;

import java.io.IOException;

public class MainTest {
    public static void main(String ... args) throws IOException {
        GridGraph graph=new GridGraph(3,1,0,1);
        graph.saveGraph("src/main/java/com/example/grafy/mygraph");
        ShortestPathSolution sol1=GraphUtils.dijkstra(graph,0,2);
        sol1.displayPath();
        System.out.println();
        System.out.println(sol1.minimumWeight);
    }
}
