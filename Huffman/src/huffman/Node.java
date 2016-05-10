package huffman;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sabry_ragab
 */
public class Node  implements Comparable<Node>{
    
    private long freq;
    private String str;

    public Node(String str, long freq) {
        this.str = str;
        this.freq = freq;
    }

    public long getFreq() {
        return freq;
    }

    public void setFreq(long freq) {
        this.freq = freq;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    
    @Override
    public int compareTo(Node o) {
        return  Long.compare(this.freq, o.freq);
    }
    
    @Override
    public String toString() {
            return str + " " + freq ;
    }

    
    
}
