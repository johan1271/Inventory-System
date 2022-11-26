package Controlador;

import Modelo.CategoriaArticulo;
import Modelo.Producto;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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

public class ProductosController {

    ObservableList<Producto> filtro;
    ObservableList<CategoriaArticulo> filtrocat;
    Servicio alert = new Servicio();
    @FXML
    private TextField buscador;

    @FXML
    private TextField buscadorcat;
    @FXML
    private TableColumn<Producto, Number> cantidad;

    @FXML
    private TableColumn<Producto, String> categoria;

    @FXML
    private JFXComboBox<CategoriaArticulo> categoriacombo;

    @FXML
    private TableColumn<Producto, String> descripcion;

    @FXML
    private TextField descripciontxt;

    @FXML
    private JFXButton guardar;

    @FXML
    private JFXButton guardarcat;

    @FXML
    private TableColumn<Producto, Number> id;

    @FXML
    private JFXButton limpiar;

    @FXML
    private JFXButton modificar;

    @FXML
    private TextField nombrecat;

    @FXML
    private TableColumn<Producto, Number> precio;

    @FXML
    private TextField preciotxt;

    @FXML
    private TextField stocktxt;

    @FXML
    private TableColumn<Producto, Number> precioventa;

    @FXML
    private TableView<Producto> tabla;

    public void initcombos() {
        CategoriaArticulo cat = new CategoriaArticulo();
        ObservableList<CategoriaArticulo> ListaAr = cat.getCategoria();
        this.categoriacombo.setItems(ListaAr);
    }

    @FXML
    void Click(ActionEvent event) throws IOException {
        if (event.getSource() == guardar) {
            String desc = descripciontxt.getText();
            String prec = preciotxt.getText();
            String stock = stocktxt.getText();
            float preciov = 0;
            CategoriaArticulo categ = categoriacombo.getSelectionModel().getSelectedItem();
            Producto prod = new Producto();
            if (desc.isEmpty() || prec.isEmpty() || stock.isEmpty() || categ == null) {
                alert.Alertasnueva("Campos vacios");
            } else {

                if (prod.Existente(desc)) {
                    alert.Alertasnueva("Ya existe");
                } else {
                    try {
                        prod.setCategoria(categ);
                        prod.setPrecio(Integer.parseInt(prec));
                        prod.setStock(Integer.parseInt(stock));
                        prod.setDescripcion(desc);

                        if (prod.guardar(prod)) {
                            alert.Alertasnueva("Se ha guardado");
                            Listar();
                            descripciontxt.setText("");
                            preciotxt.setText("");
                            stocktxt.setText("");
                            buscador.setText("");
                            buscadorcat.setText("");
                            modificar.setDisable(true);
                            guardar.setDisable(false);
                            categoriacombo.getSelectionModel().clearSelection();
                            descripciontxt.requestFocus();
                        } else {
                            alert.Alertasnueva("Se produjo un error");
                        }
                    } catch (NumberFormatException e) {
                        alert.Alertasnueva("Datos errados");
                    }

                }

            }
        }

        if (event.getSource() == modificar) {
            Producto ca = tabla.getSelectionModel().getSelectedItem();
            String desc = descripciontxt.getText();
            String prec = preciotxt.getText();
            String stock = stocktxt.getText();
            CategoriaArticulo categ = categoriacombo.getSelectionModel().getSelectedItem();
            Producto prod = new Producto();

            if (desc.isEmpty() || prec.isEmpty() || stock.isEmpty() || categ == null) {
                alert.Alertasnueva("Campos vacios");
            } else {
                try {
                    prod.setId(ca.getId());
                    prod.setCategoria(categ);
                    prod.setPrecio(Integer.parseInt(prec));
                    prod.setStock(Integer.parseInt(stock));
                    prod.setDescripcion(desc);
                    prod.setPrecioventa(prod.getPrecioventa());
                    System.out.println(prod.getPrecioventa());
                    if (prod.Editar(prod)) {
                        alert.Alertasnueva("Se ha modificado");
                        modificar.setDisable(true);
                        guardar.setDisable(false);
                        buscador.setText("");
                        descripciontxt.setText("");
                        preciotxt.setText("");
                        stocktxt.setText("");
                        categoriacombo.getSelectionModel().clearSelection();
                        descripciontxt.requestFocus();
                        buscadorcat.setText("");
                        Listar();
                    } else {
                        alert.Alertasnueva("Ocurrio un error");
                    }
                } catch (NumberFormatException e) {
                    alert.Alertasnueva("Datos errados");
                }

            }
        }
        if (event.getSource() == limpiar) {
            guardar.setDisable(false);
            modificar.setDisable(true);
            buscador.setText("");
            descripciontxt.setText("");
            preciotxt.setText("");
            stocktxt.setText("");
            buscadorcat.setText("");
            categoriacombo.getSelectionModel().clearSelection();
            descripciontxt.requestFocus();
            Listar();
        }

        if (event.getSource() == guardarcat) {
            String nombrecategoria = nombrecat.getText();
            CategoriaArticulo a = new CategoriaArticulo();
            if (nombrecategoria.isEmpty()) {
                alert.Alertasnueva("Campos vacios");
            } else {

                if (a.Existente(nombrecategoria)) {
                    alert.Alertasnueva("Ya existe");
                } else {
                    a.setNombre(nombrecategoria);
                    if (a.guardar(a)) {
                        nombrecat.setText("");
                        alert.Alertasnueva("Se ha guardado");
                        nombrecat.requestFocus();

                        Listar();
                        initcombos();
                    } else {
                        alert.Alertasnueva("Ocurrio un error");
                    }
                }

            }
        }
    }

