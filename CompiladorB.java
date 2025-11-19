import java.util.Objects;
import java.util.ArrayList;
import java.util.List;

/*
 * Em vez de imprimir o codigo C diretamente durante a analise,
 * este compilador constrói uma Árvore Sintática Abstrata em memoria.
 * A geracao de codigo ocorre apenas DEPOIS que a arvore esta pronta
 */

public class CompiladorB {

    // interface para os nos da arvore
    interface No {
        void gerarC(String prefixo);
    }

    static class Programa implements No {
        // listagem dos comandos para cada No da arvore
        List<No> comandos = new ArrayList<>();

        public void gerarC(String prefixo) {
            // Cabecalho
            System.out.println("#include <stdio.h>");
            System.out.println("int main(){");
            System.out.println("int a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, w, x, y, z;");
            System.out.println("char str[512]; // auxiliar na leitura com G");
    
    
            for (No cmd : comandos) {
                cmd.gerarC("");
            }
    
            // footer
            System.out.println("gets (str);");
            System.out.println("}");
        }
    }

    // Comandos
    static class ComandoAtribuir implements No{
        char variavel;
        String valor;

        public ComandoAtribuir(char variavel, String valor){
            this.variavel = variavel;
            this.valor = valor;
        }

        public void gerarC(String prefixo) {
            System.out.println(prefixo + variavel + " = " + ";");
        }
    }

    static class ComandoLer implements No{
        char variavel;

        public ComandoLer(char variavel){
            this.variavel = variavel;
        }

        public void gerarC(String prefixo){
            System.out.println(prefixo + "{");
            System.out.println(prefixo + "    gets(str);");
            System.out.println(prefixo + "    sscanf(str, \"%d\", &" + variavel + ");");
            System.out.println(prefixo + "}");
        }
    }

    static class 


    public static void main(String[] args) {
        String exemplo1 = "G n"
                + "G p"
                + "= i 0"
                + "W i # n {"
                + "  * a p i"
                + "  P a"
                + "  + i i 1"
                + "}";
        String exemplo2 = "G n"
                + "= i 2"
                + "% a n i"
                + "W i < n {"
                + "  I a = 0 = i n"
                + "  + i i 1"
                + "  % a n i"
                + "}"
                + "I a = 0 P 0"
                + "I a # 0 P 1";

        System.out.println("=== INICIO EXEMPLO 1 (B) ===");
        try {
            CompiladorB compilador1 = new CompiladorB(exemplo1);
            compilador1.compilar();
        } catch (Exception e) {
            System.err.println("Erro ao compilar Exemplo 1 (B): " + e.getMessage());
        }
        System.out.println("=== FIM EXEMPLO 1 (B) ===\n");

        System.out.println("=== INICIO EXEMPLO 2 (B) ===");
        try {
            CompiladorB compilador2 = new CompiladorB(exemplo2);
            compilador2.compilar();
        } catch (Exception e) {
            System.err.println("Erro ao compilar Exemplo 2 (B): " + e.getMessage());
        }
        System.out.println("=== FIM EXEMPLO 2 (B) ===");
    }
}
