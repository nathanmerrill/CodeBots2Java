package codebots.CodeBotsGUI;

import codebots.Bot;
import codebots.CodeBots;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BotWindow extends JFrame {
    private final List<JTextArea> code;
    private final JTextArea title;
    private final JTextArea vars;
    public final Bot bot;
    public BotWindow(Bot b){
        this.bot = b;
        this.setLayout(new GridLayout(CodeBots.numLines+2,1));
        title = new JTextArea(b.name);
        title.setFont(new Font(null, 0, 20));
        this.add(title);
        vars = new JTextArea("");
        this.add(vars);
        code = new ArrayList<>(CodeBots.numLines);
        for (int i = 0; i < CodeBots.numLines; i++){
            code.add(new JTextArea(""));
            this.add(code.get(i));
        }
        setValues();
        this.pack();
    }
    public void setValues(){
        String varChars = "";
        for (int i = 'A'; i <= 'E'; i++){
            varChars+=(char)i+":"+bot.getVariable((char)i)+" ";
        }
        vars.setText(varChars);
        for (int i = 0; i < CodeBots.numLines; i++){
            Color background = Color.white;
            if (bot.getVariable('C')==i){
                background = Color.cyan;
            } else if (bot.conditionIsTrue(i)){
               background = Color.green;
            } else if (bot.lineIsChanged(i)){
                background = Color.lightGray;
            }
            code.get(i).setBackground(background);
            code.get(i).setText(bot.getCondition(i).toString()+":"+bot.getLine(i).toString());
        }
    }

    @Override
    public void repaint() {
        super.repaint();
        setValues();
    }
}
