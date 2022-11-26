package Modelo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Factura {

    int id;

    private String fecha, metodo_pago;

    private Boolean estado;

    private Cliente cliente;

    private Usuarios vendedor;

    
    Connection conn = Conexion.getConnection();

    public Factura() {

    }

    public Factura(String fecha, String metodo_pago, Boolean estado, Cliente cliente, Usuarios vendedor) {
        this.fecha = fecha;
        this.metodo_pago = metodo_pago;
        this.estado = estado;
        this.cliente = cliente;
        this.vendedor = vendedor;
    }

    public Factura(int id, String fecha, String metodo_pago, Boolean estado, Cliente cliente, Usuarios vendedor) {
        this.id = id;
        this.fecha = fecha;
        this.metodo_pago = metodo_pago;
        this.estado = estado;
        this.cliente = cliente;
        this.vendedor = vendedor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getMetodo_pago() {
        return metodo_pago;
    }

    public void setMetodo_pago(String metodo_pago) {
        this.metodo_pago = metodo_pago;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Usuarios getVendedor() {
        return vendedor;
    }

    public void setVendedor(Usuarios vendedor) {
        this.vendedor = vendedor;
    }

    public boolean guardar(Factura fac) {
        boolean resp = false;
        try {

            String sql = "INSERT INTO factura (ID_factura,fecha_expedicion,estado,cedulac,cedulau,metodo_pago) VALUES (NULL,"
                    + "'" + fac.getFecha() + "','" + fac.getEstado() + "','" + fac.getCliente().getCedula() + "','" + fac.getVendedor().getCedula() + "','" + fac.getMetodo_pago() + "')";
          Statement st= conn.createStatement();
            st.execute(sql);
            resp = true;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return resp;
    }

    public ObservableList<Factura> getFacturas() {
        ObservableList<Factura> fac = FXCollections.observableArrayList();
        try {
            
            Connection con = Conexion.getConnection();
            String sql = "SELECT * FROM factura";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                int idF = rs.getInt("ID_Factura");
                String fechaF = rs.getString(("fecha_expedicion"));
                String estadoF = rs.getString(("estado"));
                int cedulac = rs.getInt("cedulac");
                int cedulau = rs.getInt(("cedulau"));
                String metodopago = rs.getString(("metodo_pago"));

                String sql2 = "SELECT * FROM cliente";
                Statement st2 = con.createStatement();
                ResultSet rs2 = st2.executeQuery(sql2);
                Cliente c = null;
                Usuarios u = null;
                Cargo carg = null;
                
                
                while (rs2.next()) {
                    int cedulacli = rs2.getInt("cedulac");
                    String nombre = rs2.getString(("nombre"));
                    String apellido = rs2.getString(("apellido"));
                    int edad = rs2.getInt("edad");
                    String sexo = rs2.getString(("sexo"));
                    int telefono = rs2.getInt("telefono");
                    String direccion = rs2.getString(("direccion"));
                    int saldo = rs2.getInt("saldo");
                    if (cedulac == cedulacli) {
                        c = new Cliente(saldo, nombre, apellido, sexo, direccion, edad, telefono, cedulacli);
                    }
                }
                
                
                
                String sql3 = "SELECT * FROM usuario";
                Statement st3 = con.createStatement();
                ResultSet rs3 = st3.executeQuery(sql3);

                while (rs3.next()) {
                    int cedulausu = rs3.getInt("cedulau");
                    String nombreusu = rs3.getString(("nombre"));
                    String apellidousu = rs3.getString(("apellido"));
                    int edadusu = rs3.getInt("edad");
                    String sexousu = rs3.getString(("sexo"));
                    int telefonousu = rs3.getInt("telefono");
                    String direccionusu = rs3.getString(("direccion"));
                    int ID_cargousu = rs3.getInt("ID_cargo");
                    String contrausu = rs3.getString(("contra"));

                    String sql4 = "SELECT * FROM cargo";
                    Statement st4 = con.createStatement();
                    ResultSet rs4 = st4.executeQuery(sql4);
                    int id_carg = 0;
                    String nombrecargo = "";
                    while (rs4.next()) {
                        id_carg = rs4.getInt("ID_cargo");
                        nombrecargo = rs4.getString("nombre");
                        if (ID_cargousu == id_carg) {
                            carg = new Cargo(nombrecargo, id_carg);
                        }
                    }
                    if (cedulau ==cedulausu) {
                        u = new Usuarios(carg, contrausu, nombreusu, apellidousu, sexousu, direccionusu, edadusu, telefonousu, cedulausu);
                    }
                    
                }

                Factura f = new Factura(idF, fechaF, metodopago, Boolean.valueOf(estadoF), c, u);
                fac.add(f);
            }

        } catch (Exception e) {
            System.out.println("error"+e);
        }

        return fac;
    }
    
    public boolean Anular(Factura fac) {
        boolean resp = false;
        try {
            Connection con = Conexion.getConnection();
            
            String falso = "false";
            String sql = "update factura set estado='"+falso+"' where ID_factura='" + fac.getId() + "' ";
            Statement st = con.createStatement();
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
        return "Factura{" + "id=" + id + ", fecha=" + fecha + ", metodo_pago=" + metodo_pago + ", estado=" + estado + ", cliente=" + cliente + ", vendedor=" + vendedor +'}';
    }

}
