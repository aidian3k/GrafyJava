package com.example.grafy;


import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.LinkedList;

public class SecondaryController {

    @FXML
    private TextField gridSizeInfoTextField;
    @FXML
    private TextField weightRangeInfoTextField;
    @FXML
    ComboBox shortestPathComboBox;
    @FXML
    ComboBox cohesionComboBox;

    @FXML
    Canvas canvas;

    private GridGraph graph;

    public void initialize() {
//        GraphHolder holder = GraphHolder.getInstance();
//        graph = holder.getGraph();
        GraphicsContext gc = canvas.getGraphicsContext2D();
        drawGridGraph(gc, canvas.getWidth(), canvas.getHeight());

        gridSizeInfoTextField.setText(graph.rowsNum + "x" + graph.colNum);
        weightRangeInfoTextField.setText(graph.minWeight + "-" + graph.maxWeight);
        //System.out.println(GraphUtils.dijkstra(graph, 1, 19));

        shortestPathComboBox.getItems().add("dijkstra");
        shortestPathComboBox.getItems().add("bellman-ford");
        shortestPathComboBox.getItems().add("floyd-warshall");
        shortestPathComboBox.getSelectionModel().select(0);

        cohesionComboBox.getItems().add("bfs");
        cohesionComboBox.getItems().add("dfs");
        cohesionComboBox.getSelectionModel().select(0);


    }

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    public void drawGridGraph(GraphicsContext gc, double widthCanvas, double heightCanvas) {
        GraphHolder holder = GraphHolder.getInstance();
        graph = holder.getGraph();

        double RATIO_EDGE_NODE_SIZE=0.5;
        double RATIO_EDGE_NODE_WIDTH=0.15;
        int colNum = graph.getColNum();
        int rowNum = graph.getRowsNum();
        int nodesNumbers = rowNum * colNum;
        int nodeNum=0;
        double nodeX=0;
        double nodeY=0;

        double nodeSize = (heightCanvas/rowNum > widthCanvas/colNum) ? widthCanvas/(colNum+RATIO_EDGE_NODE_SIZE*colNum) : heightCanvas/(rowNum+RATIO_EDGE_NODE_SIZE*colNum);
        double rightSeparator = nodeSize;
        double bottomSeparator = nodeSize;
        double nodeSeparator = (heightCanvas/rowNum > widthCanvas/colNum) ? (widthCanvas)/colNum : (heightCanvas)/rowNum;


        // Draw nodes
        for(int i=0; i<graph.getRowsNum(); i++) {
            if(i==0) {
                nodeY=0;
            }
            for(int j=0; j<graph.getColNum(); j++) {
                if(j==0) {
                    if(i==0)
                        gc.fillOval(nodeX, nodeY, nodeSize, nodeSize);
                    else {
                        nodeX = 0;
                        gc.fillOval(nodeX, nodeY, nodeSize, nodeSize);
                    }
                }
                else {
                    nodeX += nodeSeparator;
                    gc.fillOval(nodeX, nodeY, nodeSize, nodeSize);
                }
            }
            nodeY += nodeSeparator;
        }

        //Draw edges
        gc.setLineWidth(RATIO_EDGE_NODE_WIDTH*nodeSize);
        nodeNum=0;
        for(int i=0; i<rowNum; i++) {
            for(int j=0; j<colNum; j++) {
                LinkedList<Edge> nodeEdges = new LinkedList<Edge>(graph.getConnectionList(nodeNum));
                double nodeA_X = i * nodeSeparator;
                double nodeA_Y = j * nodeSeparator;
                for (Edge edge : nodeEdges) {
                    int rowNodeB = (int) Math.floor(edge.getNodeTo() / colNum);
                    int colNodeB = edge.getNodeTo() % colNum;
//                    System.out.println(nodeNum+" : "+edge.getNodeTo()+"["+rowNodeB+" "+colNodeB+"]  " + rowNodeB * nodeSeparator + " " + colNodeB * nodeSeparator + " "+nodeA_X+" "+nodeA_Y );
                    gc.strokeLine( nodeA_Y+nodeSize/2,nodeA_X+nodeSize/2, colNodeB * nodeSeparator+nodeSize/2, rowNodeB * nodeSeparator+nodeSize/2);
                }
                nodeNum++;
            }
        }


    }
}