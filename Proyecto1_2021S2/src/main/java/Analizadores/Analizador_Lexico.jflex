/*--------------------------------------------------
 ------------  1ra Area: Codigo de Usuario ---------
 ---------------------------------------------------*/

//------> Paquetes,importaciones
package Analizadores;
import java_cup.runtime.*;
import com.mycompany.proyecto1_2021s2.error;
import com.mycompany.proyecto1_2021s2.Ventana;
import com.mycompany.proyecto1_2021s2.Token;

/*----------------------------------------------------------
  ------------  2da Area: Opciones y Declaraciones ---------
  ----------------------------------------------------------*/
%%
%{
    //----> Codigo de usuario en sintaxis java
%}

//-------> Directivas
%public 
%class Analizador_Lexico
%cupsym Simbolos
%cup
%char
%column
%full
%ignorecase
%line
%unicode

//------> Expresiones Regulares

numero              = [0-9]+
decimal             = [0-9]+("."[ |0-9]+)
Letra               = [a-zA-ZñÑ]
cadena              = [\"][^\"\n]+[\"]| [\'][^\"\n]+[\']
id                  = {Letra}({Letra}|{numero}|_)*

LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]

comentariosimple    = "##" {InputCharacter}* {LineTerminator}?
comentariodoble = "#*" [^*] ~"*#" | "#*" "*"+ "#"
//------> Estados

%%

/*------------------------------------------------
  ------------  3ra Area: Reglas Lexicas ---------
  ------------------------------------------------*/

//-----> Simbolos

"{"         { System.out.println("Reconocio "+yytext()+" llaveabre");   
              Token nuevoTk = new Token("Llave Abre",yytext(), yyline, yycolumn);
              Ventana.listaTokens.add(nuevoTk);
              return new Symbol(Simbolos.llaveabre, yycolumn, yyline, yytext()); }
"}"         { System.out.println("Reconocio "+yytext()+" llavecierra");   
              Token nuevoTk = new Token("Llave cierra",yytext(), yyline, yycolumn);
              Ventana.listaTokens.add(nuevoTk);
              return new Symbol(Simbolos.llavecierra, yycolumn, yyline, yytext()); }
":"         { System.out.println("Reconocio "+yytext()+" dospuntos");   
              Token nuevoTk = new Token("Dos puntos",yytext(), yyline, yycolumn);
              Ventana.listaTokens.add(nuevoTk);
              return new Symbol(Simbolos.dospuntos, yycolumn, yyline, yytext()); }
";"         { System.out.println("Reconocio "+yytext()+" puntoycoma");   
              Token nuevoTk = new Token("Punto y Coma",yytext(), yyline, yycolumn);
              Ventana.listaTokens.add(nuevoTk);
              return new Symbol(Simbolos.puntoycoma, yycolumn, yyline, yytext()); }
","         { System.out.println("Reconocio "+yytext()+" coma");   
              Token nuevoTk = new Token("Coma",yytext(), yyline, yycolumn);
              Ventana.listaTokens.add(nuevoTk);
              return new Symbol(Simbolos.coma, yycolumn, yyline, yytext()); }
"$"         { System.out.println("Reconocio "+yytext()+" dolar");   
              Token nuevoTk = new Token("Dolar",yytext(), yyline, yycolumn);
              Ventana.listaTokens.add(nuevoTk);
              return new Symbol(Simbolos.dolar, yycolumn, yyline, yytext()); }
"\""        { System.out.println("Reconocio "+yytext()+" comilladoble");   
              Token nuevoTk = new Token("Comilla Doble",yytext(), yyline, yycolumn);
              Ventana.listaTokens.add(nuevoTk);
              return new Symbol(Simbolos.comilladoble, yycolumn, yyline, yytext()); }
"\'"        { System.out.println("Reconocio "+yytext()+" comillasimple");   
              Token nuevoTk = new Token("Comilla Simple",yytext(), yyline, yycolumn);
              Ventana.listaTokens.add(nuevoTk);
              return new Symbol(Simbolos.comillasimple, yycolumn, yyline, yytext()); }
"-"         { System.out.println("Reconocio "+yytext()+" guion");   
              Token nuevoTk = new Token("Guion",yytext(), yyline, yycolumn);
              Ventana.listaTokens.add(nuevoTk);
              return new Symbol(Simbolos.guion, yycolumn, yyline, yytext()); }
"_"         { System.out.println("Reconocio "+yytext()+" guionBajo");   
              Token nuevoTk = new Token("Guion Bajo",yytext(), yyline, yycolumn);
              Ventana.listaTokens.add(nuevoTk);
              return new Symbol(Simbolos.guionBajo, yycolumn, yyline, yytext()); }
