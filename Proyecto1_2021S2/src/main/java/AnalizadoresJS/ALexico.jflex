/*--------------------------------------------------
 ------------  1ra Area: Codigo de Usuario ---------
 ---------------------------------------------------*/

//------> Paquetes,importaciones
package AnalizadoresJS;
import java_cup.runtime.*;


/*----------------------------------------------------------
  ------------  2da Area: Opciones y Declaraciones ---------
  ----------------------------------------------------------*/
%%
%{
    //----> Codigo de usuario en sintaxis java
%}

//-------> Directivas
%public 
%class ALexico
%cupsym SimbolosJS
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
comentariodoble     = [\#*][^]+[\*#] {InputCharacter}* {LineTerminator}?
//------> Estados

%%

/*------------------------------------------------
  ------------  3ra Area: Reglas Lexicas ---------
  ------------------------------------------------*/

//-----> Simbolos

"{"         { System.out.println("Reconocio "+yytext()+" llaveabre"); return new Symbol(SimbolosJS.llaveabre, yycolumn, yyline, yytext()); }
"}"         { System.out.println("Reconocio "+yytext()+" llavecierra"); return new Symbol(SimbolosJS.llavecierra, yycolumn, yyline, yytext()); }
":"         { System.out.println("Reconocio "+yytext()+" dospuntos"); return new Symbol(SimbolosJS.dospuntos, yycolumn, yyline, yytext()); }
";"         { System.out.println("Reconocio "+yytext()+" puntoycoma"); return new Symbol(SimbolosJS.puntoycoma, yycolumn, yyline, yytext()); }
","         { System.out.println("Reconocio "+yytext()+" coma"); return new Symbol(SimbolosJS.coma, yycolumn, yyline, yytext()); }
"$"         { System.out.println("Reconocio "+yytext()+" dolar"); return new Symbol(SimbolosJS.dolar, yycolumn, yyline, yytext()); }
"\""        { System.out.println("Reconocio "+yytext()+" comilladoble"); return new Symbol(SimbolosJS.comilladoble, yycolumn, yyline, yytext()); }
"\'"        { System.out.println("Reconocio "+yytext()+" comillasimple"); return new Symbol(SimbolosJS.comillasimple, yycolumn, yyline, yytext()); }
"-"         { System.out.println("Reconocio "+yytext()+" guion"); return new Symbol(SimbolosJS.guion, yycolumn, yyline, yytext()); }
"_"         { System.out.println("Reconocio "+yytext()+" guionBajo"); return new Symbol(SimbolosJS.guionBajo, yycolumn, yyline, yytext()); }
"["         { System.out.println("Reconocio "+yytext()+" corcheteA"); return new Symbol(SimbolosJS.corcheteA, yycolumn, yyline, yytext()); }
"]"         { System.out.println("Reconocio "+yytext()+" corcheteC"); return new Symbol(SimbolosJS.corcheteC, yycolumn, yyline, yytext()); }
"="         { System.out.println("Reconocio "+yytext()+" igual"); return new Symbol(SimbolosJS.igual, yycolumn, yyline, yytext()); }
"("         { System.out.println("Reconocio "+yytext()+" parentesisA"); return new Symbol(SimbolosJS.parentesisA, yycolumn, yyline, yytext()); }
")"         { System.out.println("Reconocio "+yytext()+" parentesisC"); return new Symbol(SimbolosJS.parentesisC, yycolumn, yyline, yytext()); }
//-----> Palabras reservadas

"GraficaBarras"     { System.out.println("Reconocio "+yytext()+" graficaBarras"); return new Symbol(SimbolosJS.graficaBarras, yycolumn, yyline, yytext()); }
"Titulo"            { System.out.println("Reconocio "+yytext()+" titulo"); return new Symbol(SimbolosJS.titulo, yycolumn, yyline, yytext()); }
"Ejex"              { System.out.println("Reconocio "+yytext()+" ejeX"); return new Symbol(SimbolosJS.ejeX, yycolumn, yyline, yytext()); }
"Valores"           { System.out.println("Reconocio "+yytext()+" Valores"); return new Symbol(SimbolosJS.Valores, yycolumn, yyline, yytext()); }
"TituloX"           { System.out.println("Reconocio "+yytext()+" tituloX"); return new Symbol(SimbolosJS.tituloX, yycolumn, yyline, yytext()); }
"TituloY"           { System.out.println("Reconocio "+yytext()+" tituloY"); return new Symbol(SimbolosJS.tituloY, yycolumn, yyline, yytext()); }

"GernerarReporteEstadistico"        { System.out.println("Reconocio "+yytext()+" generarReporteEstadistico"); return new Symbol(SimbolosJS.generarReporteEstadistico, yycolumn, yyline, yytext()); }
"compare"           { System.out.println("Reconocio "+yytext()+" compare"); return new Symbol(SimbolosJS.compare, yycolumn, yyline, yytext()); }

"GraficaPie"        { System.out.println("Reconocio "+yytext()+" graficaPie"); return new Symbol(SimbolosJS.graficaPie, yycolumn, yyline, yytext()); }

"GraficaLineas"     { System.out.println("Reconocio "+yytext()+" graficaLineas"); return new Symbol(SimbolosJS.graficaLineas, yycolumn, yyline, yytext()); }
"Archivo"           { System.out.println("Reconocio "+yytext()+" ar"); return new Symbol(SimbolosJS.ar, yycolumn, yyline, yytext()); }

"DefinirGlobales"   { System.out.println("Reconocio "+yytext()+" DefinirGlobales"); return new Symbol(SimbolosJS.DefinirGlobales, yycolumn, yyline, yytext()); }
"string"            { System.out.println("Reconocio "+yytext()+" string"); return new Symbol(SimbolosJS.string, yycolumn, yyline, yytext()); }
"double"            { System.out.println("Reconocio "+yytext()+" dou"); return new Symbol(SimbolosJS.dou, yycolumn, yyline, yytext()); }




//-------> Simbolos ER
{numero}            { System.out.println("Reconocio "+yytext()+" numero"); return new Symbol(SimbolosJS.numero, yycolumn, yyline, yytext()); }
{Letra}             { System.out.println("Reconocio "+yytext()+" letra"); return new Symbol(SimbolosJS.letra, yycolumn, yyline, yytext()); }
{cadena}            { System.out.println("Reconocio "+yytext()+" cadena"); return new Symbol(SimbolosJS.cadena, yycolumn, yyline, yytext()); }
{id}                { System.out.println("Reconocio "+yytext()+" id"); return new Symbol(SimbolosJS.id, yycolumn, yyline, yytext()); }
{decimal}           { System.out.println("Reconocio "+yytext()+" decimal"); return new Symbol(SimbolosJS.decimal, yycolumn, yyline, yytext()); }

//------> Espacios
{comentariosimple}      {System.out.println("Comentario: "+yytext()); }
{comentariodoble}       {System.out.println("Comentario doble: "+yytext()); }
[ \t\r\n\f]             {/* Espacios en blanco, se ignoran */}

//------> Errores Lexicos
.                       { System.out.println("Error Lexico"+yytext()+" Linea "+yyline+" Columna "+yycolumn); 
                           
                        }

