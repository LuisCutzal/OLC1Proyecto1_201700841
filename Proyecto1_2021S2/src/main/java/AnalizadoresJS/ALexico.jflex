/*--------------------------------------------------
 ------------  1ra Area: Codigo de Usuario ---------
 ---------------------------------------------------*/

//------> Paquetes,importaciones
package AnalizadoresJS;
import java_cup.runtime.*;
import com.mycompany.proyecto1_2021s2.error;
import com.mycompany.proyecto1_2021s2.Ventana;

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
%cupsym Simbolos
%cup
%char
%column
%full
%line
%unicode

//------> Expresiones Regulares

numero              = [0-9]+
decimal             = [0-9]+("."[ |0-9]+)
caracter            = [a-zA-ZñÑ]
cadena              = [\"][^\"\n]+[\"]| [\'][^\"\n]+[\']
id                  = {caracter}({caracter}|{numero}|_)*

LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]

comentariosimple    = "//" {InputCharacter}* {LineTerminator}?
comentariodoble     = "\/*"[^]+ "\*/"{InputCharacter}* {LineTerminator}?
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
"\""        { System.out.println("Reconocio "+yytext()+" comilladoble"); return new Symbol(Simbolos.comilladoble, yycolumn, yyline, yytext()); }
"\'"        { System.out.println("Reconocio "+yytext()+" comillasimple"); return new Symbol(Simbolos.comillasimple, yycolumn, yyline, yytext()); }
"="         { System.out.println("Reconocio "+yytext()+" igual"); return new Symbol(Simbolos.igual, yycolumn, yyline, yytext()); }
"("         { System.out.println("Reconocio "+yytext()+" parentesisA"); return new Symbol(Simbolos.parentesisA, yycolumn, yyline, yytext()); }
")"         { System.out.println("Reconocio "+yytext()+" parentesisC"); return new Symbol(Simbolos.parentesisC, yycolumn, yyline, yytext()); }
"."         { System.out.println("Reconocio "+yytext()+" punto"); return new Symbol(Simbolos.punto, yycolumn, yyline, yytext()); }
"<"         { System.out.println("Reconocio "+yytext()+" menorque"); return new Symbol(Simbolos.menorque, yycolumn, yyline, yytext()); }
">"         { System.out.println("Reconocio "+yytext()+" mayorque"); return new Symbol(Simbolos.mayorque, yycolumn, yyline, yytext()); }
"!"         { System.out.println("Reconocio "+yytext()+" not"); return new Symbol(Simbolos.not, yycolumn, yyline, yytext()); }
"+"         { System.out.println("Reconocio "+yytext()+" mas"); return new Symbol(Simbolos.mas, yycolumn, yyline, yytext()); }
"-"         { System.out.println("Reconocio "+yytext()+" menos"); return new Symbol(Simbolos.menos, yycolumn, yyline, yytext()); }
"*"         { System.out.println("Reconocio "+yytext()+" multiplicacion"); return new Symbol(Simbolos.multiplicacion, yycolumn, yyline, yytext()); }
"/"         { System.out.println("Reconocio "+yytext()+" division"); return new Symbol(Simbolos.division, yycolumn, yyline, yytext()); }
"%"         { System.out.println("Reconocio "+yytext()+" modulo"); return new Symbol(Simbolos.modulo, yycolumn, yyline, yytext()); }
"&&"        { System.out.println("Reconocio "+yytext()+" and"); return new Symbol(Simbolos.and, yycolumn, yyline, yytext()); }
"||"        { System.out.println("Reconocio "+yytext()+" or"); return new Symbol(Simbolos.or, yycolumn, yyline, yytext()); }
"<="         { System.out.println("Reconocio "+yytext()+" menorIgual"); return new Symbol(Simbolos.menorIgual, yycolumn, yyline, yytext()); }
">="         { System.out.println("Reconocio "+yytext()+" mayorIgual"); return new Symbol(Simbolos.mayorIgual, yycolumn, yyline, yytext()); }
"!="         { System.out.println("Reconocio "+yytext()+" diferente"); return new Symbol(Simbolos.diferente, yycolumn, yyline, yytext()); }
"++"         { System.out.println("Reconocio "+yytext()+" incremento"); return new Symbol(Simbolos.incremento, yycolumn, yyline, yytext()); }
"--"         { System.out.println("Reconocio "+yytext()+" decremento"); return new Symbol(Simbolos.decremento, yycolumn, yyline, yytext()); }
"**"         { System.out.println("Reconocio "+yytext()+" potencia"); return new Symbol(Simbolos.potencia, yycolumn, yyline, yytext()); }
"=="         { System.out.println("Reconocio "+yytext()+" igualacion"); return new Symbol(Simbolos.igualacion, yycolumn, yyline, yytext()); }


