package codebots.conditions;

import codebots.Bot;
import codebots.FunctionParser;
import codebots.arguments.Argument;
import codebots.arguments.CondArgument;
import codebots.arguments.IntArgument;
import codebots.exceptions.BadFormatException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Condition {
    public abstract boolean isTrue(Bot current, int curLine);
    private static HashMap<FunctionParser, Condition> memoizer = new HashMap<>();
    public String string;
    public String toString(){
        return this.string;
    }
    public static Condition parseCondition(String condition){
        FunctionParser parser = new FunctionParser(condition);
        Condition c = getMemoized(parser);
        if (c != null)
            return c;
        c = createCondition(parser);
        addMemoized(parser, c);
        c.string = parser.full_line;
        return c;
    }
    private static String parseName(String line){
        if (line.contains(" ")){
            return line.substring(0, line.indexOf(" "));
        } else {
            return line;
        }
    }

    private static Condition getMemoized(FunctionParser line){
        if (memoizer.containsKey(line)){
            return memoizer.get(line);
        }
        return null;
    }
    private static void addMemoized(FunctionParser line, Condition c){
        memoizer.put(line, c);
    }
    private static Condition createCondition(FunctionParser parser){
        if (parser.name.isEmpty()){
            return new EmptyCondition();
        }
        if (parser.name.equals("cond") || parser.name.equals("ocond")){
            CondArgument arg = (CondArgument)Argument.parseArgument(parser.full_line);
            return new RefCondition(arg);
        }
        if (parser.name.equals("modified")){
            checkArguments(parser, 1, "Modified");
            return new ModifiedCondition(Argument.parseArgument(parser.args.get(0)));
        }
        if (parser.name.equals("start")){
            checkArguments(parser, 0, "Start");
            return new StartCondition();
        }
        if (parser.name.equals("equals")){
            checkArguments(parser, 2, "Equals");
            return new EqualsCondition(Argument.parseArgument(parser.args.get(0)), Argument.parseArgument(parser.args.get(1)));
        }
        if (parser.name.equals("botat")){
            checkArguments(parser, 1, "BotAt");
            Argument a = Argument.parseArgument(parser.args.get(0));
            if (!(a instanceof IntArgument)) {
                throw new BadFormatException("BotAt requires a numerical value");
            }
            return new BotAtCondition((IntArgument) a);
        }
        List<Condition> conditions = new ArrayList<>();
        for (String arg:parser.args){
            conditions.add(parseCondition(arg));
        }
        if (parser.name.equals("all")){
            return new AllCondition(conditions);
        }
        if (parser.name.equals("none")){
            return new NoneCondition(conditions);
        }
        if (parser.name.equals("any")){
            return new AnyCondition(conditions);
        }
        if (parser.name.equals("not")){
            checkArguments(parser, 1, "Not");
            return new NotCondition(conditions.get(0));
        }
        throw new BadFormatException(parser.name+" is not a valid condition");
    }

    private static void checkArguments(FunctionParser parser, int numArguments, String name){
        if (parser.args.size() != numArguments){
            throw new BadFormatException(name+" requires exactly "+numArguments+" argument(s)");
        }
    }
}
