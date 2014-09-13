package codebots.arguments;

import codebots.Bot;
import codebots.CodeBots;

public class OVarArgument extends IntArgument{
    private final VarArgument argument;
    public OVarArgument(VarArgument arg){
        this.argument = arg;
    }
    @Override
    public void setValue(Bot current, Integer value, int curLine) {
        if (argument.var == 'C'){
            value += current.getOpponent().offset% CodeBots.numLines;
        }
        argument.setValue(current.getOpponent(), value, curLine);
    }

    @Override
    public Integer getValue(Bot current, int curLine) {
        int value =  argument.getValue(current.getOpponent(), curLine);
        if (argument.var == 'C'){
            value += current.getOpponent().offset% CodeBots.numLines;
        }
        return value;
    }


    @Override
    public boolean wasChanged(Bot current, int curLine) {
        return argument.wasChanged(current.getOpponent(), curLine);
    }
}
