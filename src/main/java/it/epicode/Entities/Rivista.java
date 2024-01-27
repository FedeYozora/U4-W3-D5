package it.epicode.Entities;

import it.epicode.Abstract_Class.Elemento;
import it.epicode.Enums.Periodicita;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class Rivista extends Elemento {
    @Enumerated(EnumType.STRING)
    private Periodicita periodicita;

    // getters and setters

    public Periodicita getPeriodicita() {
        return periodicita;
    }

    public void setPeriodicita(Periodicita periodicita) {
        this.periodicita = periodicita;
    }

    @Override
    public String toString() {
        return "Rivista{" +
                "periodicita=" + periodicita +
                '}';
    }
}
