package codebots.arguments;

import codebots.Bot;
import codebots.exceptions.BadFormatException;

public class TypeArgument extends Argument<String> {
    private IntArgument lineNumber;
    public TypeArgument(IntArgument lineNumber){
        this.lineNumber = lineNumber;
    }

    @Override
    public boolean equals(Argument other, Bot current, int curLine) {
        return other.getValue(current, curLine).equals(getValue(current, curLine));
    }

    @Override
    public void setValue(Bot current, String value, int curLine) {
        throw new BadFormatException("Cannot set a type");
    }

    @Override
    public String getValue(Bot current, int curLine) {
        return current.getLine(lineNumber.getValue(current, curLine)).toString().split(" ")[0];
    }

    @Override
    public boolean wasChanged(Bot current, int curLine) {
        throw new BadFormatException("Cannot check if Line Type was changed");
    }
}
