/*Trabajar sobre este para la practica*/
import clases.*;
import clases.PseudoLexer.Token;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Principal {
	public static void main(String[] args) {

		ArrayList<PseudoLexer.Token> tokens = new ArrayList<>();
		String data = Archivo.leerArchivo("prueba.txt");
		System.out.println(data);
		AnalizadorLexico analizadorLexico = new AnalizadorLexico(data);
		PseudoParser parser;
		if(analizadorLexico.tokensAceptados()) {
			tokens = analizadorLexico.getListaTokens();
		}
		parser = new PseudoParser(tokens);
		// for(String t: parser.tokens)
		// 	System.out.println(t);
		parser.run();

		
	}

}

		/* AnalizadorSintactico analizadorSintactico;

		if( analizadorLexico.tokensAceptados())
			tokens = analizadorLexico.getListaTokens();

		analizadorSintactico = new AnalizadorSintactico(tokens);

		//System.out.println("Resultado Final: " + (analizadorSintactico.analizar()?"Correcto":"Incorrecto"));
		analizadorSintactico.ejecutar();
		System.out.println("Fin del doc"); */