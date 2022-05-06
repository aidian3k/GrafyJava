package com.example.grafy;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.IOException;

public class SecondaryController {

    @FXML
    private TextField gridSizeInfoTextField;
    @FXML
    private TextField weightRangeInfoTextField;
    @FXML
    ComboBox shortestPathComboBox;
    @FXML
    ComboBox cohesionComboBox;
    private GridGraph graph;

    public void initialize() {
        GraphHolder holder = GraphHolder.getInstance();
        graph = holder.getGraph();
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
}