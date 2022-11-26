/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import java.io.IOException;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.stage.Modality;

/**
 *
 * @author Caicedo
 */
public class Servicio{
    double x = 0;
    double y = 0;
 
   
    
    //Clientes agregar
    
    public void Alerta(String ruta) {

        try {
            // cargar la vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource(ruta));
            // cargar la ventana
            Pane ventana2 = (Pane) loader.load();
            // cargo el scene
            Stage ventana = new Stage();
            Scene scene = new Scene(ventana2);
            ventana.initModality(Modality.APPLICATION_MODAL);
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
            ventana.showAndWait();

        } catch (IOException e) {
            System.out.println("Error :" + e.getMessage());
        }
    }

    public void Ventana(String ruta) {
        try {
            // cargar la vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource(ruta));
            // cargar la ventana
            Pane ventana2 = (Pane) loader.load();
            // cargo el scene
            Stage ventana = new Stage();
            Scene scene = new Scene(ventana2);

            // seteo el scene y lo muestro
            ventana.setScene(scene);
            ventana.show();

        } catch (IOException e) {
            System.out.println("Error :" + e.getMessage());
        }

    }

    public void Alertasnueva(String texto) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Vistas/alerta.fxml"));
        Parent ventana2 = loader.load();

        AlertaController alerta = loader.getController();
        alerta.Cambiar(texto);
        Stage ventana = new Stage();
        Scene scene = new Scene(ventana2);
        ventana.initModality(Modality.APPLICATION_MODAL);
        ventana.initStyle(StageStyle.UNDECORATED);
        ventana.setScene(scene);
        ventana.showAndWait();
    }

        
    

}
