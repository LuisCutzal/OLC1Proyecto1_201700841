/*--------------------------------------------------
 ------------  1ra Area: Codigo de Usuario ---------
 ---------------------------------------------------*/

//------> Paquetes,importaciones
package Analizadores;
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
Letra               = [a-zA-ZñÑ]
cadena              = [\"][^\"\n]+[\"]| [\'][^\"\n]+[\']
id                  = {Letra}({Letra}|{numero}|_)*
especiales          = [\\][\']|[\\]["n"]

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

"{"         { System.out.println("Reconocio "+yytext()+" llaveabre"); return new Symbol(Simbolos.llaveabre, yycolumn, yyline, yytext()); }
"}"         { System.out.println("Reconocio "+yytext()+" llavecierra"); return new Symbol(Simbolos.llavecierra, yycolumn, yyline, yytext()); }
":"         { System.out.println("Reconocio "+yytext()+" dospuntos"); return new Symbol(Simbolos.dospuntos, yycolumn, yyline, yytext()); }
";"         { System.out.println("Reconocio "+yytext()+" puntoycoma"); return new Symbol(Simbolos.puntoycoma, yycolumn, yyline, yytext()); }
","         { System.out.println("Reconocio "+yytext()+" coma"); return new Symbol(Simbolos.coma, yycolumn, yyline, yytext()); }
"$"         { System.out.println("Reconocio "+yytext()+" dolar"); return new Symbol(Simbolos.dolar, yycolumn, yyline, yytext()); }
"\""        { System.out.println("Reconocio "+yytext()+" comilladoble"); return new Symbol(Simbolos.comilladoble, yycolumn, yyline, yytext()); }
"\'"        { System.out.println("Reconocio "+yytext()+" comillasimple"); return new Symbol(Simbolos.comillasimple, yycolumn, yyline, yytext()); }
"-"         { System.out.println("Reconocio "+yytext()+" guion"); return new Symbol(Simbolos.guion, yycolumn, yyline, yytext()); }
"_"         { System.out.println("Reconocio "+yytext()+" guionBajo"); return new Symbol(Simbolos.guionBajo, yycolumn, yyline, yytext()); }
"["         { System.out.println("Reconocio "+yytext()+" corcheteA"); return new Symbol(Simbolos.corcheteA, yycolumn, yyline, yytext()); }
"]"         { System.out.println("Reconocio "+yytext()+" corcheteC"); return new Symbol(Simbolos.corcheteC, yycolumn, yyline, yytext()); }
"="         { System.out.println("Reconocio "+yytext()+" igual"); return new Symbol(Simbolos.igual, yycolumn, yyline, yytext()); }
"("
//-----> Palabras reservadas

"GraficaBarras"     { System.out.println("Reconocio "+yytext()+" graficaBarras"); return new Symbol(Simbolos.graficaBarras, yycolumn, yyline, yytext()); }
"Titulo"            { System.out.println("Reconocio "+yytext()+" titulo"); return new Symbol(Simbolos.titulo, yycolumn, yyline, yytext()); }
"Ejex"              { System.out.println("Reconocio "+yytext()+" ejeX"); return new Symbol(Simbolos.ejeX, yycolumn, yyline, yytext()); }
"Valores"           { System.out.println("Reconocio "+yytext()+" Valores"); return new Symbol(Simbolos.Valores, yycolumn, yyline, yytext()); }
"TituloX"           { System.out.println("Reconocio "+yytext()+" tituloX"); return new Symbol(Simbolos.tituloX, yycolumn, yyline, yytext()); }
"TituloY"           { System.out.println("Reconocio "+yytext()+" tituloY"); return new Symbol(Simbolos.tituloY, yycolumn, yyline, yytext()); }

"GernerarReporteEstadistico"        { System.out.println("Reconocio "+yytext()+" generarReporteEstadistico"); return new Symbol(Simbolos.generarReporteEstadistico, yycolumn, yyline, yytext()); }
"compare"           { System.out.println("Reconocio "+yytext()+" compare"); return new Symbol(Simbolos.compare, yycolumn, yyline, yytext()); }

"GraficaPie"        { System.out.println("Reconocio "+yytext()+" graficaPie"); return new Symbol(Simbolos.graficaPie, yycolumn, yyline, yytext()); }

"GraficaLineas"     { System.out.println("Reconocio "+yytext()+" graficaLineas"); return new Symbol(Simbolos.graficaLineas, yycolumn, yyline, yytext()); }
"Archivo"           { System.out.println("Reconocio "+yytext()+" archivo"); return new Symbol(Simbolos.archivo, yycolumn, yyline, yytext()); }

"DefinirGlobales"   { System.out.println("Reconocio "+yytext()+" DefinirGlobales"); return new Symbol(Simbolos.DefinirGlobales, yycolumn, yyline, yytext()); }
"string"            { System.out.println("Reconocio "+yytext()+" string"); return new Symbol(Simbolos.string, yycolumn, yyline, yytext()); }
"double"            { System.out.println("Reconocio "+yytext()+" double"); return new Symbol(Simbolos.double, yycolumn, yyline, yytext()); }




//-------> Simbolos ER
{numero}            { System.out.println("Reconocio "+yytext()+" numero"); return new Symbol(Simbolos.numero, yycolumn, yyline, yytext()); }
{Letra}             { System.out.println("Reconocio "+yytext()+" letra"); return new Symbol(Simbolos.letra, yycolumn, yyline, yytext()); }
{cadena}            { System.out.println("Reconocio "+yytext()+" cadena"); return new Symbol(Simbolos.cadena, yycolumn, yyline, yytext()); }
{id}                { System.out.println("Reconocio "+yytext()+" id"); return new Symbol(Simbolos.id, yycolumn, yyline, yytext()); }
{especiales}        { System.out.println("Reconocio "+yytext()+" especiales"); return new Symbol(Simbolos.especiales, yycolumn, yyline, yytext()); }


//------> Espacios
{comentariosimple}      {System.out.println("Comentario: "+yytext()); }
{comentariodoble}       {System.out.println("Comentario doble: "+yytext()); }
[ \t\r\n\f]             {/* Espacios en blanco, se ignoran */}

//------> Errores Lexicos
.                       { System.out.println("Error Lexico"+yytext()+" Linea "+yyline+" Columna "+yycolumn); 
                           
                        }
