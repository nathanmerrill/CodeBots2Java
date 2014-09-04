package codebots.conditions;

import codebots.Bot;
import codebots.arguments.Argument;

public class ModifiedCondition extends Condition {
    private final Argument var;
    public ModifiedCondition(Argument var){
        this.var = var;
    }
    @Override
    public boolean isTrue(Bot current, int curLine) {
       return var.wasChanged(current, curLine);
    }
}
