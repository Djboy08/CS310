package pa0;
import java.util.HashMap;
// Import packages as needed

// LineUsageData.java: Handle one line's data, using a Map
public class LineUsage {
	// Variables here
    private HashMap<String, Integer> lines;
    private String maxUsageUser;
    private int maxUsageCount;
	// Constructor
	public LineUsage() {
        lines = new HashMap<String, Integer>();
        maxUsageUser = "";
        maxUsageCount = 0;
	}

	// add one sighting of a user on this line
	public void addObservation(String username) {
        if(lines.containsKey(username)){
            lines.put(username, lines.get(username)+1);
            if(lines.get(username) > maxUsageCount){
                maxUsageCount = lines.get(username);
                maxUsageUser = username;
            }
        }else{
            lines.put(username, 1);
            if(maxUsageCount == 0){
                maxUsageCount = 1;
                maxUsageUser = username;
            }
        }
	}

	// find the user with the most sightings on this line
	public Usage findMaxUsage() {
        // int sightings = 0;
        // String user = "";
        // for(String username : lines.keySet()){
        //     if(lines.get(username) > sightings){
        //         sightings = lines.get(username);
        //         user = username;
        //     }
        // }
        String user = this.maxUsageUser;
        int sightings = this.maxUsageCount;
        return new Usage(user, sightings);
	}
}
