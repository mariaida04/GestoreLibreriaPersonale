package builder;

import java.util.Objects;

public class Libro {
    private String titolo;
    private String autore;
    private String isbn;
    private String genere;
    private Valutazione valutazione;
    private StatoLettura stato;

    public static class Builder {

        //parametri obbligatori
        private String titolo;
        private String autore;
        private String isbn;

        //parametri opzionali
        private String genere;
        private Valutazione valutazione;
        private StatoLettura stato;

        //costruttore Builder
        public Builder(String titolo, String autore, String isbn) {
            this.titolo = titolo;
            this.autore = autore;
            this.isbn = isbn;
        }

        public Builder genere(String genere) {
            this.genere = genere;
            return this;
        }

        public Builder valutazione(Valutazione valutazione) {
            this.valutazione = valutazione;
            return this;
        }

        public Builder stato(StatoLettura stato) {
            this.stato = stato;
            return this;
        }

        public Libro build() {
            return new Libro(this);
        }
    }

    //costruttore Libro
    public Libro(Builder builder) {
        this.titolo = builder.titolo;
        this.autore = builder.autore;
        this.isbn = builder.isbn;
        this.genere = builder.genere;
        this.valutazione = builder.valutazione;
        this.stato = builder.stato;
    }

    //metodi getter
    public String getTitolo() {
        return titolo;
    }

    public String getAutore() {
        return autore;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getGenere() {
        return genere;
    }

    public Valutazione getValutazione() {
        return valutazione;
    }

    public StatoLettura getStato() {
        return stato;
    }

    @Override
    public String toString() {
        return "Libro: {" +
                "titolo: '" + titolo + '\'' +
                ", autore = " + autore +
                ", ISBN = " + isbn +
                ", genere = " + genere +
                ", valutazione = " + valutazione +
                ", stato = " + stato +
                '}';
    }

    @Override
    public boolean equals(Object x)
    {   if (x == null)
            return false;
        if (this == x)
            return true;
        if (!(x instanceof Libro))
            return false;
        Libro l = (Libro) x;
        if (this.isbn == null && l.isbn != null)
            return false;
        return this.isbn.equals(l.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(isbn);
    }
}
