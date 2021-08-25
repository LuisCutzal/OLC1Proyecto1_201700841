package Generador2;
import java.io.File;

public class GeneradorLexico2 {
    public static void main (String[] args){
        String path="src/main/java/AnalizadoresJS/ALexico.jflex";
        generarLexer(path);
    }
    public static void generarLexer(String path){
        File file=new File(path);
        jflex.Main.generate(file);
    }
}
