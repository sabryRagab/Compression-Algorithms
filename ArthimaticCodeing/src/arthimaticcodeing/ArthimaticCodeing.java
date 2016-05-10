/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arthimaticcodeing;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 *
 * @author sabry_ragab
 */
public class ArthimaticCodeing {

    /**
     * @param args the command line arguments
     */
    
    public static void writeData( ArrayList<Pair> charCumProb, double tagValue,
            int nDistinctChar, int dataLength){
        
        try {

                File file = new File("C:\\Users\\sabry_ragab\\Documents\\NetBeansProjects\\"
                + "ArthimaticCodeing\\src\\arthimaticcodeing\\data.txt");
                
                // if file doesnt exists, then create it
                if (!file.exists()) {
                        file.createNewFile();
                }

                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(nDistinctChar + " " + dataLength + "\n");
                for(int i = 0; i < charCumProb.size(); i++){
                    bw.write(charCumProb.get(i).getMyChar() + " " + charCumProb.get(i).getProb() + "\n");
                }
                bw.write(tagValue + "\n");
                bw.close();

        } catch (IOException e) {
                e.printStackTrace();
        }

    }
    
    public static double  compress(String data){
        
        int nChar = data.length();
        TreeMap< Character , Integer> freq = new TreeMap<>(); //elements are sorted alphabetically
        
        //calc freq for each char
        for(int i = 0; i < nChar; i++){
            
            char ch = data.charAt(i);
            
            if(freq.containsKey(ch)){
                int prevFreq = freq.get(ch);
                freq.put(ch, prevFreq + 1);
            }else{
                freq.put(ch, 1);
            }
           
        }
        
        // clac probability for each char
        ArrayList<Pair> charCumProb = new ArrayList<Pair>();
        char anyTemporaryChar = '-';
        charCumProb.add(new Pair(anyTemporaryChar, 0.0) );
        double cummulativeProb = 0.0;
        
        for (Map.Entry<Character, Integer> entry : freq.entrySet()) {
            
            char ch = entry.getKey();
            int charFreq = entry.getValue();    
            double prob = (double)charFreq / (double)nChar;   
            cummulativeProb += prob;
            charCumProb.add( new Pair(ch, cummulativeProb) );
            
        }
        
        double lower = 0.0, upper = 1.0;
       
        for(int charIndex = 0; charIndex < nChar; charIndex++){
    
            char curChar = data.charAt(charIndex);
            
            double oldLower = lower, oldUpper = upper;
            double prevCummulative = 0;
            double curCummulative = 0;
            
            for(int i = 1; i < charCumProb.size(); i++){
                
                if(curChar == charCumProb.get(i).getMyChar()){
                    prevCummulative = charCumProb.get(i - 1).getProb(); 
                    curCummulative = charCumProb.get(i).getProb();
                    break;
                }
            }
            
            lower = oldLower + (oldUpper - oldLower) * prevCummulative;
            upper = oldLower + (oldUpper - oldLower) * curCummulative;
      
        }

        double tagValue = (lower + upper )/2.0;
        
        writeData( charCumProb,  tagValue,  charCumProb.size(), nChar);
        
        return tagValue;
        
    }
    
    public static String decompress(){
        
          
        ArrayList<Pair> charCumProb = new  ArrayList<>(); 
        double tagValue= 0.0;
        int nChar = 0;
        int dataLength  = 0;
         
        File file = new File("C:\\Users\\sabry_ragab\\Documents\\NetBeansProjects\\"
                + "ArthimaticCodeing\\src\\arthimaticcodeing\\data.txt");
       
        try {

            Scanner cin = new Scanner(file);        
            nChar = cin.nextInt();  
            dataLength = cin.nextInt();
            
            while(nChar > 0){
                String s = cin.next();
                double prob = cin.nextDouble();
                char ch = s.charAt(0);
                charCumProb.add(new Pair(ch, prob));
                nChar--;
            }
            
            tagValue = cin.nextDouble();
            cin.close();
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
   
        double lower = 0.0, upper = 1.0;
        String data = "";
        double t = 0.0; 
        while(dataLength > 0){
            
            t = (tagValue - lower) / (upper - lower);
     
            dataLength--;
            
            double oldLower = lower, oldUpper = upper;
            double prevCummulative = 0;
            double curCummulative = 0;
            //get char 
            char curChar = charCumProb.get(0).getMyChar();
            
            for(int i = 1; i < charCumProb.size(); i++){
                
               if(t >= charCumProb.get(i - 1).getProb() &&
                      t < charCumProb.get(i).getProb() ){
                   
                  curChar = charCumProb.get(i).getMyChar(); //assign char
                  prevCummulative =  charCumProb.get(i - 1).getProb();
                  curCummulative = charCumProb.get(i).getProb();
                   break;
                   
               }
           }
            
           
            
            data += curChar;
       
            lower = oldLower + (oldUpper - oldLower) * prevCummulative;
            upper = oldLower + (oldUpper - oldLower) * curCummulative;
      
            
                    
        }
                
        return data;
        
    }
    
    public static void main(String[] args) {
        
       /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ArthimaticCodeingUI().setVisible(true);
            }
        });
     
    }
    
}
