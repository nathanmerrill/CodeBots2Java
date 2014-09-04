package codebots.arguments;

import codebots.Bot;
import codebots.exceptions.BadFormatException;

public class NumArgument extends IntArgument {
    private final Integer num;
    public NumArgument(Integer argument){
        num = argument;
    }

    @Override
    public Integer getValue(Bot current, int curLine) {
        return num;
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
