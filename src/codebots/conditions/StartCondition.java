package codebots.conditions;

import codebots.Bot;
import codebots.CodeBots;

public class StartCondition extends Condition{
    @Override
    public boolean isTrue(Bot current, int curLine) {
        return CodeBots.currentTurn == 0;
    }
}
