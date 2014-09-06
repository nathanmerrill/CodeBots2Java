package codebots.conditions;

import codebots.Bot;
import codebots.arguments.CondArgument;

public class RefCondition extends Condition{
    private final CondArgument condition;
    public RefCondition(CondArgument condition){
        this.condition = condition;
    }

    @Override
    public boolean isTrue(Bot current, int curLine) {
        return condition.getValue(current, curLine).isTrue(current, curLine);
    }
}
