/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Caicedo
 */
public class DetalleFactura {

    Producto articulo;
    int IDart;
    int preciou;
    int cantidad;
    int precio;
    ObservableList<CategoriaArticulo> categoria;
    Factura factura;
    Statement st;
    ResultSet rs;
    Connection conn = Conexion.getConnection();

    public DetalleFactura(Producto articulo, int cantidad, int precio, Factura factura) {
        this.factura = factura;
        this.articulo = articulo;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public DetalleFactura(Producto articulo, int IDart, int preciou, int cantidad, int precio, Factura factura) {
        this.articulo = articulo;
        this.IDart = IDart;
        this.preciou = preciou;
        this.cantidad = cantidad;
        this.precio = precio;
        this.factura = factura;
        
    }

    public DetalleFactura() {

    }


   
    
    
    

    public int getIDart() {
        return IDart;
    }

    public void setIDart(int IDart) {
        this.IDart = IDart;
    }

    public int getPreciou() {
        return preciou;
    }

    public void setPreciou(int preciou) {
        this.preciou = preciou;
    }

  
    
    
    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public Producto getArticulo() {
        return articulo;
    }

    public void setArticulo(Producto articulo) {
        this.articulo = articulo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int NumeroFactura() throws SQLException {
        int numfactura = 0;
        Connection con = Conexion.getConnection();
        String sql = "SELECT MAX(ID_factura) FROM factura";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            numfactura = rs.getInt(1);
        }

        return numfactura;
    }
    
   

    public boolean guardar(ObservableList<DetalleFactura> Lista) {
        boolean resp = false;
        try {

            for (int i = 0; i < Lista.size(); i++) {
                String sql = "INSERT INTO detalle_factura (ID_detalle_factura,ID_factura,ID_articulo,cantidad,precio,preciounidad) VALUES (NULL"
                        + ",'" + NumeroFactura() + "','" + Lista.get(i).getArticulo().getId() + "','" + Lista.get(i).getCantidad() + "','" + Lista.get(i).getPrecio() + "','" + Lista.get(i).getPreciou() + "')";
                st = conn.createStatement();
                st.execute(sql);
            }

            resp = true;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return resp;
    }

    public ObservableList<DetalleFactura> getDetalles() {
        ObservableList<DetalleFactura> cat = FXCollections.observableArrayList();
        try {
            Connection con = Conexion.getConnection();
            String sql = "SELECT * FROM detalle_factura";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            String categoria = "";
            Factura factura = null;
            while (rs.next()) {
                int iddetalle = rs.getInt("ID_detalle_factura");
                int ID_factura = rs.getInt("ID_factura");
                int ID_articulo = rs.getInt("ID_articulo");
                int cantidad = rs.getInt("cantidad");
                int precio = rs.getInt("precio");
                int unidad = rs.getInt("preciounidad");
                
                
                Producto produ = null;
                

                String sql2 = "SELECT * FROM factura";
                Statement st2 = con.createStatement();
                ResultSet rs2 = st2.executeQuery(sql2);
                while (rs2.next()) {
                    int idF = rs2.getInt("ID_Factura");
                    String fechaF = rs2.getString(("fecha_expedicion"));
                    String estadoF = rs2.getString(("estado"));
                    int cedulac = rs2.getInt("cedulac");
                    int cedulau = rs2.getInt(("cedulau"));
                    String metodopago = rs2.getString(("metodo_pago"));

                    String sql3 = "SELECT * FROM cliente";
                    Statement st3 = con.createStatement();
                    ResultSet rs3 = st3.executeQuery(sql3);
                    Cliente c = null;
                    Usuarios u = null;
                    Cargo carg = null;

                    while (rs3.next()) {
                        int cedulacli = rs3.getInt("cedulac");
                        String nombre = rs3.getString(("nombre"));
                        String apellido = rs3.getString(("apellido"));
                        int edad = rs3.getInt("edad");
                        String sexo = rs3.getString(("sexo"));
                        int telefono = rs3.getInt("telefono");
                        String direccion = rs3.getString(("direccion"));
                        int saldo = rs3.getInt("saldo");
                        if (cedulac == cedulacli) {
                            c = new Cliente(saldo, nombre, apellido, sexo, direccion, edad, telefono, cedulacli);
                        }
                    }

                    String sql4 = "SELECT * FROM usuario";
                    Statement st4 = con.createStatement();
                    ResultSet rs4 = st4.executeQuery(sql4);

                    while (rs4.next()) {
                        int cedulausu = rs4.getInt("cedulau");
                        String nombreusu = rs4.getString(("nombre"));
                        String apellidousu = rs4.getString(("apellido"));
                        int edadusu = rs4.getInt("edad");
                        String sexousu = rs4.getString(("sexo"));
                        int telefonousu = rs4.getInt("telefono");
                        String direccionusu = rs4.getString(("direccion"));
                        int ID_cargousu = rs4.getInt("ID_cargo");
                        String contrausu = rs4.getString(("contra"));

                        String sql5 = "SELECT * FROM cargo";
                        Statement st5 = con.createStatement();
                        ResultSet rs5 = st5.executeQuery(sql5);
                        int id_carg = 0;
                        String nombrecargo = "";

                        while (rs5.next()) {
                            id_carg = rs5.getInt("ID_cargo");
                            nombrecargo = rs5.getString("nombre");
                            if (ID_cargousu == id_carg) {
                                carg = new Cargo(nombrecargo, id_carg);
                            }
                        }
                        if (cedulau == cedulausu) {
                            u = new Usuarios(carg, contrausu, nombreusu, apellidousu, sexousu, direccionusu, edadusu, telefonousu, cedulausu);
                        }

                    }
                    if (ID_factura == idF) {
                        factura = new Factura(idF, fechaF, metodopago, Boolean.valueOf(estadoF), c, u);
                    }

                    String sql6 = "SELECT * FROM articulo";
                    Statement st6 = con.createStatement();
                    ResultSet rs6 = st6.executeQuery(sql6);

                    CategoriaArticulo ca = null;
                    while (rs6.next()) {
                        int idA = rs6.getInt("ID_articulo");
                        int precioA = rs6.getInt(("precio"));
                        int stock = rs6.getInt(("stock"));
                        String descripcion = rs6.getString("descripcion");
                        int idCat = rs6.getInt("ID_categoria");
                        int preciov = rs6.getInt("precioventa");

                        String sql7 = "SELECT * FROM categoria";
                        Statement st7 = con.createStatement();
                        ResultSet rs7 = st7.executeQuery(sql7);
                        int idCate = 0;
                        String nombrecate = "";
                        while (rs7.next()) {
                            idCate = rs7.getInt("ID_categoria");
                            nombrecate = rs7.getString(("nombre"));
                            if (idCat == idCate) {
                                ca = new CategoriaArticulo(idCate, nombrecate);
                                
                            }

                        }

                        if (ID_articulo == idA) {
                            produ = new Producto(precioA, stock, idA, descripcion, ca);
                            produ.setPrecioventa(preciov);


                        }
                    }

                }
                
                DetalleFactura ca = new DetalleFactura(produ,  ID_articulo, unidad, cantidad,  precio,  factura);
                cat.add(ca);
            }
            

        } catch (Exception e) {
            System.out.println(e);
        }

        return cat;
    }

    @Override
    public String toString() {
        return "DetalleFactura{" + "articulo=" + articulo + ", cantidad=" + cantidad + ", precio=" + precio + ", factura=" + factura + '}';
    }

}
