package codebots;

import codebots.actions.Action;
import codebots.actions.FlagAction;
import codebots.actions.IfAction;
import codebots.conditions.Condition;
import codebots.conditions.StartCondition;
import codebots.exceptions.BadFormatException;
import codebots.exceptions.NoOpponent;

import java.util.*;

public class Bot {
    private final Action[] actions;
    private final Action[] initialActions;
    private final Set<Integer> actionsChanged;
    private final int[] vars;
    private final Set<Character> varsChanged;
    private final Condition[] initialConditions;
    private final Condition[] conditions;
    private final Set<Integer> conditionsChanged;
    private final int startLine;
    public final String name;
    public int x, y;
    private static Random random = new Random(System.currentTimeMillis());



    public Bot(String name, String text){
        this.name = name;
        String[] lines = text.replace("\r","").split("\n");
        actions = new Action[CodeBots.numLines];
        initialActions = new Action[CodeBots.numLines];
        actionsChanged = new HashSet<>(CodeBots.numLines);
        conditions = new Condition[CodeBots.numLines];
        conditionsChanged = new HashSet<>(CodeBots.numLines);
        initialConditions = new Condition[CodeBots.numLines];
        int code_index = 0;
        for (int i = 0; code_index < CodeBots.numLines; i++){
            if (i < lines.length && lines[i].isEmpty())
                continue;
            try {
                if (i < lines.length) {
                    parseCondition(lines[i], code_index);
                } else {
                    parseCondition("Flag", code_index);
                }
            } catch (BadFormatException e){
                throw new BadFormatException("Could not parse line "+i+" in "+this.name+".",e);
            }
            initialActions[code_index] = actions[code_index];
            initialConditions[code_index] = conditions[code_index];
            code_index++;
        }
        int lineOfStart = -1;
        for (int i = 0; i < CodeBots.numLines; i++){
            Condition c = conditions[i];
            if (c instanceof StartCondition) {
                if (lineOfStart == -1)
                    lineOfStart = i;
                else
                    throw new BadFormatException(this.name +" cannot have more than 1 Start condition");
            }
        }
        if (lineOfStart == -1){
            throw new BadFormatException(this.name+" must have at least 1 Start condition");
        }
        startLine = lineOfStart;
        vars = new int[5];
        varsChanged = new HashSet<>(5);
        setVars();
    }
    public void parseCondition(String condition, int index){
        String[] parts = condition.split(":");
        if (parts.length > 2)
            throw new BadFormatException("Cannot have more than 1 ':'");
        if (parts.length == 2) {
            conditions[index] = Condition.parseCondition(parts[0]);
            actions[index] = Action.parseAction(parts[1], this);
        } else {
            conditions[index] = Condition.parseCondition("");
            actions[index] = Action.parseAction(parts[0], this);
        }
    }
    public Bot getOpponent(){
        return getOpponent(vars['D'-'A']);
    }
    public Bot getOpponent(int direction){
        CodeBots.Direction directionVal = CodeBots.Direction.values()[direction%4];
        Bot b = CodeBots.getBot(x+directionVal.x, y+directionVal.y);
        if (b == null){
            throw new NoOpponent();
        }
        return b;
    }
    public Action getLine(int lineNumber){
        return actions[lineNumber%24];
    }
    public void setLine(int lineNumber, Action action){
        actionsChanged.add(lineNumber%24);
        actions[lineNumber%24] =  action;
    }
    public int getVariable(char varName){
        return vars[varName-'A'];
    }
    public void setVariable(Character varName, int value){
        varsChanged.add(varName);
        vars[varName-'A'] = value;
    }
    public Condition getCondition(int lineNumber){
        return conditions[lineNumber%24];
    }
    public void setCondition(int lineNumber, Condition condition){
        conditionsChanged.add(lineNumber%24);
        conditions[lineNumber%24] = condition;
    }
    public String declareFlag(){
        HashMap<String, Integer> count = new HashMap<>();
        for (Action a: this.actions){
            if (a instanceof FlagAction) {
                FlagAction fl = (FlagAction) a;
                if (count.containsKey(fl.owner.name)){
                    count.put(fl.owner.name, count.get(fl.owner.name)+1);
                } else{
                    count.put(fl.owner.name, 1);
                }
            }
        }
        int maxCount = 0;
        String maxName = "";
        for (String name: count.keySet()){
            if (count.get(name) > maxCount){
                maxCount = count.get(name);
                maxName = name;
            }else if (count.get(name) == maxCount){
                maxName = "";
            }
        }
        return maxName;
    }
    public void takeTurn(){
        IfAction.recursionBlocker.clear();
        vars['E'-'A'] = random.nextInt(CodeBots.numLines);
        checkConditions();
        actionsChanged.clear();
        conditionsChanged.clear();
        varsChanged.clear();
        try {
            getLine(vars['C' - 'A']).act(this, vars['C'-'A']);
        } catch(NoOpponent e){
        } catch (Exception e){
            System.out.println(getLine(vars['C' - 'A']).toString());
            throw e;
        }
        vars['C'-'A'] = (vars['C'-'A']+1)%CodeBots.numLines;
    }
    private void setVars(){
        for (int i = 0; i < 5; i++){
            vars[i] = 0;
        }
        vars['D'-'A'] = random.nextInt(CodeBots.numLines);
    }
    public void reset(){
        System.arraycopy(initialActions, 0, actions, 0, actions.length);
        System.arraycopy(initialConditions, 0, conditions, 0, conditions.length);
        setVars();
    }
    public boolean lineWasModified(Integer lineNumber){
        return actionsChanged.contains(lineNumber);
    }
    public boolean conditionWasModified(Integer lineNumber){
        return conditionsChanged.contains(lineNumber);
    }
    public boolean varWasModified(Character varName){
        return varsChanged.contains(varName);
    }
    private void checkConditions(){
        for (int i = 0; i < CodeBots.numLines; i++){
            int lineNum = (i+startLine)%CodeBots.numLines;
            if (conditionIsTrue(i)){
                vars['C' - 'A'] = lineNum;
                return;
            }
        }
    }
    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("\n");
        string.append(this.name);
        string.append("\n");
        string.append("--------------------------------\n");
        for (int i = 0; i < CodeBots.numLines; i++){
            string.append(conditions[i].toString());
            string.append(" : ");
            string.append(actions[i].toString());
            string.append("\n");
        }
        return string.toString();
    }

    @SuppressWarnings("unchecked")
    public int getDiff(){
        int diff = 0;
        for (int i = 0; i < CodeBots.numLines; i++){
            if (lineIsChanged(i)){
                diff++;
            }
        }
        return diff;
    }
    public boolean lineIsChanged(int i){
        return !actions[i].equals(initialActions[i])
                ||!conditions[i].equals(initialConditions[i]);
    }
    public boolean conditionIsTrue(int i){
        try {
            return conditions[i].isTrue(this, i);
        }catch (NoOpponent e){
            return false;
        }
    }
}
