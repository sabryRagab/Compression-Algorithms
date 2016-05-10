/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lz77;

import java.util.ArrayList;
import java.util.Scanner;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

/**
 *
 * @author sabry_ragab
 */
public class LZ77 {
   
    
    public static ArrayList<Tag> compress(String text){
        ArrayList<Tag> dictionary = new ArrayList<Tag>();
        
        String subText= "";
        int subTextIndex = 0;
        
        for(int i = 0; i < text.length(); i++){
            
            subText += text.charAt(i);
            
            boolean isFound = false;
            String temp = "";
            
            //check found or not
            for(int j = subTextIndex - 1; j >= 0; j--){
                
                temp = text.charAt(j) + temp;
                
                if(temp.contains(subText)){
                    isFound = true;
                    break;
                }
            }
            
            //not found
            if(!isFound){
                
                int pointer = 0;
                
                String subText2 = "";
                for(int j = subText.length() - 2; j >= 0; j--){
                    subText2 = subText.charAt(j) + subText2;
                }
                
                temp = "";
                for(int j = subTextIndex - 1; j >= 0; j--){

                    temp = text.charAt(j) + temp;

                    if(temp.contains(subText2) && !subText2.equals("")){
                        pointer = subTextIndex - j;
                        break;
                    }
                }
                
                int length = subText2.length();
                char nextChar = subText.charAt(length);
                
                Tag tag = new Tag(pointer, length, nextChar);
                
                dictionary.add(tag);
                
                subText = "";
                subTextIndex = i + 1;
                
            }
   
        }
        
        //last case
        if(subTextIndex < text.length()){
            
            int pointer = 0;

            String subText2 = "";
            for(int j = subText.length() - 2; j >= 0; j--){
                subText2 = subText.charAt(j) + subText2;
            }

            String temp = "";
            for(int j = subTextIndex - 1; j >= 0; j--){

                temp = text.charAt(j) + temp;

                if(temp.contains(subText2) && !subText2.equals("")){
                    pointer = subTextIndex - j;
                    break;
                }
            }

            int length = subText2.length();
            char nextChar = subText.charAt(length);

            Tag tag = new Tag(pointer, length, nextChar);

            dictionary.add(tag);

        }
        
        return dictionary;
    }
    
    public static String decompress(ArrayList<Tag> dictionary){
        String text = "";
        
        for(int i = 0 ; i < dictionary.size(); i++){
            
            int pointer =  dictionary.get(i).getPointer();
            int length = dictionary.get(i).getLength();                
            char nextChar =  dictionary.get(i).getNextChar();
            
            for(int j = text.length() - pointer, cnt = 0; cnt < length ; j++, cnt++){
                text +=  text.charAt(j);
            }
            
            text += nextChar;
            
          }
        
        return text;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
      
        Scanner cin = new Scanner(System.in);
         java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LZ77UI().setVisible(true);
            }
        });
          
          
           
         
    }
    
}
