package codebots.arguments;

import codebots.Bot;
import codebots.actions.Action;

public class OLineArgument extends LineArgument{
    public OLineArgument(IntArgument lineNumber){
        super(lineNumber);
    }

    @Override
    public Action getValue(Bot current, int curLine) {
        return current.getOpponent().getLine(getLineNumber(current, curLine));
    }

    @Override
    public void setValue(Bot current, Action value, int curLine) {
        current.getOpponent().setLine(getLineNumber(current, curLine), value);
    }

    @Override
    public boolean wasChanged(Bot current, int curLine) {
        return current.getOpponent().lineIsChanged(lineNumber.getValue(current, curLine));
    }
}
