package codebots.conditions;

import codebots.Bot;
import codebots.arguments.Argument;

public class EqualsCondition extends Condition{
    private final Argument arg1, arg2;
    public EqualsCondition(Argument arg1, Argument arg2){
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    @Override
    public boolean isTrue(Bot current, int curLine) {
        return arg1.getValue(current, curLine).equals(arg2.getValue(current, curLine));
    }
}
