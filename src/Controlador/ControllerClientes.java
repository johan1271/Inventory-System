/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Cliente;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Caicedo
 */
public class ControllerClientes implements Initializable {

    ObservableList<Cliente> filtro;
    Servicio alerta = new Servicio();

    @FXML
    private TextField aggapellidocli;

    @FXML
    private TextField aggcedulacli;

    @FXML
    private TextField aggdireccioncli;

    @FXML
    private TextField aggedadcli;

    @FXML
    private TextField aggnombrecli;

    @FXML
    private TextField aggsaldocli;

    @FXML
    private TextField aggtelcli;

    @FXML
    private ToggleGroup sexo;

    @FXML
    private JFXButton limpiar;

    @FXML
    private JFXButton modificar;

    @FXML
    private JFXButton guardar;

    @FXML
    private TextField buscadorclientes;

    @FXML
    private TableColumn<Cliente, String> direccionclientes;

    @FXML
    private TableColumn<Cliente, Integer> edadclientes;

    @FXML
    private TableColumn<Cliente, Number> idclientes;

    @FXML
    private TableColumn<Cliente, String> nombreclientes;

    @FXML
    private TableColumn<Cliente, String> apellidoclientes;

    @FXML
    private TableColumn<Cliente, Number> saldoclientes;

    @FXML
    private TableColumn<Cliente, String> sexoclientes;

    @FXML
    private TableView<Cliente> tablaclientes;

    @FXML
    private JFXRadioButton masculino;

    @FXML
    private JFXRadioButton femenino;
    @FXML
    private TableColumn<Cliente, Integer> telefonoclientes;

    public void Click(javafx.event.ActionEvent event) throws IOException {

        if (event.getSource() == guardar) {
            System.out.println("aaa");
            String nombreclient = aggnombrecli.getText();
            String apellidoclient = aggapellidocli.getText();
            String edadclient = aggedadcli.getText();
            String telefonoclient = aggtelcli.getText();
            String cedulaclient = aggcedulacli.getText();
            String direccionclient = aggdireccioncli.getText();
            String saldoclient = aggsaldocli.getText();

            String gen = "";
            if (femenino.isSelected()) {
                gen = "Femenino";
            } else if (masculino.isSelected()) {
                gen = "Masculino";
            }
            

            try {
                //int cedulaclient = Integer.parseInt(aggcedulacli.getText());
                if (nombreclient.isEmpty() || apellidoclient.isEmpty() || edadclient.isEmpty() || telefonoclient.isEmpty() || cedulaclient.isEmpty()
                        || direccionclient.isEmpty() || gen.isEmpty() || saldoclient.isEmpty()) {

                    alerta.Alertasnueva("Campos vacios");

                } else {
                    Cliente cli = new Cliente();
                    if (cli.Existente(Integer.parseInt(aggcedulacli.getText()))) {
                        alerta.Alertasnueva("Ya existe");
                    } else {
                        cli.setCedula(Integer.parseInt(aggcedulacli.getText()));
                        cli.setNombre(aggnombrecli.getText());
                        cli.setApellido(aggapellidocli.getText());
                        cli.setEdad(Integer.parseInt(aggedadcli.getText()));
                        cli.setDireccion(aggdireccioncli.getText());
                        cli.setSaldocredito(Double.valueOf(aggsaldocli.getText()));
                        cli.setTelefono(Integer.parseInt(aggtelcli.getText()));
                        cli.setSexo(gen);
                        System.out.println(cli);

                        if (cli.guardar(cli)) {
                            aggnombrecli.setText("");
                            aggapellidocli.setText("");
                            aggcedulacli.setText("");
                            aggedadcli.setText("");
                            aggtelcli.setText("");
                            aggdireccioncli.setText("");
                            aggsaldocli.setText("");
                            femenino.setSelected(false);
                            masculino.setSelected(false);
                            alerta.Alertasnueva("Se ha guardado");
                            aggcedulacli.requestFocus();
                            Listar();
                            cli.getClientes();

                        } else {
                            alerta.Alertasnueva("Ocurrio un error");
                        }
                    }

                }
            } catch (NumberFormatException e) {
                alerta.Alertasnueva("Datos incorrectos");
            } catch (Exception e) {
                System.out.println(e);
            }

        }
        if (event.getSource() == modificar) {

            String nombreclient = aggnombrecli.getText();
            String apellidoclient = aggapellidocli.getText();
            String edadclient = aggedadcli.getText();
            String telefonoclient = aggtelcli.getText();
            String cedulaclient = aggcedulacli.getText();
            String direccionclient = aggdireccioncli.getText();
            String saldoclient = aggsaldocli.getText();
            String gen = "";
            if (femenino.isSelected()) {
                gen = "Femenino";
            } else if (masculino.isSelected()) {
                gen = "Masculino";
            }

            try {
                if (nombreclient.isEmpty() || apellidoclient.isEmpty() || edadclient.isEmpty() || telefonoclient.isEmpty() || cedulaclient.isEmpty()
                        || direccionclient.isEmpty() || gen.isEmpty() || saldoclient.isEmpty()) {

                    alerta.Alertasnueva("Campos vacios");

                } else {
                    Cliente cli = new Cliente();
                    cli.setCedula(Integer.parseInt(aggcedulacli.getText()));
                    cli.setNombre(aggnombrecli.getText());
                    cli.setApellido(aggapellidocli.getText());
                    cli.setEdad(Integer.parseInt(aggedadcli.getText()));
                    cli.setDireccion(aggdireccioncli.getText());
                    cli.setSaldocredito(Double.valueOf(aggsaldocli.getText()));
                    cli.setTelefono(Integer.parseInt(aggtelcli.getText()));
                    cli.setSexo(gen);
                    if (cli.Editar(cli)) {
                        aggnombrecli.setText("");
                        aggapellidocli.setText("");
                        aggcedulacli.setText("");
                        aggedadcli.setText("");
                        aggtelcli.setText("");
                        aggdireccioncli.setText("");
                        aggsaldocli.setText("");
                        femenino.setSelected(false);
                        masculino.setSelected(false);

                        aggcedulacli.requestFocus();
                        Listar();
                        cli.getClientes();
                        modificar.setDisable(true);
                        guardar.setDisable(false);
                        aggcedulacli.setDisable(false);
                        this.buscadorclientes.setText("");
                        alerta.Alertasnueva("Se ha modificado");

                    } else {
                        System.out.println("aqui");
                        alerta.Alertasnueva("Ocurrio un error");
                    }
                }
            } catch (NumberFormatException e) {
                alerta.Alertasnueva("Datos incorrectos");;
            }

        }

        if (event.getSource() == limpiar) {
            aggnombrecli.setText("");
            aggapellidocli.setText("");
            aggcedulacli.setText("");
            aggedadcli.setText("");
            aggtelcli.setText("");
            aggdireccioncli.setText("");
            aggsaldocli.setText("");
            aggcedulacli.requestFocus();
            aggcedulacli.setDisable(false);
            femenino.setSelected(false);
            masculino.setSelected(false);
            modificar.setDisable(true);
            guardar.setDisable(false);

        }

    }

