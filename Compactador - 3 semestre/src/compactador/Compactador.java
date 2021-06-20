import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author bsantosdias
 */

public class Compactador {

    public static void main(String[] args) throws FileNotFoundException, IOException {

        ListaEncadeada entrada = LerArquivo();
        ListaEncadeada textoCompactado = CompactaTexto(entrada); //Compacta o texto Lido
        ListaEncadeada textoDesompactado = DescompactaTexto(textoCompactado); // Descompacta o texto recem compactado
        CriaTxtCompactado(textoCompactado); // Cria o arquivo de texto Compactado
        CriaTxtDescompactado(textoDesompactado); // Cria o arquivo de texto Descompactado
        System.out.println("\nFim de programa.");
    }

    private static ListaEncadeada LerArquivo() throws FileNotFoundException, IOException {

        ListaEncadeada textoListaEncadeada = new ListaEncadeada();
        String[][] texto = new String[1000][2];
        boolean letraAnteriorDiferenteAZ = false;
        int textoPonteiro = 0;
        int contadorPosicao = 0;
        FileReader leEntrada = new FileReader("NaoCompactado.txt");
        Pattern pattern = Pattern.compile("[a-z]", Pattern.CASE_INSENSITIVE); // Padrão para ler só letras.

        String caractere;
        do {
            caractere = String.valueOf((char) leEntrada.read());
            Matcher matcher = pattern.matcher(caractere); // Verifica se o caractere em questão é uma letra e armazena
            // na matcher.
            boolean matchFound = matcher.find(); // Se for letra, matchfound recebe a letra da vez.

            if (!matchFound && contadorPosicao == 0 && texto[0][0] == null) { // Se for primeira rodada e for letra, a
                // mesma será armazenada na primeira casa.
                texto[textoPonteiro][0] = caractere;
                texto[textoPonteiro][1] = String.valueOf(contadorPosicao);
                textoPonteiro++;
                contadorPosicao++;
            } else {
                if (matchFound) { // Se for alguma letra, verificará:
                    if (letraAnteriorDiferenteAZ) {// Se caractere anterior não era uma letra, pulará para a proxima
                        // posição do array.
                        textoPonteiro++;
                        contadorPosicao++;
                    }
                    if (texto[textoPonteiro][0] == null) { // Se casa atual da array estiver vazia, letra será
                        // adicionada a ela sem sobrepor.
                        texto[textoPonteiro][0] = caractere;
                    } else { // Se já possuir algo na casa atual, adicionara a nova letra aos que já existem
                        // na casa.
                        texto[textoPonteiro][0] += caractere;
                    }
                    texto[textoPonteiro][1] = String.valueOf(contadorPosicao);
                    letraAnteriorDiferenteAZ = false;
                } else {// Se não for letra:
                    textoPonteiro++; // Vai pra casa da frente.
                    contadorPosicao++; // Vai pra casa da frente.

                    if (texto[textoPonteiro][0] == null) { // Se casa atual da array estiver vazia, caractere será
                        // adicionado a ela sem sobrepor.
                        texto[textoPonteiro][0] = caractere;
                    } else { // Se já possuir algo na casa atual, adicionara o novo caractere aos que já
                        // existem.
                        texto[textoPonteiro][0] += caractere;
                    }
                    texto[textoPonteiro][1] = String.valueOf(contadorPosicao);
                    letraAnteriorDiferenteAZ = true;
                }
            }
        } while (caractere.compareTo("0") != 0); // Leitura rodará enquanto caractere da vez for diferente de zero.
        leEntrada.close();

        int contador = 0;
        while (texto[contador][0] != null) {
            textoListaEncadeada.insereFinal(texto[contador][0]);
            // System.out.println(contador);
            contador++;
        }

        return textoListaEncadeada;
    }

