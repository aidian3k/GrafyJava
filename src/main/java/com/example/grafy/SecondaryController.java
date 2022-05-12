package com.example.grafy;


import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.util.ArrayList;
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
    @FXML
    TextField nodeFrom, nodeTo;

    @FXML
    Pane paneGraph;
    private GridGraph graph;
    double RATIO_EDGE_NODE_SIZE=0.5;
    double RATIO_EDGE_NODE_WIDTH=0.15;
    double nodeSize;
    double nodeSeparator;
    GraphicsContext gc;
    public void initialize() {
//        GraphHolder holder = GraphHolder.getInstance();
//        graph = holder.getGraph();
        gc = canvas.getGraphicsContext2D();
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

    EventHandler<MouseEvent> onMouseClickedEventHandler = event -> {
        if (event.getSource() instanceof Circle) {
            Circle circle = (Circle) (event.getSource());
            System.out.println(circle.getId());
            circle.setFill(Color.GREEN);
            if(nodeFrom.getText() != null && nodeTo.getText() != null) {
                nodeFrom.setText(null);
                nodeTo.setText(null);
            }
            if(nodeFrom.getText() == null) {
                nodeFrom.setText(circle.getId());
            }
            else if(nodeTo.getText() == null) {
                nodeTo.setText(circle.getId());
                drawShortestPath();
            }
        }
    };

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    public void drawGridGraph(GraphicsContext gc, double widthCanvas, double heightCanvas) {
        GraphHolder holder = GraphHolder.getInstance();
        graph = holder.getGraph();

        int colNum = graph.getColNum();
        int rowNum = graph.getRowsNum();
        int nodesNumbers = rowNum * colNum;
        int nodeNum=0;
        double nodeX=0;
        double nodeY=0;

        nodeSize = (heightCanvas/rowNum > widthCanvas/colNum) ? widthCanvas/(colNum+RATIO_EDGE_NODE_SIZE*colNum) : heightCanvas/(rowNum+RATIO_EDGE_NODE_SIZE*colNum);
        double rightSeparator = nodeSize;
        double bottomSeparator = nodeSize;
        nodeSeparator = (heightCanvas/rowNum > widthCanvas/colNum) ? (widthCanvas)/colNum : (heightCanvas)/rowNum;

        Circle[] circArray = new Circle[nodesNumbers];
        // Draw nodes
        for(int i=0; i<graph.getRowsNum(); i++) {
            if(i==0) {
                nodeY=0;
            }
            for(int j=0; j<graph.getColNum(); j++) {
                if(j==0) {
                    if(i!=0) nodeX = 0;
                }
                else {
                    nodeX += nodeSeparator;
                }
                circArray[nodeNum] = new Circle(nodeX+nodeSize/2, nodeY+nodeSize/2, nodeSize/2, Color.BLUE);
                circArray[nodeNum].setId(String.valueOf(nodeNum));
                circArray[nodeNum].setOnMouseClicked(onMouseClickedEventHandler);
                nodeNum++;
            }
            nodeY += nodeSeparator;
        }
        paneGraph.getChildren().addAll(circArray);

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

    public void drawShortestPath() {
        ShortestPathSolution shortestPathSolution=GraphUtils.dijkstra(graph,Integer.parseInt(nodeFrom.getText()),Integer.parseInt(nodeTo.getText()));
        ArrayList<Integer> path=new ArrayList<>(shortestPathSolution.path);

        gc.setLineWidth((RATIO_EDGE_NODE_WIDTH+0.1)*nodeSize);
        gc.setStroke(Color.RED);
        int colNum = graph.getColNum();
        int rowNum = graph.getRowsNum();
        for(int i=1; i<path.size(); i++) {
            int rowNodeA = (int) Math.floor(path.get(i-1) / colNum);
            int colNodeA = path.get(i-1) % colNum;
            double nodeA_X = colNodeA * nodeSeparator+nodeSize/2;
            double nodeA_Y = rowNodeA * nodeSeparator+nodeSize/2;
            int rowNodeB = (int) Math.floor(path.get(i) / colNum);
            int colNodeB = path.get(i) % colNum;
            double nodeB_X = colNodeB * nodeSeparator+nodeSize/2;
            double nodeB_Y = rowNodeB * nodeSeparator+nodeSize/2;
            gc.strokeLine(nodeA_X, nodeA_Y, nodeB_X, nodeB_Y);
        }

        shortestPathSolution.displayPath();
        System.out.println();
        System.out.println(shortestPathSolution.minimumWeight);
    }

}

