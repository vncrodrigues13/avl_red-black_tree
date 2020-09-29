package ArvoreRB.ArvoreRB;

import ArvoreRB.Exceptions.NotExistException;
import ArvoreRB.NoArvoreRB.NoArvoreRB;

public class ArvoreRedBlack<T extends Comparable<T>> {

    private NoArvoreRB<T> raiz;

    public void adicionar(T element) {
        // TODO
        if (raiz == null) {
            raiz = new NoArvoreRB<T>(element);
        }
        concertandoCasoUm(raiz);
    }

    public void remover(T element) {
        // TODO
    }

    public NoArvoreRB<T> buscarElemento(T element) throws NotExistException {
        return getElement(this.raiz, element);
    }

    private NoArvoreRB<T> treeAdd(NoArvoreRB<T> raiz, NoArvoreRB<T> pai, T element)
            throws ArvoreAVL.Exceptions.NotExistException {
        if (raiz == null) {
            NoArvoreRB<T> no = new NoArvoreRB<T>(pai, element);
            if (no.getPai() != null && no.getPai().isVermelho()) {
                concertandoCasoDois(no);
            }
            return no;
        }

        int comparableFactor = raiz.compareTo(element);

        if (comparableFactor >= 0) {
            raiz.setDireita(treeAdd(raiz.getDireita(), raiz, element));
        } else if (comparableFactor < 0) {
            raiz.setEsquerda(treeAdd(raiz.getEsquerda(), raiz, element));
        }

        return raiz;
    }

    private NoArvoreRB<T> getElement(NoArvoreRB<T> raiz, T element) throws NotExistException {
        if (raiz == null) {
            throw new NotExistException();
        }
        int comparableFactor = raiz.compareTo(element);

        if (comparableFactor > 0) {
            return getElement(raiz.getDireita(), element);
        } else if (comparableFactor < 0) {
            return getElement(raiz.getEsquerda(), element);
        } else {
            return raiz;
        }
    }

    private void concertandoCasoUm(NoArvoreRB<T> raiz) throws ArvoreAVL.Exceptions.NotExistException {
        if (raiz.equals(this.raiz)) {
            // caso o elemento seja a raiz, define a cor como preta
            this.raiz.setCor(false);
        } else {
            concertandoCasoDois(raiz);
        }
    }

    private void concertandoCasoDois(NoArvoreRB<T> raiz) throws ArvoreAVL.Exceptions.NotExistException {
        /*
         * Preciso concetar quando o No adicionado tiver um pai vermelho. Para resolver
         * isso vamos trocar as cores do pai e do tio, e o valor do avo so sera alterado
         * caso ele seja diferente da raiz
         */
        if (raiz.getPai() != null && raiz.isVermelho()) {
            // caso o pai seja de cor vermelha

            // caso o avo nao seja a raiz da arvore, definimos ele como vermelho, caso
            // contrario ele fica preto
            if (raiz.getAvo() != null) {

                if (!raiz.getAvo().equals(this.getRaiz())) {
                    raiz.getAvo().setVermelho();
                }
            }

            // define o pai como preto
            raiz.getPai().setCor(false);

            // define o tio como preto
            if (raiz.getTio() != null)
                raiz.getTio().setPreto();

        }
        concertandoCasoTres(raiz);
        
    }

    private void concertandoCasoTres(NoArvoreRB<T> raiz) throws ArvoreAVL.Exceptions.NotExistException {
        // Caso o tio do elemento inserido seja preto
        if (raiz.getTio() == null || !raiz.getTio().isVermelho()) {
            // caso o tio seja nulo, ou preto
            definirBalanceamento(raiz);
        }
    }

    private void rotacaoEsquerda(NoArvoreRB<T> raiz) {
        /*
         *
         *
         * 
         * 
         */
        NoArvoreRB<T> b = raiz.getDireita();
        if (raiz.getPai() != null) {
            if (raiz.compareTo(raiz.getPai().getValor()) > 0) {
                raiz.getPai().setDireita(b);
            } else {
                raiz.getPai().setEsquerda(b);
            }
        }
        b.setPai(raiz.getPai());
        raiz.setDireita(b.getEsquerda());
        b.setEsquerda(raiz);
        b.setCor(raiz.isVermelho());
        raiz.setVermelho();
        raiz.setPai(b);
    }

    private void rotacaoDireita(NoArvoreRB<T> raiz) {
        NoArvoreRB<T> b = raiz.getEsquerda();
        if (raiz.getPai() != null) {
            if (raiz.compareTo(raiz.getPai().getValor()) > 0) {
                raiz.getPai().setDireita(b);
            } else {
                raiz.getPai().setEsquerda(b);
            }
        }
        b.setPai(raiz.getPai());
        raiz.setEsquerda(b.getDireita());
        b.setDireita(raiz);
        b.setCor(raiz.isVermelho());
        raiz.setVermelho();
        raiz.setPai(b);
    }

    private void rotacaoDuplaEsquerda(NoArvoreRB<T> raiz) {
        rotacaoDireita(raiz);
        rotacaoEsquerda(raiz);
    }

    private void rotacaoDuplaDireita(NoArvoreRB<T> raiz) {
        rotacaoEsquerda(raiz);
        rotacaoDireita(raiz);
    }

    

    private void definirBalanceamento(NoArvoreRB<T> raiz) throws ArvoreAVL.Exceptions.NotExistException {
        if (raiz.getAvo() != null){
            if (raiz.getPai().compareTo(raiz.getAvo().getValor()) > 0){
                // avo -> pai a direita
                if (raiz.compareTo(raiz.getPai().getValor()) > 0){
                    // avo -> pai a direita -> raiz a direita (rotacao a esquerda)
                    /*
                        avo
                           \
                           pai
                             \
                             raiz

                    */
                    rotacaoEsquerda(raiz.getAvo());
                }else{
                    //avo -> pai a direita -> raiz a esquerda (rotacao dupla a esquerda)
                    /*
                        avo
                           \
                           pai
                          / 
                       raiz

                    */
                    rotacaoDuplaEsquerda(raiz.getAvo());
                }
            }else{
                //avo -> pai a esquerda
                if (raiz.compareTo(raiz.getPai().getValor()) > 0){
                    // avo -> pai a esquerda -> raiz a direita (rotacao dupla direita)
                    rotacaoDuplaDireita(raiz.getAvo())
                }else{
                    // avo -> pai a esquerda -> raiz a esquerda
                    rotacaoDireita(raiz.getAvo());
                }
            }
        }
    }


    public NoArvoreRB<T> getRaiz() {
        return this.raiz;
    }
}
