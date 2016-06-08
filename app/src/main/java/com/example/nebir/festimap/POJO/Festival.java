package com.example.nebir.festimap.POJO;

import java.util.Date;

/**
 * Created by nebir on 23/05/2016.
 */
public class Festival {
    private String nombre, ciudad, descripcion, start, end;
    private int id, logo, acampada;

    public Festival(){}

    public Festival(String nombre, String ciudad, String descripcion, int acampada, int logo, String start, String end, int id) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.ciudad = ciudad;
        this.acampada = acampada;
        this.logo = logo;
        this.start = start;
        this.end = end;
        this.id = id;
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

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getAcampada() {
        return acampada;
    }

    public void setAcampada(int acampada) {
        this.acampada = acampada;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "Festival{" +
                "nombre='" + nombre + '\'' +
                ", ciudad='" + ciudad + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", acampada='" + acampada + '\'' +
                ", logo='" + logo + '\'' +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
