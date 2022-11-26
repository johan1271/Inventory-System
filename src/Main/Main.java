/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Caicedo
 */
public class Main extends Application {
    private double x = 0;
    private double y = 0;
    @Override
    public void start(Stage ventana) throws Exception {
       
            // cargar la vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Vistas/login.fxml"));
            // cargar la ventana
            Parent ventana2 =loader.load();
            // cargo el scene
            
            Scene scene = new Scene(ventana2);
            ventana.initStyle(StageStyle.UNDECORATED);

            scene.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    x = event.getSceneX();
                    y = event.getSceneY();
                }
            });
            scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    ventana.setX(event.getScreenX() - x);
                    ventana.setY(event.getScreenY() - y);
                }
            });
            // seteo el scene y lo muestro
            ventana.setScene(scene);
            ventana.show();
       ;
        

    }
    
    
    
    
    public static void main(String[] args) {
        launch();
    }
}
