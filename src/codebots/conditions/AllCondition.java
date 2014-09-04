package codebots.conditions;


import codebots.Bot;

import java.util.List;

public class AllCondition extends Condition{
    private final List<Condition> conditions;
    public AllCondition(List<Condition> conditions){
        this.conditions = conditions;
    }

    @Override
    public boolean isTrue(Bot current, int curLine) {
        for (Condition c: conditions){
            if (!c.isTrue(current, curLine)){
                return false;
            }
        }
        return true;
    }
}
