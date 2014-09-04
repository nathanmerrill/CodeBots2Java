package codebots.arguments;

import codebots.Bot;

public abstract class IntArgument extends Argument<Integer>{

    @Override
    public boolean equals(Argument other, Bot current, int curLine) {
        return other.getValue(current, curLine).equals(this.getValue(current, curLine));
    }
}
