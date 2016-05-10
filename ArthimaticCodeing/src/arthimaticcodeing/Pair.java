/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arthimaticcodeing;

/**
 *
 * @author sabry_ragab
 */
public class Pair {
    private char myChar;
    private double prob;

    public Pair(char myChar, double prob) {
        this.myChar = myChar;
        this.prob = prob;
    }

    public char getMyChar() {
        return myChar;
    }

    public void setMyChar(char myChar) {
        this.myChar = myChar;
    }

    public double getProb() {
        return prob;
    }

    public void setProb(double prob) {
        this.prob = prob;
    }
    
}
