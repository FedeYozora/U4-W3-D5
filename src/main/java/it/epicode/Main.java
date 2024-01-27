package it.epicode;

import it.epicode.Abstract_Class.Elemento;
import it.epicode.Dao.Biblioteca;
import it.epicode.Entities.Libro;
import it.epicode.Entities.Prestito;
import it.epicode.Entities.Rivista;
import it.epicode.Entities.Utente;
import it.epicode.Enums.Periodicita;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // creare un EntityManagerFactory e un EntityManager per interagire con il database
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Biblioteca");
        EntityManager em = emf.createEntityManager();
        Biblioteca biblioteca = new Biblioteca(em);

        // creare un libro e una rivista
        Libro libro = new Libro();
        libro.setCodiceISBN("1234567890");
        libro.setTitolo("Il Signore degli Anelli");
        libro.setNumeroPagine(1408);
        libro.setAutore("J.R.R. Tolkien");
        libro.setGenere("Fantasy");
        libro.setDataDiPubblicazione(LocalDate.of(1954, 7, 29));

        Rivista rivista = new Rivista();
        rivista.setCodiceISBN("0987654321");
        rivista.setTitolo("La Gazzetta dello Sport");
        rivista.setNumeroPagine(50);
        rivista.setDataDiPubblicazione(LocalDate.of(2024, 1, 22));
        rivista.setPeriodicita(Periodicita.SETTIMANALE);


        // creare un utente e due prestito (uno con un'avvenuta restituzione, l'altro invece é un prestito scaduto)
        Utente utente = new Utente();
        utente.setNome("Mario");
        utente.setCognome("Rossi");
        utente.setDataDiNascita(LocalDate.of(2000,7,15));
        utente.setNumeroTessera("12345");

        Prestito prestito1 = new Prestito();
        prestito1.setUtente(utente);
        prestito1.setElementoPrestato(libro);
        prestito1.setDataInizioPrestito(LocalDate.of(2023, 1, 22));
        prestito1.setDataRestituzionePrevista(LocalDate.of(2023,1,22).plusDays(30));

        Prestito prestito2 = new Prestito();
        prestito2.setUtente(utente);
        prestito2.setElementoPrestato(rivista);
        prestito2.setDataInizioPrestito(LocalDate.of(2024, 1, 23));
        prestito2.setDataRestituzionePrevista(LocalDate.of(2024,1,23).plusDays(30));

        // salvare il libro, la rivista, l'utente e i prestiti nel database
        biblioteca.addElement(libro);
        biblioteca.addElement(rivista);
        biblioteca.addElement(utente);
        biblioteca.addElement(prestito1);
        biblioteca.addElement(prestito2);

        // cercare un elemento per ISBN
        List<Elemento> elementi = biblioteca.searchByIsbn("1234567890");
        System.out.println("Elementi trovati per ISBN: " + elementi);

//      // rimozione di un elemento dato un codice ISBN
//      biblioteca.removeElementByIsbn(0987654321);

        // cercare un elemento per anno di pubblicazione
        List<Elemento> elementoAnno = biblioteca.searchByPublicationYear(1954);
        System.out.println("Elementi trovati per quell'anno di pubblicazione: " + elementoAnno);

        // cercare un elemento per autore
        List<Elemento> elementoAutore = biblioteca.searchByAuthor("J.R.R. Tolkien");
        System.out.println("Elementi trovati per questo Autore: " + elementoAutore);

        // cercare un elemento per titolo
        List<Elemento> elementoTitolo = biblioteca.searchByTitle("Anelli");
        System.out.println("Elementi con questo titolo: " + elementoTitolo);

        // cercare degli elementi attualmente in prestito dato un numero di tessera utente

        List<Prestito> prestitoLibro = biblioteca.searchLoansByCardNumber("12345");
        System.out.println("Elementi presi in prestito dall' utente " + prestitoLibro);

        // cercare tutti i prestiti scaduti e non ancora restituiti

        List<Prestito> prestitoScaduto = biblioteca.searchOverdueLoans();
        System.out.println("Prestiti scaduti " + prestitoScaduto);

        // chiudere l'EntityManager e l'EntityManagerFactory
        em.close();
        emf.close();
    }
}