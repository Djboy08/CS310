package pa0;
// Import here as needed

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LineReport {
	// Variables here
    private LineUsage[] lines;
	// Constructor
	public LineReport() {
        lines = new LineUsage[500];
    }

	// read input data, put facts into lines array
	void loadData(String fname) {
        try{
            File file = new File(fname);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] splitLine = line.split(" ");
                int lineNumber = Integer.parseInt(splitLine[0]);
                String name = splitLine[1];

                if(lines[lineNumber-1] != null){
                    lines[lineNumber-1].addObservation(name);
                }else{
                    LineUsage lineUsage = new LineUsage();
                    lineUsage.addObservation(name);
                    lines[lineNumber-1] = lineUsage;
                }
            }
            scanner.close();
        }catch(FileNotFoundException e){
            System.out.println(e);
        }
    }

	// given loaded lines array, generate report on lines
	void generateReport() {
        String format = String.format("%-6s %-40s %s", "Line", "Most Common User", "Count");
        String f;
        System.out.println(format);
        for(int i=0; i<500; i++){
            if(lines[i] != null){
                LineUsage lineUsage = lines[i];
                Usage maxUsage = lineUsage.findMaxUsage();
                String username = maxUsage.getUser();
                int count = maxUsage.getCount();

                f = String.format("%-6d %-40s %d", i+1, username, count);
            }else{
                f = String.format("%-6d %-40s %d", i+1, "NONE", 0);
            }
            System.out.println(f);
        }
    }

	public static void main(String[] args) {
        String filename = args[0];
        LineReport report = new LineReport();
        report.loadData(filename);
        report.generateReport();
    }
}