    @FXML
    void Filtrar(KeyEvent event) {
        Producto cat = new Producto();
        ObservableList<Producto> i = cat.getProductos();

        String buscador = this.buscador.getText();

        if (buscador.isEmpty()) {
            this.tabla.setItems(i);

        } else {
            filtro.clear();

            for (Producto ca : i) {
                if (ca.getDescripcion().toLowerCase().contains(buscador)) {
                    filtro.add(ca);
                }
            }
            this.tabla.setItems(filtro);
        }
    }

    @FXML
    void ClickRow(MouseEvent event) throws IOException {

        Producto ca = tabla.getSelectionModel().getSelectedItem();
        if (ca == null) {
            System.out.println("");
        } else {
            modificar.setDisable(false);
            guardar.setDisable(true);
            descripciontxt.setText(ca.getDescripcion());
            preciotxt.setText(String.valueOf(ca.getPrecio()));
            stocktxt.setText(String.valueOf(ca.getStock()));

            CategoriaArticulo cat = new CategoriaArticulo();
            ObservableList<CategoriaArticulo> ListaAr = cat.getCategoria();

            int cont = 0;

            for (int i = 0; i < ListaAr.size(); i++) {
                if (ListaAr.get(i).getNombre().equals(ca.getCategoria().getNombre())) {
                    cont = i;
                }
            }

            categoriacombo.getSelectionModel().clearAndSelect(cont);
            buscadorcat.setText(ca.getCategoria().getNombre());

        }
    }

    public void Listar() {
        Producto prod = new Producto();
        ObservableList<Producto> items = prod.getProductos();
        this.tabla.setItems(items);
    }

    @FXML
    void Filtrarcat(KeyEvent event) {
        CategoriaArticulo cat = new CategoriaArticulo();
        ObservableList<CategoriaArticulo> i = cat.getCategoria();

        String busca = this.buscadorcat.getText();

        if (busca.isEmpty()) {
            this.categoriacombo.setItems(i);

        } else {
            filtrocat.clear();

            for (CategoriaArticulo ca : i) {
                if (ca.getNombre().toLowerCase().contains(busca)) {
                    filtrocat.add(ca);
                }
            }
            this.categoriacombo.setItems(filtrocat);
        }
    }

    @FXML
    void initialize() {
        initcombos();
        this.id.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.descripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        this.categoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        this.cantidad.setCellValueFactory(new PropertyValueFactory<>("stock"));
        this.precio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        this.precioventa.setCellValueFactory(new PropertyValueFactory<>("precioventa"));
        modificar.setDisable(true);
        filtro = FXCollections.observableArrayList();
        filtrocat = FXCollections.observableArrayList();
        Listar();
    }

}
