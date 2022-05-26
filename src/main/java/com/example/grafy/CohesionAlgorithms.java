package com.example.grafy;

import java.util.LinkedList;
import java.util.Stack;

public class CohesionAlgorithms implements Cohesion{
    @Override
    public boolean breathFirstSearch(GridGraph g, int node){

        if(g==null || node>=g.getNodesNum()){
            throw new IllegalArgumentException("COHERENT_ALGO_PROBLEM: There is a problem with arguments given to the method!");
        }

        boolean [] visited=new boolean[g.getNodesNum()];
        LinkedList<Integer> queue=new LinkedList<>();
        visited[node]=true;
        queue.add(node);
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
    @Override
    public boolean depthFirstSearch(GridGraph g, int node){

        if(g==null || node>=g.getNodesNum()){
            throw new IllegalArgumentException("COHERENT_ALGO_PROBLEM: There is a problem with arguments given to the method!");
        }

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

}
