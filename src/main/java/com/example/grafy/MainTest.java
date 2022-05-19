package com.example.grafy;

import java.io.IOException;

public class MainTest {
    public static void main(String ... args) throws IOException {
        GridGraph graph=new GridGraph(10,10,0,1);
        graph.saveGraph("src/main/java/com/example/grafy/mygraph");
        ShortestPathSolution sol1=GraphUtils.floydWarshall(graph,2);
        ShortestPathSolution sol2=GraphUtils.bellmanFord(graph,2);
        ShortestPathSolution sol3=GraphUtils.dijkstra(graph,2);
        System.out.println(sol1.getPathSolution(95) +" " + sol1.getWeightSolution(95));
        System.out.println(sol2.getPathSolution(95) +" " + sol2.getWeightSolution(95));
        System.out.println(sol3.getPathSolution(95) +" " + sol3.getWeightSolution(95));
    }
}
