package codebots;

import codebots.CodeBotsGUI.StartGUI;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.util.*;

public class CodeBots {

    private static int width, height;
    private static Map<Integer, Bot> bots;
    public final static int numLines = 24;
    public final static int totalTurns = 5000;
    public final static int totalGames = 10;
    public final static int numBotCopies = 50;
    public static int currentTurn = 0;
    public static int currentGame = 0;
    public static boolean finished = false;
    public enum Direction{
        North(0, -1), East(1, 0), South(0, 1), West(-1, 0);
        public int x, y;
        Direction(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
    public static Collection<Bot> getBots(boolean createCopy){
        if (createCopy){
            return new ArrayList<>(bots.values());
        }
        return bots.values();
    }
    public static List<Bot> getBots(){
        return new ArrayList<>(bots.values());
    }
    public static int getWidth(){
        return width;
    }
    public static int getHeight(){
        return height;
    }
    public static void addBot(Bot b, int x, int y){
        bots.put(toCoordinates(x, y), b);
    }
    private static int toCoordinates(int x, int y){
        return x+y*width;
    }
    public static Bot getBot(int x, int y){
        if (bots.containsKey(toCoordinates(x, y))) {
            return bots.get(toCoordinates(x, y));
        }
        return null;
    }
    public static void removeBot(int x, int y){
        if (bots.containsKey(toCoordinates(x,y))) {
            bots.remove(toCoordinates(x, y));
        }
    }


    public static void main(String[] args){
        if (args.length >0 && args[0].startsWith("T")){
            run();
        } else {
            StartGUI gui = new StartGUI();
            gui.run();
        }
    }
    public static void initBots(){
        bots = new HashMap<>();
        File[] files = readBotFolder();
        ArrayList<Bot> botList = new ArrayList<>(files.length*50);
        for (File f: files) {
            String filename = f.getName();
            String name = filename.substring(0,filename.indexOf("."));
            try {
                Scanner scanner = new Scanner(f);
                String lines = scanner.useDelimiter("\\Z").next();
                scanner.close();
                for (int i = 0; i < numBotCopies; i++) {
                    Bot b = new Bot(name, lines);
                    botList.add(b);
                    Scorer.addBot(b);
                }
            } catch (FileNotFoundException e){
                System.out.println("File not found");
            }
        }
        placeBots(botList);
    }
    public static File[] readBotFolder() {
        File dir = new File("src/codebots/bots");
        return dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().endsWith(".txt");
            }
        });
    }
    public static void placeBots(List<Bot> bots){
        Collections.shuffle(bots);
        int maxX = (int) Math.sqrt(bots.size())*2+1;
        width = maxX;
        int curY = 0;
        int curX = 0;
        for (Bot b : bots) {
            if (curX > maxX) {
                curY++;
                curX = (curY % 2) * 2;
            }
            b.x = curX;
            b.y = curY;
            CodeBots.bots.put(toCoordinates(b.x, b.y), b);
            curX += 4;
        }
        height = curY+1;
    }
    public static void run(){
        initBots();
        takeTurns();
        printScores();
    }
    public static void takeTurns(){
        while (!finished)
            takeTurn();
    }
    public static void takeTurn(){
        if (finished)
            return;
        Bot[] allBots = bots.values().toArray(new Bot[bots.size()]);
        for (Bot b: allBots){
            b.takeTurn();
        }
        currentTurn++;
        if (currentTurn == totalTurns){
            Scorer.scoreBots();
            currentGame++;
            currentTurn = 0;
            if (currentGame == totalGames){
                finished = true;
            } else {
                reset();
            }
        }

    }
    public static void reset(){
        for (Bot b: bots.values()){
            b.reset();
        }
        ArrayList<Bot> temp = new ArrayList<>(bots.values());
        bots.clear();
        placeBots(temp);
    }
    public static void printScores(){
        System.out.println("There were " + Scorer.noFlags + " bots with no majority flag");
        for (Scorer b: Scorer.getScores()){
            System.out.println(b.name+" got a score of "+b.getScore());
        }
    }
}
