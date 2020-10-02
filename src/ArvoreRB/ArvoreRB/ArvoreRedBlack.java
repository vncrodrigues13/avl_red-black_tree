package ArvoreRB.ArvoreRB;

import ArvoreRB.Exceptions.NotExistException;
import ArvoreRB.NoArvoreRB.NoArvoreRB;

public class ArvoreRedBlack<T extends Comparable<T>> {

    private NoArvoreRB<T> raiz;

    public void adicionar(T element) throws ArvoreAVL.Exceptions.NotExistException, NotExistException {
        // TODO
        raiz = treeAdd(this.raiz, element, null);
        // apos adicionar o elemento, preciso verificar se ele esta comprindo com as
        // propriedades da arvore red black
        insertCheck(this.raiz, element);
    }

    public void remover(T element) throws NotExistException {
        // TODO
        if (this.raiz == null) {
            System.out.println("Nao eh possivel remover de uma arvore vazia");
        } else {
            if (buscarElemento(element) != null) {
                treeRemove(this.raiz, element, null);
            } else {
                throw new NotExistException();
            }

        }
    }

    public NoArvoreRB<T> buscarElemento(T element) throws NotExistException {
        // buscar elemento
        return getElement(this.raiz, element);
    }

    private NoArvoreRB<T> getElement(NoArvoreRB<T> raiz, T element) throws NotExistException {
        if (raiz == null) {
            throw new NotExistException();
        }
        int comparableFactor = element.compareTo(raiz.getValor());

        if (comparableFactor > 0) {
            return getElement(raiz.getDireita(), element);
        } else if (comparableFactor < 0) {
            return getElement(raiz.getEsquerda(), element);
        } else {
            return raiz;
        }
    }

    private void insertCheck(NoArvoreRB<T> raiz, T element)
            throws ArvoreAVL.Exceptions.NotExistException, NotExistException {
        int comparableFactor = element.compareTo(raiz.getValor());

        if (comparableFactor > 0) {
            insertCheck(raiz.getDireita(), element);
        } else if (comparableFactor < 0) {
            insertCheck(raiz.getEsquerda(), element);
        } else {
            checandoArvore(raiz);
        }
    }

    private NoArvoreRB<T> treeAdd(NoArvoreRB<T> raiz, T element, NoArvoreRB<T> pai) {
        /*
         * Vou ficar na recursão até encontrar algum nó vázio, ou seja, significa que eu
         * vou poder adicionar o elemento naquela posição
         */
        if (raiz == null) {
            // posicao vazia para adicionar
            System.out.println("Added");
            return new NoArvoreRB<T>(pai, element);
        }

        int comparableFactor = element.compareTo(raiz.getValor());

        if (comparableFactor >= 0) {
            // caso o elemento seja maior do que a raiz atual, ele move o ponteiro para a
            // direita
            System.out.println("Right");
            raiz.setDireita(treeAdd(raiz.getDireita(), element, raiz));
        } else {
            // caso o elemento seja menor do que a raiz atual, ele move o ponteiro para a
            // esquerda
            System.out.println("Left");
            raiz.setEsquerda(treeAdd(raiz.getEsquerda(), element, raiz));
        }

        return raiz;
    }

