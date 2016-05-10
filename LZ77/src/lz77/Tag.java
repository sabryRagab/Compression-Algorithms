/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lz77;

/**
 *
 * @author sabry_ragab
 */
public class Tag {
    
    private int pointer;
    private int length;
    private char nextChar;

    public Tag(int pointer, int length, char nextChar) {
        this.pointer = pointer;
        this.length = length;
        this.nextChar = nextChar;
    }

    public void setPointer(int pointer) {
        this.pointer = pointer;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setNextChar(char nextChar) {
        this.nextChar = nextChar;
    }
    

    public int getPointer() {
        return pointer;
    }

    public int getLength() {
        return length;
    }

    public char getNextChar() {
        return nextChar;
    }
    
    
}
