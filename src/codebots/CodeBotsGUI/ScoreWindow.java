package codebots.CodeBotsGUI;

import codebots.Scorer;

import javax.swing.*;
import java.util.List;

public class ScoreWindow extends JFrame {
    public ScoreWindow(){
        JTextArea textField = new JTextArea();
        String text = "";
        this.add(new JTextField());
        List<Scorer> scorers = Scorer.getScores();
        for (int i = 1; i <= scorers.size(); i++){
            text+=i+". "+scorers.get(i-1).getScore()+"\t"+scorers.get(i-1).name+"\r\n";
        }
        textField.setText(text);
        this.add(textField);
        this.pack();
        this.setTitle("Scores");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

}
