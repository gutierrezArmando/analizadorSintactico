package clases;

import java.util.ArrayList;

public class AnalizadorSintactico{

    ArrayList<String> tokens = new ArrayList<>();
    int index;

    public AnalizadorSintactico(ArrayList<PseudoLexer.Token> tokens)
    {
        for(PseudoLexer.Token token : tokens)
            this.tokens.add(token.type.toString());
        this.tokens.add("$");
        index = 0;
    }

    public void ejecutar() {

        for(String token: tokens)
            System.out.println(token);
        
        while (!tokens.get(index).equals("$")){
            if(!enunciadoCorrecto()){
                System.out.println("Error de compilacion.");
                break;
            }
        }
    }

    public boolean enunciadoCorrecto()
    {
        boolean resultado = false;
        switch (tokens.get(index++))
        {
            case "ESCRIBIR":
                {
                    System.out.println("Verificando escribir");
                    resultado =  escribirCorrecto();
                    System.out.println("Escribir Correcto");
                } break;
            case "LEER":
                {
                    System.out.println("Verificando leer");
                    resultado = leerCorrecto();
                    System.out.println("Leer Correcto");
                } break;
            case "VARIABLE":
                {
                    System.out.println("Verificando asignacion");
                    resultado = asignacionCorrecto();
                    System.out.println("Asigancion correcta");
                }break;
            case "SI":
                {
                    System.out.println("Verificando condicional SI");
                    resultado = siCorrecto();
                    System.out.println("Concidional SI verificado: " + resultado);
                }break;
            case "MIENTRAS":
                {
                    System.out.println("Verificando condicional MIENTRAS");
                    resultado = mientrasCorrecto();
                    System.out.println("Mientras correcto");
                }break;
        }
        return resultado;
    }

    /**Valida que el enunciado leer sea correcto */
    private boolean leerCorrecto() {
        if(!tokens.get(index++).equals("CADENA")) return false;
        if(!tokens.get(index++).equals("COMA")) return false;
        if(!tokens.get(index++).equals("VARIABLE")) return false;
        return true;
    }

    
    /**Valida que el enunciado de escribir este correcto */
    private boolean escribirCorrecto() {
        if (!tokens.get(index++).equals("CADENA")) return false;
        if (tokens.get(index).equals("$")) return true;
        if(tokens.get(index).equals("COMA")) index++;
        if(!tokens.get(index++).equals("VARIABLE")) return false;
        return true;
    }
    
    /**Valida que sea un valor, sea numero o identificador */
    private boolean tokenEsValor(int index) {
        return (tokens.get(index).equals("NUMERO")||tokens.get(index).equals("VARIABLE"));
    }

    /** Valida que el enunciado operacion este correcto*/
    private boolean operacionCorrecto() {
        if(!tokenEsValor(index++)) {
            
            return false;
        }
        if(!tokens.get(index++).equals("OPARITMETICO")) {
            return false;
        }
        if(!tokenEsValor(index++)) {
            return false;
        }
        return true;
    }

    private boolean asignacionCorrecto() {
        if(!tokens.get(index++).equals("IGUAL")) return false;
        if(!operacionCorrecto()) return false;
        return true;
    }

    private boolean comparacionCorrecta() {
        if(!tokens.get(index++).equals("PARENTESISIZQ")) return false;
        if(!tokenEsValor(index++)) return false;
        if(!tokens.get(index++).equals("OPRELACIONAL")) return false;
        if(!tokenEsValor(index++)) return false;
        if(!tokens.get(index++).equals("PARENTESISDER")) return false;
        return true;
    }

    private boolean siCorrecto() {
        boolean result;
        if(!comparacionCorrecta()) return false;
        if(!tokens.get(index++).equals("ENTONCES")){
            System.out.println("Error: Se esperaba \"entonces\""); 
            return false;
        }
        
        while(!tokens.get(index).equals("FINSI")){
            result = enunciadoCorrecto();
            System.out.println("La siguiente comparacion sera con: " + tokens.get(index));
            if(!result)
                return false;
        }
        return true;
    }

    private boolean mientrasCorrecto(){
        boolean result;
        if(!comparacionCorrecta()) return false;
        while(!tokens.get(index).equals("FINMIENTRAS")) {
            result = enunciadoCorrecto();
            if(!result)
                return false;
        }
        return true;
    }

}