    @FXML
    void ClickRow(MouseEvent event) throws IOException {

        Cliente c1 = tablaclientes.getSelectionModel().getSelectedItem();
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
            aggnombrecli.setText(c1.getNombre());
            aggapellidocli.setText(c1.getApellido());
            aggcedulacli.setText(String.valueOf(c1.getCedula()));
            aggedadcli.setText(String.valueOf(c1.getEdad()));
            aggtelcli.setText(String.valueOf(c1.getTelefono()));
            aggdireccioncli.setText(c1.getDireccion());
            aggsaldocli.setText(String.valueOf(c1.getSaldocredito()));
            aggcedulacli.setDisable(true);
        }

    }

    @FXML
    void Filtrar(KeyEvent event) {
        Cliente clie = new Cliente();
        ObservableList<Cliente> i = clie.getClientes();

        String buscador = buscadorclientes.getText();

        if (buscador.isEmpty()) {
            this.tablaclientes.setItems(i);

        } else {
            filtro.clear();

            for (Cliente cliente : i) {
                String cedstring = Integer.toString(cliente.getCedula());
                if (cliente.getNombre().toLowerCase().contains(buscador) || cedstring.contains(buscador)) {
                    filtro.add(cliente);
                }
            }
            this.tablaclientes.setItems(filtro);
        }

    }

    public void Listar() {
        Cliente clie = new Cliente();
        ObservableList<Cliente> items = clie.getClientes();

        this.tablaclientes.setItems(items);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.idclientes.setCellValueFactory(new PropertyValueFactory<>("cedula"));
        this.nombreclientes.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        this.apellidoclientes.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        this.edadclientes.setCellValueFactory(new PropertyValueFactory<>("edad"));
        this.sexoclientes.setCellValueFactory(new PropertyValueFactory<>("sexo"));
        this.telefonoclientes.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        this.direccionclientes.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        this.saldoclientes.setCellValueFactory(new PropertyValueFactory<>("saldocredito"));
        filtro = FXCollections.observableArrayList();

        Listar();
        modificar.setDisable(true);

    }

}
