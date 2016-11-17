/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package T2t3Package;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author korenciak.marek
 */
public class VerificatorRandomAction {

    public static void main(String[] args) {

        int seed = 100000;
        Random randomInstance = new Random();
        ArrayList<Integer> added = new ArrayList<>();
        T2t3<Integer, String> inst = new T2t3();
        int temp = 0;

        for (int i = 0; i < seed; i++) {
            temp = randomInstance.nextInt(2 * seed);
            if (inst.add(temp, "")) {
                added.add(temp);
            }
        }
        int iter = 0;
        System.out.println("1. round finished: " + inst.componentsInto());
        System.out.println("");

        for (; seed / 2 < added.size();) {
            temp = randomInstance.nextInt(added.size());
            if (inst.delete(added.get(temp))) {
                added.remove(temp);
                iter--;
                System.out.println(iter);
            }
        }

        System.out.println("2. round finished: " + inst.componentsInto());
        System.out.println("");

        double temp2 = 0;
        int computeEndNumber = 51 * seed / 100;
        iter = 0;

        while (added.size() < computeEndNumber) {
            iter += 1;
            temp2 = randomInstance.nextDouble();
            if (temp2 < 0.8) {
                temp = randomInstance.nextInt(2 * seed);
                if (inst.add(temp, "")) {
                    added.add(temp);
                    System.out.println("action no. " + iter + " insert: " + inst.componentsInto() + "/" + computeEndNumber);
                    System.out.println("");
                }else {
                    System.out.println("action no. " + iter + " insert: result- number inserted yet");
                    System.out.println("");
                }
            } else {
                temp = randomInstance.nextInt(added.size());
                if (inst.delete(added.get(temp))) {
                    added.remove(temp);
                    System.out.println("action no. " + iter + " delete: " + inst.componentsInto() + "/" + computeEndNumber);
                    System.out.println("");
                } else {
                    System.out.println("action no. " + iter + " delete: result- number is not in data structure");
                    System.out.println("");
                }
            }            
        }
    }

}
