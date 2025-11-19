import java.util.Objects;
import java.util.ArrayList;
import java.util.List;

/*
 * Em vez de imprimir o codigo C diretamente durante a analise,
 * este compilador constroi uma Arvore Sintatica Abstrata em memoria.
 * A geracao de codigo ocorre apenas DEPOIS que a arvore esta pronta
 */

public class CompiladorB {

    // ESTRUTURA DO ANALISADOR SINTATICO
    private String input;
    private int index;
    // Olhar adiantado para o proximo caractere a ser processado
    private char lookahead;

    public CompiladorB(String input) {
        // Normaliza o input removendo espaços em branco desnecessarios e garantindo que
        // nao seja nulo
        this.input = Objects.requireNonNull(input).trim();

        // tratar erro de entrada vazia
        if (this.input.isEmpty()) {
            throw new RuntimeException("Erro: Entrada vazia");
        }
        this.index = 0;
        this.lookahead = this.input.charAt(0);
    }


    public void compilar(){
        // controu a arvore
        Programa arvore = programa();

        // percorrer arvore imprimindo C
        arvore.gerarC("");
    }

    private void lerProximoToken() {
        while (index < input.length() - 1 && Character.isWhitespace(lookahead)) {
            index++; lookahead = input.charAt(index);
        }
        if (index < input.length() - 1) {
            index++; lookahead = input.charAt(index);
        } else {
            lookahead = '\0';
        }
        while (lookahead != '\0' && Character.isWhitespace(lookahead)) {
            if (index < input.length() - 1) {
                index++; lookahead = input.charAt(index);
            } else {
                lookahead = '\0'; break;
            }
        }
    }

    private void match(char expected) {
        if (lookahead == expected) {
            lerProximoToken();
        } else {
            throw new RuntimeException("Erro: Esperado '" + expected + "' mas encontrado '" + lookahead + "'");
        }
    }

    private char getVariavel() {
        if (!Character.isLowerCase(lookahead)) throw new RuntimeException("Variavel esperada");
        char variavel = lookahead;
        lerProximoToken();
        return variavel;
    }

    private char getNumero() {
        if (!Character.isDigit(lookahead)) throw new RuntimeException("Numero esperado");
        char numero = lookahead;
        lerProximoToken();
        return numero;
    }

    private String getValor() {
        if (Character.isLowerCase(lookahead)) return String.valueOf(getVariavel());
        if (Character.isDigit(lookahead)) return String.valueOf(getNumero());
        throw new RuntimeException("Valor invalido");
    }

    private String getOperador() {
        char operador = lookahead;
        if (operador == '=') { match('='); return "=="; }
        if (operador == '<') { match('<'); return "<"; }
        if (operador == '#') { match('#'); return "!="; }
        throw new RuntimeException("Operador invalido");
    }

    private String getComparacao() {
        char variavel = getVariavel();
        String operador = getOperador();
        String valor = getValor();
        return variavel + " " + operador + " " + valor;
    }


    // Comandos da Arvore
    private No comandoAtribuir() {
        match('=');
        char variavel = getVariavel();
        String valor = getValor();
        return new ComandoAtribuir(variavel, valor);
    }

    private No comandoLer() {
        match('G');
        char variavel = getVariavel();
        return new ComandoLer(variavel);
    }

    private No comandoPrint() {
        match('P');
        String valor = getValor();
        return new ComandoPrint(valor);
    }

    private No comandoCalculo(char operador) {
        match(operador);
        char variavel = getVariavel();
        String valor1 = getValor();
        String valor2 = getValor();
        return new ComandoCalculo(variavel, valor1, valor2, operador);
    }

    private No comandoIf() {
        match('I');
        String condicao = getComparacao();
        return new ComandoIf(condicao);
    }

    private No comandoWhile() {
        match('W');
        String condicao = getComparacao();
        return new ComandoWhile(condicao);
    }

    private No comandoComposite() {
        match('{');
        ComandoComposite bloco = new ComandoComposite();
        while (lookahead != '}') {
            bloco.listaComandos.add(comando());
            if (lookahead == '\0') throw new RuntimeException("Fim inesperado dentro de bloco");
        }
        match('}');
        return bloco;
    }

    private Programa programa() {
        Programa prog = new Programa();
        while (lookahead != '\0') {
            prog.comandos.add(comando());
        }
        return prog;
    }

    private No comando() {
        switch (lookahead) {
            case '=': return comandoAtribuir();
            case 'G': return comandoLer();
            case 'P': return comandoPrint();
            case '+': return comandoCalculo('+');
            case '-': return comandoCalculo('-');
            case '*': return comandoCalculo('*');
            case '/': return comandoCalculo('/');
            case '%': return comandoCalculo('%');
            case 'I': return comandoIf();
            case 'W': return comandoWhile();
            case '{': return comandoComposite();
            default:
                throw new RuntimeException("Comando desconhecido: " + lookahead);
        }
    }

    // ESTRUTURA DA ARVORE
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
    static class ComandoAtribuir implements No {
        char variavel;
        String valor;

        public ComandoAtribuir(char variavel, String valor) {
            this.variavel = variavel;
            this.valor = valor;
        }

        public void gerarC(String prefixo) {
            System.out.println(prefixo + variavel + " = " + ";");
        }
    }

    static class ComandoLer implements No {
        char variavel;

        public ComandoLer(char variavel) {
            this.variavel = variavel;
        }

        public void gerarC(String prefixo) {
            System.out.println(prefixo + "{");
            System.out.println(prefixo + "    gets(str);");
            System.out.println(prefixo + "    sscanf(str, \"%d\", &" + variavel + ");");
            System.out.println(prefixo + "}");
        }
    }

    static class ComandoCalculo implements No {
        char variavel;
        String valor1;
        String valor2;
        char operacao;

        public ComandoCalculo(char variavel, String valor1, String valor2, char operacao) {
            this.variavel = variavel;
            this.valor1 = valor1;
            this.valor2 = valor2;
            this.operacao = operacao;
        }

        public void gerarC(String prefixo) {
            System.out.println(prefixo + variavel + "=" + valor1 + operacao + valor2 + ";");
        }
    }

    static class ComandoPrint implements No {
        String valor;

        public ComandoPrint(String valor) {
            this.valor = valor;
        }

        public void gerarC(String prefixo) {
            System.out.println(prefixo + "printf(\"%d\\n\", " + valor + ");");
        }
    }

    static class ComandoIf implements No {
        String condicao;

        public ComandoIf(String condicao) {
            this.condicao = condicao;
        }

        public void gerarC(String prefixo) {
            System.out.println(prefixo + "if ( " + condicao + " ) {");
            System.out.println(prefixo + "}");
        }
    }

    static class ComandoWhile implements No {
        String condicao;

        public ComandoWhile(String condicao) {
            this.condicao = condicao;
        }

        public void gerarC(String prefixo) {
            System.out.println(prefixo + "while ( " + condicao + " ) {");
            System.out.println(prefixo + "}");
        }
    }

    static class ComandoComposite implements No {
        List<No> listaComandos = new ArrayList<>();

        public void gerarC(String prefixo) {
            // repassa o prefixo atual para os filhos
            for (No comando : listaComandos) {
                comando.gerarC(prefixo);
            }
        }
    }

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
