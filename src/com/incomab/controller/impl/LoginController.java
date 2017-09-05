/*
 * Developed by: Alexis Peralta Holyoak.
 */
package com.incomab.controller.impl;

import com.incomab.util.EmailUtil;
import com.incomab.util.EthernetUtil;
import com.incomab.util.FxmlUtil;
import com.incomab.util.MensajesUtil;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author peral
 */
public class LoginController implements Initializable {
    private final double IMG_WIDTH = 900;
    private final double IMG_HEIGHT = 600;
    Image imgCajamarca;
    Image imgChiclayo;
    Image imgLoro;
    Image imgMacchupicchu;
    MensajesUtil mensa;
    FxmlUtil fxml;
    EmailUtil email;
    EthernetUtil internet;
    private final String path="/com/incomab/view/";
    private final int NUM_OF_IMGS = 4 ;
    private final int SLIDE_FREQ = 4; // in secs
    Preferences prefs;
    @FXML
    private MaterialDesignIconView btnClose;
    @FXML
    private MaterialDesignIconView btnMinimize;
    private ImageView bckLogin;
    @FXML
    private HBox hbxLogin;
    @FXML
    private AnchorPane LOGINPANE;
    @FXML
    private JFXSnackbar SBMENSAJE;
    @FXML
    private JFXButton BTNLOGIN;
    @FXML
    private JFXTextField TXTUSER;
    @FXML
    private JFXPasswordField TXTPASSWORD;
    @FXML
    private Hyperlink forgotLink;
    @FXML
    private StackPane stackpane;
    @FXML
    private JFXToggleButton toggleRemember;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       LoadSlide();
       SBMENSAJE=new JFXSnackbar(LOGINPANE);
       mensa=new MensajesUtil();
       fxml=new FxmlUtil();
       email=new EmailUtil();
       prefs=Preferences.userNodeForPackage(getClass());   
       TXTUSER.setText(prefs.get("user", ""));
       TXTPASSWORD.setText(prefs.get("pass", ""));
       toggleRemember.setSelected(prefs.getBoolean("toggle", false));
       }    
    //A LIST OF IMAGES IN A ETHERNAL LOOP TRANSITION
    private void LoadSlide(){
         try {
            //define image
            imgChiclayo=new Image(getClass().getResourceAsStream("/images/chiclayo.png"));
            imgCajamarca=new Image(getClass().getResourceAsStream("/images/cajamarca.png"));
            imgLoro=new Image(getClass().getResourceAsStream("/images/loro.png"));
            imgMacchupicchu=new Image(getClass().getResourceAsStream("/images/macchupicchu.png"));
            //create imageview
            ImageView vwChiclayo=new ImageView(imgChiclayo);
            ImageView vwCajamarca=new ImageView(imgCajamarca);
            ImageView vwLoro=new ImageView(imgLoro);
            ImageView vwMacchupicchu=new ImageView(imgMacchupicchu);
            //fil the HBox
            hbxLogin.getChildren().addAll(vwChiclayo,vwCajamarca,vwLoro,vwMacchupicchu);
            //transition
            EventHandler<ActionEvent> slideAction = (ActionEvent t) -> {
                TranslateTransition trans = new TranslateTransition(Duration.seconds(1.5), hbxLogin);           
                trans.setByX(-IMG_WIDTH);
                trans.setInterpolator(Interpolator.EASE_BOTH);
                trans.play();
            };
            //eventHandler
            EventHandler<ActionEvent> resetAction = (ActionEvent t) -> {
                TranslateTransition trans = new TranslateTransition(Duration.seconds(1), hbxLogin);
                trans.setByX((NUM_OF_IMGS - 1) * IMG_WIDTH);
                trans.setInterpolator(Interpolator.EASE_BOTH);
                trans.play();
            };
            //handle frames of animation
            List<KeyFrame> keyFrames = new ArrayList<>();
            for (int i = 1; i <= NUM_OF_IMGS; i++) {
                if (i == NUM_OF_IMGS) {
                    keyFrames.add(new KeyFrame(Duration.seconds(i * SLIDE_FREQ), resetAction));
                } else {
                    keyFrames.add(new KeyFrame(Duration.seconds(i * SLIDE_FREQ), slideAction));
                }
            }
            //add timeLine
            Timeline anim = new Timeline(keyFrames.toArray(new KeyFrame[NUM_OF_IMGS]));
            anim.setCycleCount(Timeline.INDEFINITE);
            anim.playFromStart();
        } catch (Exception ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }       
    }
    //to close the login screen
    @FXML
    private void handleClose(MouseEvent event) {
        System.exit(0);
    }
    //to minimize the login screen
    @FXML
    private void handleMinimize(MouseEvent event) {
        Stage stage = (Stage)((MaterialDesignIconView)event.getSource()).getScene().getWindow();
        // is stage minimizable into task bar. (true | false)
        stage.setIconified(true);
    }
    @FXML
    private void Handle_Login(MouseEvent event) {           
         Input_ok();
         String ex="";    
         if(TXTUSER.getText().trim().equals("")){
             ex=mensa.getIncorrectUser();
             Show_snack(event, ex);
         }else if(TXTPASSWORD.getText().trim().equals("")){
             ex=mensa.getIncorrectPassw();
             Show_snack(event, ex);
         } else{
             Remember_me(TXTUSER.getText().trim(), TXTPASSWORD.getText().trim());
             Stage stage = (Stage) BTNLOGIN.getScene().getWindow();
             //close login view
             stage.close();
             Load_main_window(event);
         }       
    }
    //method used to load in preferences login user information
    private void Remember_me(String user,String pass){
        if(toggleRemember.isSelected()){
           prefs.put("user", user);
           prefs.put("pass", pass);
           prefs.putBoolean("toggle", true);
        }else{
           prefs.put("user", "");
           prefs.put("pass", "");
           prefs.putBoolean("toggle", false);
        }
    }    
    //show the snackbar on screen whenever mousevent on login button 
    private void Show_snack(MouseEvent e,String m){
        SBMENSAJE.show(m, 3000);   
        Input_fail(m);
    }
    //handle style of textfields when something went wrong
    private void Input_fail(String m){
        String stilo="-fx-text-fill: red; -jfx-focus-color: red; -jfx-unfocus-color:red";
        if(m.equals(mensa.getIncorrectUser())){
        TXTUSER.setStyle(stilo);        
        }
        if(m.equals(mensa.getIncorrectPassw())){
         TXTPASSWORD.setStyle(stilo);    
        }
    }
    //handle style of textfields when everything is correct
    private void Input_ok(){
        String stilo="-fx-text-fill: black; -jfx-focus-color: #2c4251; -jfx-unfocus-color: #4d4d4d;";
        TXTUSER.setStyle(stilo);
        TXTPASSWORD.setStyle(stilo);
    }  
    //this method opens main form
    private void Load_main_window(MouseEvent event){               
       fxml.OpenNewScene(path+"MainView.fxml",false,true);
    }
    //method call when user forgets password :(
    @FXML
    private void loadForgetPassForm(MouseEvent event) {
        //CODE USE TO OPEN A DIALOG AND WRITE THE USERS EMAIL IN ORDER TO RECOVER THEIR DATADA
        JFXTextField inputCorreo = new JFXTextField();
        inputCorreo.setPromptText("email@example.com");
        //this is necessary to make the stackpane enable, because its disable by deffect
        stackpane.setDisable(false);
        JFXDialogLayout content=new JFXDialogLayout();
        content.setHeading(new Text("Ingrese su correo electronico con el que se registro:"));
        content.setBody(inputCorreo);
        //action buttons inside the dialog
        JFXButton readyDialog=new JFXButton("Listo");
        JFXButton cancelDialog=new JFXButton("Cancelar");
        JFXDialog dialog=new JFXDialog(stackpane, content, JFXDialog.DialogTransition.CENTER);
        //click if you want to close the dialog to recover your password
        cancelDialog.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dialog.close();
                stackpane.setDisable(true);
            }
        });        
        readyDialog.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!inputCorreo.getText().trim().equals("")&&inputCorreo.getText().contains("@")&&inputCorreo.getText().contains(".com")){
                    String ok="-fx-text-fill: black; -jfx-focus-color: #2c4251; -jfx-unfocus-color: #4d4d4d;";
                    inputCorreo.setStyle(ok);
                    Send_userdata_toemail(inputCorreo.getText().trim());
                }else{
                    String notok="-fx-text-fill: red; -jfx-focus-color: red; -jfx-unfocus-color:red";
                    inputCorreo.setStyle(notok);
                }
            }
        });      
        //when dialog is closed by clicking in anywhere else but the dialog, i need to disable stackpane
        dialog.setOnDialogClosed(disableStackPane->{
            stackpane.setDisable(true);
        });
        content.setActions(readyDialog,cancelDialog);        
        dialog.show();
         
    }
    private void Send_userdata_toemail(String to){
        //TODO: first validate if the email is one of the users' email in database
        
        //then send the message to recover the user information
        //change strings by concatenating results from database
        //first verify internet connection
        if(internet.IsConnected()){
            email.SendEmail(to, "contenido", "tema", "", "");
        }
    }
}
