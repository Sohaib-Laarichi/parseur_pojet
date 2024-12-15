package parseur_pojet;

import java.io.*;
import java.util.*;

public class Parseur {
    private List<String> articles;
    private List<String> noms;
    private List<String> verbes;
    private List<String> pronoms;

    private List<String> tokens;
    private int position = -1;
    private String currentToken;

    public Parseur() {
        chargerVocabulaire("vocabulaire.txt");
        pronoms = Arrays.asList("je", "tu", "il", "elle", "nous", "vous", "ils", "elles");
    }

    public void chargerPhrase(String phrase) {
        tokens = Arrays.asList(phrase.split("\\s+"));
        position = -1;
        avancer();
    }

    private void avancer() {
        position++;
        if (position < tokens.size()) {
            currentToken = tokens.get(position);
        } else {
            currentToken = null;
        }
    }

    public String analyser() {
        try {
            if (pronoms.contains(currentToken)) {
                // Vérifier le sujet avec un pronom
                pronomSujet();
                verbeAvecPronom();
            } else {
                // Vérifier le sujet avec un article + nom
                sujet();
                verbe();
            }
            complement();
            return "Phrase valide.";
        } catch (Exception e) {
            return "Erreur : " + e.getMessage();
        }
    }

    private void pronomSujet() throws Exception {
        // Si le sujet est un pronom, on avance sans vérifier l'article
        avancer();
        // Les pronoms sont déjà valides, on passe au verbe
    }

    private void sujet() throws Exception {
        if (articles.contains(currentToken)) {
            avancer();
            if (noms.contains(currentToken)) {
                avancer();
            } else {
                throw new Exception("Nom attendu après l'article.");
            }
        } else {
            throw new Exception("Article attendu au début du sujet.");
        }
    }

    private void verbe() throws Exception {
        if (verbes.contains(currentToken)) {
            avancer();
            System.out.println("Ajouter le complément : ");
        } else {
            throw new Exception("Verbe attendu après le sujet.");
        }
    }

    private void verbeAvecPronom() throws Exception {
        // Ici, vous vérifiez la conjugaison du verbe selon le pronom
        List<String> verbesAvecConjugaison = Arrays.asList(
            "mange", "mangent", // je mange, ils mangent
            "lit", "lisent",     // il lit, ils lisent
            "ouvre", "ouvrent",  // il ouvre, ils ouvrent
            "voit", "voient"     // il voit, ils voient
        );
        if (verbesAvecConjugaison.contains(currentToken)) {
            avancer();
            System.out.println("Ajouter le complément : ");
        } else {
            throw new Exception("Verbe non conjugé correctement pour le pronom.");
        }
    }

    private void complement() throws Exception {
        // Attendre le complément après le verbe
        if (articles.contains(currentToken)) {
            avancer();
            if (noms.contains(currentToken)) {
                avancer();
                System.out.println("Complément ajouté.");
            } else {
                throw new Exception("Nom attendu après l'article dans le complément.");
            }
        } else {
            throw new Exception("Article attendu au début du complément.");
        }
    }

    private void chargerVocabulaire(String chemin) {
        articles = new ArrayList<>();
        noms = new ArrayList<>();
        verbes = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(chemin))) {
            String ligne;
            List<String> listeCourante = null;

            while ((ligne = br.readLine()) != null) {
                ligne = ligne.trim();

                if (ligne.startsWith("# Articles")) {
                    listeCourante = articles;
                } else if (ligne.startsWith("# Noms")) {
                    listeCourante = noms;
                } else if (ligne.startsWith("# Verbes")) {
                    listeCourante = verbes;
                } else if (!ligne.isEmpty() && listeCourante != null) {
                    String[] mots = ligne.split(",\\s*");
                    listeCourante.addAll(Arrays.asList(mots));
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur de chargement du vocabulaire : " + e.getMessage());
        }
    }
}