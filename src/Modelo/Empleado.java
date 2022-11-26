package Modelo;


public class Empleado extends Persona{
    private String cargo = "Empleado";

    public Empleado() {
    }

    public Empleado(String cargo, String nombre, String apellido, String sexo, String direccion, int edad, int telefono, int cedula) {
        super(nombre, apellido, sexo, direccion, edad, telefono, cedula);
        this.cargo = cargo;
    }

    public String getCargo() {
        return cargo;
    }

    
    
    

   
    

    

    
    
    

    


    
    
     
}