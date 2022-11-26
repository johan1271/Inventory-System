package Controlador;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class EmpleadoController {
    double x = 0;
    double y = 0;
    @FXML
    private JFXButton BtnClientes;

   
    @FXML
    private JFXButton BtnFactura;

    @FXML
    private JFXButton BtnInicio;

    @FXML
    private JFXButton BtnProductos;

    @FXML
    private JFXButton BtnProveedores;

    @FXML
    private JFXButton Btncategorias;

    @FXML
    private JFXButton btnSalir;

    @FXML
    private Label nombre;

    @FXML
    private StackPane stack;



    

    public void factura(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent loader = FXMLLoader.load(getClass().getResource("../Vistas/Factura.fxml"));
        stack.getChildren().removeAll();
        stack.getChildren().setAll(loader);
    }

   
    

    public void displayName(String username) {
        nombre.setText(username);
    }
    
    
    public void categorias(javafx.event.ActionEvent actionEvent) throws IOException {
       Parent loader = FXMLLoader.load(getClass().getResource("../Vistas/Categoria.fxml"));
        stack.getChildren().removeAll();
        stack.getChildren().setAll(loader);
    }
    
    public void home(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent loader = FXMLLoader.load(getClass().getResource("../Vistas/Inicio.fxml"));
        stack.getChildren().removeAll();
        stack.getChildren().setAll(loader);
    }

    public void clientes(javafx.event.ActionEvent actionEvent) throws IOException {

        Parent loader = FXMLLoader.load(getClass().getResource("../Vistas/Clientes.fxml"));
        stack.getChildren().removeAll();
        stack.getChildren().setAll(loader);
        
    }

    public void productos(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent loader = FXMLLoader.load(getClass().getResource("../Vistas/Productos.fxml"));
        stack.getChildren().removeAll();
        stack.getChildren().setAll(loader);
    }

    public void proveedores(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent loader = FXMLLoader.load(getClass().getResource("../Vistas/Proveedores.fxml"));
        stack.getChildren().removeAll();
        stack.getChildren().setAll(loader);
    }
    
    
    public void Salir(javafx.event.ActionEvent actionEvent) throws IOException {

        try {
            // cargar la vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Vistas/login.fxml"));
            // cargar la ventana
            Parent ventana2 = loader.load();
            // cargo el scene
            Stage ventana = new Stage();
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
        } catch (IOException e) {
            System.out.println("Error :" + e.getMessage());
        }

        Stage stag = (Stage) this.btnSalir.getScene().getWindow();
        stag.close();
    }

}
