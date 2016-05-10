/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package predictivecoding;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author sabry_ragab
 */
public class PredictiveCoding {

    public static String getQ(int quantizerType, int value) {
        int step = 512 / (1 << quantizerType);
        int number = 0;
        for (int i = -255; i < 256; i += step) {
            int low = i;
            int high = low + step - 1;
            if (low <= value && high >= value) {
                break;
            }
            number++;
        }
        String res = Integer.toBinaryString(number);
        while (res.length() < quantizerType) {
            res = '0' + res;
        }
        return res;
    }

    public static int getQInverse(int quantizerType, String code) {
        int value = 0;
        int bitIndex = 0;
        for (int i = code.length() - 1; i >= 0; i--) {
            if (code.charAt(i) == '1') {
                value += (1 << (bitIndex));
            }
            bitIndex++;
        }

        int step = 512 / (1 << quantizerType);
        int number = 0;
        int res = 0;
        for (int i = -255; i < 256; i += step) {
            int low = i;
            int high = low + step - 1;
            if (number == value) {
                res = (low + high) / 2;
                break;
            }
            number++;

        }
        return res;
    }

    public static void writeData(int quantizerType, int h, int w, int firstPixel, String data) {
        try {

            File file = new File("compression_Info.txt");

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(quantizerType + "\n");
            bw.write(h + " " + w + "\n");
            bw.write(firstPixel + "\n");
            bw.write(data + "\n");
            bw.close();

            System.out.println("Compression Done");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void compress(int quantizerType) {
        ImageRT img = new ImageRT();
        img.readImage("C:\\Users\\sabry_ragab\\Documents\\NetBeansProjects\\"
                + "PredictiveCoding\\lena.jpg");
        int[][] pixels = img.getPixels();
        String compressedData = "";
        int firstPixel = pixels[0][0];
        int prev = firstPixel;
        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                if (i == 0 && j == 0) { //first element
                    continue;
                }
                int diff = pixels[i][j] - prev;
                String code = getQ(quantizerType, diff);
                compressedData += code;
                int midValue = getQInverse(quantizerType, code);
                prev += midValue;
            }
        }
        writeData(quantizerType, img.getHeight(), img.getWidth(), firstPixel, compressedData);
    }

    public static void decompress() throws FileNotFoundException {
        //read data
        Scanner cin = new Scanner(new File("compression_Info.txt"));
        int quantizerType = cin.nextInt();
        int height = cin.nextInt();
        int width = cin.nextInt();
        int firstPixel = cin.nextInt();
        String data = cin.next();
        cin.close();
        //decompress
        int[][] pixels = new int[height][width];
        pixels[0][0] = firstPixel;
        int prev = firstPixel;
        int dataIndex = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (i == 0 && j == 0) { //first element
                    continue;
                }

                String code = data.substring(dataIndex, dataIndex + quantizerType);
                int midValue = getQInverse(quantizerType, code);
                prev += midValue;
                pixels[i][j] = prev;
                dataIndex += quantizerType;
            }
        }

        ImageRT img = new ImageRT();
        img.setPixels(pixels);
        img.writeImage("C:\\Users\\sabry_ragab\\Documents\\NetBeansProjects\\PredictiveCoding\\"
                + "lenaAfter60year.jpg");
    }

    public static void main(String[] args) throws FileNotFoundException {

        compress(2);
        decompress();

    }

}