    private static ListaEncadeada CompactaTexto(ListaEncadeada textoEntrada) throws IOException {
        ListaEncadeada lista = new ListaEncadeada(); // Guarda somente palavras.
        No entrada = textoEntrada.getIni();
        ListaEncadeada textoCompactado = new ListaEncadeada(); // Retorno final.
        Pattern pattern = Pattern.compile("[a-z]"); // Verifica se é uma palavra.

        do {
            Matcher matcher = pattern.matcher(entrada.getElemento()); // Verifica se o caractere em questão é uma
            // palavra e armazena na matcher.
            boolean matchFound = matcher.find(); // Se for palavra, matchfound recebe a palavra da vez.

            if (matchFound) { // É uma palavra, então:
                if (lista.vazia()) {// Se for primeira rodada e for palavra, a mesma será armazenada na primeira
                    // casa do texto compactado.
                    textoCompactado.insereFinal(entrada.getElemento());
                    lista.insereInicio(entrada.getElemento());

                } else { // Se não estiver vazia:
                    boolean retorno = lista.buscaLinear(entrada.getElemento());

                    if (retorno) {
                        String elemento = String.valueOf(lista.getPosicao(entrada.getElemento()));
                        textoCompactado.insereFinal(elemento);

                        lista.removeElemento(entrada.getElemento());
                        lista.insereInicio(entrada.getElemento());

                    } else {/*
                             * Palavra não foi achada na lista, então é adicionada ao compactado e a lista
                             * de verificação de repetidos a partir da função OrdenaLista.
                         */
                        textoCompactado.insereFinal(entrada.getElemento());
                        lista.insereInicio(entrada.getElemento());
                    }
                }
            } else {// Se não for palavra, conteudo será adicionado na lista compactada.
                textoCompactado.insereFinal(entrada.getElemento());
            }
            entrada = entrada.getProx();
        } while (entrada.getProx() != null); // Rodará enquanto posição atual não estiver vazia.

        return textoCompactado;
    }

    private static ListaEncadeada DescompactaTexto(ListaEncadeada textoEntrada) throws IOException {
        ListaEncadeada lista = new ListaEncadeada(); // Guarda somente palavras.
        No entrada = textoEntrada.getIni();
        ListaEncadeada textoCompactado = new ListaEncadeada(); // Retorno final.
        Pattern pattern = Pattern.compile("[a-z]");
        Pattern patternNuber = Pattern.compile("[0-9]+");

        do {
            Matcher matcher = pattern.matcher(entrada.getElemento()); // Verifica se o caractere em questão é uma
            // Palavra e armazena na matcher.
            boolean matchFound = matcher.find(); // Se for palavra, matchfound recebe a palavra da vez.

            if (matchFound) { // É uma palavra, então:

                textoCompactado.insereFinal(entrada.getElemento());
                lista.insereInicio(entrada.getElemento());

            } else {
                Matcher matcherNumber = patternNuber.matcher(entrada.getElemento()); // Verifica se o caractere em questão é um numero
                boolean matchNumberFound = matcherNumber.find();
                if (matchNumberFound) {
                    String elemento = String.valueOf(lista.getElementoPosicao(Integer.valueOf(entrada.getElemento())));
                    textoCompactado.insereFinal(elemento);

                    lista.removeElemento(elemento);
                    lista.insereInicio(elemento);
                } else {
                    textoCompactado.insereFinal(entrada.getElemento());
                }
            }
            entrada = entrada.getProx();
        } while (entrada.getProx() != null); // Rodará enquanto posição atual não estiver vazia.

        return textoCompactado;
    }

    private static void CriaTxtCompactado(ListaEncadeada textoCompactado) throws IOException {// Escreve texto compactado.
        FileWriter escreveSaida = new FileWriter("Compactado.txt");
        No no = textoCompactado.getIni();

        do {
            escreveSaida.write(no.getElemento());
            no = no.getProx();
        } while (no.getProx() != null);
        escreveSaida.write("0");
        escreveSaida.close();

    }

    private static void CriaTxtDescompactado(ListaEncadeada textoDesompactado) throws IOException {
        var escreveSaida = new FileWriter("NovoDescompactado.txt");
        No no = textoDesompactado.getIni();

        do {
            escreveSaida.write(no.getElemento());
            no = no.getProx();
        } while (no.getProx() != null);
        escreveSaida.write("\n" + "0");
        escreveSaida.close();
    }
}
