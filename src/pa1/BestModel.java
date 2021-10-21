package pa1;
import edu.princeton.cs.algs4.In;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class BestModel {
    private String s1;
    private String s2;
    private MarkovModel m1;
    private MarkovModel m2;
    private int k;

    private class DiffModel implements Comparable<DiffModel> {
        private String subString;
        private double liklihood1;
        private double liklihood2;
        private double diff;
        private String posORneg;

        public DiffModel(String subString, double likelihood1, double likelihood2) {
            this.subString = subString;
            this.liklihood1 = likelihood1;
            this.liklihood2 = likelihood2;
            this.diff = likelihood1 - likelihood2;
            if(this.diff < 0){
                this.posORneg = "-";
            }else{
                this.posORneg = "+";
            }
            this.diff = Math.abs(diff);
        }

        @Override public int compareTo(DiffModel other) {
            return Double.compare(other.diff, this.diff);
        }

    }

    public static HashMap<String, Double> sortByValue(HashMap<String, Double> hm)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Double> > list =
               new LinkedList<Map.Entry<String, Double> >(hm.entrySet());
 
        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Double> >() {
            public int compare(Map.Entry<String, Double> o1,
                               Map.Entry<String, Double> o2)
            {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });
         
        // put data from sorted list to hashmap
        HashMap<String, Double> temp = new LinkedHashMap<String, Double>();
        for (Map.Entry<String, Double> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    public BestModel(int k, String file1, String file2){
        this.k = k;

        In in = new In(file1);
        String s1 = in.readAll().replace("\\s", " ");
        in = new In(file2);
        String s2 = in.readAll().replace("\\s", " ");


        this.m1 = new MarkovModel(k,s1);
        this.m2 = new MarkovModel(k,s2);

        
    }


    public MarkovModel getModel1(){
        return this.m1;
    }

    public MarkovModel getModel2(){
        return this.m2;
    }

    public double calcLaplace(String sequence){
        MarkovModel m1 = getModel1();
        MarkovModel m2 = getModel2();
        PriorityQueue<DiffModel> pq = new PriorityQueue<DiffModel>();
        TreeMap<String, Double> m1_lapaces = new TreeMap<String, Double>();
        TreeMap<String, Double> m2_lapaces = new TreeMap<String, Double>();
        HashMap<String, Double> diff_laplaces = new HashMap<String, Double>();
        HashMap<String, String> positive_negative = new HashMap<String, String>();

        int index = 0;
        int length = sequence.length();
        double m1Avg = 0;
        double m2Avg = 0;
        while(index < length){
            String subText = MarkovModel.subString(sequence, index, index + this.k + 1).replaceAll("\\s", " ");
            double m1_ll = Math.log(m1.laplace(subText));
            m1_lapaces.put(subText, m1_ll);
            double m2_ll = Math.log(m2.laplace(subText));
            m2_lapaces.put(subText, m2_ll);

            double difference = Math.abs(m1_ll - m2_ll);
            diff_laplaces.put(subText, difference);
            DiffModel dm1 = new DiffModel(subText, m1_ll, m2_ll);
            pq.add(dm1);

            if(m1_ll - m2_ll > 0){
                positive_negative.put(subText, "+");
            }else{
                positive_negative.put(subText, "-");
            }

            m1Avg += m1_ll;
            m2Avg += m2_ll;
            index++;
        }
        m1Avg /= index;
        m2Avg /= index;

        System.out.print(m1Avg + "\t" + m2Avg + "\t" + (m1Avg-m2Avg));

        System.out.println();
        Map<String, Double> hm1 = sortByValue(diff_laplaces);
        int i = 1;
        // for(String key : hm1.keySet()){
        //     if(i > 10){ break;}
        //     System.out.printf("\"%s\"\t %.3f %.3f %s%.3f\n", key, m1_lapaces.get(key), m2_lapaces.get(key), positive_negative.get(key) ,diff_laplaces.get(key));
        //     i++;
        // }

        for(i = 0; i<10; i++){
            DiffModel dm = pq.poll();
            String key = dm.subString;
            double m1_ll = dm.liklihood1;
            double m2_ll = dm.liklihood2;
            double diff = dm.diff;

            System.out.printf("\"%s\"\t %.3f %.3f %s%.3f\n", key, m1_ll, m2_ll, positive_negative.get(key) ,diff);
        }

        return (1.0);
    }

    public static void main(String[] args){
        int k = Integer.parseInt(args[0]);
        BestModel model = new BestModel(k, args[1], args[2]);
        MarkovModel m1 = model.getModel1();
        MarkovModel m2 = model.getModel2();

        for(int i=3; i < args.length; i++){
            System.out.print(args[i]);
            System.out.print("\t");

            String element = args[i];
            In seq = new In(element);
            String lines = seq.readAll();

            model.calcLaplace(lines);
            System.out.println();
            
            // System.out.print(avgM1 + "\t" + avgM2 + "\t" + (avgM1-avgM2));
            // System.out.println();
        }
    }
}
