package parseur_pojet;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ParseurGUI extends JFrame {
    private Parseur parseur;
    private JTextField inputField;
    private JTextArea validArea;
    private JTextArea invalidArea;
    private JProgressBar progressBar;

    public ParseurGUI() {
        parseur = new Parseur();
        setTitle("Parseur Récursif Descendant");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Zone de saisie
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Saisie de la phrase"));
        JLabel inputLabel = new JLabel("Entrez une ou plusieurs phrases (séparées par '-') : ");
        inputField = new JTextField(30);
        JButton validateButton = new JButton("Valider");
        JButton clearButton = new JButton("Effacer");

        // Effet de survol sur les boutons
        validateButton.setBackground(Color.LIGHT_GRAY);
        validateButton.setOpaque(true);
        validateButton.setBorderPainted(false);
        validateButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                validateButton.setBackground(Color.GREEN);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                validateButton.setBackground(Color.LIGHT_GRAY);
            }
        });

        clearButton.setBackground(Color.LIGHT_GRAY);
        clearButton.setOpaque(true);
        clearButton.setBorderPainted(false);
        clearButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                clearButton.setBackground(Color.RED);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                clearButton.setBackground(Color.LIGHT_GRAY);
            }
        });

        inputPanel.add(inputLabel);
        inputPanel.add(inputField);
        inputPanel.add(validateButton);
        inputPanel.add(clearButton);

        // Zone des résultats
        JPanel resultPanel = new JPanel(new GridLayout(2, 1, 5, 5));

        // Phrases valides
        JPanel validPanel = new JPanel(new BorderLayout());
        validPanel.setBorder(BorderFactory.createTitledBorder("Phrases Valides"));
        validArea = new JTextArea();
        validArea.setEditable(false);
        validArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        validArea.setBackground(new Color(220, 255, 220));
        JScrollPane validScrollPane = new JScrollPane(validArea);
        validPanel.add(validScrollPane, BorderLayout.CENTER);

        // Phrases invalides
        JPanel invalidPanel = new JPanel(new BorderLayout());
        invalidPanel.setBorder(BorderFactory.createTitledBorder("Phrases Invalides"));
        invalidArea = new JTextArea();
        invalidArea.setEditable(false);
        invalidArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        invalidArea.setBackground(new Color(255, 220, 220));
        JScrollPane invalidScrollPane = new JScrollPane(invalidArea);
        invalidPanel.add(invalidScrollPane, BorderLayout.CENTER);

        // Ajouter les zones de résultats au panneau des résultats
        resultPanel.add(validPanel);
        resultPanel.add(invalidPanel);

        // Barre de progression
        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        mainPanel.add(progressBar, BorderLayout.SOUTH);

        // Actions des boutons
        validateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validerPhrases();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                effacer();
            }
        });

        // Ajouter les composants au panel principal
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(resultPanel, BorderLayout.CENTER);

        // Ajouter le panel principal à la fenêtre
        add(mainPanel);
    }

    private void validerPhrases() {
        String phrase = inputField.getText().trim();

        if (phrase.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer une ou plusieurs phrases.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Réinitialiser les zones de résultats
        validArea.setText("");
        invalidArea.setText("");

        // Séparer les phrases par le tiret
        String[] phrases = phrase.split("-");
        progressBar.setMaximum(phrases.length);
        progressBar.setValue(0);

        for (String p : phrases) {
            String trimmedPhrase = p.trim();
            if (!trimmedPhrase.isEmpty()) {
                parseur.chargerPhrase(trimmedPhrase);
                String resultat = parseur.analyser();

                if (resultat.equals("Phrase valide.")) {
                    validArea.append("✔️ " + trimmedPhrase + "\n");
                } else {
                    invalidArea.append("❌ " + trimmedPhrase + " (" + resultat + ")\n");
                }
            }
            progressBar.setValue(progressBar.getValue() + 1);
        }
    }

    private void effacer() {
        inputField.setText("");
        validArea.setText("");
        invalidArea.setText("");
        progressBar.setValue(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ParseurGUI gui = new ParseurGUI();
            gui.setVisible(true);
        });
    }
}
