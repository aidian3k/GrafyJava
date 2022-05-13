package com.example.grafy;


import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

public class SecondaryController {
    @FXML
    private TextField gridSizeInfoTextField;
    @FXML
    private TextField weightRangeInfoTextField;
    @FXML
    private ComboBox shortestPathComboBox;
    @FXML
    private ComboBox cohesionComboBox;
    @FXML
    private TextField pathWeight;
    @FXML
    private Canvas canvas;
    @FXML
    private TextField nodeFrom, nodeTo;
    @FXML
    private Label cohesionInfoLabel;
    @FXML
    Pane paneGraph;
    final FileChooser saveFileChooser = new FileChooser();
    private GridGraph graph;
    double RATIO_EDGE_NODE_SIZE=0.5;
    double RATIO_EDGE_NODE_WIDTH=0.15;
    double nodeSize;
    double nodeSeparator;
    public void initialize() {
        drawGridGraph(paneGraph.getPrefWidth(), paneGraph.getPrefHeight());

        gridSizeInfoTextField.setText(graph.rowsNum + "x" + graph.colNum);
        weightRangeInfoTextField.setText(graph.minWeight + "-" + graph.maxWeight);

        shortestPathComboBox.getItems().add("dijkstra");
        shortestPathComboBox.getItems().add("bellman-ford");
        shortestPathComboBox.getItems().add("floyd-warshall");
        shortestPathComboBox.getSelectionModel().select(0);

        cohesionComboBox.getItems().add("bfs");
        cohesionComboBox.getItems().add("dfs");
    }
    public void cohesionComboBoxAction() {
        if(cohesionComboBox.getValue() == "bfs") {
            cohesionInfoLabel.setText(GraphUtils.breathFirstSearch(graph, 0) ? "Is cohesion! [bfs]" : "Not cohesion:( [bfs]");
        }
        else if(cohesionComboBox.getValue() == "dfs")
            cohesionInfoLabel.setText(GraphUtils.depthFirstSearch(graph, 0) ? "Is cohesion! [dfs]" : "Not cohesion:( [dfs]");
        else
            cohesionInfoLabel.setText("");
    }
    public void redrawButtonAction() {
        paneGraph.getChildren().clear();
        drawGridGraph(paneGraph.getPrefWidth(), paneGraph.getPrefHeight());
    }
    public void saveButtonAction() throws IOException {
        saveFileChooser.setTitle("Save graph");
        saveFileChooser.setInitialFileName("mygraph");
        saveFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("text file", "*.txt"));
        try {
            File file = saveFileChooser.showSaveDialog(null);
            graph.saveGraph(file.getAbsolutePath());
            System.out.println("Zapisano");
        } catch(Exception e) {
            System.out.println("Nie zapisano");;
        }
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
    public void drawGridGraph(double widthCanvas, double heightCanvas) {
        GraphHolder holder = GraphHolder.getInstance();
        graph = holder.getGraph();

        int colNum = graph.getColNum();
        int rowNum = graph.getRowsNum();
        int nodesNumbers = rowNum * colNum;
        int nodeNum;
        double nodeX=0;
        double nodeY=0;
        double HUE_MAX = 0;
        double HUE_MIN = 250;

        nodeSize = (heightCanvas/rowNum > widthCanvas/colNum) ? widthCanvas/(colNum+RATIO_EDGE_NODE_SIZE*colNum) : heightCanvas/(rowNum+RATIO_EDGE_NODE_SIZE*colNum);
        double rightSeparator = nodeSize;
        double bottomSeparator = nodeSize;
        nodeSeparator = Math.min(heightCanvas / rowNum, widthCanvas / colNum);

        //Draw edges
        nodeNum=0;
        for(int i=0; i<rowNum; i++) {
            for(int j=0; j<colNum; j++) {
                LinkedList<Edge> nodeEdges = new LinkedList<Edge>(graph.getConnectionList(nodeNum));
                double nodeA_X = j * nodeSeparator;
                double nodeA_Y = i * nodeSeparator;
                for (Edge edge : nodeEdges) {
                    int colNodeB = (int) Math.floor(edge.getNodeTo() / colNum);
                    int rowNodeB = edge.getNodeTo() % colNum;
//                    System.out.println(nodeNum+" : "+edge.getNodeTo()+"["+rowNodeB+" "+colNodeB+"]  " + rowNodeB * nodeSeparator + " " + colNodeB * nodeSeparator + " "+nodeA_X+" "+nodeA_Y );
                    double shiftX = RATIO_EDGE_NODE_WIDTH*nodeSize/4;
                    double shiftY = RATIO_EDGE_NODE_WIDTH*nodeSize/4;
                    if(edge.getNodeFrom() < edge.getNodeTo()) {
                        if(nodeA_X == rowNodeB*nodeSeparator)
                            shiftX *= -1;
                        if(nodeA_Y == colNodeB * nodeSeparator)
                            shiftY *= -1;
                    } else {
                        if(nodeA_X != rowNodeB*nodeSeparator)
                            shiftX *= -1;
                        if(nodeA_Y != colNodeB * nodeSeparator)
                            shiftY *= -1;
                    }

                    Line line = new Line(nodeA_X+nodeSize/2+shiftX,nodeA_Y+nodeSize/2+shiftY, rowNodeB * nodeSeparator+nodeSize/2+shiftX, colNodeB * nodeSeparator+nodeSize/2+shiftY);
                    line.setStrokeWidth(RATIO_EDGE_NODE_WIDTH*nodeSize/2);

                    double hue = HUE_MIN + (HUE_MAX-HUE_MIN) * (edge.getWeight()-graph.getMinWeight()) / (graph.getMaxWeight() - graph.getMinWeight());
                    line.setStroke(Color.hsb(hue, 1.0, 1.0));

                    paneGraph.getChildren().add(line);
                }
                nodeNum++;
            }
        }

        Circle[] circArray = new Circle[nodesNumbers];
        nodeNum=0;
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
    }
    public void drawShortestPath() {
        String shortestPathAlg = (String) shortestPathComboBox.getValue();
        ShortestPathSolution shortestPathSolution;
        if( shortestPathAlg.equals("dijkstra")) {
            shortestPathSolution=GraphUtils.dijkstra(graph,Integer.parseInt(nodeFrom.getText()),Integer.parseInt(nodeTo.getText()));
        }
        else if( shortestPathAlg.equals("bellman-ford")) {
            shortestPathSolution=GraphUtils.bellmanFord(graph,Integer.parseInt(nodeFrom.getText()),Integer.parseInt(nodeTo.getText()));
        }
        else {
            shortestPathSolution=GraphUtils.floydWarshall(graph,Integer.parseInt(nodeFrom.getText()),Integer.parseInt(nodeTo.getText()));
        }

        ArrayList<Integer> path=new ArrayList<>(shortestPathSolution.path);

        int colNum = graph.getColNum();
        int rowNum = graph.getRowsNum();
        int hue = ThreadLocalRandom.current().nextInt(1, 360);
        for(int i=1; i<path.size(); i++) {
            int rowNodeA = (int) Math.floor(path.get(i-1) / colNum);
            int colNodeA = path.get(i-1) % colNum;
            double nodeA_X = colNodeA * nodeSeparator+nodeSize/2;
            double nodeA_Y = rowNodeA * nodeSeparator+nodeSize/2;
            int rowNodeB = (int) Math.floor(path.get(i) / colNum);
            int colNodeB = path.get(i) % colNum;
            double nodeB_X = colNodeB * nodeSeparator+nodeSize/2;
            double nodeB_Y = rowNodeB * nodeSeparator+nodeSize/2;

            Line line = new Line(nodeA_X, nodeA_Y, nodeB_X, nodeB_Y);
            line.setStrokeWidth((RATIO_EDGE_NODE_WIDTH+0.2)*nodeSize);
            line.setStroke(Color.hsb(hue, 1.0, 1.0));
            paneGraph.getChildren().add(line);
        }

//        shortestPathSolution.displayPath();
        pathWeight.setText(String.valueOf(Math.round(shortestPathSolution.minimumWeight * 10000.0) / 10000.0));
    }
}

