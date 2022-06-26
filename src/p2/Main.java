package p2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        PRGraphImpl graph = new PRGraphImpl(); //creating the graph
        String[] words; // the array of words in line
        try {
            BufferedReader in = new BufferedReader(new FileReader("file.txt")); // reading the file
            String line;
            int lineNum = 1; // the line number in file
            while((line = in.readLine()) != null){
                words = line.split(" "); // splits the line on words
                String command = words[0]; // getting a first word in line as command
                switch(command){ // switch for particular command
                    case "PROCESS": {
                        graph.addProcess(words[1]);
                        lineNum++;
                        break;
                    }
                    case "RESOURCE": {
                        graph.addResource(words[1]);
                        lineNum++;
                        break;
                    }
                    case "OPEN": {
                        graph.open(words[1], words[2]);
                        lineNum++;
                        break;
                    }
                    case "CLOSE": {
                        graph.close(words[1], words[2]);
                        lineNum++;
                        break;
                    }
                    default: {
                        System.out.println("Error in line "+ lineNum +" :"+ line);
                        lineNum++;
                    }
                }
            }
            in.close(); //closing the buffer reader
            System.out.println(graph); // printing graph
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