//-----> Palabras reservadas

"class"             { System.out.println("Reconocio "+yytext()+" class_js"); return new Symbol(Simbolos.class_js, yycolumn, yyline, yytext()); }
"Class"             { System.out.println("Reconocio "+yytext()+" Class_js"); return new Symbol(Simbolos.Class_js, yycolumn, yyline, yytext()); }
"var"               { System.out.println("Reconocio "+yytext()+" var_js"); return new Symbol(Simbolos.var_js, yycolumn, yyline, yytext()); }
"let"               { System.out.println("Reconocio "+yytext()+" let_js"); return new Symbol(Simbolos.let_js, yycolumn, yyline, yytext()); }
"const"             { System.out.println("Reconocio "+yytext()+" const_js"); return new Symbol(Simbolos.const_js, yycolumn, yyline, yytext()); }

"require"           { System.out.println("Reconocio "+yytext()+" require"); return new Symbol(Simbolos.require, yycolumn, yyline, yytext()); }
"true"              { System.out.println("Reconocio "+yytext()+" true_js"); return new Symbol(Simbolos.true_js, yycolumn, yyline, yytext()); }
"fasle"             { System.out.println("Reconocio "+yytext()+" false_js"); return new Symbol(Simbolos.false_js, yycolumn, yyline, yytext()); }
"if"                { System.out.println("Reconocio "+yytext()+" if_js"); return new Symbol(Simbolos.if_js, yycolumn, yyline, yytext()); }
"else"              { System.out.println("Reconocio "+yytext()+" else_js"); return new Symbol(Simbolos.else_js, yycolumn, yyline, yytext()); }
"for"               { System.out.println("Reconocio "+yytext()+" for_js"); return new Symbol(Simbolos.for_js, yycolumn, yyline, yytext()); }

"while"             { System.out.println("Reconocio "+yytext()+" while_js"); return new Symbol(Simbolos.while_js, yycolumn, yyline, yytext()); }
"do"                { System.out.println("Reconocio "+yytext()+" do_js"); return new Symbol(Simbolos.do_js, yycolumn, yyline, yytext()); }

"switch"            { System.out.println("Reconocio "+yytext()+" switch_js"); return new Symbol(Simbolos.switch_js, yycolumn, yyline, yytext()); }

"case"              { System.out.println("Reconocio "+yytext()+" case_js"); return new Symbol(Simbolos.case_js, yycolumn, yyline, yytext()); }
"break"             { System.out.println("Reconocio "+yytext()+" break_js"); return new Symbol(Simbolos.break_js, yycolumn, yyline, yytext()); }
"default"           { System.out.println("Reconocio "+yytext()+" default_js"); return new Symbol(Simbolos.default_js, yycolumn, yyline, yytext()); }
"console"           { System.out.println("Reconocio "+yytext()+" console_js"); return new Symbol(Simbolos.console_js, yycolumn, yyline, yytext()); }
"log"               { System.out.println("Reconocio "+yytext()+" log_js"); return new Symbol(Simbolos.log_js, yycolumn, yyline, yytext()); }



//-------> Simbolos ER
{numero}            { System.out.println("Reconocio "+yytext()+" numero"); return new Symbol(Simbolos.numero, yycolumn, yyline, yytext()); }

{cadena}            { System.out.println("Reconocio "+yytext()+" cadena"); return new Symbol(Simbolos.cadena, yycolumn, yyline, yytext()); }
{id}                { System.out.println("Reconocio "+yytext()+" id"); return new Symbol(Simbolos.id, yycolumn, yyline, yytext()); }
{decimal}           { System.out.println("Reconocio "+yytext()+" decimal"); return new Symbol(Simbolos.decimal, yycolumn, yyline, yytext()); }

//------> Espacios
{comentariosimple}      {System.out.println("Comentario: "+yytext()); 
                        Ventana.listacomentarios.add(yytext());    
                        }
{comentariodoble}       {System.out.println("Comentario doble: "+yytext()); 
                        Ventana.listacomentarios.add(yytext()); 
                        }
[ \t\r\n\f]             {/* Espacios en blanco, se ignoran */}

//------> Errores Lexicos
.                       { System.out.println("Error Lexico"+yytext()+" Linea "+yyline+" Columna "+yycolumn); 
                            error nuevo = new error("Error Lexico", yytext(), yyline, yycolumn);
                            Ventana.listaErrores.add(nuevo);
                        }

