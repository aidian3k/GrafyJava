package com.example.grafy;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BasicGraphFunctions {

    protected ArrayList<List<Edge>> graph=new ArrayList<>();
    protected int colNum; //Initialized 0
    protected int rowsNum;
    protected double minWeight;
    protected double maxWeight;

    void addEdgeToList(int index, Edge e){

        try {
            graph.get(index).add(e);
        }
        catch(IndexOutOfBoundsException ex){
            throw new IndexOutOfBoundsException("There is a problem with reading given graph! The size given is > than given lines with data in file");
        }

    }

    void saveGraph (String path) throws IOException {
        PrintWriter pw=new PrintWriter(new FileWriter(path));

        if(graph==null){
            throw new IllegalArgumentException("Graph value cannot be null!");
        }
        else{
            pw.println(rowsNum+" "+colNum);
            for (List<Edge> edges : graph) {
                pw.print("\t");
                for (Edge e : edges) {
                    pw.print(" " + e.getNodeTo() + " :" + e.getWeight() + " ");
                }
                pw.println();
            }
        }
        pw.close();
    }

    void readGraph(String path) throws IOException {
        BufferedReader br=new BufferedReader(new FileReader(path));
        try{
            String [] words=br.readLine().split("\s");
            double minWeightIn=Double.POSITIVE_INFINITY;
            double maxWeightIn=Double.NEGATIVE_INFINITY;
            rowsNum=Integer.parseInt(words[0]);
            colNum=Integer.parseInt(words[1]);

            for (int c = 0; c < colNum; c++) {
                for (int r = 0; r < rowsNum; r++) {
                    graph.add(new LinkedList<>()); //Initializing memory for the graph representation
                }
            }

            int nodeNum = 0;
            String line;

            while((line=br.readLine())!=null){
                words=line.split("[\s:]+");

                for(int i=1; i<words.length; i+=2){
                    double currWeight=Double.parseDouble(words[i+1]);
                    addEdgeToList(nodeNum,new Edge(nodeNum,Integer.parseInt(words[i]),currWeight));

                    if(maxWeightIn < currWeight){
                        maxWeightIn = currWeight;
                    }
                    if(minWeightIn > currWeight){
                        minWeightIn = currWeight;
                    }

                }

                nodeNum++;

            }

            minWeight=minWeightIn;
            maxWeight=maxWeightIn;
            br.close();
        }
        catch(ArrayIndexOutOfBoundsException | NumberFormatException e){
            throw new IOException("FILE_READ_PROBLEM: There is a problem with reading given graph, check the file with graph!");
        }

        br.close();
    }
    LinkedList<Edge> getConnectionList(int node){
        return ((LinkedList<Edge>)graph.get(node))==null ? null : (LinkedList<Edge>)graph.get(node);
    }
}
