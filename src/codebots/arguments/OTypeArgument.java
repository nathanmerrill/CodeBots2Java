package codebots.arguments;

import codebots.Bot;
import codebots.CodeBots;

public class OTypeArgument extends TypeArgument{
    public OTypeArgument(IntArgument lineNumber){
        super(lineNumber);
    }

    @Override
    public String getValue(Bot current, int curLine) {
        return current.getOpponent().getLine((lineNumber.getValue(current, curLine)+current.getOpponent().offset)% CodeBots.numLines).toString().split(" ")[0];
    }
}
