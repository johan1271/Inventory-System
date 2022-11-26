package Controlador;

import Modelo.Cargo;
import Modelo.Usuarios;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class UsuariosController {

    ObservableList<Usuarios> filtro;
    Servicio alert = new Servicio();
    @FXML
    private JFXRadioButton admin;

    @FXML
    private TableColumn<Usuarios, String> apellido;

    @FXML
    private TextField apellidotxt;

    @FXML
    private TextField buscador;

    @FXML
    private TableColumn<Usuarios, String> cargo;

    @FXML
    private ToggleGroup cargotoggle;

    @FXML
    private TextField cedulatxt;

    @FXML
    private TableColumn<Usuarios, String> contra;

    @FXML
    private TextField contratxt;

    @FXML
    private TableColumn<Usuarios, String> direccion;

    @FXML
    private TextField direcciontxt;

    @FXML
    private TableColumn<Usuarios, Number> edad;

    @FXML
    private TextField edadtxt;

    @FXML
    private JFXRadioButton empleado;

    @FXML
    private JFXRadioButton femenino;

    @FXML
    private ToggleGroup gentoggle;

    @FXML
    private JFXButton guardar;

    @FXML
    private TableColumn<Usuarios, Number> id;

    @FXML
    private JFXButton limpiar;

    @FXML
    private JFXRadioButton masculino;

    @FXML
    private JFXButton modificar;

    @FXML
    private TableColumn<Usuarios, String> nombre;

    @FXML
    private TextField nombretxt;

    @FXML
    private TableColumn<Usuarios, String> sexo;

    @FXML
    private TableView<Usuarios> tabla;

    @FXML
    private TableColumn<Usuarios, Number> telefono;

    @FXML
    private TextField telefonotxt;

    @FXML
    void Click(ActionEvent event) throws IOException {
        if (event.getSource() == guardar) {
            String cedulau = cedulatxt.getText();
            String nombreu = nombretxt.getText();
            String apellidou = apellidotxt.getText();
            String edadu = edadtxt.getText();
            String telefonou = telefonotxt.getText();
            String direccionu = direcciontxt.getText();
            String contrau = contratxt.getText();
            String genu = "";
            String cargou = "";
            if (femenino.isSelected()) {
                genu = "Femenino";
            } else if (masculino.isSelected()) {
                genu = "Masculino";
            }

            if (admin.isSelected()) {
                cargou = "administrador";
            } else if (empleado.isSelected()) {
                cargou = "empleado";
            }
            if (nombreu.isEmpty() || apellidou.isEmpty() || edadu.isEmpty() || telefonou.isEmpty() || cedulau.isEmpty()
                    || direccionu.isEmpty() || genu.isEmpty() || contrau.isEmpty() || cargou.isEmpty()) {

                alert.Alertasnueva("Campos vacios");

            } else {
                Usuarios usu = new Usuarios();
                try {
                    if (usu.Existente(Integer.parseInt(cedulau))) {
                        alert.Alertasnueva("Ya existe");
                    } else {

                        Cargo c = new Cargo();
                        ObservableList<Cargo> Lista = c.getCargo();
                        int idc = 0;
                        for (int i = 0; i < Lista.size(); i++) {
                            if (Lista.get(i).getNombre().equals(cargou)) {
                                idc = Lista.get(i).getId();
                            }
                        }
                        c.setId(idc);
                        c.setNombre(cargou);
                        usu.setCedula(Integer.parseInt(cedulau));
                        usu.setNombre(nombreu);
                        usu.setApellido(apellidou);
                        usu.setEdad(Integer.parseInt(edadu));
                        usu.setDireccion(direccionu);
                        usu.setSexo(genu);
                        usu.setTelefono(Integer.parseInt(telefonou));
                        usu.setCargo(c);
                        usu.setContra(contrau);

                        if (usu.guardar(usu)) {
                            cedulatxt.setText("");
                            nombretxt.setText("");
                            apellidotxt.setText("");
                            edadtxt.setText("");
                            telefonotxt.setText("");
                            direcciontxt.setText("");
                            contratxt.setText("");
                            femenino.setSelected(false);
                            masculino.setSelected(false);
                            admin.setSelected(false);
                            empleado.setSelected(false);
                            alert.Alertasnueva("Se ha guardado");
                            cedulatxt.requestFocus();
                            Listar();
                        } else {
                            alert.Alertasnueva("Ocurrio un error");
                        }

                    }
                } catch (NumberFormatException e) {
                    alert.Alertasnueva("Datos errados");
                }

            }

        }
        if (event.getSource() == modificar) {

            String cedulau = cedulatxt.getText();
            String nombreu = nombretxt.getText();
            String apellidou = apellidotxt.getText();
            String edadu = edadtxt.getText();
            String telefonou = telefonotxt.getText();
            String direccionu = direcciontxt.getText();
            String contrau = contratxt.getText();
            String genu = "";
            String cargou = "";
            if (femenino.isSelected()) {
                genu = "Femenino";
            } else if (masculino.isSelected()) {
                genu = "Masculino";
            }

            if (admin.isSelected()) {
                cargou = "administrador";
            } else if (empleado.isSelected()) {
                cargou = "empleado";
            }
            if (nombreu.isEmpty() || apellidou.isEmpty() || edadu.isEmpty() || telefonou.isEmpty() || cedulau.isEmpty()
                    || direccionu.isEmpty() || genu.isEmpty() || contrau.isEmpty() || cargou.isEmpty()) {

                alert.Alertasnueva("Campos vacios");

            } else {
                try {
                    Usuarios usu = new Usuarios();
                    Cargo c = new Cargo();
                    ObservableList<Cargo> Lista = c.getCargo();
                    int idc = 0;
                    for (int i = 0; i < Lista.size(); i++) {
                        if (Lista.get(i).getNombre().equals(cargou)) {
                            idc = Lista.get(i).getId();
                        }
                    }

                    c.setId(idc);
                    c.setNombre(cargou);
                    usu.setCedula(Integer.parseInt(cedulau));
                    usu.setNombre(nombreu);
                    usu.setApellido(apellidou);
                    usu.setEdad(Integer.parseInt(edadu));
                    usu.setDireccion(direccionu);
                    usu.setSexo(genu);
                    usu.setTelefono(Integer.parseInt(telefonou));
                    usu.setCargo(c);
                    usu.setContra(contrau);
                    if (usu.Editar(usu)) {
                        cedulatxt.setText("");
                        nombretxt.setText("");
                        apellidotxt.setText("");
                        edadtxt.setText("");
                        telefonotxt.setText("");
                        direcciontxt.setText("");
                        contratxt.setText("");
                        femenino.setSelected(false);
                        masculino.setSelected(false);
                        admin.setSelected(false);
                        empleado.setSelected(false);

                        cedulatxt.requestFocus();

                        modificar.setDisable(true);
                        guardar.setDisable(false);
                        cedulatxt.setDisable(false);
                        this.buscador.setText("");
                        alert.Alertasnueva("Se ha modificado");
                        Listar();

                    } else {
                        System.out.println("aqui");
                        alert.Alertasnueva("Ocurrio un error");
                    }
                } catch (NumberFormatException e) {
                    alert.Alertasnueva("Datos errados");
                }

            }
        }

        if (event.getSource() == limpiar) {
            cedulatxt.setText("");
            nombretxt.setText("");
            apellidotxt.setText("");
            edadtxt.setText("");
            telefonotxt.setText("");
            direcciontxt.setText("");
            contratxt.setText("");
            femenino.setSelected(false);
            masculino.setSelected(false);
            admin.setSelected(false);
            empleado.setSelected(false);
            cedulatxt.requestFocus();
            modificar.setDisable(true);
            guardar.setDisable(false);
            cedulatxt.setDisable(false);
            Listar();

        }

    }

    public void Listar() {
        Usuarios usu = new Usuarios();
        ObservableList<Usuarios> items = usu.getUsuarios();

        this.tabla.setItems(items);
    }

    @FXML
    void ClickRow(MouseEvent event) {
        Usuarios c1 = tabla.getSelectionModel().getSelectedItem();
        if (c1 == null) {
            System.out.println("");
        } else {
            modificar.setDisable(false);
            guardar.setDisable(true);
            if (c1.getSexo().equals("Femenino")) {
                femenino.setSelected(true);
            } else if (c1.getSexo().equals("Masculino")) {
                masculino.setSelected(true);
            }

            if (c1.getCargo().getNombre().equals("empleado")) {
                empleado.setSelected(true);
            } else if (c1.getCargo().getNombre().equals("administrador")) {
                admin.setSelected(true);
            }
            nombretxt.setText(c1.getNombre());
            apellidotxt.setText(c1.getApellido());
            cedulatxt.setText(String.valueOf(c1.getCedula()));
            edadtxt.setText(String.valueOf(c1.getEdad()));
            telefonotxt.setText(String.valueOf(c1.getTelefono()));
            direcciontxt.setText(c1.getDireccion());
            contratxt.setText(String.valueOf(c1.getContra()));
            cedulatxt.setDisable(true);
        }
    }

    @FXML
    void Filtrar(KeyEvent event) {
        Usuarios clie = new Usuarios();
        ObservableList<Usuarios> i = clie.getUsuarios();

        String busca = buscador.getText();

        if (busca.isEmpty()) {
            this.tabla.setItems(i);

        } else {
            filtro.clear();

            for (Usuarios usu : i) {
                String cedstring = Integer.toString(usu.getCedula());
                if (usu.getNombre().toLowerCase().contains(busca) || cedstring.contains(busca)) {
                    filtro.add(usu);
                }
            }
            this.tabla.setItems(filtro);
        }
    }

    @FXML
    void initialize() {
        this.id.setCellValueFactory(new PropertyValueFactory<>("cedula"));
        this.nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        this.apellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        this.edad.setCellValueFactory(new PropertyValueFactory<>("edad"));
        this.sexo.setCellValueFactory(new PropertyValueFactory<>("sexo"));
        this.telefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        this.direccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        this.cargo.setCellValueFactory(new PropertyValueFactory<>("cargo"));
        this.contra.setCellValueFactory(new PropertyValueFactory<>("contra"));
        filtro = FXCollections.observableArrayList();

        Listar();
        modificar.setDisable(true);
    }

}
