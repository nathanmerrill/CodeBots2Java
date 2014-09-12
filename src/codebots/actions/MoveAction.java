package codebots.actions;


import codebots.Bot;

public class MoveAction extends Action {
    @Override
    public void act(Bot b, int curLine) {
        b.move();
    }
}