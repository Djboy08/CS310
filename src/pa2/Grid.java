// Grid.java, for pa3
// originally by Scott Madin
package pa2;

import java.util.*;

/**
 * Class to demonstrate greedy algorithms. Skeleton.
 */
public class Grid {
    private boolean[][] grid = null;
    private ArrayList<Set<Spot>> allGroups; // All groups

    /**
     * Very simple constructor
     * 
     * @param ingrid a two-dimensional array of boolean to be used as the grid to
     *               search
     */
    public Grid(boolean[][] ingrid) {
        grid = ingrid;
        allGroups = new ArrayList<Set<Spot>>();
        allGroups = calcAllGroups();
    }

    /**
     * Main method, creates a Grid, then asks it for the size of the group
     * containing the given point.
     */
    public static void main(String[] args) {
        int arg1 = Integer.parseInt(args[0]);
        int arg2 = Integer.parseInt(args[1]);
        // Check arguments here.

        // Usage: java Grid 3 7 to search from (3, 7), top occupied square
        // of L-shaped group.
        // -all to print all sets.

        boolean[][] gridData = { { false, false, false, false, false, false, false, false, false, true },
                { false, false, false, true, true, false, false, false, false, true },
                { false, false, false, false, false, false, false, false, false, false },
                { false, false, false, false, true, false, false, true, false, false },
                { false, false, false, true, false, false, false, true, false, false },
                { false, false, false, false, false, false, false, true, true, false },
                { false, false, false, false, true, true, false, false, false, false },
                { false, false, false, false, false, false, false, false, false, false },
                { false, false, false, false, false, false, false, false, false, false },
                { false, false, false, false, false, false, false, false, false, false } };
        // Other stuff here.
        Grid g = new Grid(gridData);
        // g.calcAllGroups();
        System.out.println();
        System.out.println(g.groupSize(1, 3));
        System.out.println(g.groupSize(3, 7));
        System.out.println(g.groupSize(4, 3));


    }

    public void printAllGroups() {
        for (Set<Spot> g : allGroups) {
            for (Spot s : g)
                System.out.println(s);
            System.out.println("");
        }
    }


    public ArrayList<Set<Spot>> calcAllGroups() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == true) {
                    Set<Spot> group = new HashSet<Spot>();
                    calcGroup(i, j, group);
                    allGroups.add(group);
                }
            }
        }

        // loop through g
        for (int i = 0; i < allGroups.size(); i++) {
            System.out.println(allGroups.get(i));
            Set<Spot> s = allGroups.get(i);
            // loop through s
            for (Spot spot : s) {
                spot.setGroup(i+1);
                int h = spot.getI();
                int k = spot.getJ();
            }
        }
        return allGroups;
    }


    private void calcGroup(int i, int j, Set<Spot> group) {
        if (i >= 10 || i<0) return;
        if (j >= 10 || j<0) return;
        if (grid[i][j] == false) return;
        grid[i][j] = false;
        group.add(new Spot(i, j));
        calcGroup(i + 1, j, group);
        calcGroup(i - 1, j, group);
        calcGroup(i, j + 1, group);
        calcGroup(i, j - 1, group);
    }

    /**
     * Prints out a usage message
     */
    private static void printUsage() {
        System.out.println("Usage: java Grid <i> <j>");
        System.out.println("Find the size of the cluster of spots including position i,j");
        System.out.println("Usage: java Grid -all");
        System.out.println("Print all spots.");
    }

    /**
     * This calls the recursive method to find the group size
     * 
     * @param i the first index into grid (i.e. the row number)
     * @param j the second index into grid (i.e. the col number)
     * @return the size of the group
     */
    public int groupSize(int i, int j) {
        Spot s = new Spot(i, j);
        // loop through allGroups   
        for (Set<Spot> group : allGroups) {
            // loop through group
            for (Spot spot : group) {
                if (spot.equals(s)) {
                    return group.size();
                }
            }
        }
        return -1;
    }

    /**
     * Nested class to represent a filled spot in the grid
     */
    public static class Spot {
        int i;
        int j;
        int group;

        /**
         * Constructor for a Spot
         * 
         * @param i the i-coordinate of this Spot
         * @param j the j-coordinate of this Spot
         */
        public Spot(int i, int j) {
            this.i = i;
            this.j = j;
            this.group = 0; // Default. Will be set later.
        }

        /**
         * Tests whether this Spot is equal (i.e. has the same coordinates) to another
         * 
         * @param o an Object
         * @return true if o is a Spot with the same coordinates
         */
        public boolean equals(Object o) {
            if (o == null)
                return false;
            if (o.getClass() != getClass())
                return false;
            Spot other = (Spot) o;
            return (other.i == i) && (other.j == j);

        }

        /**
         * Returns an int based on Spot's contents another way: (new
         * Integer(i)).hashCode()^(new Integer(j)).hashCode();
         */
        public int hashCode() {
            return i << 16 + j; // combine i and j two halves of int
        }

        public void setGroup(int g) {
            group = g;
        }

        public int getI() {
            return i;
        }

        public int getJ() {
            return j;
        }

        public int getGroup() {
            return group;
        }

        /**
         * Returns a String representing this Spot, just the coordinates. You can add
         * group if you want.
         */
        public String toString() {
            return "(" + i + " , " + j + ")";
        }
    }

}