package codebots.arguments;

import codebots.Bot;
import codebots.conditions.Condition;

public class CondArgument extends Argument<Condition>{
    private final IntArgument lineNumber;
    public CondArgument(IntArgument lineNumber){
        this.lineNumber = lineNumber;
    }

    @Override
    public void setValue(Bot current, Condition value, int curLine) {
        current.setCondition(lineNumber.getValue(current, curLine), value);
    }

    @Override
    public Condition getValue(Bot current, int curLine) {
        return current.getCondition(lineNumber.getValue(current, curLine));
    }

    @Override
    public boolean equals(Argument other, Bot current, int curLine) {
        return other.getValue(current, curLine).equals(getValue(current, curLine));
    }


    @Override
    public boolean wasChanged(Bot current, int curLine) {
        return current.conditionWasModified(lineNumber.getValue(current, curLine));
    }
}
