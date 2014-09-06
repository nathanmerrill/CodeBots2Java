package codebots.arguments;

import codebots.Bot;
import codebots.conditions.Condition;

public class OCondArgument extends CondArgument{
    public OCondArgument(IntArgument lineNumber){
        super(lineNumber);
    }

    @Override
    public void setValue(Bot current, Condition value, int curLine) {
        current.getOpponent().setCondition(super.lineNumber.getValue(current, curLine), value);
    }

    @Override
    public Condition getValue(Bot current, int curLine) {
        return current.getOpponent().getCondition(super.lineNumber.getValue(current, curLine));
    }

    @Override
    public boolean wasChanged(Bot current, int curLine) {
        return current.getOpponent().conditionWasModified(lineNumber.getValue(current, curLine));
    }
}
