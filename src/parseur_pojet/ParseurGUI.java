package parseur_pojet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ParseurGUI extends JFrame {
    private Parseur parseur;
    private JTextField inputField;
    private JTextArea resultArea;

    public ParseurGUI() {
        parseur = new Parseur();
        setTitle("Parseur Récursif Descendant");
        setSize(700, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        
     // Zone de saisie
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Saisie de la phrase"));
        JPanel inputContentPanel = new JPanel(new FlowLayout());
        JLabel inputLabel = new JLabel("Entrez une phrase : ");
        inputField = new JTextField(30);
        JButton validateButton = new JButton("Valider");
        inputContentPanel.add(inputLabel);
        inputContentPanel.add(inputField);
        inputContentPanel.add(validateButton);
        inputPanel.add(inputContentPanel, BorderLayout.CENTER);

        // Zone de résultats
        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.setBorder(BorderFactory.createTitledBorder("Résultats"));
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(resultArea);
        resultPanel.add(scrollPane, BorderLayout.CENTER);

        // Action du bouton
        validateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validerPhrase();
            }
        });

        // Ajouter les composants au panel principal
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Ajouter le panel principal à la fenêtre
        add(mainPanel);
    }

    private void validerPhrase() {
        String phrase = inputField.getText();
        parseur.chargerPhrase(phrase);
        String resultat = parseur.analyser();
        resultArea.append("Phrase : " + phrase + "\n");
        resultArea.append("Résultat : " + resultat + "\n\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ParseurGUI gui = new ParseurGUI();
            gui.setVisible(true);
        });
    }
}
