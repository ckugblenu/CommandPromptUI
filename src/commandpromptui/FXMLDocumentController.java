/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commandpromptui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import processing.Loop;
import processing.SystemCommandExecutor;

/**
 *
 * @author makafui
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private ImageView terminalTab, loopTab;

    @FXML
    private AnchorPane commandWindow, loopWindow, mainWindow;

    @FXML
    private Button runButton, clearButton, launchLoopButton;

    @FXML
    private TextField commandText, loopCountNumber;

    @FXML
    private TextArea resultText;
    
    @FXML 
    private Label iterationsCount;
    

    @FXML
    // Method to switch between Command Window and Loop Window
    private void handleButtonAction(MouseEvent event) {

        if (event.getTarget() == terminalTab) {
            commandWindow.setVisible(true);
            loopWindow.setVisible(false);
        } else {
            commandWindow.setVisible(false);
            loopWindow.setVisible(true);
        }
    }

    @FXML
    private void executeCommand(ActionEvent event) {
        try {
            List<String> commands = new ArrayList<String>();
            commands.add("/bin/bash");
            commands.add("-c");
            commands.add(commandText.getText());

            // execute the command
            SystemCommandExecutor commandExecutor = new SystemCommandExecutor(commands);
            int result = commandExecutor.executeCommand();

            // get the stdout and stderr from the command that was run
            StringBuilder stdout = commandExecutor.getStandardOutputFromCommand();
            StringBuilder stderr = commandExecutor.getStandardErrorFromCommand();
            resultText.setText(stdout.toString());
            if (!stderr.toString().equals("")) {
                mainWindow.setStyle("-fx-background-color: #ff0000");
            }
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    // Clear Command Prompt
    private void clear(ActionEvent event) {
        commandText.clear();
    }
    
    @FXML
    private void launchLoop(ActionEvent event) {
        Loop loop = new Loop(loopCountNumber.getText());
        loop.setDaemon(true);
        loop.start();
        iterationsCount.setText(loopCountNumber.getText());
    }

    // Add validation functionality to the LoopTextField to use only Numbers
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Handle Validation for only numbers
       loopCountNumber.textProperty().addListener(new ChangeListener<String>() {
           @Override
           public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
             if (!newValue.matches("\\d+")) {
                    loopCountNumber.setText(oldValue);
             }
           }
           
       });
    }

}