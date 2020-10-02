package ArvoreRB.NoArvoreRB;

import ArvoreAVL.Exceptions.NotExistException;

public class NoArvoreRB<T extends Comparable<T>> implements Comparable<T>{

    private T valor;
    private NoArvoreRB<T> pai;
    private NoArvoreRB<T> esquerda;
    private NoArvoreRB<T> direita;
    private boolean vermelho; // true = vermelho; false = preto

    public NoArvoreRB(NoArvoreRB<T> pai,T valor){
        this.pai = pai;
        this.valor = valor;
        this.vermelho = true;
        
    }


    public NoArvoreRB(T valor){
        this.valor = valor;
    }

    public T getValor(){
        return this.valor;
    }


    public NoArvoreRB<T> getPai() {
        return this.pai;
    }

    public void setPai(NoArvoreRB<T> pai) {
        this.pai = pai;
    }

    public NoArvoreRB<T> getEsquerda() {
        return this.esquerda;
    }

    public void setEsquerda(T esquerda) {
        this.esquerda = new NoArvoreRB<T>(esquerda);
    }
    public void setEsquerda(NoArvoreRB<T> esquerda){
        this.esquerda = esquerda;
    }

    public NoArvoreRB<T> getDireita() {
        return this.direita;
    }

    public void setDireita(T direita) {
        this.direita = new NoArvoreRB<T>(direita);
    }
    public void setDireita(NoArvoreRB<T> direita){
        this.direita = direita;
    }
    public boolean isVermelho() {
        return this.vermelho;
    }
    public void setVermelho() {
        this.vermelho = true;
    }
    public void setPreto(){
        this.vermelho = false;
    }
    public void setCor(boolean color){
        this.vermelho = color;
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


    public NoArvoreRB<T> getTio() throws NotExistException{
        NoArvoreRB<T> g;
        try{
            g = this.getPai().getPai();
            if (this.getPai().compareTo(g.valor) > 0){
                return g.getEsquerda(); //se o pai estiver do lado direito, o tio estara do lado esquerdo
            }else{
                return g.getDireita(); //se o pai n estiver do lado direito, o tio estara
            }
        }catch (NullPointerException e){
            return null;
        }
    }

    public NoArvoreRB<T> getAvo() throws NotExistException{
        try{
            return this.getPai().getPai();
        }catch (NullPointerException e){
            return null;
        }
    }

    public void removerDireita(){
        this.direita = null;
    }

    public void removerEsquerda(){
        this.esquerda = null;
    }

    @Override
    public NoArvoreRB<T> clone() {
        NoArvoreRB<T> novo = new NoArvoreRB<T>(valor);
        return novo;
    }

    

    @Override
    public boolean equals(T element){
        return element.compareTo(this.valor) == 0;
    }

    @Override
    public int compareTo(T o) {
        return this.valor.compareTo(o);
    }

    @Override
    public String toString(){
        return this.valor.toString() + " -  "+this.vermelho;
    }
}
