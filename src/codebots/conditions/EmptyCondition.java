package codebots.conditions;

import codebots.Bot;

public class EmptyCondition extends Condition{
    @Override
    public boolean isTrue(Bot current, int curLine) {
        return false;
    }
}
