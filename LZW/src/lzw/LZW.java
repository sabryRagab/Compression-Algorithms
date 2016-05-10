/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lzw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author sabry_ragab
 */
public class LZW {

    /**
     * Compression
     * abaababbaabaabaaaababbbbbbbb
     * abaababbaabaabaaaababbbbbbbb
     * 97 98 97 128 128 129 131 134 130 129 98 138 139 138
     */
    public static ArrayList<Integer> compress(String data) {
      
        
        HashMap<String, Integer> dictionary = new HashMap<String, Integer>();
        //add dictionary data for every char (0 - 127)
        int index = 0;
        while (index < 128) {
            char ch = (char) index;
            dictionary.put(ch + "", index++);
        }
        
        ArrayList<Integer> compressedData = new ArrayList<Integer>();
        String cur = "";
        
        for (int i = 0; i < (int) data.length(); i++) {
          
            cur += data.charAt(i);
            
            if (dictionary.containsKey(cur) == false) {
                
                char lastChar = cur.charAt((int) cur.length() - 1);
                String sub = cur.substring(0, (int) cur.length() - 1); //remove last char 
                
                dictionary.put(cur, index++); //add to dictionary
                cur = "" + lastChar;
                
                compressedData.add(dictionary.get(sub)); //add 'sub' refrence from the dictionary
            }
        }

        //add last string -> i am sure that it is in the dictionary table 
        compressedData.add(dictionary.get(cur));        
        return compressedData;
    }
    /**
     * Decompression
     */
    public static String decompress(ArrayList<Integer> compressedData){
        
        String data = ""; //actual data
        HashMap<String, Integer> sDictionary = new HashMap<String, Integer>(); // key is string
        HashMap<Integer, String> iDictionary = new HashMap<Integer, String>(); //key is int
        
        int dictionaryIter = 0;
        while (dictionaryIter < 128) {
             
            char ch = (char) dictionaryIter;
            iDictionary.put(dictionaryIter, ch + "");
            sDictionary.put(ch + "", dictionaryIter);
            dictionaryIter++;
        }
        
        int dataIter = 0;
        for(int i = 0; i < compressedData.size(); i++){
            
            if(iDictionary.containsKey( compressedData.get(i)) ){ //normal case
                
                data += iDictionary.get(compressedData.get(i)); 
                
                String temp = "";
                for(int j = dataIter; j < data.length(); j++){
                    
                    temp += data.charAt(j);
                    
                    //check found in dictionary or not
                    if(sDictionary.containsKey( temp ) == false){
                        
                        //add it
                        iDictionary.put(dictionaryIter, temp);
                        sDictionary.put(temp, dictionaryIter);
                        dictionaryIter++;
                        
                        //very importatnt -> index = index of last char in temp
                        dataIter = j; 
                        temp = data.charAt(j) + "";
                        
                    }
                    
                }
                
            }else{ //tricky case
                
                String sub = data.substring(dataIter) + data.charAt(dataIter);
                dataIter = data.length();
           
                // add it to the dictionary
                iDictionary.put(dictionaryIter, sub);
                sDictionary.put(sub, dictionaryIter);
                dictionaryIter++;
                i--; //to ask to enter this number a gain
            }
        }
        
        return data;
    }
    
    /**
     * 
     */
    public static double calcCompressionRatio(int nDataChar, int compressedDataSize, int maxNumber){
      
        double pre = nDataChar * 8.0; // 8bit for each char
        
        double nBit =  Math.log(maxNumber) + 1; //base 2
        double post = compressedDataSize * nBit;
        
        return (pre / post);
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
         
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LZWUI().setVisible(true);
            }
        });
        
        
    }
    
}
