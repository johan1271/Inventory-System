/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Caicedo
 */
public class Cargo {
    String nombre;
    int id;

    public Cargo(String nombre, int id) {
        this.nombre = nombre;
        this.id = id;
    }

    public Cargo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return  nombre;
    }
    
    public ObservableList<Cargo> getCargo() {
        ObservableList<Cargo> cat = FXCollections.observableArrayList();
        try {
            Connection con = Conexion.getConnection();
            String sql = "SELECT * FROM cargo";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("ID_Cargo");
                String nombre = rs.getString(("nombre"));
                Cargo ca = new Cargo(nombre,id);
                cat.add(ca);
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return cat;
    }
    
    
}
