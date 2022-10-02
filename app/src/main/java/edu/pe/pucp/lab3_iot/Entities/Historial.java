package edu.pe.pucp.lab3_iot.Entities;

public class Historial {
    private Mascotita mascotita;
    private String origen;
    private String destino;

    public Historial(Mascotita mascotita, String origen, String destino) {
        this.mascotita = mascotita;
        this.origen = origen;
        this.destino = destino;
    }

    public Mascotita getMascotita() {
        return mascotita;
    }

    public void setMascotita(Mascotita mascotita) {
        this.mascotita = mascotita;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }


}
