package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Cliente extends Persona {

    Statement st;
    ResultSet rs;
    Connection conn = Conexion.getConnection();
    private double saldocredito;

    public Cliente(double saldocredito, String nombre, String apellido, String sexo, String direccion, int edad, int telefono, int cedula) {
        super(nombre, apellido, sexo, direccion, edad, telefono, cedula);
        this.saldocredito = saldocredito;
    }

    public Cliente() {
    }

    public double getSaldocredito() {
        return saldocredito;
    }

    public void setSaldocredito(double saldocredito) {
        this.saldocredito = saldocredito;
    }

    public boolean guardar(Cliente cliente) {
        boolean resp = false;
        try {

            String sql = "INSERT INTO cliente (cedulac,nombre,apellido,edad,sexo,telefono,direccion,saldo) VALUES ('" + cliente.getCedula() + "'"
                    + ",'" + cliente.getNombre() + "','" + cliente.getApellido() + "','" + cliente.getEdad() + "','" + cliente.getSexo() + "','" + cliente.getTelefono() + "','" + cliente.getDireccion() + "'"
                    + ",'" + cliente.getSaldocredito() + "')";
            st = conn.createStatement();
            st.execute(sql);
            resp = true;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return resp;
    }

    public boolean Existente(int id) {
        boolean resp = false;
        try {
            Connection con = Conexion.getConnection();

            String sql = "SELECT * FROM cliente WHERE cedulac = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                resp = true;
            } else {
                resp = false;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return resp;
    }
    
    public boolean Editar(Cliente cliente) {
        boolean resp = false;
        try {
            Connection con = Conexion.getConnection();
            String sql = "update cliente set cedulac = '"+cliente.getCedula()+"', nombre = '"+cliente.getNombre()+"', apellido = '"+cliente.getApellido()+"',"
                    + "edad = '"+cliente.getEdad()+"'"
                    + ",sexo = '"+cliente.getSexo()+"',"
                    + "telefono = '"+cliente.getTelefono()+"', direccion = '"+cliente.getDireccion()+"',"
                    + "saldo = '"+cliente.getSaldocredito()+"'  WHERE cedula = '"+cliente.getCedula()+"'";
            st = con.createStatement();
            st.execute(sql);
            resp =true;
        }catch(Exception e){
            resp=false;
            System.out.println(e);
        }
        return resp;
    }

    public ObservableList<Cliente> getClientes() {
        ObservableList<Cliente> cliente = FXCollections.observableArrayList();
        try {
            Connection con = Conexion.getConnection();
            String sql = "SELECT * FROM cliente";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                int cedula = rs.getInt("cedulac");
                String nombre = rs.getString(("nombre"));
                String apellido = rs.getString(("apellido"));
                int edad = rs.getInt("edad");
                String sexo = rs.getString(("sexo"));
                int telefono = rs.getInt("telefono");
                String direccion = rs.getString(("direccion"));
                int saldo = rs.getInt("saldo");

                Cliente c = new Cliente(saldo, nombre, apellido, sexo, direccion, edad, telefono, cedula);
                cliente.add(c);
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return cliente;
    }

    @Override
    public String toString() {
        return  super.getNombre();
    }

}
