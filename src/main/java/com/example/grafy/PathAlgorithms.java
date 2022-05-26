package com.example.grafy;

import java.util.LinkedList;
import java.util.PriorityQueue;

public class PathAlgorithms implements ShortestPath{

    CohesionAlgorithms isCoherent = new CohesionAlgorithms(); // Used composition

    public ShortestPathSolution dijkstra(GridGraph g, int nodeA){

        if(g==null || nodeA>=g.getNodesNum() || !isCoherent.breathFirstSearch(g,nodeA)){
            throw new IllegalArgumentException("SHORTEST_PATH_ALGO_PROBLEM: There is a problem with arguments given to the method!");
        }

        double [] distance=new double[g.getNodesNum()];
        int [] prev=new int[g.getNodesNum()];

        for(int i=0;i<g.getNodesNum();i++){
            distance[i]=Double.POSITIVE_INFINITY;
            prev[i]=-1;
        }

        Node v=new Node(); //Initializing new object, which will be used in loop while
        PriorityQueue<Node> Q=new PriorityQueue<>(g.getNodesNum());
        Q.add(new com.example.grafy.Node(nodeA,0));
        distance[nodeA]=0; //It is not necessary--all distance doubles are default set 0

        while(Q.size()!=0){

            com.example.grafy.Node u=Q.poll();
            LinkedList<Edge> connectionList=g.getConnectionList(u.numNode);
            for(Edge e : connectionList){

                v=new com.example.grafy.Node(e.getNodeTo(),e.getWeight());
                if(distance[v.numNode] > distance[u.numNode] + v.weight){
                    distance[v.numNode]=distance[u.numNode] + v.weight;
                    prev[v.numNode]=u.numNode;
                    Q.add(v);
                }

            }

        }

        return new ShortestPathSolution(nodeA, prev, distance);
    }

    public ShortestPathSolution bellmanFord(GridGraph g, int nodeA){

        if(g==null || nodeA>=g.getNodesNum() || !isCoherent.breathFirstSearch(g,nodeA)){
            throw new IllegalArgumentException("SHORTEST_PATH_ALGO_PROBLEM: There is a problem with arguments given to the method!");
        }

        double [] distance=new double[g.getNodesNum()];
        int [] prev=new int[g.getNodesNum()];

        for(int i=0;i<g.getNodesNum();++i){
            distance[i]=Double.POSITIVE_INFINITY;
            prev[i]=-1;
        }

        distance[nodeA]=0;

        for(int i=1;i<g.getNodesNum();++i){
            for(int j=0;j<g.getNodesNum();++j){
                for(Edge e : g.getConnectionList(j)){
                    int u=e.getNodeFrom();
                    int v=e.getNodeTo();
                    double weight=e.getWeight();
                    if(distance[u] + weight < distance[v]){
                        distance[v]=distance[u] + weight;
                        prev[v]=u;
                    }
                }
            }
        }

        //We also have to check the existence of the negative weight-cycles

        for(int i=0;i<g.getNodesNum();++i){
            for(Edge e : g.getConnectionList(i)){
                int u=e.getNodeFrom();
                int v=e.getNodeTo();
                double weight=e.getWeight();
                if(distance[u] + weight < distance[v]){
                    throw new IllegalArgumentException("ERROR: Given graph contains cycle!");
                }
            }
        }

        return new ShortestPathSolution(nodeA, prev, distance); //Time complexity of the algorithm is O(n^3), the advantage of that algorithm is that it accepts negative weights
    }

    public ShortestPathSolution floydWarshall(GridGraph g, int nodeA){

        if(g==null || nodeA>=g.getNodesNum() || !isCoherent.breathFirstSearch(g,nodeA)){
            throw new IllegalArgumentException("SHORTEST_PATH_ALGO_PROBLEM: There is a problem with arguments given to the method!");
        }

        int numOfNodes=g.getNodesNum();
        double [][] distance=new double[numOfNodes][numOfNodes];
        int [][] path=new int[numOfNodes][numOfNodes];

        for(int i=0;i<numOfNodes;++i){
            for(int j=0;j<numOfNodes;++j){
                distance[i][j]=Double.POSITIVE_INFINITY;
            }
        }

        for(int i=0;i<numOfNodes;++i){
            for(Edge e : g.getConnectionList(i)){
                int nodeTo=e.getNodeTo();
                int nodeFrom=e.getNodeFrom();
                double weight=e.getWeight();
                distance[nodeFrom][nodeTo]=weight;
                path[nodeFrom][nodeTo]=nodeTo;
            }
        }

        for(int i=0;i<numOfNodes;++i){
            distance[i][i]=0;
            path[i][i]=i;
        }

        for(int k=0;k<numOfNodes;++k){
            for(int i=0;i<numOfNodes;++i){
                for(int j=0;j<numOfNodes;++j){
                    if(distance[i][j] > distance[i][k] + distance[k][j]){
                        distance[i][j]=distance[i][k] + distance[k][j];
                        path[i][j]=path[i][k];
                    }
                }
            }
        }

        double [] distanceAnswer=distance[nodeA];

        return new ShortestPathSolution(nodeA, path, distanceAnswer); //Algorithm, which finds paths between all pairs of vertexes. Algorithm also allows us to have negative weights. Time complexity O(n^3).
    }

}
