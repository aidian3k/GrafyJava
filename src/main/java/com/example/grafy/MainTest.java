package com.example.grafy;

import java.io.*;
import java.util.LinkedList;

public class MainTest {
    public static void main(String ... args) throws IOException {
        GridGraph graph=new GridGraph("/Users/aidian3k/IdeaProjects/Grafy/src/main/java/com/example/grafy/mygraph");
        System.out.println(GraphUtils.dijkstra(graph,11,0));
    }
}
