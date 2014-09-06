package codebots;

import codebots.exceptions.BadFormatException;

import java.util.ArrayList;
import java.util.List;

public class FunctionParser {
    public final String name;
    public final List<String> args;
    public final String full_line;
    public FunctionParser(String function){
        if (function.contains("//")) {
            function = function.substring(0, function.indexOf("//"));
        }
        function = function.replace(" ","").trim().toLowerCase();
        full_line = function;
        args = new ArrayList<>();
        if (!function.contains("(")) {
            name = function;
            return;
        }
        name = function.substring(0, function.indexOf("(")).trim();
        if (!function.endsWith(")")){
            throw new BadFormatException(function+" needs closing parenthesis");
        }
        int numOpenParenthesis = 0;
        function = function.substring(function.indexOf("(")+1, function.length()-1);
        int start = 0;
        for (int i = 0; i < function.length(); i++){
            switch (function.charAt(i)){
                case ',':
                    if (numOpenParenthesis==0){
                        args.add(function.substring(start, i));
                        start = i+1;
                    }
                    break;
                case '(':
                    numOpenParenthesis++;
                    break;
                case ')':
                    numOpenParenthesis--;
                    break;
            }
        }
        if (numOpenParenthesis != 0){
            throw new BadFormatException("Needs closing parenthesis");
        }
        args.add(function.substring(start));
    }

    @Override
    public int hashCode() {
        return full_line.hashCode();
    }
}
