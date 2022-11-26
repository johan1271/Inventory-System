package Modelo;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Caicedo
 */
public class Usuarios extends Persona{
    Cargo cargo;
    String contra;
    Statement st;
    ResultSet rs;
    Connection conn = Conexion.getConnection();

 

   
    

    public Usuarios() {
    }

    public Usuarios(Cargo cargo, String contra, String nombre, String apellido, String sexo, String direccion, int edad, int telefono, int cedula) {
        super(nombre, apellido, sexo, direccion, edad, telefono, cedula);
        this.cargo = cargo;
        this.contra = contra;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

   

    
   
    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }

    public boolean guardar(Usuarios usu) {
        boolean resp = false;
        try {
            String sql = "INSERT INTO usuario (cedulau,nombre,apellido,edad,sexo,telefono,direccion,ID_cargo,contra) VALUES ('" + usu.getCedula() + "'"
                    + ",'" + usu.getNombre() + "','" + usu.getApellido() + "','" + usu.getEdad() + "','" + usu.getSexo() + "','" + usu.getTelefono() + "','" + usu.getDireccion() + "'"
                    + ",'" + usu.getCargo().getId() + "', '" + usu.getContra() + "')";
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

            String sql = "SELECT * FROM usuario WHERE cedulau = ?";
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
    
    public boolean Editar(Usuarios usu) {
        boolean resp = false;
        try {
            Connection con = Conexion.getConnection();
            String sql = "update usuario set cedulau = '"+usu.getCedula()+"', nombre = '"+usu.getNombre()+"', apellido = '"+usu.getApellido()+"',"
                    + "edad = '"+usu.getEdad()+"'"
                    + ",sexo = '"+usu.getSexo()+"',"
                    + "telefono = '"+usu.getTelefono()+"', direccion = '"+usu.getDireccion()+"',"
                    + "ID_cargo = '"+usu.getCargo().getId()+"', contra = '"+usu.getContra()+"'  WHERE cedula = '"+usu.getCedula()+"'";
            st = con.createStatement();
            st.execute(sql);
            resp =true;
        }catch(Exception e){
            resp=false;
            System.out.println(e);
        }
        return resp;
    }
    public ObservableList<Usuarios> getUsuarios() {
        ObservableList<Usuarios> usu = FXCollections.observableArrayList();
        try {
            Connection con = Conexion.getConnection();
            String sql = "SELECT * FROM usuario";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            Cargo c = null;
            while (rs.next()) {
                int cedula = rs.getInt("cedulau");
                String nombre = rs.getString(("nombre"));
                String apellido = rs.getString(("apellido"));
                int edad = rs.getInt("edad");
                String sexo = rs.getString(("sexo"));
                int telefono = rs.getInt("telefono");
                String direccion = rs.getString(("direccion"));
                int ID_cargo = rs.getInt("ID_cargo");
                String contra = rs.getString(("contra"));
                
                String sql2 = "SELECT * FROM cargo";
                Statement st2 = con.createStatement();
                ResultSet rs2 = st2.executeQuery(sql2);
                int id_carg = 0;
                String nombrecargo ="";
                while (rs2.next()) {                    
                    id_carg = rs2.getInt("ID_cargo");
                    nombrecargo = rs2.getString("nombre");
                    if (ID_cargo == id_carg) {
                        c = new Cargo(nombrecargo,id_carg);
                    }
                }
                Usuarios u = new Usuarios(c,contra, nombre, apellido, sexo, direccion, edad, telefono, cedula);
                usu.add(u);
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return usu;
    }

    @Override
    public String toString() {
        return super.getNombre();
    }
    
}