"["         { System.out.println("Reconocio "+yytext()+" corcheteA");   
              Token nuevoTk = new Token("CorcheteA",yytext(), yyline, yycolumn);
              Ventana.listaTokens.add(nuevoTk);
              return new Symbol(Simbolos.corcheteA, yycolumn, yyline, yytext()); }
"]"         { System.out.println("Reconocio "+yytext()+" corcheteC");   
              Token nuevoTk = new Token("CorcheteC",yytext(), yyline, yycolumn);
              Ventana.listaTokens.add(nuevoTk);
              return new Symbol(Simbolos.corcheteC, yycolumn, yyline, yytext()); }
"="         { System.out.println("Reconocio "+yytext()+" igual");   
              Token nuevoTk = new Token("Igual",yytext(), yyline, yycolumn);
              Ventana.listaTokens.add(nuevoTk);
              return new Symbol(Simbolos.igual, yycolumn, yyline, yytext()); }
"("         { System.out.println("Reconocio "+yytext()+" parentesisA");   
              Token nuevoTk = new Token("ParentesisA",yytext(), yyline, yycolumn);
              Ventana.listaTokens.add(nuevoTk);
              return new Symbol(Simbolos.parentesisA, yycolumn, yyline, yytext()); }
")"         { System.out.println("Reconocio "+yytext()+" parentesisC");  
              Token nuevoTk = new Token("ParentesisC",yytext(), yyline, yycolumn);
              Ventana.listaTokens.add(nuevoTk);
              return new Symbol(Simbolos.parentesisC, yycolumn, yyline, yytext()); }
//-----> Palabras reservadas

"GraficaBarras"     { System.out.println("Reconocio "+yytext()+" graficaBarras");  
                        Token nuevoTk = new Token("Grafica Barras",yytext(), yyline, yycolumn);
                        Ventana.listaTokens.add(nuevoTk);
                        return new Symbol(Simbolos.graficaBarras, yycolumn, yyline, yytext()); }
"Titulo"            { System.out.println("Reconocio "+yytext()+" titulo");  
                        Token nuevoTk = new Token("Titulo",yytext(), yyline, yycolumn);
                        Ventana.listaTokens.add(nuevoTk);
                        return new Symbol(Simbolos.titulo, yycolumn, yyline, yytext()); }
"Ejex"              { System.out.println("Reconocio "+yytext()+" ejeX");  
                        Token nuevoTk = new Token("EjeX",yytext(), yyline, yycolumn);
                        Ventana.listaTokens.add(nuevoTk);
                        return new Symbol(Simbolos.ejeX, yycolumn, yyline, yytext()); }
"Valores"           { System.out.println("Reconocio "+yytext()+" Valores");  
                        Token nuevoTk = new Token("Valores",yytext(), yyline, yycolumn);
                        Ventana.listaTokens.add(nuevoTk);
                        return new Symbol(Simbolos.Valores, yycolumn, yyline, yytext()); }
"TituloX"           { System.out.println("Reconocio "+yytext()+" tituloX");  
                        Token nuevoTk = new Token("Titulo X",yytext(), yyline, yycolumn);
                        Ventana.listaTokens.add(nuevoTk);
                        return new Symbol(Simbolos.tituloX, yycolumn, yyline, yytext()); }
"TituloY"           { System.out.println("Reconocio "+yytext()+" tituloY");  
                        Token nuevoTk = new Token("Titulo Y",yytext(), yyline, yycolumn);
                        Ventana.listaTokens.add(nuevoTk);
                        return new Symbol(Simbolos.tituloY, yycolumn, yyline, yytext()); }

"GenerarReporteEstadistico"        { System.out.println("Reconocio "+yytext()+" generarReporteEstadistico");  
                        Token nuevoTk = new Token("Generar Reporte Estadistico",yytext(), yyline, yycolumn);
                        Ventana.listaTokens.add(nuevoTk);
                        return new Symbol(Simbolos.generarReporteEstadistico, yycolumn, yyline, yytext()); }
"compare"           { System.out.println("Reconocio "+yytext()+" compare");  
                        Token nuevoTk = new Token("Compare",yytext(), yyline, yycolumn);
                        Ventana.listaTokens.add(nuevoTk);
                        return new Symbol(Simbolos.compare, yycolumn, yyline, yytext()); }

"GraficaPie"        { System.out.println("Reconocio "+yytext()+" graficaPie");  
                        Token nuevoTk = new Token("Grafia Pie",yytext(), yyline, yycolumn);
                        Ventana.listaTokens.add(nuevoTk);
                        return new Symbol(Simbolos.graficaPie, yycolumn, yyline, yytext()); }