    private void treeRemove(NoArvoreRB<T> raiz, T element, NoArvoreRB<T> pai) throws NotExistException {
        if (raiz == null) {
            throw new NotExistException();
        }
        int comparableFactor = element.compareTo(raiz.getValor());

        if (comparableFactor > 0) {
            treeRemove(raiz.getDireita(), element, raiz);
        } else if (comparableFactor < 0) {
            treeRemove(raiz.getEsquerda(), element, raiz);
        } else {
            if (pai == null) {
                // significa que vc esta removendo a raiz
                if (raiz.numeroDeFilhos() == 2) {

                    if (this.raiz.getEsquerda().numeroDeFilhos() == 2) {
                        NoArvoreRB<T> value = getMaximoValor(this.raiz.getEsquerda());
                        remover(value.getValor());
                        this.raiz = value;
                        this.raiz.setPai(null);

                    } else if (this.raiz.getEsquerda().numeroDeFilhos() == 1) {

                        if (this.raiz.getEsquerda().getEsquerda() != null) {
                            NoArvoreRB<T> value = this.raiz.getEsquerda();
                            remover(value.getValor());
                            this.raiz = value;

                        } else {
                            NoArvoreRB<T> value = getMaximoValor(this.raiz.getEsquerda());
                            remover(value.getValor());
                            this.raiz = value;
                            this.raiz.setPai(null);
                        }

                    } else {
                        NoArvoreRB<T> value = this.raiz.getEsquerda();
                        remover(value.getValor());
                        this.raiz = value;
                    }
                } else if (raiz.numeroDeFilhos() == 1) {
                    if (raiz.getDireita() != null) {
                        NoArvoreRB<T> value = raiz.getDireita();
                        remover(value.getValor());
                        this.raiz = value;
                        this.raiz.setPai(null);
                    } else {
                        this.raiz = raiz.getEsquerda();
                        this.raiz.setPai(null);
                    }
                } else {
                    this.raiz = null;
                }
                if (this.raiz != null) {
                    this.raiz.setPai(null);
                    this.raiz.setPreto();
                }
            } else {
                if (raiz.numeroDeFilhos() == 2) {
                    /**
                     * Caso o no que eu vá remover tenha dois filhos, eu vou pro nó a esquerda e
                     * vejo se: ==>>Se o filho a esquerda tiver 2 filhos, eu pego o valor mais alto
                     * ==>>Se o filho a esquerda tiver 1 filho, eu procuro saber{ -Se o filho for o
                     * valor a direita, eu coloco ele no lugar do nó removido -Se for o valor a
                     * esquerda, eu passo o seu pai no lugar do no removido } ==>>Se o filho a
                     * esquerda não tiver filho
                     */
                    if (raiz.getEsquerda().numeroDeFilhos() == 2) {
                        remover2filhos(raiz, pai);
                    } else if (raiz.getEsquerda().numeroDeFilhos() == 1) {
                        remover1filho(raiz, pai);
                    } else {
                        removerFolhaEsquerda(raiz, pai);
                    }

                } else if (raiz.numeroDeFilhos() == 1) {
                    NoArvoreRB<T> filho;
                    if (raiz.getDireita() != null) {
                        // caso o unico filho seja a direita, precisamos recolocar ele em relação ao pai
                        filho = raiz.getDireita();
                    } else {
                        filho = raiz.getEsquerda();
                    }

                    // caso a raiz removida esteja a direita do pai, precisa repor a sua posição com
                    // seu unico filho
                    if (raiz.compareTo(pai.getValor()) > 0) {
                        // caso a raiz removida esteja a direita do pai
                        // colocamos na posição da raiz removida, o UNICO filho que ela possui
                        pai.setDireita(filho);
                    } else {
                        // caso a raiz removida esteja a esquerda do pai
                        // colocamos na posição da raiz removida, o UNICO filho que ela possui
                        pai.setEsquerda(filho);
                    }
                    filho.setPai(pai);
                } else {
                    // caso o no a ser removido, nao tenha nenhum filho, removemos ele, e o pai
                    // aponta agora para null
                    if (raiz.compareTo(pai.getValor()) > 0) {
                        // caso a raiz a ser removida seja a direita do pai, setamos ela como null
                        pai.removerDireita();
                    } else {
                        // caso a raiz a ser removida seja a esquerda do pai, setamos ela como null
                        pai.removerEsquerda();
                    }
                }
            }
        }

    }

    private void remover2filhos(NoArvoreRB<T> raiz, NoArvoreRB<T> pai) {
        // ==>>Se o filho a esquerda tiver 2 filhos, eu pego o valor mais alto
        NoArvoreRB<T> maximoValor = getMaximoValor(raiz.getEsquerda());
        if (maximoValor.isVermelho() && pai.isVermelho()) {
            maximoValor.setPreto();
        }
        if (raiz.compareTo(pai.getValor()) > 0) {
            pai.setDireita(maximoValor);
        } else {
            pai.setDireita(maximoValor);
        }
        maximoValor.setPai(pai);
    }

    private void remover1filho(NoArvoreRB<T> raiz, NoArvoreRB<T> pai) {
        // * ==>>Se o filho a esquerda tiver 1 filho, eu procuro saber{
        // * -Se o filho for o valor a direita, eu coloco ele no lugar do nó removido
        // * -Se for o valor a esquerda, eu passo o seu pai no lugar do no removido
        // *}
        if (raiz.getEsquerda().getEsquerda() != null) {
            // se o neto da esquerda existir
            if (raiz.compareTo(pai.getValor()) > 0) {
                pai.setDireita(raiz.getEsquerda());
            } else {
                pai.setEsquerda(raiz.getEsquerda());
            }
            raiz.getEsquerda().setPai(pai);
        } else {
            // se o neto da esquerda não existir, o da direita existe
            if (raiz.compareTo(pai.getValor()) > 0) {
                pai.setDireita(raiz.getDireita());
            } else {
                pai.setEsquerda(raiz.getDireita());
            }
            raiz.getDireita().setPai(pai);
        }
    }

