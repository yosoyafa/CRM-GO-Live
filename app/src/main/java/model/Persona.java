package model;

public class Persona {
    private String nombre, cedula, telefono1, telefono2, direccion;

    public Persona(String nombre, String cedula, String direccion, String telefono1, String telefono2) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.telefono1 = telefono1;
        this.telefono2 = telefono2;
        this.direccion = direccion;
    }

    public String toString(){
        return "Nombre: "+nombre+
                "\nCedula: "+cedula+
                "\ntel1: "+telefono1+
                "\ntel2: "+telefono2+
                "\ndir: "+direccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono1() {
        return telefono1;
    }

    public void setTelefono1(String telefono1) {
        this.telefono1 = telefono1;
    }

    public String getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }
}
