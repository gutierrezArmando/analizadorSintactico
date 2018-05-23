package clases;

import java.util.ArrayList;

public class AnalizadorLexico {

    private PseudoLexer.Token token;
    private ArrayList<PseudoLexer.Token> tokens = new ArrayList<>();
    private PseudoLexer lexer;

    public AnalizadorLexico(String data) {
        lexer = new PseudoLexer(data);
        while ((token = lexer.nextToken())!=null)
            tokens.add(token);
    }

    public ArrayList<PseudoLexer.Token> getListaTokens() {
        return tokens;
    }

    public boolean tokensAceptados(){
        for(PseudoLexer.Token token: tokens)
        {
            if(token.type.toString().equals("ERROR"))
                return false;
        }
        return true;
    }
}
