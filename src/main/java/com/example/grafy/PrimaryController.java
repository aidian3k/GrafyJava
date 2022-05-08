package com.example.grafy;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class PrimaryController {
    @FXML
    private TextField gridSizeInputTextField;
    @FXML
    private TextField weightRangeInputTextField;

    @FXML
    private TextField fileNameTextField;

    final FileChooser fileChooser = new FileChooser();

    @FXML
    private void generateButtonPressed() throws IOException {
        String[] gridSize = gridSizeInputTextField.getText().split("x");
        int rowNum = Integer.parseInt(gridSize[0]);
        int colNum = Integer.parseInt(gridSize[1]);
        String[] range = weightRangeInputTextField.getText().split("-");

        double minWeight = Double.parseDouble(range[0]);
        double maxWeight = Double.parseDouble(range[1]);

        GridGraph graph=new GridGraph(rowNum, colNum, minWeight, maxWeight);
        GraphHolder holder = GraphHolder.getInstance();
        holder.setGraph(graph);

        switchToSecondary();
    }
    @FXML
    private void generateFromFileButtonPressed() throws IOException {
        GridGraph graph=new GridGraph(fileNameTextField.getText());
        GraphHolder holder = GraphHolder.getInstance();
        holder.setGraph(graph);

        switchToSecondary();
    }
    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    @FXML
    private void handleChooseFileButton() {
        fileChooser.setTitle("Graph File Chooser");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File file = fileChooser.showOpenDialog(null);
        if(file != null) {
            fileNameTextField.setText(file.getAbsolutePath());
        } else {
            System.out.println("A file is invalid!");
            fileNameTextField.setText("");
        }
    }

    public String getGridSizeInput() {
        return gridSizeInputTextField.getText();
    }

    public String getWeightRangeInput() {
        return weightRangeInputTextField.getText();
    }

}
