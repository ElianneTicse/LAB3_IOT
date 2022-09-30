package edu.pe.pucp.lab3_iot.Entities;

public class Mascotita {
    String nombre;
    String genero;
    String duenho;
    String dni;
    String descripcion;

    public Mascotita(String nombre, String genero, String duenho, String dni, String descripcion) {
        this.nombre = nombre;
        this.genero = genero;
        this.duenho = duenho;
        this.dni = dni;
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getDuenho() {
        return duenho;
    }

    public void setDuenho(String duenho) {
        this.duenho = duenho;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }




}
