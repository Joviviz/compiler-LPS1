import java.util.Objects;

public class Compilador {
    private String input;
    private int index;
    // Olhar adiantado para o próximo caractere a ser processado
    private char lookahead;

    public Compilador(String input) {
        // Normaliza o input removendo espaços em branco desnecessários e garantindo que
        // nao seja nulo
        this.input = Objects.requireNonNull(input).trim();

        // tratar erro de entrada vazia
        if (this.input.isEmpty()) {
            throw new RuntimeException("Erro: Entrada vazia");
        }
        this.index = 0;
        this.lookahead = this.input.charAt(0);
    }

    // junta tudo
    public void compilar() {
        printarHeader();
        aParteDoMeioPrograma();
        printarFooter();
    }

    // printa o cabecalho do codigo C
    private void printarHeader() {
        System.out.println("#include <stdio.h>");
        System.out.println("int main() {");
        System.out.println("int a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, w, x, y, z;");
        System.out.println("char str[512]; // auxiliar na leitura com G");
    }

    // finaliza o codigo C
    private void printarFooter() {
        System.out.println("gets (str);");
        System.out.println("}");
    }

    // Tokenizar a entrada
    private void tokenizar(){
        // pula espacos em branco
        while (index < input.length() - 1 && Character.isWhitespace(lookahead)) {
            index++;
            lookahead = input.charAt(index);
        }

        // +1 no indice ate o fim da string
        if (index < input.length() - 1) {
            index++;
            lookahead = input.charAt(index);
        } else {
            lookahead = '\0';
        }

        while (lookahead != '\0' && Character.isWhitespace(lookahead)) {
            if (index < input.length() - 1) {
                index++;
                lookahead = input.charAt(index);
            } else {
                lookahead = '\0';
                break;
            }
        }
    }

    // verificar se o char bate com o lookahead
    private void match(char expected){
        if (lookahead == expected) {
            tokenizar();
        } else {
            throw new RuntimeException("Erro de sintaxe: esperado '" + expected + "', mas encontrado '" + lookahead + "'");
        }
    }

    private void aParteDoMeioPrograma() {
        while (lookahead != '\0') {
            comandos();
        }
    }

    // COMANDOS DA LINGUAGEM
    // AssignCommand
    private void comandoAtribuir(){
        match('=');
        char variavel = getVariavel();
        String valor = getValor();
        System.out.println(variavel + " = " + valor + ";");
    }
    // GetCommand
    private void comandoLer(){
        match('G');
        char variavel = getVariavel();
        
    }

    private void comandos() {
        switch (lookahead){
        }
    }


}
