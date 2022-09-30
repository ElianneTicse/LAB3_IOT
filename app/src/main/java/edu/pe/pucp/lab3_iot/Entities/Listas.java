package edu.pe.pucp.lab3_iot.Entities;

import java.util.ArrayList;

public class Listas {
    private static ArrayList<Mascotita> listaMascotas=new ArrayList<>();
    private static ArrayList<Historial> listaHistorial=new ArrayList<>();

    public static ArrayList<Mascotita> getListaMascotas() {
        return listaMascotas;
    }

    public static void addMascota(Mascotita mascotita){
        listaMascotas.add(mascotita);
    }

    public static Mascotita getByPosition(int index){
        return listaMascotas.get(index);
    }

    public static ArrayList<Historial> getListaHistorial(){
        return listaHistorial;
    }
    public static void addHistorial(Historial historial){
        listaHistorial.add(historial);
    }
    public static ArrayList<Historial> historialMostrarCuatro(){
        int x = listaHistorial.size();
        return new ArrayList<>(listaHistorial.subList(x-5,x-1));
    }
}
