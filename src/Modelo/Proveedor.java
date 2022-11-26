package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Proveedor {

    private int ID;
    private String nombre;
    private String direccion;
    private String correo;
    private int telefono;
    Statement st;
    ResultSet rs;
    Connection conn = Conexion.getConnection();

    public Proveedor(int ID, String nombre, String direccion, String correo, int telefono) {
        this.ID = ID;
        this.nombre = nombre;
        this.direccion = direccion;
        this.correo = correo;
        this.telefono = telefono;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Proveedor() {

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public boolean guardar(Proveedor prov) {
        boolean resp = false;
        try {
            String sql = "insert into proveedor(ID_proveedor,nombre,direccion , telefono, correo) values('" + prov.getID() + "','" + prov.getNombre() + "', '" + prov.getDireccion() + "','" + prov.getTelefono() + "','" + prov.getCorreo() + "')";
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

            String sql = "SELECT * FROM proveedor WHERE ID_proveedor = ?";
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

    public ObservableList<Proveedor> getProveedor() {
        ObservableList<Proveedor> prov = FXCollections.observableArrayList();
        try {
            Connection con = Conexion.getConnection();
            String sql = "SELECT * FROM proveedor";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("ID_proveedor");
                String nombre = rs.getString(("nombre"));
                String direccion = rs.getString(("direccion"));
                int telefono = rs.getInt(("telefono"));
                String correo = rs.getString(("correo"));
                Proveedor ca = new Proveedor(id, nombre, direccion, correo, telefono);
                prov.add(ca);
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return prov;
    }

    public boolean Editar(Proveedor prov) {
        boolean resp = false;
        try {
            Connection con = Conexion.getConnection();
            String sql = "update proveedor set ID_proveedor='" + prov.getID() + "', nombre='" + prov.getNombre() + "',direccion='" + prov.getDireccion() + "', telefono='" + prov.getTelefono() + "', correo='" + prov.getCorreo() + "'"
                    + "where ID_proveedor='" + prov.getID() + "' ";
            st = con.createStatement();
            st.execute(sql);
            resp = true;
        } catch (Exception e) {
            resp = false;
            System.out.println(e);
        }
        return resp;
    }

    @Override
    public String toString() {
        return "Proveedor{" + "nombre=" + nombre + ", direccion=" + direccion + ", correo=" + correo + ", telefono=" + telefono + '}';
    }

}
