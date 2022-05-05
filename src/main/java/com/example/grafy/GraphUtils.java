package com.example.grafy;

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

    public static double bellmanFord(GridGraph g, int nodeA, int nodeB){
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

        System.out.print("The shortest path from node "+nodeA+" to node "+nodeB+": "+nodeA);
        printPath(prev,nodeB);
        System.out.println();
        System.out.println("Value of the shortest path: "+distance[nodeB]);
        return distance[nodeB]; //Time complexity of the algorithm is O(n^3), the advantage of that algorithm is that it accepts negative weights
    }

    public static double floydWarshall(GridGraph g, int nodeA, int nodeB){
        int numOfNodes=g.getNodesNum();
        double [][] distance=new double[numOfNodes][numOfNodes];
        int [][] next=new int[numOfNodes][numOfNodes];

        for(int i=0;i<numOfNodes;++i){
            for(int j=0;j<numOfNodes;++j){
                distance[i][j]=Double.POSITIVE_INFINITY;
            }
        }

        for(int i=0;i<numOfNodes;++i){
            distance[i][i]=0;
        }

        for(int i=0;i<numOfNodes;++i){
            for(Edge e : g.getConnectionList(i)){
                int nodeTo=e.getNodeTo();
                int nodeFrom=e.getNodeFrom();
                double weight=e.getWeight();
                distance[nodeFrom][nodeTo]=weight;
            }
        }

        for(int k=0;k<numOfNodes;++k){
            for(int i=0;i<numOfNodes;++i){
                for(int j=0;j<numOfNodes;++j){
                    if(distance[i][j] > distance[i][k] + distance[k][j]){
                        distance[i][j]=distance[i][k] + distance[k][j];
                        next[i][j]=next[i][k];
                    }
                }
            }
        }

        return distance[nodeA][nodeB]; //Algorithm, which finds paths between all pairs of vertexes. Algorithm also allows us to have negative weights. Time complexity O(n^3).
    }

    private static void printPath(int [] prev, int j){
        if(prev[j]==-1) return;
        printPath(prev,prev[j]);
        System.out.print(" -> "+j+" ");
    }

}
