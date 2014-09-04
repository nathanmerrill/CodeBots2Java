package codebots.arguments;

import codebots.Bot;
import codebots.exceptions.BadFormatException;

public class ThisArgument extends IntArgument{


    @Override
    public Integer getValue(Bot current, int curLine) {
        return curLine;
    }

    @Override
    public void setValue(Bot current, Integer value, int curLine) {
        throw new BadFormatException("Not allowed to set to This");
    }


    @Override
    public boolean wasChanged(Bot current, int curLine) {
        throw new BadFormatException("Cannot check if This was changed");
    }
}
