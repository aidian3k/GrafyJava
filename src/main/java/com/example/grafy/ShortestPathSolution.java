package com.example.grafy;

import java.util.ArrayList;

public class ShortestPathSolution {
    public int source;
    public int destination;
    public double minimumWeight;
    public ArrayList<Integer> path=new ArrayList<>(); //Can be done better, but I do not have any idea

    public ShortestPathSolution(int source, int destination, int [] prevArray, double [] weightArray){

        if(weightArray.length != prevArray.length || source < 0 || destination < 0 || weightArray.length < 1){
            throw new IllegalArgumentException("Illegal argument given to the constructor ShortestPathSolution!");
        }

        this.source=source;
        this.destination=destination;
        this.minimumWeight=weightArray[destination];

        path.add(source);
        getPathSolution(prevArray,destination); //Method to track path

    }

    public ShortestPathSolution(int source, int destination, double [][] weightArray, int [][] nextArray){
        if(weightArray.length != nextArray.length || source < 0 || destination < 0 || weightArray.length < 1){
            throw new IllegalArgumentException("Illegal argument given to the constructor ShortestPathSolution!");
        }

        this.source=source;
        this.destination=destination;
        this.minimumWeight=weightArray[source][destination];

        path.add(source);
        getPathSolution(source, destination, nextArray); //Method to track path
    }
    private void getPathSolution(int [] prevArray, int destination){
        if(prevArray[destination]==-1) return;
        getPathSolution(prevArray,prevArray[destination]);
        path.add(destination);
    }

    private void getPathSolution(int source, int destination, int [][] nextArray){
        if(nextArray[source]==null){
            return;
        }

        while(source!=destination){
            source=nextArray[source][destination];
            path.add(source);
        }
    }

    public void displayPath() {
        for(int i=0;i<path.size()-1;++i){
            System.out.print(path.get(i)+" -> ");
        }
        System.out.print(path.get(path.size()-1));
    }

}