    public void removerFolhaEsquerda(NoArvoreRB<T> raiz, NoArvoreRB<T> pai) {
        if (raiz.getEsquerda().isVermelho() && pai.isVermelho()) {
            raiz.getEsquerda().setPreto();
        }
        if (raiz.compareTo(pai.getValor()) > 0) {
            pai.setDireita(raiz.getEsquerda());
        } else {
            pai.setDireita(raiz.getEsquerda());
        }
        raiz.getEsquerda().setPai(pai);
    }

    private void checandoArvore(NoArvoreRB<T> raiz) throws ArvoreAVL.Exceptions.NotExistException, NotExistException {
        // vai verificar se a arvore esta cumprindo todas as propriedades
        concertandoCasoUm(raiz);
    }

    private void concertandoCasoUm(NoArvoreRB<T> raiz)
            throws ArvoreAVL.Exceptions.NotExistException, NotExistException {
        if (raiz.equals(this.raiz)) {
            // caso o elemento seja a raiz, define a cor como preta
            this.raiz.setCor(false);
            System.out.println("Case 1 complete");
        } else {
            concertandoCasoTres(raiz);
        }
    }

    private void concertandoCasoDois(NoArvoreRB<T> raiz)
            throws ArvoreAVL.Exceptions.NotExistException, NotExistException {


                
        /*
         * Preciso consertar quando o No adicionado tiver um pai vermelho. Para resolver
         * isso vamos trocar as cores do pai e do tio, e o valor do avo so sera alterado
         * caso ele seja diferente da raiz
         */
        if (raiz.getPai() != null && raiz.getPai().isVermelho()) {
            // caso o pai seja de cor vermelha

            // caso o avo nao seja a raiz da arvore, definimos ele como vermelho, caso
            // contrario ele fica preto
            if (raiz.getAvo() != null) {

                if (!raiz.getAvo().equals(this.getRaiz())) {
                    System.out.println("O avo passou a ser vermelho");
                    raiz.getAvo().setVermelho();
                }
            }

            // define o pai como preto
            raiz.getPai().setCor(false);
            System.out.println("A cor do pai do pai era vermelha, passou a ser preta");

            // se houver tio, defina o tio como preto
            if (raiz.getTio() != null) {
                raiz.getTio().setPreto();
                System.out.println("A cor do tio era vermelha, passou a ser preta");
            }

        }

        // concertandoCasoTres(raiz);

    }

    private void concertandoCasoTres(NoArvoreRB<T> raiz)
            throws ArvoreAVL.Exceptions.NotExistException, NotExistException {
        // Caso o tio do elemento inserido seja preto
        // Se o tio for NILL, ele é preto
        if (raiz.getTio() == null || !raiz.getTio().isVermelho()) {
            // caso o tio seja NILL ou preto
            definirBalanceamento(raiz);
        }
        concertandoCasoDois(raiz);
    }

    private void rotacaoEsquerda(NoArvoreRB<T> raiz) throws ArvoreAVL.Exceptions.NotExistException {
        System.out.println("Rotacao a esquerda");
        NoArvoreRB<T> avo = raiz.getAvo();
        NoArvoreRB<T> pai = raiz.getPai();
        
        if (avo.equals(this.raiz)){
            // se o avo for a raiz
            pai.setPai(null);
        }else{
            // caso o avo nao seja a raiz, significa que ele tem um pai
            raiz.setPai(avo.getPai());
            if (avo.compareTo(avo.getPai().getValor())> 0){
                avo.getPai().setDireita(pai);
            }else{
                avo.getPai().setEsquerda(pai);
            }
        }
        
        
        /*
         * avo 
         *    \
         *     pai  / \ pai =>> avo raiz \ 
         *       \
         *      raiz
         */
        if (avo.getPai() == null) {
            this.raiz = pai;
        } else {
            if (avo.equals(avo.getPai().getEsquerda())) {
                // se o avo for o filho a esquerda
                avo.getPai().setEsquerda(pai);
            } else {
                // se o avo for o filho a direita
                avo.getPai().setDireita(pai);
            }
            pai.setPai(avo.getPai());
        }
        avo.setPai(pai);
        avo.setDireita(pai.getEsquerda());
        pai.setEsquerda(avo);
        pai.setCor(avo.isVermelho());
        avo.setVermelho();
        if (avo.equals(this.raiz)){
            this.raiz = pai;
        }
        

    }

