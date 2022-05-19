package com.example.grafy;

import java.util.ArrayList;
import java.util.Collections;

public class ShortestPathSolution {
    public int source;
    public int [] prevArray;
    public double [] weightArray; //Default initialized null
    private final int mode; // Mode is to determine which algorithm was chosen by the user to choose methods
    private int [][] nextArray; // It is needed to determine the path array in FloydWarshall algorithm

    public ShortestPathSolution(int source, int [] prevArray, double [] weightArray){

        if(weightArray.length != prevArray.length || source < 0 || weightArray.length < 1){
            throw new IllegalArgumentException("Illegal argument given to the constructor ShortestPathSolution!");
        }

        this.source=source;
        this.prevArray=prevArray;
        this.weightArray= weightArray;
        this.mode=0;
    }

    public ShortestPathSolution(int source, int [][] nextArray, double [] weightArray){

        if(weightArray.length != nextArray.length || source < 0 || weightArray.length < 1){
            throw new IllegalArgumentException("Illegal argument given to the constructor ShortestPathSolution!");
        }

        this.source=source;
        this.nextArray=nextArray;
        this.weightArray= weightArray;
        this.mode=1;
    }

    public ArrayList<Integer> getPathSolution(int destination) {
        ArrayList<Integer> path = new ArrayList<>();

        if(mode==0) {
            while (prevArray[destination] != -1) {
                path.add(destination);
                destination = prevArray[destination];
            }
            path.add(source);
            Collections.reverse(path);

        }
        else {
            int sourceNode=source;
            path.add(sourceNode);
            while(sourceNode!=destination){
                sourceNode=nextArray[sourceNode][destination];
                path.add(sourceNode);

            }
        }

        return path;
    }

    public double getWeightSolution(int destination){
        if(weightArray==null || destination>weightArray.length){
            throw new IllegalArgumentException("Illegal argument given! Program cannot get Weight!");
        }

        return weightArray[destination];
    }

    public void displayPath(int destination) {
        ArrayList<Integer> path = getPathSolution(destination);

        for(int i=0;i<path.size()-1;++i){
            System.out.print(path.get(i)+" -> ");
        }
        System.out.print(path.get(path.size()-1));
    }

    public void displayWeights(){
        for(int i=0 ; i < weightArray.length ; ++i){
            System.out.println("Node number "+source+" to node number "+i+". Shortest weight: "+getWeightSolution(i));
        }
    }

}