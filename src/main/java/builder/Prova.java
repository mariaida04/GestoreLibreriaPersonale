package builder;

import singleton.Libreria;
import strategy.*;

public class Prova {

    public static void main (String[] args) {
            Libro l1 = new Libro.Builder("Piccole Donne","Louisa May Alcott","858443385").stato(StatoLettura.LETTO).valutazione(Valutazione.CINQUE_STELLE).genere("Fantasy")
                    .build();
            Libro l2 = new Libro.Builder("Il piccolo principe","Antoine de Saint-Exupéry","858448785").stato(StatoLettura.LETTO).valutazione(Valutazione.DUE_STELLE).genere("Romanzo bambini")
                .build();
            Libro l3 = new Libro.Builder("1984","George Orwell","85987548785").stato(StatoLettura.DA_LEGGERE).valutazione(Valutazione.CINQUE_STELLE).genere("Storico")
                    .build();
            Libro l4 = new Libro.Builder("Il nome della rosa","Umberto Eco","8545748785").stato(StatoLettura.IN_LETTURA).valutazione(Valutazione.TRE_STELLE).genere("Saggio")
                    .build();
            Libro l5 = new Libro.Builder("La coscienza di Zeno","George Orwell","85993148785").stato(StatoLettura.DA_LEGGERE).valutazione(Valutazione.QUATTRO_STELLE).genere("Romanzo")
                    .build();
            Libro l6 = new Libro.Builder("La divina commedia","Dante Alighieri","8599148785").stato(StatoLettura.LETTO).valutazione(Valutazione.DUE_STELLE).genere("Classico")
                    .build();
            Libro l7 = new Libro.Builder("Il codice da Vinci","Dan Brown","859913476585").stato(StatoLettura.IN_LETTURA).valutazione(Valutazione.UNA_STELLA).genere("Thriller")
                    .build();
            Libro l8 = new Libro.Builder("Harry Potter","Dan Brown","85991341377885").stato(StatoLettura.LETTO).valutazione(Valutazione.UNA_STELLA).genere("Thriller")
                    .build();

            Libreria lib = Libreria.getInstance();
            lib.aggiungiLibro(l1);
            lib.aggiungiLibro(l2);
            lib.aggiungiLibro(l3);
            lib.aggiungiLibro(l4);
            lib.aggiungiLibro(l5);
            lib.aggiungiLibro(l6);
            lib.aggiungiLibro(l7);
            lib.aggiungiLibro(l8);
            System.out.println(lib);

            //System.out.println(lib.eseguiStrategy(new FiltraPerStatoLettura(StatoLettura.IN_LETTURA)));
    }
}
