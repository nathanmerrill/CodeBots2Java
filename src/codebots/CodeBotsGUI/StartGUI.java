package codebots.CodeBotsGUI;

import codebots.CodeBots;

public class StartGUI {
    private MainWindow host;
    public StartGUI(){
        CodeBots.initBots();
        host = new MainWindow();
    }
    public void run(){
        host.setVisible(true);
    }
}
