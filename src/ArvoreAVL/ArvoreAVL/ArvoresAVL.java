package ArvoreAVL.ArvoreAVL;

import ArvoreAVL.Exceptions.NotExistException;
import ArvoreAVL.NoArvoreAVL.NoArvoreAVL;

public class ArvoresAVL<T extends Comparable<T>> {

    private NoArvoreAVL<T> raiz;

    public void adicionar(T valor) {
        raiz = treeAdd(raiz, valor);
    }

    public void remover(T valor) throws NotExistException {
        if (raiz != null) {
            raiz = treeRemove(raiz, valor);
        }
    }

    public NoArvoreAVL<T> getElement(T element) throws NotExistException {
        return buscarElemento(raiz, element);
    }

    private NoArvoreAVL<T> treeAdd(NoArvoreAVL<T> raiz, T element) {
        /*
         * Vou ficar na recursão até encontrar algum nó vázio, ou seja, significa que eu
         * vou poder adicionar o elemento naquela posição
         */

        if (raiz == null) {
            System.out.println("Added");
            return new NoArvoreAVL<T>(element);
        }

        int comparableFactor = element.compareTo(raiz.getValor());

        if (comparableFactor >= 0) {
            System.out.println("Right");
            raiz.setDireita(treeAdd(raiz.getDireita(), element));
        } else {
            System.out.println("Left");
            raiz.setEsquerda(treeAdd(raiz.getEsquerda(), element));
        }

        if (calcularFatorDeBalanceamento(raiz) < -1 || calcularFatorDeBalanceamento(raiz) > 1) {
            raiz = balancear(raiz);
        }
        return raiz;
    }

    private int calcularFatorDeBalanceamento(NoArvoreAVL<T> raiz) {
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

    private NoArvoreAVL<T> balancear(NoArvoreAVL<T> raiz) {
        if (raiz.getDireita() != null) {
            // se houver nó a direita, só pode ser apenas RR ou RL
            if (raiz.getDireita().getDireita() != null) // se houver no a direita, após a direita, significa que ela é
                                                        // um RR
                return rotationRR(raiz);
            else // se houver no a esquerda, após a direita, significa que ela é um RL
                return rotationRL(raiz);
        } else {
            // se houver nó a esquerda, só pode ser LL ou LR
            if (raiz.getEsquerda().getEsquerda() != null) // se houver no a esquerda, após a esquerda, significa que ela
                                                          // é um LL
                return rotationLL(raiz);
            else // se houver nó a direita, após a esquerda, significa que ela é um LR
                return rotationLR(raiz);
        }

    }

    private NoArvoreAVL<T> rotationRR(NoArvoreAVL<T> raiz) {
        System.out.println("Rotation RR");
        NoArvoreAVL<T> x = raiz.getDireita();
        raiz.setDireita(x.getEsquerda());
        x.setEsquerda(raiz);
        return x;
    }

    private NoArvoreAVL<T> rotationRL(NoArvoreAVL<T> raiz) {
        System.out.println("Rotation RL");
        raiz.setDireita(rotationLL(raiz.getDireita()));
        return rotationRR(raiz);
    }

    private NoArvoreAVL<T> rotationLL(NoArvoreAVL<T> raiz) {
        System.out.println("Rotation LL");
        NoArvoreAVL<T> x = raiz.getEsquerda();
        raiz.setEsquerda(x.getDireita());
        x.setDireita(raiz);
        return x;
    }

    private NoArvoreAVL<T> rotationLR(NoArvoreAVL<T> raiz) {
        System.out.println("Rotation LR");
        raiz.setEsquerda(rotationRR(raiz.getEsquerda()));
        return rotationLL(raiz);
    }

    private NoArvoreAVL<T> treeRemove(NoArvoreAVL<T> raiz, T element) throws NotExistException {
        //TODO o pai precisa apontar para Null tbm
        if (raiz == null) {
            throw new NotExistException();
        }

        int comparableFactor = element.compareTo(raiz.getValor());

        if (comparableFactor > 0) {
            // se a comparacao entre a raiz e o elemento for menor que 0, siginifica que o
            // valor esta a sua direita

            if (raiz.getDireita().compareTo(element) == 0) {
                // caso o valor a ser exlcuido seja o proximo elemento a direita
                raiz.setDireita(removerNo(raiz.getEsquerda()));
                // remove corretamente o No
            }

            raiz.setDireita(treeRemove(raiz.getDireita(), element));
        } else if (comparableFactor < 0) {
            // se a comparacao entre a raiz e o elemento for menor que 0, siginifica que o
            // valor esta a sua esquerda
            if (raiz.getEsquerda().compareTo(element) == 0) {
                // caso o valor a ser exlcuido seja o proximo elemento a esquerda
                raiz.setEsquerda(removerNo(raiz.getEsquerda()));
                // remove corretamente o No
            }

            raiz.setEsquerda(treeRemove(raiz.getEsquerda(), element));
        }

        if (calcularFatorDeBalanceamento(raiz) < -1 || calcularFatorDeBalanceamento(raiz) > 1) {
            raiz = balancear(raiz);
        }
        return raiz;
    }

    private NoArvoreAVL<T> removerNo(NoArvoreAVL<T> raiz) throws NotExistException {
        if (raiz.numeroDeFilhos() == 2) {
            // caso o no tenha dois filhos
            NoArvoreAVL<T> valor = getMaximoValor(raiz.getEsquerda());
            treeRemove(this.raiz, valor.getValor());
            return valor;

        } else if (raiz.numeroDeFilhos() == 1) {
            // caso o no tenha um filho
            if (raiz.getDireita() != null) {
                return new NoArvoreAVL<T>(raiz.getDireita().getValor());
            } else {
                return new NoArvoreAVL<T>(raiz.getEsquerda().getValor());
            }
        } else {
            // caso n tenha filho, apenas fica null no lugar
            return null;
        }
    }

    private NoArvoreAVL<T> buscarElemento(NoArvoreAVL<T> raiz, T element) throws NotExistException {
        if (raiz == null) {
            /*
             * caso ele chegue em uma raiz nula sem ter encontrado nenhum elemento, ele
             * retorna um erro dizendo que nao encontrou o elemento
             */
            throw new NotExistException();
        }

        int comparableFactor = element.compareTo(raiz.getValor());

        if (comparableFactor > 0) {
            // se o valor do fator de comparacao for maior que 0 o elemento esta a sua
            // direita
            // entao ele faz uma recursao pelo no a sua direita
            return buscarElemento(raiz.getDireita(), element);
        } else if (comparableFactor < 0) {
            // se o falor do fator de comparacao for menor que 0, o elementao esta a sua
            // esquerda
            // entao ele faz uma recursao pelo no a sua esquerda
            return buscarElemento(raiz.getEsquerda(), element);
        } else {
            // se o valor do fator de comparacao for igual a 0, significa que ele encontrou
            // o elemento
            // entao ele retorna
            return raiz;
        }
    }

    private NoArvoreAVL<T> getMaximoValor(NoArvoreAVL<T> raiz){
        if (raiz.getDireita() != null){
            return getMaximoValor(raiz.getDireita());
        }else{
            return raiz;
        }
    }

    public NoArvoreAVL<T> getRaiz() {
        return this.raiz;
    }

}
