/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huffman;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Stack;
import javafx.scene.layout.Priority;
import oracle.jrockit.jfr.events.ContentTypeImpl;

/**
 *
 * @author sabry_ragab
 */
public class Huffman {
    
    public static int nBit = 0;
    
    public static String readData() {
        File file = new File("C:\\Users\\sabry_ragab\\Documents\\"
                + "NetBeansProjects\\Huffman\\src\\huffman\\data.txt");
        String data = "";
        try {

            Scanner cin = new Scanner(file);
            while (cin.hasNext()) {
                String line = cin.next();
                data += line;
            }

            cin.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return data;
    }
    
    public static void writeData(String data){
        try {

                File file = new File("C:\\Users\\sabry_ragab\\Documents\\"
                + "NetBeansProjects\\Huffman\\src\\huffman\\data.txt");
                
                // if file doesnt exists, then create it
                if (!file.exists()) {
                        file.createNewFile();
                }

                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(data);
                bw.close();


        } catch (IOException e) {
                e.printStackTrace();
        }

    }
    
    //read from info file
    public static HashMap<String, String> readDictionary() {
        
       HashMap<String, String> dictionary = new HashMap<>();
       
        File file = new File("C:\\Users\\sabry_ragab\\Documents\\"
          + "NetBeansProjects\\Huffman\\src\\huffman\\info.txt");

        try {

            Scanner cin = new Scanner(file);
            
            //char - code
            while (cin.hasNext()) {
                
                String ch = cin.next();
                String code = cin.next();
                
                dictionary.put(code, ch);
            }

            cin.close();
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        return dictionary;
    }
    
    public static void writeDictionary( HashMap<String, String> dictionary){
        //dictionary contain (char, code)
        try {
            
            File file = new File("C:\\Users\\sabry_ragab\\Documents\\"
          + "NetBeansProjects\\Huffman\\src\\huffman\\info.txt");

            // if file doesnt exists, then create it
            if (!file.exists()) {
                    file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            
            for (Map.Entry<String, String> entry : dictionary.entrySet()) {
                String ch = entry.getKey();
                String code = entry.getValue();

               bw.write(ch + " " + code + "\n");
            }
            
            bw.close();


        } catch (IOException e) {
                e.printStackTrace();
        }
    }

    public static HashMap<Character, Long> getFrequency(String data) {

        HashMap<Character, Long> freq = new HashMap<Character, Long>();

        for (int i = 0; i < (int) data.length(); i++) {

            if (freq.containsKey(data.charAt(i))) { //exist before

                Long perv = freq.get(data.charAt(i));
                freq.put(data.charAt(i), perv + 1);

            } else {
                freq.put(data.charAt(i), 1L);
            }
        }

        return freq;
    }

    public static HashMap<String, String> getCodes(HashMap<Character, Long> charFrequancy) {

        PriorityQueue<Node> pq = new PriorityQueue<Node>();

        //add initial frequency Values for each char
        for (Map.Entry<Character, Long> entry : charFrequancy.entrySet()) {
            String str = entry.getKey() + "";
            Long freq = entry.getValue();

            pq.add(new Node(str, freq));
        }

        // build tree
        HashMap<String, Pair> prev = new HashMap<>();
        // String is the parent 
        // pair.first is child 1
        // pair.second is child 2

        while (pq.size() > 2) {
            Node node1 = pq.poll();
            Node node2 = pq.poll();

            //combin this two nodes
            pq.add(new Node(node1.getStr() + node2.getStr(),
                    node1.getFreq() + node2.getFreq()));

            prev.put(node1.getStr() + node2.getStr(), //key -> parent
                    new Pair(node1.getStr(), node2.getStr())); //value- > childs
        }

        //go from down to top for assigning codes to every char
        Stack st = new Stack();
        // i am sure that it contains 1 or 2 elemnets only in pq
        while (!pq.isEmpty()) {

            Node topNode;
            String root;

            //element 1
             if (!pq.isEmpty()) {
                topNode = pq.poll();
                root = topNode.getStr();
                st.push(new Pair(root, "0"));
             }

            //element 2
            if (!pq.isEmpty()) {
                topNode = pq.poll();
                root = topNode.getStr();
                st.push(new Pair(root, "1"));
            }
        }

        HashMap<String, String> codes = new HashMap<>(); //codes of each char

        while (!st.isEmpty()) {
            Pair p = (Pair) st.peek();
            st.pop();

            String parent = p.getFirst();
            String code = p.getSecond();

            if (parent.length() == 1) { // is a character
               // System.out.println(parent + " " + code);
                codes.put(parent, code);
                continue;
            }

            Pair childs = prev.get(parent);
            st.push(new Pair(childs.getFirst(), code + "0")); //put child 1
            st.push(new Pair(childs.getSecond(), code + "1")); //put child 2

        }

        return codes;
    }
    //write in binary format
    public static void writeCompressedData(String compressedData){
        nBit = (int) compressedData.length(); 
        char[] content = new char [nBit / 8 + 1];
        
        for(int i = 0; i < nBit; i++) {
            if(compressedData.charAt(i) == '0'){
                //set 0
                int index = i / 8;
                int subIndex = i % 8;
                //flip then and
                content[index] = (char) ((content[index]) & (~(1 << subIndex)) );
            }else{
                //set = 1
                 int index = i / 8;
                int subIndex = i % 8;
                //use or operator
                content[index] = (char) ((content[index]) | (1 << subIndex) );
            }
        }
        
        //write to the file
        try {

                File file = new File("C:\\Users\\sabry_ragab\\Documents\\"
                    + "NetBeansProjects\\Huffman\\src\\huffman\\decompressedData.txt");

                // if file doesnt exists, then create it
                if (!file.exists()) {
                        file.createNewFile();
                }

                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(nBit + "\n");
                bw.write(content);
                bw.close();

        } catch (IOException e) {
                e.printStackTrace();
        }

        
    }
    
    public static String readCompressedData(){
        File file = new File("C:\\Users\\sabry_ragab\\Documents\\"
          + "NetBeansProjects\\Huffman\\src\\huffman\\decompressedData.txt");
        String compressedData = "";
         char[] content = null ;
        try {

            Scanner cin = new Scanner(file);
            
            //nBit
            //content
            nBit = 0;
            while (cin.hasNext()) {
                nBit = cin.nextInt();
                content = cin.next().toCharArray();
            }

            cin.close();
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        //fill compressed data
        for(int i = 0; i < nBit; i++){
            int index = i / 8;
            int subIndex = i % 8;
            
            int value = (int)( content[index] & (1 << subIndex) );
            if( value != 0  ){ //if equal 1
                compressedData += '1';
            } else {
                compressedData += '0';
            }
        }
        
        return compressedData;
    }
    
    public static String compress() {
        String data = readData();
        HashMap<Character, Long> charFrequancy = getFrequency(data);
        HashMap<String, String> dictionary = getCodes(charFrequancy);
        
        String compressed = "";
       
        for (int i = 0; i < data.length(); i++) {
            compressed += dictionary.get(data.charAt(i) + "");
        }
        
        //write codes to inforamtion file
        writeDictionary(dictionary);
        writeCompressedData(compressed);
         
        return compressed;
    }

    public static String decompress() {

       
        HashMap<String, String> dictionary = readDictionary(); 
        String compressedData = readCompressedData();
        
        String data = "";
        String cur = "";

        for (int i = 0; i < compressedData.length(); i++) {

            cur += compressedData.charAt(i);

            if (dictionary.containsKey(cur)) {
                data += dictionary.get(cur);
                cur = "";
            }
        }
        
        writeData(data);
        
        return data;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HuffmanUI().setVisible(true);
            }
        });

        

    }

}
