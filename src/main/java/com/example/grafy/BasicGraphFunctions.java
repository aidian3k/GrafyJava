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
        graph.get(index).add(e);
    }

    void saveGraph (String path) throws IOException {
        PrintWriter pw=new PrintWriter(new FileWriter(path));
        if(graph==null){
            pw.println("Graph value is null");
            System.exit(1);
        }
        else{
            pw.println(rowsNum+" "+colNum);
            for(int i=0;i<graph.size();i++){
                pw.print("\t");
                for(Edge e : graph.get(i)){
                    pw.print(" "+ e.getNodeTo() + " :"+e.getWeight() + " ");
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
            rowsNum=Integer.parseInt(words[0]);
            colNum=Integer.parseInt(words[1]);
            for (int c = 0; c < colNum; c++) {
                for (int r = 0; r < rowsNum; r++) {
                    graph.add(new LinkedList<>()); //Initializing memory for the graph representation
                }
            }
            int nodeNum = 0;
            String line=null;
            while((line=br.readLine())!=null){
                words=line.split("[\s:]+");
                for(int i=1; i<words.length; i+=2){
                    addEdgeToList(nodeNum,new Edge(nodeNum,Integer.parseInt(words[i]),Double.parseDouble(words[i+1])));
                }
                nodeNum++;
            }
            br.close();
        }
        catch(ArrayIndexOutOfBoundsException | NumberFormatException e){
            throw new IOException("Cannot read graph given by the user:"+"\n"+e.getMessage());
        }
        br.close();
    }

    LinkedList<Edge> getConnectionList(int node){
        return ((LinkedList<Edge>)graph.get(node))==null ? null : (LinkedList<Edge>)graph.get(node);
    }
}
