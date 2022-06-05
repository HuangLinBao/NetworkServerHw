package com.net;
import java.io.*;
import java.util.*;
import java.net.*;

public class Main {

    public static void main(String[] args) throws Exception {
        String courseNumber;
        String courseName;
        String capitalizedSentence;
        //this whole procedure is just reading data from the file and storing it into a HashMap
        File file = new File("/home/ayyy/Courses.txt");
       BufferedReader br = new BufferedReader(new FileReader(file));
       HashMap<String,String> map = new HashMap<String,String >();
        String line = null;
        while ((line = br.readLine()) != null) {

            // split the line by :
            String[] parts = line.split("->");

            // first part is name, second is number
            String number = parts[0].trim();
            String name = parts[1].trim();

            // put name, number in HashMap if they are
            // not empty
            if (!name.equals("") && !number.equals(""))
                map.put(number, name);
        }
        if (br != null) {
            br.close();
        }
        //HashMap storing ends here
        ServerSocket welcomeSocket = new ServerSocket(8080);//TCP server socket

        while (true) {

            Socket connectionSocket = welcomeSocket.accept();//accept the connection

            BufferedReader inFromClient =
                    new BufferedReader(new
                            InputStreamReader(connectionSocket.getInputStream()));//BufferedReader to read input from client



            DataOutputStream  outToClient =
                    new DataOutputStream(connectionSocket.getOutputStream());//Input stream to output the data to client

            courseNumber = inFromClient.readLine();//get the data from client
            if (map.containsKey(courseNumber)){
                 courseName = map.get(courseNumber);// check if the course exists
            }
            else
                courseName = "404 Not Found";// if it doesn't exist
            capitalizedSentence = courseName.toUpperCase() + '\n';//capitalize

            outToClient.writeBytes(capitalizedSentence);// out to client

        }
    }
}
