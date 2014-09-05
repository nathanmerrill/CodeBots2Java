package codebots.arguments;

import codebots.Bot;

public class OVarArgument extends IntArgument{
    private final VarArgument argument;
    public OVarArgument(VarArgument arg){
        this.argument = arg;
    }
    @Override
    public void setValue(Bot current, Integer value, int curLine) {
        argument.setValue(current.getOpponent(), value, curLine);
    }

    @Override
    public Integer getValue(Bot current, int curLine) {
        return argument.getValue(current.getOpponent(), curLine);
    }


    @Override
    public boolean wasChanged(Bot current, int curLine) {
        return argument.wasChanged(current.getOpponent(), curLine);
    }
}
