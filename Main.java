import java.util.Arrays;
import java.util.Scanner;

public class Main {
    static String[][] matriz = new String[6][4];
    static String palavra = "";
    static int erros = 0;
    static int acertos = 0;

    public static void print(String string) {
        System.out.println(string);
    }

    public static Boolean[] atualizarLetrasDescobertas(char guess, char[] palavraArray, Boolean[] letrasDescobertas) {
        // Retorna o array de booleans de letras descobertas atualizado usando a letra adivinhada. Só é chamado se a palavra tiver a letra.

        for (int i = 0; i< palavraArray.length; i++) {
            if (guess == palavraArray[i]) {
                letrasDescobertas[i] = true;
            }
        }
        return letrasDescobertas;
    }

    public static boolean palavraContemLetra(char guess, char[] palavraArray) {
        // Verifica se a palavra tem a letra inserida e adiciona um erro se não tiver.

        boolean letraExiste = false;
        for (int i=0; i<palavraArray.length; i++) {
            if (guess == palavraArray[i]) {
                letraExiste = true;
                break;
            }
        }
        if (letraExiste) {
            acertos++;
            return true;
        } else {
            erros++;
            System.out.println("Letra '" + guess + "' não existe na palavra.");
            return false;
        }
    }

    public static boolean todasAsLetrasDescobertas(Boolean[] letrasDescobertas) {
        // Retorna se todas as letras da palavra foram descobertas.

        for (int i=0; i<letrasDescobertas.length; i++) {
            if (!letrasDescobertas[i]) {
                return false;
            }
        }
        return true;
    }

    public static void render(char[] palavraArray, Boolean[] letrasDescobertas, int erros) {
        // Imprime a figura do boneco, linha por linha, e a palavra com as letras descobertas reveladas.

        atualizarBoneco(erros);

        int x, y;
        for (y=0; y<6; y++) {
            System.out.println("");
            for (x = 0; x < 4; x++) {
                System.out.print(matriz[y][x]);
            }
        }

        System.out.println("");
        for (x=0; x<palavra.length(); x++) {
            if (letrasDescobertas[x]) {
                System.out.print(palavraArray[x]);
            } else {
                System.out.print("_");
            }
            System.out.print(" ");
        }
    }

    public static void atualizarBoneco(int erros) {
        if (erros >= 2) {
            matriz[2][2] = " ㋡ ";
        }
        if (erros >= 4) {
            matriz[3][2] = "——  ";
        }
        if (erros >= 6) {
            matriz[3][2] = "————";
        }
        if (erros >= 8) {
            matriz[4][2] = " / ";
        }
        if (erros >= 10) {
            matriz[4][2] = " /|";
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int x, y;
        char guess;
        for (y=0; y<6; y++) {
            for (x=0; x<4; x++) {
                matriz[y][x] = "   ";
            }
        }
        for (y=0; y<6; y++) {
            matriz[y][0] = " | ";
        }
        matriz[0][1] = " — ";
        matriz[0][2] = " — ";
        matriz[1][2] = " | ";

        print("Digite a palavra a ser adivinhada: ");
        palavra = sc.next().toUpperCase();

        char[] palavraArray = palavra.toCharArray();
        Boolean[] letrasDescobertas = new Boolean[palavra.length()];
        Arrays.fill(letrasDescobertas, false);
        // Cria um array com cada letra da palavra e um outro array de booleans de comprimento igual que registra se cada letra foi descoberta.

        while (erros < 10 && !todasAsLetrasDescobertas(letrasDescobertas)) {
            render(palavraArray, letrasDescobertas, erros);
            print("");
            System.out.println("Erros: " + erros + "/10");
            System.out.println("Acertos: " + acertos);
            print("Insira uma letra: ");
            guess = sc.next().toUpperCase().charAt(0);
            if (palavraContemLetra(guess, palavraArray)) {
                letrasDescobertas = atualizarLetrasDescobertas(guess, palavraArray, letrasDescobertas);
            }
        }

        if (erros >= 10) {
            Arrays.fill(letrasDescobertas, true);
            render(palavraArray, letrasDescobertas, erros);
            print("");
            print("Tentativas esgotadas.");
        } else {
            render(palavraArray, letrasDescobertas, erros);
            print("");
            print("Parabéns!");
        }
    }
}