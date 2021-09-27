package pa0;
// One user's record on one line: how many times
// this user has been seen on this line
public class Usage {
	// Put variables here.
    private String username;
    private int count;

	public Usage(String x, int count) {
        this.username = x;
        setCount(count);
	}

	public void setCount(int x) {
        this.count = x;
	}

	public String getUser() {
        return this.username;
	}

	public int getCount() {
        return this.count;
	}
}
