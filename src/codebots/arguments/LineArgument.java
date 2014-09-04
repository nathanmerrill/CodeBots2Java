package codebots.arguments;

import codebots.Bot;
import codebots.actions.Action;

public class LineArgument extends Argument<Action<?>> {
    private final IntArgument lineNumber;
    public LineArgument(IntArgument lineNumber){
        this.lineNumber = lineNumber;
    }

    public int getLineNumber(Bot current, int curLine){
        return lineNumber.getValue(current, curLine);
    }

    @Override
    public void setValue(Bot current, Action<?> value, int curLine) {
        current.setLine(lineNumber.getValue(current, curLine), value);
    }

    @Override
    public Action<?> getValue(Bot current, int curLine) {
        return current.getLine(lineNumber.getValue(current, curLine));
    }

    @Override
    public boolean equals(Argument other, Bot current, int curLine) {
        return other.getValue(current, curLine).equals(getValue(current, curLine));
    }


    @Override
    public boolean wasChanged(Bot current, int curLine) {
        return current.lineWasModified(lineNumber.getValue(current, curLine));
    }
}
