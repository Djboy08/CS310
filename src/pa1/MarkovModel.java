package pa1;


import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import edu.princeton.cs.algs4.ST;


public class MarkovModel {
    private int k;
    private String s;
    private TreeMap<String, Integer> st;
    private ST<String, Integer> alphabet;
    private int S;

    public MarkovModel(int k, String s){
        this.k = k;
        this.s = s;
        this.st = new TreeMap<String, Integer>();
        this.alphabet = new ST<String, Integer>();
        this.S = 0;

        model(this.s, this.k);
    }

    public static String subString(String s, int index, int goal){
        int length = s.length();

        if(goal >= length){
            int difference = goal - length;
            String sub = s.substring(index, length);
            String sub2 = s.substring(0, difference);
            String joinedString = sub + sub2;
            return joinedString;
        }
        return s.substring(index, goal);
    }

    public void model(String s, int k){
        int length = s.length();
        int index = 0;
        while(index < length){
            // String subbedText = s.substring(index, index + this.k);
            // for(int i=this.k+1; i>=2 ;i--){
                String subText = MarkovModel.subString(s, index, index + k);
                String subText2 = MarkovModel.subString(s, index, index + k + 1);
                addKey(subText);
                addKey(subText2);
                addAlphabet(s.substring(index, index+1));
            // }
            // String subText = this.subString(s, index, index + this.k);
            // addKey(subText);
            index++;
        }
        this.S = alphabet.size();
        // if(length/this.k != 0){
        //     String returnString = stringCircle(s, this.k);
        //     // System.out.println(returnString+ "WO");
        //     addKey(returnString);
        // }
    }

    // public String stringCircle(String str, int totalLetters){
    //     int length = str.length();
    //     char lastCharacter = s.charAt(length-1);
    //     String circle = s.substring(0, totalLetters-1);
    //     String returnString = lastCharacter+circle;
    //     return returnString;
    // }


    public void addKey(String key){
        if(st.get(key) == null){
            st.put(key, 1);
        }else{
            st.put(key, st.get(key) + 1);
        }
    }

    public void addAlphabet(String key){
        if(alphabet.get(key) == null){
            alphabet.put(key, 1);
        }else{
            alphabet.put(key, alphabet.get(key) + 1);
        }
    }

    public double laplace(String s){
        int S = getS();
        int NP = 0;
        String sub = s.substring(0, s.length() - 1);
        int NPC = 0;
        
        if(st.get(sub) != null){
            NP = st.get(sub);
        }
        if(st.get(s) != null){
            NPC = st.get(s);
        }

        double num = (NPC + 1);
        double den = (NP + S);
        double d = num/den;

        return d;
    }

    // The order of the model
    public int getK(){
        return this.k;
    }

    // returns size of alphabet
    public int getS(){
        return this.S;
    }
    
    public TreeMap<String, Integer> getST(){
        return this.st;
    }

    public String toString(){
        ArrayList<String> list_k = new ArrayList<String>();
        ArrayList<String> list_k_1 = new ArrayList<String>();
        String str = "S = "+getS();
        for(String key : st.keySet()){
            if(key.length() == this.k){
                list_k.add(key);
            }else{
                list_k_1.add(key);
            }
        }
        Collections.sort(list_k);
        Collections.sort(list_k_1);
        for(String key : list_k){
            str += "\n\""+key+"\"\t" + st.get(key);
        }
        for(String key : list_k_1){
            str += "\n\""+key+"\"\t" + st.get(key);
        }
        // str += "\n\""+key+"\"\t" + st.get(key);
        return str;
    }

    public static void main(String[] args){
        int k = Integer.parseInt(args[0]);
        String s = "";
        try{
            File file = new File(args[1]);
            Scanner scanner = new Scanner(file);
            while(scanner.hasNext()){
                String line = scanner.nextLine();
                s += line;
            }
            scanner.close();
            s.replaceAll("\\s"," ");
            MarkovModel model = new MarkovModel(k, s);
            System.out.println(model);
            System.out.printf("%.4f\n", model.laplace("aac"));
            System.out.printf("%.4f\n", model.laplace("aaa"));
            System.out.printf("%.4f\n", model.laplace("aab"));
        }catch(FileNotFoundException e){
            System.out.println(e);
        }

    }
}