package codebots.arguments;

import codebots.Bot;

public class OTypeArgument extends TypeArgument{
    public OTypeArgument(IntArgument lineNumber){
        super(lineNumber);
    }

    @Override
    public String getValue(Bot current, int curLine) {
        return current.getOpponent().getLine(lineNumber.getValue(current, curLine)).toString().split(" ")[0];
    }
}
