package it.epicode.Dao;

import it.epicode.Abstract_Class.Elemento;
import it.epicode.Entities.Libro;
import it.epicode.Entities.Prestito;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

public class Biblioteca {
    private EntityManager em;

    public Biblioteca(EntityManager em) {
        this.em = em;
    }

    public void addElement(Object obj) {
        em.getTransaction().begin();
        em.persist(obj);
        em.getTransaction().commit();
    }

    public void removeElementByIsbn(String isbn) {
        Libro libro = em.find(Libro.class, isbn);
        if (libro != null) {
            em.getTransaction().begin();
            em.remove(libro);
            em.getTransaction().commit();
        }
    }

    public List<Elemento> searchByIsbn(String isbn) {
        TypedQuery<Elemento> query = em.createQuery("SELECT e FROM Elemento e WHERE e.codiceISBN = :isbn", Elemento.class);
        query.setParameter("isbn", isbn);
        return query.getResultList();
    }

    public List<Elemento> searchByPublicationYear(int anno) {
        TypedQuery<Elemento> query = em.createQuery("SELECT e FROM Elemento e WHERE YEAR(e.dataDiPubblicazione) = :anno", Elemento.class);
        query.setParameter("anno", anno);
        return query.getResultList();
    }

    public List<Elemento> searchByAuthor(String autore) {
        TypedQuery<Elemento> query = em.createQuery("SELECT e FROM Elemento e WHERE e.autore = :autore", Elemento.class);
        query.setParameter("autore", autore);
        return query.getResultList();
    }

    public List<Elemento> searchByTitle(String titolo) {
        TypedQuery<Elemento> query = em.createQuery("SELECT e FROM Elemento e WHERE e.titolo LIKE :titolo", Elemento.class);
        query.setParameter("titolo", "%" + titolo + "%");
        return query.getResultList();
    }

    public List<Prestito> searchLoansByCardNumber(String numeroTessera) {
        TypedQuery<Prestito> query = em.createQuery("SELECT p FROM Prestito p WHERE p.utente.numeroTessera = :numeroTessera AND p.dataRestituzionePrevista > :dataAttuale", Prestito.class);
        query.setParameter("numeroTessera", numeroTessera);
        query.setParameter("dataAttuale", LocalDate.now());
        return query.getResultList();
    }

    public List<Prestito> searchOverdueLoans() {
        TypedQuery<Prestito> query = em.createQuery("SELECT p FROM Prestito p WHERE p.dataRestituzionePrevista < :dataAttuale AND p.dataRestituzioneEffettiva IS NULL", Prestito.class);
        query.setParameter("dataAttuale", LocalDate.now());
        return query.getResultList();
    }
}