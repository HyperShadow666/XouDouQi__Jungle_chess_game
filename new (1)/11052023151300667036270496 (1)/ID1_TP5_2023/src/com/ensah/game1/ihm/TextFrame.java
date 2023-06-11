package com.ensah.game1.ihm;


import javax.swing.*;
import java.awt.*;

public class TextFrame extends JFrame {

    public TextFrame() {
        setTitle("Les Regles Du Jeu");
        setBounds(400, 150, 500, 300);

        String rulesText = "Comment Jouer?\n" +
                "Vous cliquez sur la piece que vous voulez deplacer, et vous pouvez la deplacer avec les fleches sur le clavier"+
                "Règles du jeu:\n" +
                "1 - Vous ne pouvez déplacer qu'une case vers le haut, le bas, la gauche ou la droite\n" +
                "2 - Les déplacements en diagonale ne sont pas autorisés\n" +
                "3 - Le rat est le seul animal capable de nager dans le lac\n" +
                "4 - Pour remporter le jeu, vous devez atteindre le sanctuaire\n" +
                "5 - Hiérarchie des pièces : (de la plus puissante à la plus faible)\n" +
                "  E : Elephant (La plus puissante)\n" +
                "  L : Lion\n" +
                "  T : Tigre\n" +
                "  P : Panthère\n" +
                "  D : Chien\n" +
                "  W : Loup\n" +
                "  C : Chat\n" +
                "  R : Rat\n" +
                "6 - Chaque animal peut manger un autre animal identique (Exemple : Le loup mange le loup)\n" +
                "7 - Exception : (Le rat est le seul animal capable de tuer l'éléphant)\n" +
                "8 - Vous ne pouvez pas entrer dans votre sanctuaire\n" +
                "9 - Le tigre et le lion peuvent sauter par-dessus le lac, mais pas s'il y a un rat sur leur chemin\n" +
                "10 - Si vous êtes dans un piège, n'importe quel animal peut vous tuer.\n\n";

        JTextPane rulesTextPane = new JTextPane();
        rulesTextPane.setEditable(false);
        rulesTextPane.setText(rulesText);

        // Set background color
        rulesTextPane.setBackground(Color.YELLOW);

        // Set font to bold
        Font font = rulesTextPane.getFont();
        Font boldFont = new Font(font.getFontName(), Font.BOLD, font.getSize());
        rulesTextPane.setFont(boldFont);

        JScrollPane scrollPane = new JScrollPane(rulesTextPane);
        add(scrollPane);
    }
}
