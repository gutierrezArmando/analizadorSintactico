package clases;

import java.util.ArrayList;

public class PseudoParser {
    public ArrayList<String> tokens = new ArrayList<>();
    int index;
    public String mensaje;
    boolean resultadoFinal;

    public PseudoParser(ArrayList<PseudoLexer.Token> tokens)
    {
        for(PseudoLexer.Token token : tokens)
            this.tokens.add(token.type.toString());
        this.tokens.add("$");
        index = 0;
    }

    public void run(){
        if(!esProgramaValido()) {
            System.out.println(mensaje);
            return;
        }
        return;
        // while(!tokens.get(index).equals("$")){
        //     resultadoFinal = esEnunciado();
        //     System.out.println(mensaje);
        //     if(!resultadoFinal){
        //         System.out.println("Error de compilacion");
        //         return;
        //     }   
        // }
        // System.out.println("Compilacion finalizada");
    }

    private boolean esProgramaValido(){
        Boolean resultado;
        if(!tokens.get(index++).equals("INICIOPROGRAMA")){
            mensaje = "Error: Se esperaba 'inicio-programa', recibido: " + tokens.get(index-1);
            return false;  
        }
        while(!tokens.get(index).equals("FINPROGRAMA")&&!tokens.get(index).equals("$")) {
            resultado = esEnunciado();
            System.out.println(mensaje);
            //System.out.println("Siguiente token: " + tokens.get(index));
            if(!resultado)
                return false;
        }
        //System.out.println("Salida del ciclo");
        if(tokens.get(index).equals("$")) {
            mensaje = "Error:Fin de documento, no se encontro 'fin-programa'";
            return false;
        }
        else{
            mensaje = "Programa valido";
            System.out.println(mensaje);
        }
        return true;
    }

    private boolean esEnunciado() {
        boolean resultado;
        //System.out.println("Iniciando con: " + tokens.get(index));
        switch (tokens.get(index)) {
            case "VARIABLE":
                {
                    System.out.print("Verificando ASIGNACION : ");
                    resultado = this.asignacionCorrecto();
                }break;
            case "LEER":
                {
                    System.out.print("Verificando LEER : ");
                    resultado = this.leerCorrecto();
                }break;
            case "ESCRIBIR":
                {
                    System.out.print("Verificando ESCRIBIR : ");
                    resultado = this.escribirCorrecto();
                }break;
            case "SI":
                {
                    System.out.println("* Verificando estructura CONDICIONAL SI");
                    resultado = this.siCorrecto();
                }break;
            case "MIENTRAS":
                {
                    System.out.println("* Verificando estructura CONDICIONAl MIENTRAS");
                    resultado = this.mientrasCorrecto();
                }break;    
            default:
                {
                    resultado = false;
                    mensaje = "Error de operador";
                }
                break;
        }
        return resultado;
    }

    /** Valida que el enunciado operacion este correcto*/
    private boolean operacionCorrecto() {
        /**Usa los 3 primeros token, iniciando con el numero de token apuntado, segun la instruccion anterior */
        if(!tokenEsValor(index++)){
            mensaje = "Error: Se esperaba un NUMERO o VARIABLE";
            index--;
            return false;
        }
        if(!tokens.get(index++).equals("OPARITMETICO")) {
            mensaje = "Error: Se esperaba un OPERADOR ARITMETICO";
            index-=2;
            return false;
        }
        if(!tokenEsValor(index++)){
            mensaje = "Error: Se esperaba un NUMERO o VARIABLE";
            index-=3;
            return false;
        }
        mensaje = "Operacion correcta";
        /**Apunta al siguiente token, despues del ultimo numero o variable utilizado aqui */
        return true;
    }

    /**Valida que sea un valor, sea numero o identificador */
    private boolean tokenEsValor(int index) {
        return (tokens.get(index).equals("NUMERO")||tokens.get(index).equals("VARIABLE"));
    }

