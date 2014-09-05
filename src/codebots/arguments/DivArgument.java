package codebots.arguments;

import codebots.Bot;
import codebots.CodeBots;
import codebots.exceptions.BadFormatException;

public class DivArgument extends IntArgument{
    private final IntArgument arg1, arg2;
    public DivArgument(IntArgument arg1, IntArgument arg2){
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    @Override
    public Integer getValue(Bot current, int lineNumber) {
        int divideBy = arg2.getValue(current, lineNumber);
        if (divideBy == 0){
            return 0;
        }
        return arg1.getValue(current,lineNumber)/divideBy % CodeBots.numLines;
    }

    @Override
    public void setValue(Bot current, Integer value, int curLine) {
        throw new BadFormatException("Cannot set to a number");
    }

    @Override
    public boolean wasChanged(Bot current, int curLine) {
        throw new BadFormatException("Cannot check if number was changed");
    }
}
