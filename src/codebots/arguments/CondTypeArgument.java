package codebots.arguments;

import codebots.Bot;
import codebots.exceptions.BadFormatException;

public class CondTypeArgument extends Argument<String>{
    private final IntArgument lineNumber;
    public CondTypeArgument(IntArgument lineNumber){
        this.lineNumber = lineNumber;
    }

    @Override
    public void setValue(Bot current, String value, int curLine) {
        throw new BadFormatException("Cannot set a conditional type");
    }

    @Override
    public String getValue(Bot current, int curLine) {
        return current.getCondition(lineNumber.getValue(current, curLine)).toString().split(" ")[0];
    }

    @Override
    public boolean equals(Argument other, Bot current, int curLine) {
        return other.getValue(current, curLine).equals(getValue(current, curLine));
    }


    @Override
    public boolean wasChanged(Bot current, int curLine) {
        throw new BadFormatException("Cannot check if Conditional Type was changed");
    }
}
