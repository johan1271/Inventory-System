package Modelo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Producto {

    private int precio;
    private int precioventa;
    private int total;
    private int stock;
    private int cantidad;
    private int id;
    private String descripcion;
    private CategoriaArticulo categoria;
    Statement st;
    ResultSet rs;
    Connection conn = Conexion.getConnection();

    public Producto(int precio, int stock, int id, String descripcion, CategoriaArticulo categoria) {
        this.precio = precio;
        this.stock = stock;
        this.id = id;
        this.descripcion = descripcion;
        this.categoria = categoria;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getPrecioventa() {
        int porcentaje =  (int) (getPrecio() * 0.2);
        return precioventa = getPrecio() + porcentaje;
    }

    public void setPrecioventa(int precioventa) {
        this.precioventa = precioventa;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Producto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public CategoriaArticulo getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaArticulo categoria) {
        this.categoria = categoria;
    }

    public boolean guardar(Producto prod) {
        boolean resp = false;
        try {
            String sql = "insert into articulo(ID_articulo, precio, stock, descripcion, ID_categoria, precioventa) values(NULL,'" + prod.getPrecio() + "', '" + prod.getStock() + "','" + prod.getDescripcion() + "','" + prod.getCategoria().getId() + "'"
                    + ", '" + prod.getPrecioventa() + "' )";
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
            String sql = "select descripcion from articulo where descripcion = '" + nombre + "'";
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

    public ObservableList<Producto> getProductos() {
        ObservableList<Producto> prod = FXCollections.observableArrayList();
        try {
            Connection con = Conexion.getConnection();
            String sql = "SELECT * FROM articulo";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            CategoriaArticulo ca = null;
            while (rs.next()) {
                int idA = rs.getInt("ID_articulo");
                int precioA = rs.getInt(("precio"));
                int stock = rs.getInt(("stock"));
                String descripcion = rs.getString("descripcion");
                int idCat = rs.getInt("ID_categoria");
                int preciov = rs.getInt("precioventa");

                String sql2 = "SELECT * FROM categoria";
                Statement st1 = con.createStatement();
                ResultSet rs1 = st1.executeQuery(sql2);
                int idCate = 0;
                String nombrecate = "";
                while (rs1.next()) {
                    idCate = rs1.getInt("ID_categoria");
                    nombrecate = rs1.getString(("nombre"));
                    if (idCat == idCate) {
                        ca = new CategoriaArticulo(idCate, nombrecate);

                    }

                }
                Producto produ = new Producto(precioA, stock, idA, descripcion, ca);
                produ.setPrecioventa(preciov);
                prod.add(produ);
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return prod;
    }

    public boolean Editar(Producto produ) {
        boolean resp = false;
        try {
            Connection con = Conexion.getConnection();
            String sql = "update articulo set precio ='" + produ.getPrecio() + "',stock = '" + produ.getStock() + "',"
                    + " descripcion ='" + produ.getDescripcion() + "', ID_categoria= '" + produ.getCategoria().getId() + "' "
                    + ", precioventa= '" + produ.getPrecioventa() + "' WHERE ID_articulo ='" + produ.getId() + "'";
            st = con.createStatement();
            st.execute(sql);

            resp = true;
        } catch (Exception e) {
            resp = false;
            System.out.println(e);
        }
        return resp;
    }

    public boolean ActualizarIventario(ObservableList<Producto> Lista) {
        boolean resp = false;
        try {
            Connection con = Conexion.getConnection();

            for (int i = 0; i < Lista.size(); i++) {
                String sql = "update articulo set stock ='" + (Lista.get(i).getStock() - Lista.get(i).getCantidad()) + "' WHERE ID_articulo ='" + Lista.get(i).getId() + "'";
                st = con.createStatement();
                st.execute(sql);
            }

            resp = true;
        } catch (Exception e) {
            resp = false;
            System.out.println(e);
        }
        return resp;
    }

    public int Stock(int id) {
        int stock = 0;

        try {
            String sql = "select stock from articulo where ID_articulo = '" + id + "'";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                stock = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return stock;
    }

    public boolean SumarInventario(ObservableList<DetalleFactura> Lista) {
        boolean resp = false;
        try {
            Connection con = Conexion.getConnection();

            for (int i = 0; i < Lista.size(); i++) {
                String sql = "update articulo set stock ='" + (Stock(Lista.get(i).getIDart()) + Lista.get(i).getCantidad()) + "' WHERE ID_articulo ='" + Lista.get(i).getIDart() + "'";
                st = con.createStatement();
                st.execute(sql);
            }

            resp = true;
        } catch (Exception e) {
            resp = false;
            System.out.println(e);
        }
        return resp;
    }

    @Override
    public String toString() {
        return descripcion;
    }

}
