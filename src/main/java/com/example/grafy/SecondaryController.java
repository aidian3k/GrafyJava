package com.example.grafy;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.Modality;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class SecondaryController {
    @FXML
    private TextField gridSizeInfoTextField;
    @FXML
    private TextField weightRangeInfoTextField;
    @FXML
    private ComboBox<String> shortestPathComboBox;
    @FXML
    private ComboBox<String> cohesionComboBox;
    @FXML
    private TextField pathWeight;
    @FXML
    private TextField nodeFrom;
    @FXML
    private TextField nodeTo;
    @FXML
    private Label cohesionInfoLabel;
    @FXML
    Pane paneGraph;
    @FXML
    ImageView colorScaleImg;
    @FXML
    Label edgeScaleFrom;
    @FXML
    Label edgeScaleTo;
    @FXML
    private Label time;
    final FileChooser saveFileChooser = new FileChooser();
    private GridGraph graph;
    double RATIO_EDGE_NODE_SIZE=0.5;
    double RATIO_EDGE_NODE_WIDTH=0.15;
    double nodeSize;
    double nodeSeparator;
    double HUE_MAX = 0;
    double HUE_MIN = 250;
    Circle[] circleArray;

    ShortestPathSolution shortestPathSolution;
    private final ShortestPath pathAlgorithms = new PathAlgorithms();

    private final Cohesion cohesionAlgorithms = new CohesionAlgorithms();

    public void initialize() {
        drawGridGraph(paneGraph.getPrefWidth(), paneGraph.getPrefHeight());
        colorScaleImg.setImage(createColorScaleImage((int) colorScaleImg.getFitWidth(), (int) colorScaleImg.getFitHeight()));
        edgeScaleFrom.setText(String.valueOf(Math.round(graph.minWeight*100.0)/100.0));
        edgeScaleTo.setText(String.valueOf(Math.round(graph.maxWeight*100.0)/100.0));

        gridSizeInfoTextField.setText(graph.rowsNum + "x" + graph.colNum);
        weightRangeInfoTextField.setText(Math.round(graph.minWeight*100.0)/100.0 + " - " + Math.round(graph.maxWeight*100.0)/100.0);

        shortestPathComboBox.getItems().add("dijkstra");
        shortestPathComboBox.getItems().add("bellman-ford");
        shortestPathComboBox.getItems().add("floyd-warshall");
        shortestPathComboBox.getSelectionModel().select(0);

        cohesionComboBox.getItems().add("bfs");
        cohesionComboBox.getItems().add("dfs");
    }
    public void cohesionComboBoxAction() {
        if(cohesionComboBox.getValue().equals("bfs")) {
            cohesionInfoLabel.setText(cohesionAlgorithms.breathFirstSearch(graph, 0) ? "Is cohesion! [bfs]" : "Not cohesion:( [bfs]");
        }
        else if(cohesionComboBox.getValue().equals("dfs"))
            cohesionInfoLabel.setText(cohesionAlgorithms.depthFirstSearch(graph, 0) ? "Is cohesion! [dfs]" : "Not cohesion:( [dfs]");
        else
            cohesionInfoLabel.setText("");
    }
    public void redrawButtonAction() {
        paneGraph.getChildren().clear();
        nodeFrom.setText(null);
        nodeTo.setText(null);
        drawGridGraph(paneGraph.getPrefWidth(), paneGraph.getPrefHeight());
    }
    public void saveButtonAction() {
        saveFileChooser.setTitle("Save graph");
        saveFileChooser.setInitialFileName("mygraph");
        saveFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("text file", "*.txt"));
        try {
            File file = saveFileChooser.showSaveDialog(null);
            graph.saveGraph(file.getAbsolutePath());
            System.out.println("Saved!");
        } catch(Exception e) {
            System.out.println("There was a problem with saving file!");
        }
    }

    public void displayAlert() throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setResizable(false);

        alert.setTitle("Creating new graph alert");
        alert.setHeaderText("Do you want to save the current graph?");
        alert.setContentText("If you press no, all the data will be deleted");
        alert.initModality(Modality.APPLICATION_MODAL);

        ButtonType okButton= new ButtonType("Yes");
        ButtonType cancelButton= new ButtonType("No");
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(okButton, cancelButton, buttonTypeCancel);

        Optional<ButtonType> option = alert.showAndWait();

        if( option.isPresent() && option.get() == okButton){
            saveButtonAction();
            switchToPrimary();
        }
        else if(option.isPresent() && option.get() == cancelButton){
            switchToPrimary();
        }
        else {
            alert.close();
        }
    }

    EventHandler<MouseEvent> onMouseClickedEventHandler = event -> {
        if (event.getSource() instanceof Circle) {
            Circle circle = (Circle) (event.getSource());
            if(nodeFrom.getText() != null && nodeTo.getText() != null) {
                nodeTo.setText(null);
            }
            if(nodeFrom.getText() == null) {
                nodeFrom.setText(circle.getId());
                colorNodesByDistance();
                circleArray[(Integer.parseInt(circle.getId()))].setFill(Color.LIGHTGREY);
            }
            else if(nodeTo.getText() == null) {
                nodeTo.setText(circle.getId());
                drawShortestPath();
            }
        }
    };
    public void colorNodesByDistance() {
        String shortestPathAlg = shortestPathComboBox.getValue();
        if( shortestPathAlg.equals("dijkstra")) {
            shortestPathSolution= pathAlgorithms.dijkstra(graph, Integer.parseInt(nodeFrom.getText()));
            time.setText("Choose the target node!");
        }
        else if( shortestPathAlg.equals("bellman-ford")) {
            shortestPathSolution= pathAlgorithms.bellmanFord(graph,Integer.parseInt(nodeFrom.getText()));
            time.setText("Choose the target node!");
        }
        else {
            shortestPathSolution= pathAlgorithms.floydWarshall(graph,Integer.parseInt(nodeFrom.getText()));
            time.setText("Choose the target node!");
        }
        for(int i=0; i<graph.getRowsNum()*graph.getColNum(); i++) {
            DoubleSummaryStatistics stat = Arrays.stream(shortestPathSolution.weightArray).summaryStatistics();
            double min = stat.getMin();
            double max = stat.getMax();
            double hue = HUE_MIN + (HUE_MAX-HUE_MIN) * (shortestPathSolution.getWeightSolution(i)-min) / (max - min);
            circleArray[i].setFill(Color.hsb(hue, 1.0, 1.0 ));
        }
    }
    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
    public void drawGridGraph(double widthCanvas, double heightCanvas) {
        GraphHolder holder = GraphHolder.getInstance();
        graph = holder.getGraph();
        time.setText("Choose the initial node");

        paneGraph.setStyle("-fx-background-color: #000000");
        nodeFrom.setText(null);
        nodeTo.setText(null);

        int colNum = graph.getColNum();
        int rowNum = graph.getRowsNum();
        int nodesNumbers = rowNum * colNum;
        int nodeNum;
        double nodeX=0;
        double nodeY=0;
        double min = Math.min(heightCanvas / rowNum, widthCanvas / colNum);
        if(rowNum<100 && colNum<100)
           // nodeSize = (heightCanvas/rowNum > widthCanvas/colNum) ? widthCanvas/(colNum+RATIO_EDGE_NODE_SIZE*colNum) : heightCanvas/(rowNum+RATIO_EDGE_NODE_SIZE*colNum);
            nodeSize = (heightCanvas/rowNum > widthCanvas/colNum) ? widthCanvas/(colNum+RATIO_EDGE_NODE_SIZE*colNum) : heightCanvas/(rowNum+RATIO_EDGE_NODE_SIZE*colNum);
        else
            nodeSize = min;

        nodeSeparator = min;

        //Draw edges
        nodeNum=0;
        for(int i=0; i<rowNum; i++) {
            for(int j=0; j<colNum; j++) {
                LinkedList<Edge> nodeEdges = new LinkedList<>(graph.getConnectionList(nodeNum));
                double nodeA_X = j * nodeSeparator;
                double nodeA_Y = i * nodeSeparator;
                for (Edge edge : nodeEdges) {
                    int colNodeB = (int) Math.floor(edge.getNodeTo() / colNum);
                    int rowNodeB = edge.getNodeTo() % colNum;
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

        circleArray = new Circle[nodesNumbers];
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
                circleArray[nodeNum] = new Circle(nodeX+nodeSize/2, nodeY+nodeSize/2, nodeSize/2, Color.BLUE);
                circleArray[nodeNum].setId(String.valueOf(nodeNum));
                circleArray[nodeNum].setOnMouseClicked(onMouseClickedEventHandler);
                nodeNum++;
            }
            nodeY += nodeSeparator;
        }
        paneGraph.getChildren().addAll(circleArray);
    }
    public void drawShortestPath() {
        String shortestPathAlg = shortestPathComboBox.getValue();

        if( shortestPathAlg.equals("dijkstra")) {
            shortestPathSolution= pathAlgorithms.dijkstra(graph,Integer.parseInt(nodeFrom.getText()));
        }
        else if( shortestPathAlg.equals("bellman-ford")) {
            shortestPathSolution= pathAlgorithms.bellmanFord(graph,Integer.parseInt(nodeFrom.getText()));
        }
        else {
            shortestPathSolution= pathAlgorithms.floydWarshall(graph,Integer.parseInt(nodeFrom.getText()));
        }

        ArrayList<Integer> path=new ArrayList<>(shortestPathSolution.getPathSolution(Integer.parseInt(nodeTo.getText())));

        int colNum = graph.getColNum();
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
            line.setStroke(Color.WHITE);
            paneGraph.getChildren().add(line);
        }

        pathWeight.setText(String.valueOf(Math.round(shortestPathSolution.getWeightSolution(Integer.parseInt(nodeTo.getText())) * 10000.0) / 10000.0));
    }

    public Image createColorScaleImage(int width, int height) {
        WritableImage image = new WritableImage(width, height);
        PixelWriter pixelWriter = image.getPixelWriter();
        double min = graph.getMinWeight();
        double max = graph.getMaxWeight();
        for (int x = 0; x < width; x++) {
            double value = graph.getMinWeight() + (graph.getMaxWeight() - graph.getMinWeight()) * x / width;
            Color color = Color.hsb(HUE_MIN + (HUE_MAX-HUE_MIN) * (value-min)/(max-min), 1.0, 1.0);
            for (int y = 0; y < height; y++) {
                pixelWriter.setColor(x, y, color);
            }
        }
        return image;
    }
}