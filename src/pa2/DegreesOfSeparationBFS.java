package pa2;

import edu.princeton.cs.algs4.BreadthFirstPaths;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.SymbolGraph;

public class DegreesOfSeparationBFS {
    SymbolGraph sg;
    BreadthFirstPaths bfs;
    int src;

    // constructor takes three arguments: file name, delimiter, and source
    public DegreesOfSeparationBFS(String filename, String delimiter, String source) {
        sg = new SymbolGraph(filename, delimiter);

        if (!sg.contains(source)) {
            StdOut.println(source + " not in database.");
            return;
        }
        src = sg.indexOf(source);
        bfs = new BreadthFirstPaths(sg.graph(), src);
    }

    // returns the number of degrees of separation between the source vertex
    // and the destination vertex
    public int baconNumber(String destination) {
        if (!sg.contains(destination)) {
            StdOut.println(destination + " not in database.");
            return -1;
        }

        int dst = sg.indexOf(destination);
        if (!bfs.hasPathTo(dst)) {
            StdOut.println("No path between " + sg.nameOf(src) + " and " + sg.nameOf(dst));
            return -1;
        }
        Stack<Integer> stack = graphPath(destination);

        return stack.size() / 2;
    }

    public void printHistogram() {

        // compute histogram of Kevin Bacon numbers - 100 for infinity
        int MAX_BACON = 100;
        int[] hist = new int[MAX_BACON + 1];
        for (int v = 0; v < sg.graph().V(); v++) {
            int bacon = Math.min(MAX_BACON, bfs.distTo(v));
            hist[bacon]++;
        }

        // print out histogram - even indices are actors
        for (int i = 0; i < MAX_BACON; i += 2) {
            if (hist[i] == 0)
                break;
            StdOut.printf("%3d %8d\n", i / 2, hist[i]);
        }
        StdOut.printf("Inf %8d\n", hist[MAX_BACON]);
    }

    public Stack<Integer> graphPath(String sink) {
        int dst = sg.indexOf(sink);
        Stack<Integer> stack = new Stack<Integer>();
        for (int v : bfs.pathTo(dst)) {
            stack.push(v);
        }
        return stack;
    }

    public SymbolGraph getSymbolGraph() {
        return sg;
    }

    public BreadthFirstPaths getBreadthFirstPaths() {
        return bfs;
    }

    public static void main(String[] args) {
        String filename = args[0];
        String delimiter = args[1];
        String source = args[2];
        // source = source + " " + sink;
        DegreesOfSeparationBFS d = new DegreesOfSeparationBFS(filename, delimiter, source);
        d.printHistogram();
        // System.out.println(d.baconNumber(sink));
        // System.out.println(d.graphPath(sink));
    }
}