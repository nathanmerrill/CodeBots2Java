package codebots.actions;

import codebots.Bot;
import codebots.arguments.Argument;
import codebots.arguments.LineArgument;
import codebots.conditions.Condition;
import codebots.exceptions.BadFormatException;

import java.util.HashSet;

public class IfAction extends Action{
    private final Condition condition;
    private final LineArgument trueLine;
    private final LineArgument falseLine;
    public final static HashSet<String> recursionBlocker = new HashSet<>();
    public IfAction(Condition condition, Argument trueLine, Argument falseLine){
        this.condition = condition;
        if (! (trueLine instanceof LineArgument)) {
            throw new BadFormatException("First argument on If must be a line");
        }
        if (! (falseLine instanceof LineArgument)) {
            throw new BadFormatException("Second argument on If must be a line");
        }
        this.trueLine = (LineArgument) trueLine;
        this.falseLine = (LineArgument) falseLine;
    }

    @Override
    public void act(Bot b, int curLine) {
        if (recursionBlocker.contains(this.toString()))
            return;
        recursionBlocker.add(this.toString());
        int lineNumber;
        if (condition.isTrue(b, curLine)){
            lineNumber = trueLine.getLineNumber(b, curLine);
        } else {
            lineNumber = falseLine.getLineNumber(b, curLine);
        }
        b.getLine(lineNumber).act(b, lineNumber);
    }
}
