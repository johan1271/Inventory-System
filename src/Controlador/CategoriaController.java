/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controlador;

import Modelo.CategoriaArticulo;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;

import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class CategoriaController {
    ObservableList<CategoriaArticulo> filtro;
    Servicio alert = new Servicio();

    @FXML
    private TextField buscador;

    @FXML
    private JFXButton guardar;

    @FXML
    private TableColumn<CategoriaArticulo, Number> id;

    @FXML
    private JFXButton limpiar;

    @FXML
    private JFXButton modificar;

    @FXML
    private TableColumn<CategoriaArticulo, String> nombre;

    @FXML
    private TextField nombretxt;

    @FXML
    private TableView<CategoriaArticulo> tabla;

    @FXML
    void Click(ActionEvent event) throws IOException, SQLException {
        if (event.getSource() == guardar) {

            String nombrecat = nombretxt.getText();
            CategoriaArticulo a = new CategoriaArticulo();
            if (nombrecat.isEmpty()) {
                alert.Alertasnueva("Campos vacios");
            } else {

                if (a.Existente(nombrecat)) {
                    alert.Alertasnueva("Ya existe");
                } else {
                    a.setNombre(nombrecat);
                    if (a.guardar(a)) {
                        nombretxt.setText("");
                        alert.Alertasnueva("Se ha guardado");
                        nombretxt.requestFocus();
                        Listar();
                    } else {
                        alert.Alertasnueva("Ocurrio un error");
                    }
                }

            }
        }
        
        
        if (event.getSource() == limpiar) {
            nombretxt.setText("");
            buscador.setText("");
            nombretxt.requestFocus();
            modificar.setDisable(true);
            guardar.setDisable(false);
            Listar();

        }
        
        if (event.getSource() == modificar) {
            CategoriaArticulo ca = tabla.getSelectionModel().getSelectedItem();
            String nombrecat = nombretxt.getText();
            if (nombrecat.isEmpty()) {
                alert.Alertasnueva("Campos vacios");

            }else{
                CategoriaArticulo cat = new CategoriaArticulo();
                cat.setNombre(nombrecat);
                cat.setId(ca.getId());
                
                if (cat.Editar(cat)) {
                    modificar.setDisable(true);
                    guardar.setDisable(false);
                    alert.Alertasnueva("Se ha modificado");
                    nombretxt.setText("");
                    buscador.setText("");
                    nombretxt.requestFocus();
                    Listar();
                }
            }
        }
    }

    public void Listar(){
        CategoriaArticulo cat = new CategoriaArticulo();
        ObservableList<CategoriaArticulo> items = cat.getCategoria();
        this.tabla.setItems(items);
        
    }
    
    @FXML
    void Filtrar(KeyEvent event) {
        CategoriaArticulo cat = new CategoriaArticulo();
        ObservableList<CategoriaArticulo> i = cat.getCategoria();
        
        String buscador = this.buscador.getText();
        
        if (buscador.isEmpty()) {
            this.tabla.setItems(i);
            
        }else{
            filtro.clear();
            
            for (CategoriaArticulo ca : i) {
                if (ca.getNombre().toLowerCase().contains(buscador) ) {
                   filtro.add(ca);
                }  
            }
            this.tabla.setItems(filtro);
        }
    }
    
     @FXML
    void ClickRow(MouseEvent event) throws IOException {

        CategoriaArticulo ca = tabla.getSelectionModel().getSelectedItem();
        if (ca == null) {
            System.out.println("");
        } else {
            modificar.setDisable(false);
            guardar.setDisable(true);
            
            nombretxt.setText(ca.getNombre());
          
        }
    }
    
    
    @FXML
    public void initialize() {
        
        this.id.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        filtro = FXCollections.observableArrayList();
        Listar();
        modificar.setDisable(true);
    }

}
