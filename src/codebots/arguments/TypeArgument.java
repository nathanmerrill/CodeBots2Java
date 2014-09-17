package codebots.arguments;

import codebots.Bot;
import codebots.exceptions.BadFormatException;

public class TypeArgument extends Argument<String> {
    protected IntArgument lineNumber;
    public TypeArgument(IntArgument lineNumber){
        this.lineNumber = lineNumber;
    }

    @Override
    public void setValue(Bot current, String value, int curLine) {
        throw new BadFormatException("Cannot set a type");
    }

    @Override
    public String getValue(Bot current, int curLine) {
        return current.getLine(lineNumber.getValue(current, curLine)).getParser().name;
    }

    @Override
    public boolean wasChanged(Bot current, int curLine) {
        throw new BadFormatException("Cannot check if Line Type was changed");
    }
}
