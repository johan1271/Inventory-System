/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controlador;

import Modelo.CategoriaArticulo;
import Modelo.Cliente;
import Modelo.DetalleFactura;
import Modelo.Factura;
import Modelo.Producto;
import Modelo.Usuarios;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author SP
 */
public class FacturaController implements Initializable {

    private ObservableList<String> metodosdepago = FXCollections.observableArrayList();
    ; 
    ObservableList<Producto> filtro;
    ObservableList<Usuarios> filtrovend;
    Servicio alert = new Servicio();
    ObservableList<Producto> articulos;
    ObservableList<DetalleFactura> ListaDetalle;
    ObservableList<Producto> filtroprod;
    ObservableList<Cliente> filtrocli;
    @FXML
    private JFXButton btnagregarprod;

    @FXML
    private TextField buscarcliente;
    @FXML
    private JFXButton btncalcular;

    @FXML
    private TextField buscarvendedor;

    @FXML
    private DatePicker fechatxt;

    @FXML
    private JFXButton crearfactura;

    @FXML
    private Label preciotoaltxt;

    @FXML
    private TextField stock;

    @FXML
    private JFXComboBox<Cliente> combocliente;

    @FXML
    private JFXComboBox<String> combometodo;

    @FXML
    private JFXComboBox<Usuarios> combovendedor;

    @FXML
    private JFXButton btneliminarprodu;

    @FXML
    private JFXButton btnmodificarprod;

    @FXML
    private TextField buscadorclientes;

    @FXML
    private TextField buscaprodu;

    @FXML
    private TextField cantidadP;

    @FXML
    private TableColumn cantidadart;

    @FXML
    private TextField categoriaP;

    @FXML
    private TableColumn categoriaart;

    @FXML
    private JFXComboBox<Producto> comboproducto;

    @FXML
    private TableColumn descripcionart;

    @FXML
    private TableColumn idarticulo;

    @FXML
    private JFXButton limpiar;

    @FXML
    private TextField precioP;

    @FXML
    private TableView<Producto> tablaclientes;

    @FXML
    private TableColumn valort;

    @FXML
    private TableColumn valoruni;

    @FXML
    void Filtrarprod(KeyEvent event) {
        Producto cat = new Producto();
        ObservableList<Producto> i = cat.getProductos();

        String busca = this.buscaprodu.getText();

        if (busca.isEmpty()) {
            this.comboproducto.setItems(i);

        } else {
            filtroprod.clear();

            for (Producto ca : i) {
                if (ca.getDescripcion().toLowerCase().contains(busca)) {
                    filtroprod.add(ca);
                }
            }
            this.comboproducto.setItems(filtroprod);
        }
    }

    public void initcombos() {
        Producto cat = new Producto();
        ObservableList<Producto> Lista = cat.getProductos();
        this.comboproducto.setItems(Lista);

        Cliente cli = new Cliente();
        ObservableList<Cliente> Listacli = cli.getClientes();
        this.combocliente.setItems(Listacli);

        this.combometodo.setItems(metodosdepago);

        Usuarios u = new Usuarios();
        ObservableList<Usuarios> Listau = u.getUsuarios();
        this.combovendedor.setItems(Listau);

    }

    @FXML
    void setear(ActionEvent event) {

        try {
            Producto p = comboproducto.getSelectionModel().getSelectedItem();
            ObservableList<Producto> ListaP = p.getProductos();

            for (int i = 0; i < ListaP.size(); i++) {
                if (ListaP.get(i).getDescripcion().equals(p.getDescripcion())) {
                    buscaprodu.setText(ListaP.get(i).getDescripcion());
                    precioP.setText(String.valueOf(ListaP.get(i).getPrecioventa()));
                    categoriaP.setText(ListaP.get(i).getCategoria().getNombre());
                    stock.setText(String.valueOf(ListaP.get(i).getStock()));
                }
            }
        } catch (Exception e) {
            System.out.println("");
        }

    }