    private void rotacaoDireita(NoArvoreRB<T> raiz) throws ArvoreAVL.Exceptions.NotExistException {
        System.out.println("Rotacao a direita");
        NoArvoreRB<T> avo = raiz.getAvo();
        NoArvoreRB<T> pai = raiz.getPai();
        if (avo.equals(this.raiz)){
            // se o avo for a raiz
            pai.setPai(null);
        }else{
            // caso o avo nao seja a raiz, significa que ele tem um pai
            raiz.setPai(avo.getPai());
            if (avo.compareTo(avo.getPai().getValor())> 0){
                avo.getPai().setDireita(pai);
            }else{
                avo.getPai().setEsquerda(pai);
            }
        }
        /*
         * avo / pai / raiz
         * 
         */
        if (avo.getPai() == null) {
            this.raiz = pai;
        } else {
            if (avo.equals(avo.getPai().getEsquerda())) {
                // se o avo for o filho a esquerda
                avo.getPai().setEsquerda(pai);
            } else {
                // se o avo for o filho a direita
                avo.getPai().setDireita(pai);
            }
            pai.setPai(avo.getPai());
        }
        avo.setPai(pai);
        avo.setEsquerda(pai.getDireita());
        pai.setDireita(avo);
        pai.setCor(avo.isVermelho());
        avo.setVermelho();
        if (avo.equals(this.raiz)){
            this.raiz = pai;
        }
    }

    private void rotacaoDuplaEsquerda(NoArvoreRB<T> raiz) throws ArvoreAVL.Exceptions.NotExistException {
        System.out.println("Dupla esquerda");
        NoArvoreRB<T> pai = raiz.getPai();
        NoArvoreRB<T> avo = raiz.getAvo();
        pai.setEsquerda(raiz.getDireita());
        raiz.setDireita(pai);
        avo.setDireita(raiz);
        raiz.setPai(avo);
        pai.setPai(raiz);
        rotacaoEsquerda(pai);
    }

    private void rotacaoDuplaDireita(NoArvoreRB<T> raiz) throws ArvoreAVL.Exceptions.NotExistException {
        System.out.println("Dupla direita");
        NoArvoreRB<T> pai = raiz.getPai();
        NoArvoreRB<T> avo = raiz.getAvo();
        pai.setDireita(raiz.getEsquerda());
        raiz.setEsquerda(pai);
        avo.setEsquerda(raiz);
        raiz.setPai(avo);
        pai.setPai(raiz);
        rotacaoDireita(pai);
    }

    private void definirBalanceamento(NoArvoreRB<T> raiz)
            throws ArvoreAVL.Exceptions.NotExistException, NotExistException {

        if (raiz.getTio() == null) {
            if (raiz.getAvo() != null) {
                if (raiz.getPai().compareTo(raiz.getAvo().getValor()) > 0) {
                    // avo -> pai a direita
                    if (raiz.compareTo(raiz.getPai().getValor()) > 0 && raiz.getPai().getEsquerda() == null) {
                        // avo -> pai a direita -> raiz a direita (rotacao a esquerda)

                        rotacaoEsquerda(raiz);
                    } else {
                        // avo -> pai a direita -> raiz a esquerda (rotacao dupla a esquerda)

                        if (calcularFatorDeBalanceamento(raiz.getAvo()) > 1
                                || calcularFatorDeBalanceamento(raiz.getAvo()) < 1) {
                            rotacaoDuplaEsquerda(raiz);
                        }

                    }
                } else {
                    // avo -> pai a esquerda
                    if (raiz.compareTo(raiz.getPai().getValor()) > 0) {
                        // avo -> pai a esquerda -> raiz a direita (rotacao dupla direita)
                        if (calcularFatorDeBalanceamento(raiz.getAvo()) > 1
                                || calcularFatorDeBalanceamento(raiz.getAvo()) < 1) {
                            rotacaoDuplaDireita(raiz);
                        }
                    } else {
                        // avo -> pai a esquerda -> raiz a esquerda
                        rotacaoDireita(raiz);
                    }
                }
            }
        }
    }

    private int calcularFatorDeBalanceamento(NoArvoreRB<T> raiz) {
        if (raiz.getEsquerda() != null && raiz.getDireita() != null) {
            // in case that have 2 chilrens
            return calcularFatorDeBalanceamento(raiz.getEsquerda()) - calcularFatorDeBalanceamento(raiz.getDireita());
        } else if (raiz.getEsquerda() != null && raiz.getDireita() == null) {
            // left children only
            return calcularFatorDeBalanceamento(raiz.getEsquerda()) - (-1);
            // if i don't have the right children, we put a -1 in place, and continue the
            // recursion
        } else if (raiz.getEsquerda() == null && raiz.getDireita() != null) {
            // right children only
            return calcularFatorDeBalanceamento(raiz.getDireita()) - (-1);
        } else {
            return 0;
        }
    }

    public NoArvoreRB<T> getMaximoValor(NoArvoreRB<T> raiz) {
        if (raiz.getDireita() != null) {
            return getMaximoValor(raiz.getDireita());
        } else {
            return raiz;
        }
    }

    public NoArvoreRB<T> getRaiz() {
        return this.raiz;
    }
}
