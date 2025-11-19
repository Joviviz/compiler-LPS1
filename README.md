# Compilador LPS1 (TCD - Partes A e B)

## Sobre o Projeto

Este projeto consiste em dois compiladores (Parte A e B) para a linguagem **LPS1** (Linguagem de Programação Simples 1), desenvolvidos para o Trabalho de Conclusão da Disciplina (TCD) de Compiladores.

O objetivo dos compiladores é traduzir um código-fonte escrito em LPS1 para um código-fonte equivalente em **linguagem C**. A saída C gerada é impressa diretamente no terminal.

* **Parte A:** Analisador sintático com geração de código imediata (misturada à análise).
* **Parte B:** Analisador sintático que constrói uma Árvore Sintática Abstrata (ASA) antes de gerar o código.

## Como Funciona

Os compiladores foram implementados em Java usando a técnica de **Recursive Descent Parser** (Analisador Descendente Recursivo).

### Parte A (`CompiladorA.java`)

Conforme o analisador lê o código LPS1 caractere por caractere (o `lookahead`), ele chama os métodos apropriados. A principal característica do trabalho é que os comandos de geração de código C (`System.out.println(...)`) estão misturados diretamente dentro desses métodos de análise.

Para cada regra da gramática da LPS1 (definida no TCD), existe um método Java correspondente (ex: `comandoWhile()`, `comandoIf()`, etc.).

### Parte B
Nesta versão, o foco é a construção da **ASA (Árvore Sintática Abstrata)**.

1.  O analisador lê o código e cria objetos (Nós) representando a estrutura do programa.
2.  Nenhum código é impresso durante a leitura.
3.  Apenas após a árvore estar completa, o método `gerarC()` percorre a estrutura e imprime o código final.

## Como Rodar os Compiladores

Para compilar e executar o programa, você precisa ter o **JDK (Java Development Kit)** instalado em sua máquina.

O processo correto tem dois passos: **1. Compilar** (com `javac`) e **2. Executar** (com `java`).

Siga as instruções abaixo:

1.  **Abra seu terminal** (Prompt de Comando, PowerShell, Terminal, etc.).

2.  **Navegue até a pasta** onde o respectivo arquivo `Compilador.java` está salvo:

3.  **Compile o arquivo:** Use o comando `javac` para transformar o `Compilador.java` em bytecode (`Compilador.class`).
    ```bash
    javac CompiladorA.java
    ```
    (Se nada aparecer, funcionou! Se aparecerem erros, corrija o código Java e compile novamente.)

4.  **Execute o programa:** Agora, use o comando `java` (sem `.java` ou `.class`) para rodar o bytecode.
    ```bash
    java CompiladorA
    ```
    (Realizar os mesmo passos para o Compilador B)

### O que esperar da Execução

O método `main` de ambos compiladores já está configurado para executar os dois exemplos fornecidos no TCD. Ao rodar o respectivo `java Compilador`, você verá o código C gerado para ambos os exemplos impresso no seu terminal, pronto para ser copiado.
---

## Sobre a LPS1

| Comando LPS1 | Tradução C (Conceito)            | Descrição |
| :---         | :---                             | :---  |
| `Ga`         | `{ gets(str); sscanf(... &a); }` | **G**et: Lê um valor do teclado para a variável `a`. |
| `=a5`        | `a = 5;`                         | **Assign**: Atribui o valor `5` à variável `a`. |
| `+abc`       | `a = b + c;`                     | **Add**: Soma `b` e `c` e guarda em `a`. |
| `-abc`       | `a = b - c;`                     | **Sub**: Subtrai `c` de `b` e guarda em `a`. |
| `Pa`         | `printf("%d\n", a);`             | **P**rint: Imprime o valor da variável `a` na tela. |
| `I<a5{...}`  | `if (a < 5) {...}`               | **I**f: Executa o bloco se `a` for menor que `5`. |
| `Wi#n{...}`  | `while (i != n) {...}`           | **W**hile: Executa o bloco enquanto `i` for diferente (`#`) de `n`. | 

## Referência Bibliográfica
- [A Simple Recursive Descent Parser](https://math.hws.edu/javanotes/c9/s5.html)
- [Análise Sintática Ascendente PDF](https://erinaldosn.wordpress.com/wp-content/uploads/2012/05/anc3a1lise-sintc3a1tica-ascendente.pdf)
- [Compiladores - Aula 13 - Análise Sintática Ascendente](https://youtu.be/U5TlpXnwCgA)
= [YACC for Java](https://byaccj.sourceforge.net/)