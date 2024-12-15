package parseur_pojet;

public class ParseurTest {
    public static void main(String[] args) {
        Parseur parseur = new Parseur();

        String[] phrasesValides = {
            "le fromage mange la souris",
            "une souris mangent un fromage"
        };

        String[] phrasesInvalides = {
            "fromage mange souris",
            "le mange souris",
            "le fromage mange"
        };

        System.out.println("Tests des phrases valides :");
        for (String phrase : phrasesValides) {
            System.out.println("Phrase : " + phrase);
            parseur.chargerPhrase(phrase);
            System.out.println(parseur.analyser());
        }

        System.out.println("\nTests des phrases invalides :");
        for (String phrase : phrasesInvalides) {
            System.out.println("Phrase : " + phrase);
            parseur.chargerPhrase(phrase);
            System.out.println(parseur.analyser());
        }
    }
}


