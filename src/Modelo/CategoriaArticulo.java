package Modelo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CategoriaArticulo {
    int id;
    String nombre;
    Statement st;
    ResultSet rs;
    Connection conn = Conexion.getConnection();

    public CategoriaArticulo(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public CategoriaArticulo() {
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

   
    
    
    
    public boolean guardar(CategoriaArticulo cat) {
        boolean resp = false;
        try {
            String sql = "insert into categoria(ID_categoria, nombre) values(NULL,'" + cat.getNombre() + "')";
            st = conn.createStatement();
            st.execute(sql);
            resp = true;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return resp;
    }

    public boolean Existente(String nombre) {
        boolean resp = false;
        try {
            String sql = "select nombre from categoria where nombre = '" + nombre + "'";
            st = conn.createStatement();
            rs = st.executeQuery(sql);
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
    
    public ObservableList<CategoriaArticulo> getCategoria() {
        ObservableList<CategoriaArticulo> cat = FXCollections.observableArrayList();
        try {
            Connection con = Conexion.getConnection();
            String sql = "SELECT * FROM categoria";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("ID_categoria");
                String nombre = rs.getString(("nombre"));
                CategoriaArticulo ca = new CategoriaArticulo(id, nombre);
                cat.add(ca);
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return cat;
    }
    
    public boolean Editar(CategoriaArticulo cat) {
        boolean resp = false;
        try {
            Connection con = Conexion.getConnection();
            String sql = "update categoria set nombre = '"+cat.getNombre()+"'  WHERE ID_categoria = '"+cat.getId()+"'";
            st = con.createStatement();
            st.execute(sql);
            resp =true;
        }catch(Exception e){
            resp=false;
            System.out.println(e);
        }
        return resp;
    }
}
