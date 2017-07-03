package com.uis.carmensandiego.carmensandiego.model;

public class Viajar {

    private Integer casoId;
    private Integer destinoId;

    public Viajar(Integer casoId, Integer destinoId){
        this.casoId = casoId;
        this.destinoId = destinoId;

    }
    public Integer getDestinoId() {
        return destinoId;
    }

    public void setDestinoId(Integer destinoId) {
        this.destinoId = destinoId;
    }

    public Integer getCasoId() {
        return casoId;
    }

    public void setCasoId(Integer casoId) {
        this.casoId = casoId;
    }
}
