package codebots.conditions;

import codebots.Bot;
import codebots.arguments.IntArgument;
import codebots.exceptions.NoOpponent;

public class BotAtCondition extends Condition{
    private final IntArgument direction;
    public BotAtCondition(IntArgument direction){
        this.direction = direction;
    }
    @Override
    public boolean isTrue(Bot current, int curLine) {
        try {
            current.getOpponent(direction.getValue(current, curLine));
            return true;
        }catch (NoOpponent e){
            return false;
        }
    }
}
