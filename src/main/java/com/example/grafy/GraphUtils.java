package com.example.grafy;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Stack;

public class GraphUtils {

    public static boolean breathFirstSearch(GridGraph g, int node){
        boolean [] visited=new boolean[g.getNodesNum()];
        LinkedList<Integer> queue=new LinkedList<>();
        visited[node]=true;
        queue.add(node);
        while(queue.size()!=0){
            node=queue.pollFirst();
            LinkedList<Edge> current=g.getConnectionList(node);
            for(Edge e : current){
                int currNode=e.getNodeTo();
                if(!visited[currNode]){
                    visited[currNode]=true;
                    queue.add(currNode);
                }
            }
        }
        for(boolean bool : visited){
            if(!bool){
                return false;
            }
        }
        return true;
    }

    public static boolean depthFirstSearch(GridGraph g, int node){
        Stack<Integer> stack=new Stack<>();
        boolean [] visited=new boolean[g.getNodesNum()];
        visited[node]=true;
        stack.push(node);

        while(stack.size()!=0){
            node=stack.pop();
            LinkedList<Edge> current=g.getConnectionList(node);
            for(Edge e : current){
                int currNode=e.getNodeTo();

                if(!visited[currNode]){
                    visited[currNode]=true;
                    stack.push(currNode);
                }

            }
        }

        for(boolean bool : visited){
            if(!bool){
                return false;
            }
        }

        return true; //This algorithm is very similar to the BFS, the difference is that LinkedList is replaced by the Stack
    }

    public static double dijkstra(GridGraph g, int nodeA, int nodeB){
        double [] distance=new double[g.getNodesNum()];
        int [] prev=new int[g.getNodesNum()];

        for(int i=0;i<g.getNodesNum();i++){
            distance[i]=Double.POSITIVE_INFINITY;
            prev[i]=-1;
        }

        Node v=new Node(); //Initializing new object, which will be used in loop while
        PriorityQueue<Node> Q=new PriorityQueue<>(g.getNodesNum());
        Q.add(new Node(nodeA,0));
        distance[nodeA]=0; //It is not necessary--all distance doubles are default set 0

        while(Q.size()!=0){

            Node u=Q.poll();
            LinkedList<Edge> connectionList=g.getConnectionList(u.numNode);
            for(Edge e : connectionList){

                v=new Node(e.getNodeTo(),e.getWeight());
                if(distance[v.numNode] > distance[u.numNode] + v.weight){
                    distance[v.numNode]=distance[u.numNode] + v.weight;
                    prev[v.numNode]=u.numNode;
                    Q.add(v);
                }

            }

        }
        System.out.print("The shortest path from node "+nodeA+" to node "+nodeB+": "+nodeA);
        printPath(prev,nodeB);
        System.out.println();
        System.out.println("Value of the shortest path: "+distance[nodeB]);
        return distance[nodeB];
    }

    private static void printPath(int [] prev, int j){
        if(prev[j]==-1) return;
        printPath(prev,prev[j]);
        System.out.print(" -> "+j+" ");
    }

}
