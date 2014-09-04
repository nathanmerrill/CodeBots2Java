package codebots.conditions;

import codebots.Bot;

public class NotCondition extends Condition{
    private Condition c;
    public NotCondition(Condition c){
        this.c = c;
    }

    @Override
    public boolean isTrue(Bot current, int curLine) {
        return !c.isTrue(current, curLine);
    }
}
