
/**
 *
 * @author bsantosdias
 */

public class ListaEncadeada {
    private No ini;

    // Cria uma lista vazia
    public ListaEncadeada() {
        ini = null;
    }

    public No getIni() {
        return ini;
    }

    public void setIni(No ini) {
        this.ini = ini;
    }

    public boolean vazia() {
        return ini == null;
    }

    // Desempenho constante O(1), independente do tamanho da lista
    public void insereInicio(String elemento) {
        No novo = new No(elemento, ini);
        ini = novo;
    }

    // Desempenho linear O(n), dependente linearmente do tamanho da lista
    public void insereFinal(String elemento) {
        No novo = new No(elemento, null);
        No temp = ini;

        if (vazia())
            ini = novo;
        else {
            while (temp.getProx() != null) {
                temp = temp.getProx();
            }
            temp.setProx(novo);
        }
    }

    // Desempenho linear O(n), dependente linearmente do tamanho da lista
    public void insereOrdenado(String elemento) {
        No novo = new No(elemento, ini);
        No temp = ini;
        No anterior = null;

        while (temp != null && temp.getElemento().compareTo(novo.getElemento()) < 0) {
            anterior = temp;
            temp = temp.getProx();
        }
        // Lista vazia e inserir no início
        if (anterior == null) {
            ini = novo;
        } else {
            // Inserir entre 2 nos ou no final
            novo.setProx(temp);
            anterior.setProx(novo);
        }
    }

    // Desempenho linear O(n), dependente linearmente do tamanho da lista
    public boolean buscaLinear(String x) {
        No temp = ini;

        while (temp != null) {
            if (temp.getElemento().equals(x)) {
                return true; // Achou
            }
            temp = temp.getProx();
        }

        return false; // Não achou
    }

    public void removeElemento(String elemento) {
        No temp = ini;
        No anterior = null;
        
        while (temp != null && !temp.getElemento().equals(elemento)){
            anterior = temp;
            temp = temp.getProx();
        }
        //Remover primeiro
        if(anterior == null){
            ini=ini.getProx();
            return;
        }
        //Remover um nó entre dois nós ou no final
        if(temp != null && temp.getElemento().equals(elemento)){
            anterior.setProx(temp.getProx());
            return;
        }
    }

    public int contaNos() {
        int cont = 0;
        No temp = ini;

        while (temp != null) {
            temp = temp.getProx();
            cont++;
        }
        return cont;
    }

    public int getPosicao(String elemento) {
        No temp = ini;
        int contador = 1;

        while (temp != null) {
            if (temp.getElemento().equals(elemento)) {
                return contador; // Achou
            }
            contador++;
            temp = temp.getProx();
        }

        return -1; // Não achou
    }

    public String getElementoPosicao(Integer posicao) {
        No temp = ini;

        for(int i = 0; i < posicao-1; i++){
            temp = temp.getProx();
        }

        return temp.getElemento();
    }
}
