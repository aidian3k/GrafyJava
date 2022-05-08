package com.example.grafy;

import java.io.IOException;

public class MainTest {
    public static void main(String ... args) throws IOException {
        GridGraph graph=new GridGraph(10,10,0,1);
        graph.saveGraph("src/main/java/com/example/grafy/mygraph");
        ShortestPathSolution sol1=GraphUtils.floydWarshall(graph,0,89);
        System.out.print("The shortest path from node "+sol1.source+" to node "+sol1.destination+": ");
        sol1.displayPath();
        System.out.println();
        System.out.println("Value of the shortest path: "+sol1.minimumWeight);



    }
}
