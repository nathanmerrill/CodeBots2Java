package codebots.arguments;

import codebots.Bot;
import codebots.CodeBots;
import codebots.exceptions.BadFormatException;

import java.util.List;

public class AddArgument extends IntArgument{
    private final List<Argument> addends;
    public AddArgument(List<Argument> addends){
        this.addends = addends;
    }

    @Override
    public Integer getValue(Bot current, int lineNumber) {
        int sum = 0;
        for (Argument addend: addends){
            sum += (int)addend.getValue(current, lineNumber);
        }
        return sum % CodeBots.numLines;
    }

    @Override
    public void setValue(Bot current, Integer value, int curLine) {
        throw new BadFormatException("Cannot set to an addend");
    }

    @Override
    public boolean wasChanged(Bot current, int curLine) {
        throw new BadFormatException("Cannot check if number was changed");
    }
}
