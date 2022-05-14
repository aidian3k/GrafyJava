package com.example.grafy;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

public class GridGraph extends BasicGraphFunctions {
    private static final Random rnd=new Random();

    public GridGraph( int rowsNum, int colNum, double minWeight, double maxWeight){
        if(rowsNum<=0 || colNum<=0){
            throw new IllegalArgumentException("Illegal argument given! The program cannot generate graph with negative rows or columns");
        }

        if(minWeight>maxWeight || minWeight<0 || maxWeight<0){
            throw new IllegalArgumentException("WEIGHT_ERROR: The weights given by the user are illegal!");
        }

        this.colNum=colNum;
        this.rowsNum=rowsNum;
        this.minWeight=minWeight;
        this.maxWeight=maxWeight;

        for (int c = 0; c < colNum; c++) {
            for (int r = 0; r < rowsNum; r++) {
                int num = c * rowsNum + r;
                graph.add(new LinkedList<>()); //Initializing memory for the graph representation
            }
        }

        int i=0;

        if(colNum==1){
            addEdgeToList(i, new Edge(i, i + 1, rnd.nextDouble(minWeight, maxWeight)));

            ++i;

            for(; i < rowsNum - 1 ; i++ ){
                addEdgeToList(i, new Edge(i, i + 1, rnd.nextDouble(minWeight, maxWeight)));
                addEdgeToList(i, new Edge(i, i - 1, rnd.nextDouble(minWeight, maxWeight)));
            }

            addEdgeToList(i, new Edge(i, i - 1, rnd.nextDouble(minWeight, maxWeight)));

        }
        else {
            if (colNum > 1) {
                addEdgeToList(i, new Edge(i, i + 1, rnd.nextDouble(minWeight, maxWeight)));
            }

            if (rowsNum > 1) {
                addEdgeToList(i, new Edge(i, i + colNum, rnd.nextDouble(minWeight, maxWeight)));
            }

            i++;

            if (colNum > 2) {
                for (; i < colNum - 1; i++) {
                    addEdgeToList(i, new Edge(i, i - 1, rnd.nextDouble(minWeight, maxWeight)));
                    addEdgeToList(i, new Edge(i, i + 1, rnd.nextDouble(minWeight, maxWeight)));
                    if (rowsNum > 1) {
                        addEdgeToList(i, new Edge(i, i + colNum, rnd.nextDouble(minWeight, maxWeight)));
                    }
                }
            }

            if (colNum > 1) addEdgeToList(i, new Edge(i, i - 1, rnd.nextDouble(minWeight, maxWeight)));
            if (rowsNum > 1) addEdgeToList(i, new Edge(i, i - 1, rnd.nextDouble(minWeight, maxWeight)));

            i++;

            for (; i < (rowsNum - 1) * colNum; i++) {

                if (i % colNum == 0) {
                    addEdgeToList(i, new Edge(i, i - colNum, rnd.nextDouble(minWeight, maxWeight)));
                    addEdgeToList(i, new Edge(i, i + 1, rnd.nextDouble(minWeight, maxWeight)));
                    addEdgeToList(i, new Edge(i, i + colNum, rnd.nextDouble(minWeight, maxWeight)));
                } else if (i % colNum == colNum - 1) {
                    addEdgeToList(i, new Edge(i, i - colNum, rnd.nextDouble(minWeight, maxWeight)));
                    addEdgeToList(i, new Edge(i, i - 1, rnd.nextDouble(minWeight, maxWeight)));
                    addEdgeToList(i, new Edge(i, i + colNum, rnd.nextDouble(minWeight, maxWeight)));
                } else {
                    addEdgeToList(i, new Edge(i, i - colNum, rnd.nextDouble(minWeight, maxWeight)));
                    addEdgeToList(i, new Edge(i, i - 1, rnd.nextDouble(minWeight, maxWeight)));
                    addEdgeToList(i, new Edge(i, i + colNum, rnd.nextDouble(minWeight, maxWeight)));
                    addEdgeToList(i, new Edge(i, i + 1, rnd.nextDouble(minWeight, maxWeight)));
                }

            }

            if (rowsNum > 1 && i == (rowsNum - 1) * colNum) {
                addEdgeToList(i, new Edge(i, i - colNum, rnd.nextDouble(minWeight, maxWeight)));
            }

            if (rowsNum > 1 && i == (rowsNum - 1) * colNum) {
                addEdgeToList(i, new Edge(i, i + 1, rnd.nextDouble(minWeight, maxWeight)));
            }

            i++;

            for (; i < colNum * rowsNum - 1; i++) {
                addEdgeToList(i, new Edge(i, i - 1, rnd.nextDouble(minWeight, maxWeight)));
                addEdgeToList(i, new Edge(i, i - colNum, rnd.nextDouble(minWeight, maxWeight)));
                addEdgeToList(i, new Edge(i, i + 1, rnd.nextDouble(minWeight, maxWeight)));
            }

            if (rowsNum > 1 && i == rowsNum * colNum - 1)
                addEdgeToList(i, new Edge(i, i - colNum, rnd.nextDouble(minWeight, maxWeight)));
            if (colNum > 1 && i == rowsNum * colNum - 1)
                addEdgeToList(i, new Edge(i, i - 1, rnd.nextDouble(minWeight, maxWeight)));
        }
    }

    public GridGraph(String path) throws IOException{
       super.readGraph(path);
    }

    public int getColNum(){
        return this.colNum;
    }

    public int getRowsNum(){
        return this.rowsNum;
    }

    public int getNodesNum(){
        return colNum*rowsNum;
    }
    public double getMinWeight() { return minWeight; }
    public double getMaxWeight() { return maxWeight; }
}
