package ArvoreAVL;

import java.util.Scanner;
import ArvoreAVL.*;
import ArvoreAVL.ArvoreAVL.ArvoresAVL;
public class testTree {
    public static void main(String[] args) {
        ArvoresAVL<Integer> arvore = new ArvoresAVL<>();
        Scanner in = new Scanner(System.in);
        int awnser;

        do{
            System.out.print("Valor: ");
            awnser = in.nextInt();
            arvore.adicionar(awnser);
            
        }while (true);

        
    }
}
