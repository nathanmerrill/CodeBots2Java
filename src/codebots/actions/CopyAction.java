package codebots.actions;


import codebots.Bot;
import codebots.arguments.Argument;

public class CopyAction extends Action<CopyAction>{

    private final Argument copyTo;
    private final Argument copyFrom;
    public CopyAction(Argument from, Argument to){
        copyTo = to;
        copyFrom = from;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void act(Bot b, int curLine) {
        copyTo.setValue(b, copyFrom.getValue(b, curLine), curLine);
    }
}
