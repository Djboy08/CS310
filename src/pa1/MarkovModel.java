package pa1;

import edu.princeton.cs.algs4.In;


import java.util.PriorityQueue;


public class BestModel {
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
            this.posORneg = this.diff > 0 ? "+" : "-";
            this.diff = Math.abs(diff);
        }

        @Override
        public int compareTo(DiffModel other) {
            return Double.compare(other.diff, this.diff);
        }

    }

    public BestModel(int k, String file1, String file2) {
        this.k = k;

        In in = new In(file1);
        String s1 = in.readAll().replace("\\s", " ");
        in = new In(file2);
        String s2 = in.readAll().replace("\\s", " ");

        this.m1 = new MarkovModel(k, s1);
        this.m2 = new MarkovModel(k, s2);

    }

    public MarkovModel getModel1() {
        return this.m1;
    }

    public MarkovModel getModel2() {
        return this.m2;
    }

    public double classifySequence(String sequence, String filename) {
        MarkovModel m1 = getModel1();
        MarkovModel m2 = getModel2();
        PriorityQueue<DiffModel> pq = new PriorityQueue<DiffModel>();

        System.out.print(filename);
        System.out.print("\t");

        int index = 0;
        int length = sequence.length();
        double m1Avg = 0;
        double m2Avg = 0;
        while (index < length) {
            String subText = MarkovModel.subString(sequence, index, index + this.k + 1).replaceAll("\\s", " ");
            double m1_ll = Math.log(m1.laplace(subText));
            double m2_ll = Math.log(m2.laplace(subText));

            DiffModel dm = new DiffModel(subText, m1_ll, m2_ll);
            pq.add(dm);

            m1Avg += m1_ll;
            m2Avg += m2_ll;
            index++;
        }
        m1Avg /= index;
        m2Avg /= index;

        System.out.print(m1Avg + "\t" + m2Avg + "\t" + (m1Avg - m2Avg));

        System.out.println();

        for (int i = 0; i < 10; i++) {
            DiffModel dm = pq.poll();
            String key = dm.subString;
            double m1_ll = dm.liklihood1;
            double m2_ll = dm.liklihood2;
            double diff = dm.diff;

            System.out.printf("\"%s\"\t %.3f %.3f %s%.3f\n", key, m1_ll, m2_ll, dm.posORneg, diff);
        }
        pq.clear();

        return (1.0);
    }

    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        BestModel model = new BestModel(k, args[1], args[2]);

        for (int i = 3; i < args.length; i++) {

            String element = args[i];
            In seq = new In(element);
            String lines = seq.readAll();

            model.classifySequence(lines, element);
            System.out.println();
        }
    }
}
