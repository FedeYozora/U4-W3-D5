package it.epicode.Entities;

import it.epicode.Abstract_Class.Elemento;

import javax.persistence.Entity;

@Entity
public class Libro extends Elemento {
    private String autore;
    private String genere;

    // getters and setters

    public String getAutore() {
        return autore;
    }

    public void setAutore(String autore) {
        this.autore = autore;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "autore='" + autore + '\'' +
                ", genere='" + genere + '\'' +
                '}';
    }
}
