/*
 * Developed by: Alexis Peralta Holyoak.
 */
package com.incomab.util;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author peral
 */

//WRITE ALL METHODS FOR FXML
public class FxmlUtil {
    
    public void OpenNewScene(String resource,boolean border,boolean maximized){
         try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(resource));
                Parent root1 = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                if(!border){
                stage.initStyle(StageStyle.TRANSPARENT);
                }
                if(maximized){
                stage.setMaximized(true);
                }
                stage.setScene(new Scene(root1));  
                stage.show();
        } catch(IOException e) {
                System.out.println(e.getMessage());
          }
    }
    /*Method Created By: Alexis Peralta Holyoak
    Params: String,JFXTextField,String,StackPane,ArrayList<String>, Arraylist<EventHandler<ActionEvent>>
    Return: One dialog*/
    public void ShowMessageDialog(String header, String content,StackPane stackPane){
       
    }
    
}
