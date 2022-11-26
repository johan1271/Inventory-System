/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.CategoriaArticulo;
import Modelo.DetalleFactura;
import Modelo.Factura;
import Modelo.Producto;
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

public class BuscarFacturaController {

    Servicio alert = new Servicio();
    ObservableList<Factura> filtro;
    @FXML
    private JFXButton btnanularfac;

    @FXML
    private TextField buscador;

    @FXML
    private TableColumn cantidad;

    @FXML
    private TableColumn clientefactura;

    @FXML
    private TableColumn descripcion;

    @FXML
    private TableColumn estadofactura;

    @FXML
    private TableColumn fechafactura;

    @FXML
    private TableColumn idarticulo;

    @FXML
    private TableColumn idfactura;

    @FXML
    private TableColumn metodofactura;

    @FXML
    private TableColumn preciototal;

    @FXML
    private TableColumn preciounidad;

    @FXML
    private TableView<DetalleFactura> tabladetalle;

    @FXML
    private TableView<Factura> tablafactura;

    @FXML
    private TableColumn vendedorfactura;

    @FXML
    void CLickRow(MouseEvent event) throws SQLException, IOException {
        if (tablafactura.getSelectionModel().getSelectedItem() != null) {
            DetalleFactura cat = new DetalleFactura();
            Factura fa = tablafactura.getSelectionModel().getSelectedItem();
            ObservableList<DetalleFactura> items = cat.getDetalles();
            ObservableList<DetalleFactura> l = FXCollections.observableArrayList();

            for (int i = 0; i < items.size(); i++) {
                if (items.get(i).getFactura().getId() == fa.getId()) {

                    DetalleFactura n = items.get(i);
                    l.add(n);
                    tabladetalle.setItems(l);

                    /*
                ObservableList<DetalleFactura> detalle;
                detalle = FXCollections.observableList(items.get(i));*/
                }
            }

            if (fa.getEstado() == false) {
                btnanularfac.setDisable(true);
            } else {
                btnanularfac.setDisable(false);
            }

            this.descripcion.setCellValueFactory(new PropertyValueFactory<>("articulo"));
            this.idarticulo.setCellValueFactory(new PropertyValueFactory<>("IDart"));
            this.cantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
            this.preciototal.setCellValueFactory(new PropertyValueFactory<>("precio"));
            this.preciounidad.setCellValueFactory(new PropertyValueFactory<>("preciou"));
        }

    }

    @FXML
    void Filtrar(KeyEvent event) {
        Factura factura = new Factura();
        ObservableList<Factura> items = factura.getFacturas();
        String buscador = this.buscador.getText();

        if (buscador.isEmpty()) {
            this.tablafactura.setItems(items);

        } else {
            filtro.clear();

            for (Factura f : items) {
                String IDstring = Integer.toString(f.getId());
                if (IDstring.contains(buscador)) {
                    filtro.add(f);
                }
            }
            this.tablafactura.setItems(filtro);
        }
    }

    @FXML
    void anular(ActionEvent event) throws IOException {

        Factura fa = tablafactura.getSelectionModel().getSelectedItem();
        if (fa != null) {
            Producto producto = new Producto();
            DetalleFactura cat = new DetalleFactura();
            ObservableList<DetalleFactura> items = cat.getDetalles();
            ObservableList<DetalleFactura> l = FXCollections.observableArrayList();

            for (int i = 0; i < items.size(); i++) {
                if (items.get(i).getFactura().getId() == fa.getId()) {

                    DetalleFactura n = items.get(i);
                    l.add(n);

                    /*
                ObservableList<DetalleFactura> detalle;
                detalle = FXCollections.observableList(items.get(i));*/
                }
            }
            if (fa.Anular(fa)) {
                if (producto.SumarInventario(l)) {
                    alert.Alertasnueva("Se ha anulado la factura");
                    btnanularfac.setDisable(true);
                    Listar();
                    buscador.setText("");
                }

            }
        }

    }

    public void Listar() {
        Factura cat = new Factura();
        ObservableList<Factura> items = cat.getFacturas();
        this.tablafactura.setItems(items);

    }

    @FXML
    public void initialize() {
        this.idfactura.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.fechafactura.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        this.clientefactura.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        this.vendedorfactura.setCellValueFactory(new PropertyValueFactory<>("vendedor"));
        this.metodofactura.setCellValueFactory(new PropertyValueFactory<>("metodo_pago"));
        this.estadofactura.setCellValueFactory(new PropertyValueFactory<>("estado"));

        filtro = FXCollections.observableArrayList();
        Listar();
        btnanularfac.setDisable(true);
    }

}