    @FXML
    void click(ActionEvent event) throws IOException {

        if (event.getSource() == btnagregarprod) {
            DetalleFactura deta = new DetalleFactura();
            Producto pr = new Producto();

            CategoriaArticulo cat = new CategoriaArticulo();

            ObservableList<CategoriaArticulo> Listact = cat.getCategoria();

            ObservableList<Producto> ListaP = pr.getProductos();
            if (buscaprodu.getText().isEmpty() || precioP.getText().isEmpty() || cantidadP.getText().isEmpty() || categoriaP.getText().isEmpty()) {
                alert.Alertasnueva("Campos vacios");
            } else {
                boolean existe = false;
                for (Producto ca : articulos) {
                    if (ca.getDescripcion().toLowerCase().contains(buscaprodu.getText())) {
                        existe = true;
                    }
                }
                if (existe) {
                    alert.Alertasnueva("Ya existe");
                } else {
                    try {
                        if (Integer.parseInt(cantidadP.getText()) > Integer.parseInt(stock.getText())) {
                            alert.Alertasnueva("No hay suficiente");
                        } else {

                            int precio = 0, precioT;
                            for (int i = 0; i < ListaP.size(); i++) {
                                if (ListaP.get(i).getDescripcion().equals(buscaprodu.getText())) {
                                    pr.setId(ListaP.get(i).getId());
                                    pr.setPrecio(ListaP.get(i).getPrecioventa());
                                    precio = ListaP.get(i).getPrecioventa();
                                    pr.setStock(ListaP.get(i).getStock());
                                }
                            }
                            for (int i = 0; i < Listact.size(); i++) {
                                if (Listact.get(i).getNombre().equals(categoriaP.getText())) {
                                    cat.setId(Listact.get(i).getId());
                                    cat.setNombre(Listact.get(i).getNombre());
                                }
                            }
                            pr.setDescripcion(buscaprodu.getText());
                            pr.setCategoria(cat);
                            pr.setCantidad(Integer.parseInt(cantidadP.getText()));
                            precioT = precio * Integer.parseInt(cantidadP.getText());
                            pr.setTotal(precioT);

                            deta.setArticulo(pr);
                            deta.setCantidad(Integer.parseInt(cantidadP.getText()));
                            deta.setPrecio(precioT);
                            deta.setPreciou(precio);

                            ListaDetalle.add(deta);

                            articulos.add(pr);
                            tablaclientes.setItems(articulos);

                            alert.Alertasnueva("Producto agregado");
                            System.out.println(articulos);

                            //editar la cantidad del producto
                            buscaprodu.setText("");
                            comboproducto.getSelectionModel().clearSelection();
                            precioP.setText("");
                            cantidadP.setText("");
                            categoriaP.setText("");
                            stock.setText("");

                        }
                    } catch (NumberFormatException e) {
                        alert.Alertasnueva("Datos errados");
                    }

                }

            }

        }
        if (event.getSource() == btnmodificarprod) {
            Producto pr = new Producto();
            CategoriaArticulo cat = new CategoriaArticulo();
            DetalleFactura det = new DetalleFactura();
            ObservableList<CategoriaArticulo> Listact = cat.getCategoria();
            ObservableList<Producto> ListaP = pr.getProductos();
            if (cantidadP.getText().isEmpty()) {
                alert.Alertasnueva("Campos vacios");
            } else {
                try {
                    if (Integer.parseInt(cantidadP.getText()) > Integer.parseInt(stock.getText())) {
                        alert.Alertasnueva("No hay suficiente");
                    } else {

                        int precio = 0, precioT;
                        for (int i = 0; i < ListaP.size(); i++) {
                            if (ListaP.get(i).getDescripcion().equals(buscaprodu.getText())) {
                                pr.setId(ListaP.get(i).getId());
                                pr.setPrecio(ListaP.get(i).getPrecioventa());
                                precio = ListaP.get(i).getPrecioventa();
                            }
                        }
                        for (int i = 0; i < Listact.size(); i++) {
                            if (Listact.get(i).getNombre().equals(categoriaP.getText())) {
                                cat.setId(Listact.get(i).getId());
                                cat.setNombre(Listact.get(i).getNombre());
                            }
                        }
                        pr.setDescripcion(buscaprodu.getText());
                        pr.setCategoria(cat);
                        pr.setCantidad(Integer.parseInt(cantidadP.getText()));
                        precioT = precio * Integer.parseInt(cantidadP.getText());

                        pr.setTotal(precioT);
                        int cont = 0;
                        for (int i = 0; i < articulos.size(); i++) {
                            if (articulos.get(i).getId() == pr.getId()) {
                                cont = i;
                            }
                        }

                        articulos.set(cont, pr);
                        //Falta por hacer
                        det.setArticulo(pr);
                        det.setCantidad(Integer.parseInt(cantidadP.getText()));
                        det.setPrecio(precioT);
                        det.setPreciou(precio);
                        int contD = 0;
                        for (int i = 0; i < ListaDetalle.size(); i++) {
                            if (ListaDetalle.get(i).getArticulo().getDescripcion().equals(pr.getDescripcion())) {
                                contD = i;
                            }
                        }
                        ListaDetalle.set(contD, det);
                        alert.Alertasnueva("Se ha modificado");
                       
                        buscaprodu.setText("");
                        comboproducto.getSelectionModel().clearSelection();
                        precioP.setText("");
                        cantidadP.setText("");
                        categoriaP.setText("");
                        btnagregarprod.setDisable(false);
                        btnmodificarprod.setDisable(true);
                        buscaprodu.setDisable(false);
                        comboproducto.setDisable(false);
                        tablaclientes.refresh();
                        stock.setText("");

                        //tablaclientes.setItems(articulos);
                    }
                } catch (NumberFormatException e) {
                    alert.Alertasnueva("Datos errados");
                }

            }
        }
        if (event.getSource() == limpiar) {
            buscaprodu.setText("");
            comboproducto.getSelectionModel().clearSelection();
            precioP.setText("");
            cantidadP.setText("");
            categoriaP.setText("");
            btnagregarprod.setDisable(false);
            btnmodificarprod.setDisable(true);
            buscaprodu.setDisable(false);
            comboproducto.setDisable(false);
            stock.setText("");
            System.out.println(buscaprodu.getText());
            initcombos();
        }

        if (event.getSource() == btneliminarprodu) {
            Producto pr = new Producto();
            int cont = 0, contD = 0;
            if (tablaclientes.getSelectionModel() == null) {
                alert.Alertasnueva("No has seleccionado");
            } else {
                if (articulos.isEmpty()) {
                    alert.Alertasnueva("No hay articulos");
                } else {
                    for (int i = 0; i < articulos.size(); i++) {
                        if (articulos.get(i).getId() == pr.getId()) {
                            cont = i;
                        }
                    }
                    articulos.remove(cont);

                    for (int i = 0; i < ListaDetalle.size(); i++) {
                        if (ListaDetalle.get(i).getArticulo().getDescripcion().equals(pr.getDescripcion())) {
                            contD = i;
                        }
                    }
                    ListaDetalle.remove(contD);
                    System.out.println(ListaDetalle);
                    alert.Alertasnueva("Se ha eliminado");
                    buscaprodu.setText("");
                    comboproducto.getSelectionModel().clearSelection();
                    precioP.setText("");
                    cantidadP.setText("");
                    categoriaP.setText("");
                    stock.setText("");
                    btnagregarprod.setDisable(false);
                    btnmodificarprod.setDisable(true);
                    buscaprodu.setDisable(false);
                    comboproducto.setDisable(false);
                    initcombos();

                }
            }

        }

        if (event.getSource() == crearfactura) {

            if (articulos.isEmpty()) {
                alert.Alertasnueva("No hay articulos");
            } else {
                LocalDate fecha = fechatxt.getValue();
                Cliente cliente = combocliente.getSelectionModel().getSelectedItem();
                String metodopago = combometodo.getSelectionModel().getSelectedItem();
                Usuarios vendedor = combovendedor.getSelectionModel().getSelectedItem();

                if (fecha == null || cliente == null || metodopago.isEmpty() || vendedor == null) {
                    alert.Alertasnueva("Campos vacios");
                } else {

                    Factura fac = new Factura();

                    DetalleFactura df = new DetalleFactura();
                    fac.setCliente(cliente);
                    fac.setEstado(true);
                    fac.setMetodo_pago(metodopago);
                    fac.setFecha(fecha.toString());
                    fac.setVendedor(vendedor);

                    if (fac.guardar(fac)) {
                        if (df.guardar(ListaDetalle)) {
                            alert.Alertasnueva("Se ha creado la factura");

                            Producto update = new Producto();
                            if (update.ActualizarIventario(articulos)) {
                                System.out.println(articulos);
                                crearfactura.setDisable(true);
                                combovendedor.getSelectionModel().clearSelection();
                                combometodo.getSelectionModel().clearSelection();
                                combocliente.getSelectionModel().clearSelection();
                                buscarcliente.setText("");
                                buscarvendedor.setText("");
                                preciotoaltxt.setText("0");
                                fechatxt.setValue(null);
                                buscaprodu.setText("");
                                comboproducto.getSelectionModel().clearSelection();
                                precioP.setText("");
                                cantidadP.setText("");
                                categoriaP.setText("");
                                stock.setText("");
                                btnagregarprod.setDisable(false);
                                btnmodificarprod.setDisable(true);
                                buscaprodu.setDisable(false);
                                comboproducto.setDisable(false);
                                Listar();
                            }

                        }

                    }

                }

            }
        }
        if (event.getSource() == btncalcular) {
            if (articulos.isEmpty()) {
                alert.Alertasnueva("No hay articulos");
            } else {
                LocalDate fecha = fechatxt.getValue();
                Cliente cliente = combocliente.getSelectionModel().getSelectedItem();
                String metodopago = combometodo.getSelectionModel().getSelectedItem();
                Usuarios vendedor = combovendedor.getSelectionModel().getSelectedItem();

                if (fecha == null || cliente == null || metodopago.isEmpty() || vendedor == null) {
                    alert.Alertasnueva("Campos vacios");
                } else {
                    double totalC = 0;
                    for (int i = 0; i < articulos.size(); i++) {
                        totalC = totalC + articulos.get(i).getTotal();
                        preciotoaltxt.setText(String.valueOf(totalC));
                        crearfactura.setDisable(false);

                    }

                }

            }
        }
    }

