package codebots.actions;


import codebots.Bot;
import codebots.CodeBots;

public class MoveAction extends Action<MoveAction> {
    @Override
    public void act(Bot b, int curLine) {
        CodeBots.Direction direction = CodeBots.Direction.values()[b.getVariable('D')%4];
        int newX = b.x + direction.x;
        int newY = b.y + direction.y;
        if (newX < 0){
            newX += CodeBots.getWidth();
        } else if (b.x >= CodeBots.getWidth()){
            newX -= CodeBots.getWidth();
        }
        if (newY < 0){
            newY += CodeBots.getHeight();
        } else if (newY >= CodeBots.getHeight()){
            newY -= CodeBots.getHeight();
        }
        if (CodeBots.getBot(newX, newY) != null) {
            return;
        }
        CodeBots.removeBot(b.x, b.y);
        b.x = newX;
        b.y = newY;
        CodeBots.addBot(b, b.x, b.y);
    }
}