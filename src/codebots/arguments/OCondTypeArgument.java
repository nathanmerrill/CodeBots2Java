package codebots.arguments;

import codebots.Bot;

public class OCondTypeArgument extends CondTypeArgument{
    public OCondTypeArgument(IntArgument lineNumber){
        super(lineNumber);
    }

    @Override
    public String getValue(Bot current, int curLine) {
        return current.getOpponent().getCondition(lineNumber.getValue(current, curLine)).toString().split(" ")[0];
    }
}
