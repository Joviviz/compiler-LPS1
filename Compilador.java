import java.util.Objects;

public class Compilador {
    private String input;
    private int index;
    // Olhar adiantado para o proximo caractere a ser processado
    private char lookahead;

    public Compilador(String input) {
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
        System.out.println("  gets (str);");
        System.out.println("}");
    }

    // Tokenizar a entrada
    private void lerProximoToken() {
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
    private void match(char expected) {
        if (lookahead == expected) {
            lerProximoToken();
        } else {
            throw new RuntimeException(
                    "Erro de sintaxe: esperado '" + expected + "', mas encontrado '" + lookahead + "'");
        }
    }

    private void aParteDoMeioPrograma() {
        while (lookahead != '\0') {
            comandos();
        }
    }

    // VALOR VARIAVEL E NUMERO
    private String getValor() {
        if (Character.isLowerCase(lookahead)) {
            return String.valueOf(getVariavel());
        } else if (Character.isDigit(lookahead)) {
            return String.valueOf(getNumero());
        } else {
            throw new RuntimeException(
                    "Erro de sintaxe: Numero ou Variavel esperado, mas encontrado '" + lookahead + "'");
        }
    }

    private char getVariavel() {
        if (!Character.isLowerCase(lookahead)) {
            throw new RuntimeException("Variavel (letra minuscula) esperada, mas encontrado '" + lookahead + "'");
        }
        char variavel = lookahead;
        lerProximoToken();
        return variavel;

    }

    private char getNumero() {
        if (!Character.isDigit(lookahead)) {
            throw new RuntimeException("Numero esperado, mas encontrado '" + lookahead + "'");
        }
        char numero = lookahead;
        lerProximoToken();
        return numero;
    }
    // LER COMPARACAO E OPERADORES
    public String getComparacao() {
        char variavel = getVariavel();
        String operador = getOperador();
        String valor = getValor();
        return variavel + " " + operador + " " + valor;
    }

    private String getOperador() {
        char operador = lookahead;
        if (operador == '=') {
            match('=');
            return "==";
        } else if (operador == '<') {
            match('<');
            return "<";
        } else if (operador == '#') {
            match('#');
            return "!=";
        } else {
            throw new RuntimeException("Operador invalido: '" + operador + "'");
        }
    }

    // COMANDOS DA LINGUAGEM
    // AssignCommand
    private void comandoAtribuir() {
        match('=');
        char variavel = getVariavel();
        String valor = getValor();
        System.out.println(variavel + " = " + valor + ";");
    }

    // GetCommand
    private void comandoLer() {
        match('G');
        char variavel = getVariavel();
        System.out.println("{ gets(str);");
        System.out.println("    sscanf(str, \"%d\", &" + variavel + " );");
        System.out.println("  }");
    }

    // Comandos de matematica basica
    private void comandoSomar() {
        match('+');
        char variavel = getVariavel();
        String valor_1 = getValor();
        String valor_2 = getValor();
        System.out.println(variavel + " = " + valor_1 + " + " + valor_2 + ";");
    }

    private void comandoSubtrair() {
        match('-');
        char variavel = getVariavel();
        String valor_1 = getValor();
        String valor_2 = getValor();
        System.out.println(variavel + " = " + valor_1 + " - " + valor_2 + ";");
    }

    private void comandoMultiplicar() {
        match('*');
        char variavel = getVariavel();
        String valor_1 = getValor();
        String valor_2 = getValor();
        System.out.println(variavel + " = " + valor_1 + " * " + valor_2 + ";");
    }

    private void comandoDividir() {
        match('/');
        char variavel = getVariavel();
        String valor_1 = getValor();
        String valor_2 = getValor();
        System.out.println(variavel + " = " + valor_1 + " / " + valor_2 + ";");
    }

    private void comandoModulo() {
        match('%');
        char variavel = getVariavel();
        String valor_1 = getValor();
        String valor_2 = getValor();
        System.out.println(variavel + " = " + valor_1 + " % " + valor_2 + ";");
    }

    // COMANDOS DIVERSOS
    // PrintCommand
    private void comandoPrint() {
        match('P');
        String valor = getValor();
        System.out.println("printf(\"%d\\n\", " + valor + ");");
    }

    // IfCommand
    private void comandoIf() {
        match('I');
        String condicao = getComparacao();
        System.out.println("if( " + condicao + " ) {");

        // rodar o comando dentro do if
        comandos();

        System.out.println("}");
    }

    // WhileCommand
    private void comandoWhile() {
        match('W');
        String condicao = getComparacao();
        System.out.println("while ( " + condicao + ") {");

        // rodar o comando dentro do while
        comandos();
        System.out.println("  }");
    }

    // CompositeCommand
    private void comandoComposite() {
        match('{');
        while (lookahead != '}') {
            // colocar o maximo de comandos ate fechar a chave
            comandos();
            if (lookahead == '\0') {
                throw new RuntimeException("Erro: '}' esperado antes do fim da entrada.");
            }
        }
        match('}');
    }

    private void comandos() {
        switch (lookahead) {
            case '=':
                comandoAtribuir();
                break;
            case 'G':
                comandoLer();
                break;
            case '+':
                comandoSomar();
                break;
            case '-':
                comandoSubtrair();
                break;
            case '*':
                comandoMultiplicar();
                break;
            case '/':
                comandoDividir();
                break;
            case '%':
                comandoModulo();
                break;
            case 'P':
                comandoPrint();
                break;
            case 'I':
                comandoIf();
                break;
            case 'W':
                comandoWhile();
                break;
            case '{':
                comandoComposite();
                break;
            default:
                // Se o lookahead nao for nulo, mas não for um comando, eh um erro.
                if (lookahead != '\0') {
                    throw new RuntimeException("Comando inexistente: '" + lookahead + "'");
                }

        }
    }

    public static void main(String[] args) {
        String exemplo1 = 
                  "G n"
                + "G p"
                + "= i 0"
                + "W i # n {"
                + "  * a p i"
                + "  P a"
                + "  + i i 1"
                + "}";
        String exemplo2 = 
                  "G n"
                + "= i 2"
                + "% a n i"
                + "W i < n {"
                + "  I a = 0 = i n"
                + "  + i i 1"
                + "  % a n i"
                + "}"
                + "I a = 0 P 0"
                + "I a # 0 P 1";

        System.out.println("=== INICIO EXEMPLO 1 ===");
        try {
            Compilador compilador1 = new Compilador(exemplo1);
            compilador1.compilar();
        } catch (Exception e) {
            System.err.println("Erro ao compilar Exemplo 1: " + e.getMessage());
        }
        System.out.println("=== FIM EXEMPLO 1 ===\n");

        System.out.println("=== INICIO EXEMPLO 2 ===");
        try {
            Compilador compilador2 = new Compilador(exemplo2);
            compilador2.compilar();
        } catch (Exception e) {
            System.err.println("Erro ao compilar Exemplo 2: " + e.getMessage());
        }
        System.out.println("=== FIM EXEMPLO 2 ===");
    }
}
