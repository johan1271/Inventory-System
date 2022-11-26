/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controlador;

import Modelo.Conexion;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.jfoenix.controls.JFXButton;
import java.sql.SQLException;
import javafx.scene.Parent;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Caicedo
 */
public class LoginController  {

    Connection conn = Conexion.getConnection();
    Servicio alert = new Servicio() ;
    Statement st;
    ResultSet rs;
    /**
     * Initializes the controller class.
     */

    @FXML
    private Button btncerrar;

    @FXML
    private JFXButton btnlog;

    @FXML
    private PasswordField contra;

    @FXML
    private TextField usuario;

    double x = 0;
    double y = 0;

    @FXML
    void salir(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void Loggear(ActionEvent event) throws IOException, SQLException {
        String usu = String.valueOf(usuario.getText());
        String cont = String.valueOf(contra.getText());
        String u = "", c = "", cargo = "", nombre = "", apellido = "";
        if (usu.isEmpty() || cont.isEmpty()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Vistas/alerta.fxml"));
            Parent ventana2 = loader.load();

            AlertaController alerta = loader.getController();
            alerta.Cambiar("Campos vacios");
            Stage ventana = new Stage();
            Scene scene = new Scene(ventana2);
            ventana.initModality(Modality.APPLICATION_MODAL);
            ventana.initStyle(StageStyle.UNDECORATED);
            ventana.setScene(scene);
            ventana.showAndWait();

        } else {
                String sql = "select nombre,apellido,cedulau, contra, ID_cargo from usuario where cedulau = '" + usu + "' and contra='" + cont + "'";
                st = conn.createStatement();

                rs = st.executeQuery(sql);

                while (rs.next()) {
                    nombre = rs.getString("nombre");
                    apellido = rs.getString("apellido");
                    u = rs.getString("cedulau");
                    c = rs.getString("contra");
                    cargo = rs.getString("ID_cargo");
                }

                if (u.equals(usu) && cont.equals(c)) {

                    usuario.setStyle("-fx-border-color:  #023A61; -fx-border-width: 0px 0px 2px 0px; -fx-background-color: transparent; ");
                    contra.setStyle("-fx-border-color:  #023A61; -fx-border-width: 0px 0px 2px 0px; -fx-background-color: transparent; ");
                    if (cargo.equals("2")) {

                        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Vistas/Administrador.fxml"));
                        Parent ventana2 = loader.load();

                        AdminController admin = loader.getController();
                        
                        
                        
                        admin.displayName(nombre+" "+ apellido);
                        Stage ventana = new Stage();
                        Scene scene = new Scene(ventana2);
                        ventana.setScene(scene);
                        ventana.show();

                        Stage mystage = (Stage) this.btnlog.getScene().getWindow();
                        mystage.close();


                    } else if (cargo.equals("1")) {
                        System.out.println(nombre + cargo);
                        System.out.println("Empleado");
                        
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Vistas/Empleado.fxml"));
                        Parent ventana2 = loader.load();
                        EmpleadoController emple = loader.getController();
                        emple.displayName(nombre+" "+ apellido);
                        Stage ventana = new Stage();
                        Scene scene = new Scene(ventana2);
                        ventana.setScene(scene);
                        ventana.show();

                        Stage mystage = (Stage) this.btnlog.getScene().getWindow();
                        mystage.close();
                    }

                } else {
                    usuario.setStyle("-fx-border-color: red; -fx-border-width: 0px 0px 2px 0px; -fx-background-color: transparent; ");
                    contra.setStyle("-fx-border-color: red; -fx-border-width: 0px 0px 2px 0px; -fx-background-color: transparent; ");
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../Vistas/alerta.fxml"));
                    Parent ventana2 = loader.load();

                    AlertaController alerta = loader.getController();
                    alerta.Cambiar("Datos invalidos");
                    Stage ventana = new Stage();
                    Scene scene = new Scene(ventana2);
                    ventana.initModality(Modality.APPLICATION_MODAL);
                    ventana.initStyle(StageStyle.UNDECORATED);
                    ventana.setScene(scene);
                    ventana.showAndWait();
                }
           
        }

    }

    @FXML
    public void initialize() {
    }

}
