package codebots.actions;

import codebots.Bot;
import codebots.FunctionParser;
import codebots.arguments.Argument;
import codebots.conditions.Condition;
import codebots.exceptions.BadFormatException;

import java.util.HashMap;

public abstract class Action {
    private static HashMap<FunctionParser, Action> memoizer = new HashMap<>();
    public abstract void act(Bot bot, int curLine);
    public boolean equals(Action action){
        return action.parser.equals(this.parser);
    }
    @Override
    public String toString(){
        return this.parser.full_line;
    }
    public FunctionParser getParser(){
        return parser;
    }
    private FunctionParser parser;
    public static Action parseAction(String line, Bot creator){
        if (line.contains("Flag")){
            line = line.replace("Flag","Flag_"+creator.name);
        }
        FunctionParser parser = new FunctionParser(line);
        Action a = getMemoized(parser);
        if (a == null) {
            a = createAction(parser, creator);
        }
        a.parser = parser;
        addMemoized(parser, a);
        return a;
    }
    private static Action getMemoized(FunctionParser line){
        if (memoizer.containsKey(line)){
            return memoizer.get(line);
        }
        return null;
    }
    private static void addMemoized(FunctionParser line, Action a){
        memoizer.put(line, a);
    }
    private static Action createAction(FunctionParser parser, Bot creator){
        if (parser.name.equals("")){
            return new EmptyAction();
        }
        if (parser.name.equals("move")){
            if (!parser.args.isEmpty()){
                throw new BadFormatException("Move should not have any arguments");
            }
            return new MoveAction();
        } else if (parser.name.equals("copy")){
            if (parser.args.size() != 2){
                throw new BadFormatException("Copy requires exactly 2 arguments");
            }
            return new CopyAction(Argument.parseArgument(parser.args.get(0)), Argument.parseArgument(parser.args.get(1)));
        } else if (parser.name.startsWith("flag")){
            if (!parser.args.isEmpty()){
                throw new BadFormatException("Flag should not have any arguments");
            }
            return new FlagAction(creator);
        } else if (parser.name.equals("if")){
            if (parser.args.size() != 3){
                throw new BadFormatException("If should have 3 or more arguments");
            }
            return new IfAction(Condition.parseCondition(parser.args.get(0)), Argument.parseArgument(parser.args.get(1)), Argument.parseArgument(parser.args.get(2)));
        }
        throw new BadFormatException("Unrecognized action");
    }
}