    @FXML
    void ClickRow(MouseEvent event) throws IOException {

        Producto ca = tablaclientes.getSelectionModel().getSelectedItem();
        if (ca == null) {
            System.out.println("");
            btneliminarprodu.setDisable(true);
        } else {
            btnmodificarprod.setDisable(false);
            btnagregarprod.setDisable(true);

            buscaprodu.setText(ca.getDescripcion());

            precioP.setText(String.valueOf(ca.getPrecio()));
            cantidadP.setText(String.valueOf(ca.getCantidad()));
            categoriaP.setText(ca.getCategoria().getNombre());

            Producto cat = new Producto();
            ObservableList<Producto> L = cat.getProductos();
            int cont = 0;

            for (int i = 0; i < L.size(); i++) {

                if (L.get(i).getDescripcion().equals(ca.getDescripcion())) {
                    cont = i;

                }
            }

            comboproducto.getSelectionModel().clearAndSelect(cont);
            buscaprodu.setDisable(true);
            comboproducto.setDisable(true);
            cantidadP.requestFocus();
            btneliminarprodu.setDisable(false);
        }
    }

    @FXML
    void Filtrar(KeyEvent event) {
        Producto clie = new Producto();

        String buscador = buscadorclientes.getText();

        if (buscador.isEmpty()) {
            this.tablaclientes.setItems(articulos);

        } else {
            filtro.clear();

            for (Producto cliente : articulos) {
                if (cliente.getDescripcion().toLowerCase().contains(buscador)) {
                    filtro.add(cliente);
                }
            }
            this.tablaclientes.setItems(filtro);
        }

    }

