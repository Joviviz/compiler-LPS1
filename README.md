# Compilador para Linguagem de Programação Simples 1 (LPS1)

## Sobre o Projeto

Este projeto é um compilador para a linguagem **LPS1** (Linguagem de Programação Simples 1), desenvolvido para o Trabalho de Conclusão da Disciplina (TCD) de Compiladores.

O objetivo deste compilador é traduzir um código-fonte escrito em LPS1 para um código-fonte equivalente em **linguagem C**. A saída C gerada é impressa diretamente no terminal.

## Como Funciona

O `Compilador.java` foi implementado usando a técnica de **Recursive Descent Parser** (conferir referência bibliográfica)*.

Para cada regra da gramática da LPS1 (definida no TCD), existe um método Java correspondente (ex: `comandoWhile()`, `comandoIf()`, etc.).

Conforme o analisador lê o código LPS1 caractere por caractere (o `lookahead`), ele chama os métodos apropriados. A principal característica do trabalho é que os comandos de geração de código C (`System.out.println(...)`) estão misturados diretamente dentro desses métodos de análise.

## Como Rodar o Compilador

Para compilar e executar o programa, você precisa ter o **JDK (Java Development Kit)** instalado em sua máquina.
O processo correto tem dois passos: **1. Compilar** (com `javac`) e **2. Executar** (com `java`).

Siga as instruções abaixo:

1.  **Abra seu terminal** (Prompt de Comando, PowerShell, Terminal, etc.).

2.  **Navegue até a pasta** onde o arquivo `Compilador.java` está salvo:

3.  **Compile o arquivo:** Use o comando `javac` para transformar o `Compilador.java` em bytecode (`Compilador.class`).
    ```bash
    javac Compilador.java
    ```
    (Se nada aparecer, funcionou! Se aparecerem erros, corrija o código Java e compile novamente.)

4.  **Execute o programa:** Agora, use o comando `java` (sem `.java` ou `.class`) para rodar o bytecode.
    ```bash
    java Compilador
    ```

### O que esperar da Execução

O método `main` do `Compilador.java` já está configurado para executar os dois exemplos fornecidos no TCD. Ao rodar `java Compilador`, você verá o código C gerado para ambos os exemplos impresso no seu terminal, pronto para ser copiado.
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
- https://math.hws.edu/javanotes/c9/s5.html
