package Modelo;

public class Administrador extends Persona{
    private String cargo;

    public Administrador() {
    }

    public Administrador(String cargo, String nombre, String apellido, String sexo, String direccion, int edad, int telefono, int cedula) {
        super(nombre, apellido, sexo, direccion, edad, telefono, cedula);
        this.cargo = cargo;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
    
    
    
    
    
}