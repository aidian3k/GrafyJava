package com.example.grafy;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

public class PrimaryController {
    @FXML
    private TextField gridSizeXInputTextField;
    @FXML
    private TextField gridSizeYInputTextField;
    @FXML
    private TextField weightRangeStartInputTextField;
    @FXML
    private TextField weightRangeEndInputTextField;
    @FXML
    private TextField fileNameTextField;
    @FXML
    private Label warningLabel;
    final FileChooser fileChooser = new FileChooser();
    @FXML
    private void generateButtonAction() throws IOException {
        try {
            int rowNum, colNum;
            double minWeight, maxWeight;
            rowNum = Integer.parseInt(gridSizeXInputTextField.getText());
            colNum = Integer.parseInt(gridSizeYInputTextField.getText());
            minWeight = Double.parseDouble(weightRangeStartInputTextField.getText());
            maxWeight = Double.parseDouble(weightRangeEndInputTextField.getText());

            if(rowNum<=0 || colNum<=0 || colNum>150 || rowNum>150)
                warningLabel.setText("Rows and columns numbers must be in <1-150>");
            else if(maxWeight <= minWeight)
                warningLabel.setText("First number of edge weight range must be bigger than second!");
            else {
                GridGraph graph = new GridGraph(rowNum, colNum, minWeight, maxWeight);
                GraphHolder holder = GraphHolder.getInstance();

                holder.setGraph(graph);
                switchToSecondary();
            }
        } catch(NumberFormatException e) {
            warningLabel.setText("Wrong input!");
        }
    }
    @FXML
    private void generateFromFileButtonAction() throws IOException {
        try {
            GridGraph graph = new GridGraph(fileNameTextField.getText());

            if(!checkIfGrid(graph)) {
                warningLabel.setText("Graph in file is not a grid :(");
            }
            else {
                GraphHolder holder = GraphHolder.getInstance();
                holder.setGraph(graph);
//                System.out.println(graph.getConnectionList(0).get(0).getNodeTo());System.out.println("dsg1");
                switchToSecondary();
            }
        } catch(FileNotFoundException e) {
            warningLabel.setText("File was not found!");
        } catch(IOException e) {
            warningLabel.setText("There is a problem with reading given graph, check the file with graph!");
        }
    }
    public boolean checkIfGrid(GridGraph readGraph){

        if(readGraph == null){
            throw new IllegalArgumentException("Given readGraph is null!");
        }

        int readRows = readGraph.rowsNum;
        int readColumn = readGraph.colNum;
        GridGraph goodGraph = new GridGraph(readRows,readColumn,0,1);

        for(int i = 0 ; i < goodGraph.getNodesNum() ; i++ ){

            int sum = 0;
            LinkedList<Edge> goodList = goodGraph.getConnectionList(i);
            LinkedList<Edge> readList = readGraph.getConnectionList(i);

            if(goodList.size()!=readList.size()){
                return false;
            }

            for(int j = 0 ; j < goodList.size() ; ++j){
                int goodNodeTo = goodList.get(j).getNodeTo();
                int readNodeTo = readList.get(j).getNodeTo();
                sum+=goodNodeTo-readNodeTo;
            }

            if(sum!=0){
                return false;
            }

        }
        return true;
    }

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
    @FXML
    private void handleChooseFileButton() {
        fileChooser.setTitle("Graph File Chooser");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("text file", "*.txt"));

        File file = fileChooser.showOpenDialog(null);
        if(file != null) {
            fileNameTextField.setText(file.getAbsolutePath());
        } else {
            System.out.println("A file is invalid!");
            fileNameTextField.setText("");
        }
    }
    public String getGridSizeXInput() {
        return gridSizeXInputTextField.getText();
    }
    public String getGridSizeYInput() {
        return gridSizeXInputTextField.getText();
    }

    public String getWeightStartRangeInput() {
        return weightRangeStartInputTextField.getText();
    }

    public String getWeightRangeEndInputTextField() {
        return weightRangeEndInputTextField.getText();
    }

    @FXML
    private void watchDecimalInputStart() {
        String input = weightRangeStartInputTextField.getText();
        int size = input.length();

        if(!input.matches("^[0-9]*\\.?[0-9]*$")) {
            weightRangeStartInputTextField.setText(input.substring(0,size-1));
            weightRangeStartInputTextField.positionCaret(size-1);
        }
    }
    @FXML
    private void watchDecimalInputEnd() {
        String input = weightRangeEndInputTextField.getText();
        int size = input.length();

        if(!input.matches("^[0-9]*\\.?[0-9]*$")) {
            weightRangeEndInputTextField.setText(input.substring(0,size-1));
            weightRangeEndInputTextField.positionCaret(size-1);
        }
    }
    @FXML
    private void watchIntInputX() {
        String input = gridSizeXInputTextField.getText();
        int size = input.length();

        if(!input.matches("^[0-9]*")) {
            gridSizeXInputTextField.setText(input.substring(0,size-1));
            gridSizeXInputTextField.positionCaret(size-1);
        }
    }
    @FXML
    private void watchIntInputY() {
        String input = gridSizeYInputTextField.getText();
        int size = input.length();

        if(!input.matches("^[0-9]*")) {
            gridSizeYInputTextField.setText(input.substring(0,size-1));
            gridSizeYInputTextField.positionCaret(size-1);
        }
    }
}
