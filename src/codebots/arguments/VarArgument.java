package codebots.arguments;

import codebots.Bot;

public class VarArgument extends IntArgument{
    private final char var;
    public VarArgument(char argument){
        var = argument;
    }


    @Override
    public Integer getValue(Bot current, int curLine) {
        return current.getVariable(var);
    }

    @Override
    public void setValue(Bot current, Integer value, int curLine) {
        current.setVariable(var, value);
    }


    @Override
    public boolean wasChanged(Bot current, int curLine) {
        return current.varWasModified(var);
    }
}
