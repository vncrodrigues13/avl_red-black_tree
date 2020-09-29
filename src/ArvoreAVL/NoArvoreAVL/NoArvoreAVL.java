package ArvoreAVL.NoArvoreAVL;

public class NoArvoreAVL <T extends Comparable<T>> implements Comparable<T> {
    private T valor;
    private NoArvoreAVL<T> esquerda;
    private NoArvoreAVL<T> direita;

    public NoArvoreAVL(T valor){
        this.valor = valor;
    }

    public T getValor(){
        return this.valor;
    }


    public NoArvoreAVL<T> getEsquerda() {
        return this.esquerda;
    }

    public void setEsquerda(T esquerda) {
        this.esquerda = new NoArvoreAVL<T>(esquerda);
    }
    public void setEsquerda(NoArvoreAVL<T> esquerda){
        this.esquerda = esquerda;
    }

    public NoArvoreAVL<T> getDireita() {
        return this.direita;
    }

    public void setDireita(T direita) {
        this.direita = new NoArvoreAVL<T>(direita);
    }
    public void setDireita(NoArvoreAVL<T> direita){
        this.direita = direita;
    }

    public int numeroDeFilhos(){
        if (getDireita() != null && getEsquerda() != null){
            return 2;
        }else if ((getDireita() != null && getEsquerda() == null) || (getDireita() == null && getEsquerda() != null)){
            return 1;
        }else {
            return 0;
        }
    }

    @Override
    public NoArvoreAVL<T> clone() {
        NoArvoreAVL<T> novo = new NoArvoreAVL<T>(valor);
        return novo;
    }

    @Override
    public int compareTo(T o){
        return this.valor.compareTo(o);
    }

    @Override
    public boolean equals (Object element){
        return this.valor.equals(element);
    }
    
}
