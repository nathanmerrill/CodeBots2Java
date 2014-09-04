package codebots.conditions;


import codebots.Bot;

import java.util.List;

public class AnyCondition extends Condition{
    private final List<Condition> conditions;
    public AnyCondition(List<Condition> conditions){
        this.conditions = conditions;
    }

    @Override
    public boolean isTrue(Bot current, int curLine) {
        for (Condition c: conditions){
            if (c.isTrue(current, curLine)){
                return true;
            }
        }
        return false;
    }
}
