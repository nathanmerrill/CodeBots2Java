package codebots.arguments;

import codebots.Bot;

public class OArgument extends Argument{
    private final Argument argument;
    public OArgument(Argument argument){
        this.argument = argument;
    }

    @Override
    public boolean equals(Argument other, Bot current, int curLine) {
        return other.getValue(current, curLine).equals(argument.getValue(current.getOpponent(), curLine));
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setValue(Bot current, Object value, int curLine) {
        argument.setValue(current.getOpponent(), value, curLine);
    }

    @Override
    public Object getValue(Bot current, int curLine) {
        return argument.getValue(current.getOpponent(), curLine);
    }

    @Override
    public boolean wasChanged(Bot current, int curLine) {
        return argument.wasChanged(current.getOpponent(), curLine);
    }
}