    /**Valida que el enunciado asignacion este correcto */
    private boolean asignacionCorrecto() {
        if(!tokens.get(index++).equals("VARIABLE")) {
            mensaje = "Error: Se esperaba una VARIABLE";
            return false;
        }
        if(!tokens.get(index++).equals("IGUAL")) {
            mensaje = "Error: Se esperaba simbolo '=' " + "encontrado: " + tokens.get(index-1);
            return false;
        }
        if(!operacionCorrecto())
            if(!tokenEsValor(index++)) {
                mensaje = "Error: Se esperana una VARIABLE";
                return false;
            }
        mensaje = "Asignacion Correcta";
        return true;
        /**Deja el apuntador de token preparado para analizar el siguiente enunciado */
    }

    /**Valida que el enunciado leer sea correcto */
    private boolean leerCorrecto() {
        if(!tokens.get(index++).equals("LEER")){
            mensaje = "Error: Se esperaba palabra LEER";
            return false;
        }
        if(!tokens.get(index++).equals("CADENA")){
            mensaje = "Error: Se esperaba una CADENA";
            return false;
        }
        if(!tokens.get(index++).equals("COMA")){
            mensaje = "Error: Se esperaba una COMA"; 
            return false;
        }
        if(!tokens.get(index++).equals("VARIABLE")){
            mensaje = "Error: Se esperaba una VARIABLE";
            return false;
        }
        mensaje="Leer Correcto";
        return true;
    }

    /**Valida que el enunciado de escribir este correcto */
    private boolean escribirCorrecto() {
        if (!tokens.get(index++).equals("ESCRIBIR")){
            mensaje = "Error: Se esperaba palabra reservada ESCRIBIR";
            return false;
        }
        if (!tokens.get(index++).equals("CADENA")){
            mensaje = "Error: Se esperaba una cadena";
            return false;
        }
        if (tokens.get(index).equals("$")) {
            mensaje = "Escribir correcto";
            return true;
        }
        if(tokens.get(index).equals("COMA")) {
            mensaje = "En encontro una COMA";
            index++;
            if(!tokens.get(index++).equals("VARIABLE")) {
                mensaje = "Error: Se esperaba una variable";
                return false;
            }
        }
        mensaje = "Escribir correcto";
        return true;
    }/**Fin de metodo escribir correcto */

    private boolean comparacionCorrecta() {
        if(!tokens.get(index++).equals("PARENTESISIZQ")) {
            mensaje = "Error: Se esperaba '(', recibido: " + tokens.get(index-1);
            return false;
        }
        if(!tokenEsValor(index++)) {
            mensaje = "Error: Se esperaba 'VALOR'";
            return false;
        }
        if(!tokens.get(index++).equals("OPRELACIONAL")) {
            mensaje = "Error: Se esperaba 'OPERADOR RELACIONAL'";
            return false;
        }
        if(!tokenEsValor(index++)) {
            mensaje = "Error: Se esperaba 'VALOR'";
            return false;
        }
        if(!tokens.get(index++).equals("PARENTESISDER")) {
            mensaje = "Error: Se esperaba ')'";
            return false;
        }
        return true;
    }

    private boolean siCorrecto() {
        boolean result;
        if(!tokens.get(index++).equals("SI")) {
            mensaje = "Error: Se esperaba instruccion SI";
            return false;
        }
        if(!comparacionCorrecta()) return false;
        if(!tokens.get(index++).equals("ENTONCES")){
            System.out.println("Error: Se esperaba \"entonces\""); 
            return false;
        }
        
        while(!tokens.get(index).equals("FINSI")){
            result = esEnunciado();
            System.out.println(mensaje);
            if(!result)
                return false;
        }
        index++;
        mensaje = "** Fin de SI fue correcto";
        return true;
    }

    private boolean mientrasCorrecto(){
        boolean result;
        if (!tokens.get(index++).equals("MIENTRAS")){
            mensaje = "Error: se esperaba instruccion 'MIENTRAS' Recibido: " + tokens.get(index);
            return false;
        }
        if(!comparacionCorrecta()) return false;
        while(!tokens.get(index).equals("FINMIENTRAS")) {
            result = esEnunciado();
            System.out.println(mensaje);
            if(!result)
                return false;
        }
        index++;
        mensaje = "** Ciclo MIENTRAS fue correcto";
        return true;
    }
}