    @FXML
    void filtrocliente(KeyEvent event) {
        Cliente cat = new Cliente();
        ObservableList<Cliente> i = cat.getClientes();

        String busca = this.buscarcliente.getText();

        if (busca.isEmpty()) {
            this.combocliente.setItems(i);

        } else {
            filtrocli.clear();

            for (Cliente ca : i) {
                if (ca.getNombre().toLowerCase().contains(busca)) {
                    filtrocli.add(ca);
                }
            }
            this.combocliente.setItems(filtrocli);
        }
    }

    @FXML
    void filtrovend(KeyEvent event) {
        Usuarios cat = new Usuarios();
        ObservableList<Usuarios> i = cat.getUsuarios();

        String busca = this.buscarvendedor.getText();

        if (busca.isEmpty()) {
            this.combovendedor.setItems(i);

        } else {
            filtrovend.clear();

            for (Usuarios ca : i) {
                if (ca.getNombre().toLowerCase().contains(busca)) {
                    filtrovend.add(ca);
                }
            }
            this.combovendedor.setItems(filtrovend);
        }
    }

    public void Listar() {
        articulos = FXCollections.observableArrayList();
        this.idarticulo.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.descripcionart.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        this.categoriaart.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        this.cantidadart.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        this.valoruni.setCellValueFactory(new PropertyValueFactory<>("precio"));
        this.valort.setCellValueFactory(new PropertyValueFactory<>("total"));
        ListaDetalle = FXCollections.observableArrayList();
        tablaclientes.setItems(articulos);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initcombos();
        filtroprod = FXCollections.observableArrayList();
        categoriaP.setDisable(true);
        precioP.setDisable(true);

        btnmodificarprod.setDisable(true);

        filtro = FXCollections.observableArrayList();
        filtrocli = FXCollections.observableArrayList();
        filtrovend = FXCollections.observableArrayList();
        Listar();
        metodosdepago.add("Contado");
        metodosdepago.add("Credito");
        stock.setDisable(true);
        crearfactura.setDisable(true);
        btneliminarprodu.setDisable(true);
    }

}
