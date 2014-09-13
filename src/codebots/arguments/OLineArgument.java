package codebots.arguments;

import codebots.Bot;
import codebots.CodeBots;
import codebots.actions.Action;

public class OLineArgument extends LineArgument{
    public OLineArgument(IntArgument lineNumber){
        super(lineNumber);
    }

    @Override
    public Action getValue(Bot current, int curLine) {
        return current.getOpponent().getLine((getLineNumber(current, curLine)+current.getOpponent().offset)% CodeBots.numLines);
    }

    @Override
    public void setValue(Bot current, Action value, int curLine) {
        current.getOpponent().setLine((getLineNumber(current, curLine)+current.getOpponent().offset)% CodeBots.numLines, value);
    }

    @Override
    public boolean wasChanged(Bot current, int curLine) {
        return current.getOpponent().lineIsChanged((lineNumber.getValue(current, curLine)+current.getOpponent().offset)% CodeBots.numLines);
    }
}
