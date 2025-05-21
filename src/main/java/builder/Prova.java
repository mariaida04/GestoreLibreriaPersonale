package builder;

import singleton.Libreria;

public class Prova {

    public static void main (String[] args) {
            Libro l1 = new Libro.Builder("Piccole Donne","Louisa May Alcott","858443385").stato(Libro.StatoLettura.LETTO).valutazione(5)
                    .build();
            Libro l2 = new Libro.Builder("Il piccolo principe","Antoine de Saint-Exupéry","858448785").stato(Libro.StatoLettura.LETTO).valutazione(5)
                .build();
            System.out.println((l1));

            Libreria lib = Libreria.getInstance();
            lib.aggiungiLibro(l1);
            System.out.println(lib);


            Libreria lib2 = Libreria.getInstance();
            lib2.aggiungiLibro(l2);
            System.out.println(lib2);

    }
}
