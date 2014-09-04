package codebots.CodeBotsGUI;

import codebots.Bot;
import codebots.CodeBots;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class MainGrid extends JPanel {
    public final static int squareWidth = 11;
    public final static int squareHeight = 9;
    private Map<String, Character> botSymbols;
    private Map<Point, Color> highlights;
    public MainGrid(){
        super();
        Dimension size = new Dimension(CodeBots.getWidth()*squareWidth, (CodeBots.getHeight()+1)*squareHeight);
        this.setPreferredSize(size);
        highlights = new HashMap<>();
        botSymbols = new HashMap<>();
        HashSet<Character> used = new HashSet<>();
        for (Bot b: CodeBots.getBots(false)){
            if (botSymbols.containsKey(b.name))
                continue;
            boolean finished = false;
            for (int i = 0; i < b.name.length(); i++){
                if (!used.contains(b.name.toUpperCase().charAt(i))){
                    char c = b.name.toUpperCase().charAt(i);
                    botSymbols.put(b.name, c);
                    used.add(c);
                    finished = true;
                    break;
                } else if (!used.contains(b.name.toLowerCase().charAt(i))){
                    char c = b.name.toLowerCase().charAt(i);
                    botSymbols.put(b.name, c);
                    used.add(c);
                    finished = true;
                    break;
                }
            }
            if (finished)
                continue;
            for (int i = 33; i <= 687; i++){
                if (i == 127) {
                    i += 3;
                }else if (i == 141){
                    i+= 4;
                } else if (i==149 || i == 160){
                    i+=1;
                } else if (i == 157){
                    i+=2;
                }
                if (!used.contains((char) i)){
                    botSymbols.put(b.name, (char) i);
                    used.add((char) i);
                    finished = true;
                    break;
                }
            }

            if (finished)
                continue;
            throw new NoSuchElementException("Can't find character");
        }
    }

    public void addHighlight(int x, int y, Color color){
        highlights.put(new Point(x, y), color);
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        for (int i = 0; i < CodeBots.getWidth(); i++) {
            for (int j = 0; j< CodeBots.getHeight(); j++){
                Bot b = CodeBots.getBot(i, j);
                if (b== null)
                    continue;
                Point p = new Point(i, j);
                if (highlights.containsKey(p)){
                    g.setColor(highlights.remove(p));
                } else {
                    g.setColor(Color.black);
                }
                g.drawString(botSymbols.get(b.name)+"", i*squareWidth, j*squareHeight+squareHeight);
            }
        }
    }
}
