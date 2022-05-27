package com.example.grafy;

public interface ShortestPath {

    ShortestPathSolution dijkstra(GridGraph g, int nodeA);

    ShortestPathSolution bellmanFord(GridGraph g, int nodeA);

    ShortestPathSolution floydWarshall(GridGraph g, int nodeA);

}