"GraficaLineas"     { System.out.println("Reconocio "+yytext()+" graficaLineas");  
                        Token nuevoTk = new Token("Grafica Lineas",yytext(), yyline, yycolumn);
                        Ventana.listaTokens.add(nuevoTk);
                        return new Symbol(Simbolos.graficaLineas, yycolumn, yyline, yytext()); }
"Archivo"           { System.out.println("Reconocio "+yytext()+" ar");  
                        Token nuevoTk = new Token("Archivo",yytext(), yyline, yycolumn);
                        Ventana.listaTokens.add(nuevoTk);
                        return new Symbol(Simbolos.ar, yycolumn, yyline, yytext()); }

"DefinirGlobales"   { System.out.println("Reconocio "+yytext()+" DefinirGlobales");  
                        Token nuevoTk = new Token("Definir Globales",yytext(), yyline, yycolumn);
                        Ventana.listaTokens.add(nuevoTk);
                        return new Symbol(Simbolos.DefinirGlobales, yycolumn, yyline, yytext()); }
"string"            { System.out.println("Reconocio "+yytext()+" string");  
                        Token nuevoTk = new Token("String",yytext(), yyline, yycolumn);
                        Ventana.listaTokens.add(nuevoTk);
                        return new Symbol(Simbolos.string, yycolumn, yyline, yytext()); }
"double"            { System.out.println("Reconocio "+yytext()+" dou");  
                        Token nuevoTk = new Token("Double",yytext(), yyline, yycolumn);
                        Ventana.listaTokens.add(nuevoTk);
                        return new Symbol(Simbolos.dou, yycolumn, yyline, yytext()); }



"PuntajeEspecifico" { System.out.println("Reconocio "+yytext()+" PuntajeEspecifico");  
                        Token nuevoTk = new Token("Puntaje Especifico",yytext(), yyline, yycolumn);
                        Ventana.listaTokens.add(nuevoTk);
                        return new Symbol(Simbolos.PuntajeEspecifico, yycolumn, yyline, yytext()); }
"PuntajeGeneral"    { System.out.println("Reconocio "+yytext()+" PuntajeGeneral");  
                        Token nuevoTk = new Token("Puntaje General",yytext(), yyline, yycolumn);
                        Ventana.listaTokens.add(nuevoTk);
                        return new Symbol(Simbolos.PuntajeGeneral, yycolumn, yyline, yytext()); }

//-------> Simbolos ER
{numero}            { System.out.println("Reconocio "+yytext()+" numero");  
                        Token nuevoTk = new Token("Numero",yytext(), yyline, yycolumn);
                        Ventana.listaTokens.add(nuevoTk);
                        return new Symbol(Simbolos.numero, yycolumn, yyline, yytext()); }
{Letra}             { System.out.println("Reconocio "+yytext()+" letra"); 
                        Token nuevoTk = new Token("Letra",yytext(), yyline, yycolumn);
                        Ventana.listaTokens.add(nuevoTk);
                        return new Symbol(Simbolos.letra, yycolumn, yyline, yytext()); }
{cadena}            { System.out.println("Reconocio "+yytext()+" cadena"); 
                        Token nuevoTk = new Token("Cadena",yytext(), yyline, yycolumn);
                        Ventana.listaTokens.add(nuevoTk);
                        return new Symbol(Simbolos.cadena, yycolumn, yyline, yytext()); }
{id}                { System.out.println("Reconocio "+yytext()+" id"); 
                        Token nuevoTk = new Token("Identificador",yytext(), yyline, yycolumn);
                        Ventana.listaTokens.add(nuevoTk);
                        return new Symbol(Simbolos.id, yycolumn, yyline, yytext()); }
{decimal}           { System.out.println("Reconocio "+yytext()+" decimal"); 
                        Token nuevoTk = new Token("Decimal",yytext(), yyline, yycolumn);
                        Ventana.listaTokens.add(nuevoTk);
                        return new Symbol(Simbolos.decimal, yycolumn, yyline, yytext());}

//------> Espacios
{comentariosimple}      {System.out.println("Comentario: "+yytext()); }
{comentariodoble}       {System.out.println("Comentario doble: "+yytext()); }
[ \t\r\n\f]             {/* Espacios en blanco, se ignoran */}

//------> Errores Lexicos
.                       { System.out.println("Error Lexico FCA"+yytext()+" Linea "+yyline+" Columna "+yycolumn); 
                            error nuevo = new error("Error Lexico en FCA: ", yytext(), yyline, yycolumn);
                            Ventana.listaErrores.add(nuevo);
                        }
