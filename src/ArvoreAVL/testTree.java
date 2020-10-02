package ArvoreAVL;

import java.util.Scanner;
import ArvoreAVL.*;
import ArvoreAVL.ArvoreAVL.ArvoresAVL;
public class testTree {
    public static ArvoresAVL<Integer> arvore = new ArvoresAVL()<>();
    public static Scanner in = new Scanner(System.in);
    public static void main(String[] args) {
        //esta implementado na arvore add,get,remove (adicionar,getElement,remover), pode adicionar casos de teste no main
        //boa diversão!
    }

    public static void demonstrar_balanceamento_esquerda(){
        arvore.adicionar(50);
        arvore.adicionar(70);
        arvore.adicionar(90);
    }

    public static void demonstar_rotacao_direita()  {
        arvore.adicionar(50);
        arvore.adicionar(30);
        arvore.adicionar(20);
    }

    public static void demonstrar_rotacao_dupla_esquerda()  {
        arvore.adicionar(50);
        arvore.adicionar(80);
        arvore.adicionar(70);
    }

    public static void demonstrar_rotacao_dupla_direita() {
        arvore.adicionar(50);
        arvore.adicionar(30);
        arvore.adicionar(40);
    }

    public static void test1()  {
        arvore.adicionar(50);
        arvore.remover(50);
        arvore.adicionar(25);
        arvore.adicionar(30);
        System.out.println(arvore.getRaiz());
    }

    public static void removerDaArvoreVazia()  {
        arvore.adicionar(20);
        arvore.remover(50);
    }


    public static void loopAdicionar()  {
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
