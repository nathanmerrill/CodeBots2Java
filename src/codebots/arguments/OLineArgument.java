package codebots.arguments;

import codebots.Bot;
import codebots.actions.Action;

public class OLineArgument extends LineArgument{
    public OLineArgument(IntArgument lineNumber){
        super(lineNumber);
    }

    @Override
    public Action<?> getValue(Bot current, int curLine) {
        return super.getValue(current.getOpponent(), curLine);
    }

    @Override
    public void setValue(Bot current, Action<?> value, int curLine) {
        super.setValue(current.getOpponent(), value, curLine);
    }

    @Override
    public int getLineNumber(Bot current, int curLine) {
        return super.getLineNumber(current.getOpponent(), curLine);
    }

    @Override
    public boolean equals(Argument other, Bot current, int curLine) {
        return super.equals(other, current.getOpponent(), curLine);
    }

    @Override
    public boolean wasChanged(Bot current, int curLine) {
        return super.wasChanged(current.getOpponent(), curLine);
    }
}
