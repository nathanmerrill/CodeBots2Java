package codebots.arguments;

import codebots.Bot;
import codebots.FunctionParser;
import codebots.exceptions.BadFormatException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Argument<T> {
    public abstract T getValue(Bot current, int curLine);
    public abstract void setValue(Bot current, T value, int curLine);
    public boolean equals(Argument other, Bot current, int curLine){
        return other.getValue(current, curLine).equals(getValue(current, curLine));
    }
    public abstract boolean wasChanged(Bot current, int curLine);

    private static HashMap<FunctionParser, Argument> memoizer = new HashMap<>();
    public static Argument parseArgument(String argument){
        FunctionParser parser = new FunctionParser(argument);
        if (memoizer.containsKey(parser))
            return memoizer.get(parser);
        Argument returnArg = createArgument(parser);
        memoizer.put(parser, returnArg);
        return returnArg;
    }
    private static Argument createArgument(FunctionParser parser) {
        if (parser.name.length() == 1) {
            char letter = parser.name.toUpperCase().charAt(0);
            for (char c : new char[]{'A', 'B', 'C', 'D', 'E'}) {
                if (c == letter) {
                    return new VarArgument(c);
                }
            }
        }
        if (parser.name.equals("this")) {
            return new ThisArgument();
        }
        if (parser.args.isEmpty()) {
            try {
                return new NumArgument(Integer.parseInt(parser.name));
            } catch (NumberFormatException e) {
                throw new BadFormatException("Do not understand " + parser.name + " as an argument");
            }
        }
        List<Argument> parsedArgs = new ArrayList<>();
        for (String arg : parser.args) {
            parsedArgs.add(parseArgument(arg));
        }
        if (parser.name.equals("line")) {
            checkArguments(parser,1,"Type");
            checkNumerical(parsedArgs, "Line");
            IntArgument intArgument = (IntArgument) parsedArgs.get(0);
            return new LineArgument(intArgument);
        }
        if (parser.name.equals("type")){
            checkArguments(parser,1,"Type");
            checkNumerical(parsedArgs, "Type");
            IntArgument intArgument = (IntArgument) parsedArgs.get(0);
            return new TypeArgument(intArgument);
        }
        if (parser.name.equals("cond")){
            checkArguments(parser,1,"Cond");
            checkNumerical(parsedArgs, "Cond");
            IntArgument intArgument = (IntArgument) parsedArgs.get(0);
            return new CondArgument(intArgument);
        }
        if (parser.name.equals("condtype")){
            checkArguments(parser,1,"CondType");
            checkNumerical(parsedArgs, "CondType");
            IntArgument intArgument = (IntArgument) parsedArgs.get(0);
            return new CondTypeArgument(intArgument);
        }
        if (parser.name.equals("oline")){
            checkArguments(parser,1,"OLine");
            checkNumerical(parsedArgs, "OLine");
            IntArgument intArgument = (IntArgument) parsedArgs.get(0);
            return new OLineArgument(intArgument);
        }
        if (parser.name.equals("otype")){
            checkArguments(parser, 1, "OType");
            checkNumerical(parsedArgs, "OType");
            IntArgument intArgument = (IntArgument) parsedArgs.get(0);
            return new OTypeArgument(intArgument);
        }
        if (parser.name.equals("ocond")){
            checkArguments(parser, 1, "OCond");
            checkNumerical(parsedArgs, "OCond");
            IntArgument intArgument = (IntArgument) parsedArgs.get(0);
            return new OCondArgument(intArgument);
        }
        if (parser.name.equals("ocondtype")){
            checkArguments(parser, 1, "OCondType");
            checkNumerical(parsedArgs, "OCondType");
            IntArgument intArgument = (IntArgument) parsedArgs.get(0);
            return new OCondTypeArgument(intArgument);
        }
        if (parser.name.equals("ovar")){
            checkArguments(parser, 1, "OVar");
            if (!(parsedArgs.get(0) instanceof VarArgument)) {
                throw new BadFormatException("OVar must take a var argument");
            }
            VarArgument varArgument = (VarArgument) parsedArgs.get(0);
            return new OVarArgument(varArgument);
        }
        if (parser.name.equals("add")){
            checkNumerical(parsedArgs, "Add");
            return new AddArgument(parsedArgs);
        }
        if (parser.name.equals("sub")){
            checkNumerical(parsedArgs, "Sub");
            checkArguments(parser, 2, "Sub");
            return new SubArgument((IntArgument)parsedArgs.get(0), (IntArgument)parsedArgs.get(1));
        }
        if (parser.name.equals("mult")){
            checkNumerical(parsedArgs, "Mult");
            return new MultArgument(parsedArgs);
        }
        if (parser.name.equals("div")){
            checkNumerical(parsedArgs, "Div");
            checkArguments(parser, 2, "Div");
            return new DivArgument((IntArgument)parsedArgs.get(0), (IntArgument)parsedArgs.get(1));
        }
        if (parser.name.equals("mod")){
            checkNumerical(parsedArgs, "Mod");
            checkArguments(parser, 2, "Mod");
            return new ModArgument((IntArgument)parsedArgs.get(0), (IntArgument)parsedArgs.get(1));
        }
        throw new BadFormatException(parser.name+" is not a valid argument");
    }
    private static void checkArguments(FunctionParser parser, int numArguments, String name){
        if (parser.args.size() != numArguments){
            throw new BadFormatException(name+" requires exactly "+numArguments+" argument(s)");
        }
    }
    private static void checkNumerical(List<Argument> arguments, String name){
        for (Argument arg: arguments) {
            if (!(arg instanceof IntArgument)) {
                throw new BadFormatException(name + " only accepts numerical arguments");
            }
        }
    }
}
