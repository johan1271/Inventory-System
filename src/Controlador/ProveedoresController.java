/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Proveedor;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
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

public class ProveedoresController {
    
    ObservableList<Proveedor> filtro;
    Servicio alert = new Servicio();
    @FXML
    private TextField buscador;
    
    @FXML
    private TableColumn<Proveedor, String> correo;
    
    @FXML
    private TextField correotxt;
    
    @FXML
    private TableColumn<Proveedor, String> direccion;
    
    @FXML
    private TextField direcciontxt;
    
    @FXML
    private JFXButton guardar;
    
    @FXML
    private TableColumn<Proveedor, Number> id;
    
    @FXML
    private TextField idtxt;
    
    @FXML
    private JFXButton limpiar;
    
    @FXML
    private JFXButton modificar;
    
    @FXML
    private TableColumn<Proveedor, String> nombre;
    
    @FXML
    private TextField nombretxt;
    
    @FXML
    private TableView<Proveedor> tabla;
    
    @FXML
    private TableColumn<Proveedor, Number> telefono;
    
    @FXML
    private TextField telefonotxt;
    
    @FXML
    void Click(ActionEvent event) throws IOException {
        if (event.getSource() == guardar) {
            String idprov = idtxt.getText();
            String nombreprov = nombretxt.getText();
            String direccionprov = direcciontxt.getText();
            String telefonoprov = telefonotxt.getText();
            String correoprov = correotxt.getText();
            
            Proveedor prov = new Proveedor();
            if (idprov.isEmpty() || nombreprov.isEmpty() || correoprov.isEmpty() || direccionprov.isEmpty() || telefonoprov.isEmpty()) {
                alert.Alertasnueva("Campos vacios");
            } else {
                try {
                    if (prov.Existente(Integer.parseInt(idprov))) {
                        alert.Alertasnueva("Ya existe");
                    } else {
                        prov.setID(Integer.parseInt(idprov));
                        prov.setCorreo(correoprov);
                        prov.setDireccion(direccionprov);
                        prov.setTelefono(Integer.parseInt(telefonoprov));
                        prov.setNombre(nombreprov);
                        
                        if (prov.guardar(prov)) {
                            idtxt.setText("");
                            nombretxt.setText("");
                            direcciontxt.setText("");
                            correotxt.setText("");
                            telefonotxt.setText("");
                            alert.Alertasnueva("Se ha guardado");
                            idtxt.requestFocus();
                            Listar();
                        } else {
                            alert.Alertasnueva("Ha ocurrido un error");
                        }
                    }
                } catch (NumberFormatException e) {
                    alert.Alertasnueva("Datos errados");
                }
                
            }
        }
        if (event.getSource() == modificar) {
            String idprov = idtxt.getText();
            String nombreprov = nombretxt.getText();
            String direccionprov = direcciontxt.getText();
            String telefonoprov = telefonotxt.getText();
            String correoprov = correotxt.getText();
            if (idprov.isEmpty() || nombreprov.isEmpty() || correoprov.isEmpty() || direccionprov.isEmpty() || telefonoprov.isEmpty()) {
                alert.Alertasnueva("Campos vacios");
            } else {
                try {
                    Proveedor prov = new Proveedor(Integer.parseInt(idprov), nombreprov, direccionprov, correoprov, Integer.parseInt(telefonoprov));
                    
                    if (prov.Editar(prov)) {
                        idtxt.setText("");
                        nombretxt.setText("");
                        direcciontxt.setText("");
                        correotxt.setText("");
                        telefonotxt.setText("");
                        alert.Alertasnueva("Se ha modificado");
                        idtxt.requestFocus();
                        Listar();
                        idtxt.setDisable(false);
                        modificar.setDisable(true);
                        guardar.setDisable(false);
                        
                    } else {
                        alert.Alertasnueva("Ha ocurrido un error");
                    }
                } catch (Exception e) {
                    alert.Alertasnueva("Datos errados");
                }
                
            }
            
        }
        if (event.getSource() == limpiar) {
            idtxt.setText("");
            nombretxt.setText("");
            direcciontxt.setText("");
            correotxt.setText("");
            telefonotxt.setText("");
            modificar.setDisable(true);
            guardar.setDisable(false);
            idtxt.setDisable(false);
        }
    }
    
    public void Listar() {
        Proveedor prod = new Proveedor();
        ObservableList<Proveedor> items = prod.getProveedor();
        this.tabla.setItems(items);
        System.out.println(items);
    }
    
    @FXML
    void ClickRow(MouseEvent event) {
        Proveedor ca = tabla.getSelectionModel().getSelectedItem();
        if (ca == null) {
            System.out.println("");
        } else {
            modificar.setDisable(false);
            guardar.setDisable(true);
            idtxt.setDisable(true);
            idtxt.setText(String.valueOf(ca.getID()));
            nombretxt.setText(ca.getNombre());
            direcciontxt.setText(String.valueOf(ca.getDireccion()));
            telefonotxt.setText(String.valueOf(ca.getTelefono()));
            correotxt.setText(ca.getCorreo());
            
        }
    }
    
    @FXML
    void Filtrar(KeyEvent event) {
        Proveedor cat = new Proveedor();
        ObservableList<Proveedor> i = cat.getProveedor();
        
        String buscador = this.buscador.getText();
        
        if (buscador.isEmpty()) {
            this.tabla.setItems(i);
            
        } else {
            filtro.clear();
            
            for (Proveedor ca : i) {
                if (ca.getNombre().toLowerCase().contains(buscador)) {
                    filtro.add(ca);
                }
            }
            this.tabla.setItems(filtro);
        }
    }
    
    @FXML
    void initialize() {
        modificar.setDisable(true);
        idtxt.requestFocus();
        this.id.setCellValueFactory(new PropertyValueFactory<>("ID"));
        this.nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        this.direccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        this.correo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        this.telefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        filtro = FXCollections.observableArrayList();
        Listar();
    }
    
}
