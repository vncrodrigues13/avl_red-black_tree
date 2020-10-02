package ArvoreRB.ArvoreRB;

import java.util.Scanner;

import ArvoreAVL.Exceptions.NotExistException;

public class test {

    public static ArvoreRedBlack<Integer> arvore = new ArvoreRedBlack<>();
    public static Scanner in = new Scanner(System.in);
    public static void main(String[] args) throws NotExistException, ArvoreRB.Exceptions.NotExistException {
        removerDaArvoreVazia();
    }

    public static void demonstrar_balanceamento_esquerda() throws NotExistException, ArvoreRB.Exceptions.NotExistException {
        arvore.adicionar(50);
        arvore.adicionar(70);
        arvore.adicionar(90);
    }

    public static void demonstar_rotacao_direita() throws NotExistException, ArvoreRB.Exceptions.NotExistException {
        arvore.adicionar(50);
        arvore.adicionar(30);
        arvore.adicionar(20);
    }

    public static void demonstrar_rotacao_dupla_esquerda()
            throws NotExistException, ArvoreRB.Exceptions.NotExistException {
        arvore.adicionar(50);
        arvore.adicionar(80);
        arvore.adicionar(70);
    }

    public static void demonstrar_rotacao_dupla_direita()
            throws NotExistException, ArvoreRB.Exceptions.NotExistException {
        arvore.adicionar(50);
        arvore.adicionar(30);
        arvore.adicionar(40);
    }

    public static void test1() throws NotExistException, ArvoreRB.Exceptions.NotExistException {
        arvore.adicionar(50);
        arvore.remover(50);
        arvore.adicionar(25);
        arvore.adicionar(30);
        System.out.println(arvore.getRaiz());
    }

    public static void removerDaArvoreVazia() throws ArvoreRB.Exceptions.NotExistException, NotExistException {
        arvore.adicionar(20);
        arvore.remover(50);
    }


    public static void loopAdicionar() throws NotExistException, ArvoreRB.Exceptions.NotExistException {
        int awnser;
        do{
            System.out.println("Caso queira para o loop, insira o valor 999");
            System.out.print("Insira o valor: ");
            awnser = in.nextInt();
            arvore.adicionar(awnser);
            if (awnser != 999) break;
        }while (true);
    }



}
