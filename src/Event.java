public class Event {
    public static final int ARRIVAL = 0;
    public static final int DEPARTURE = 1;

    private int type;
    private double time;

    public Event(int type, double time) {
        this.type = type;
        this.time = time;
    }
    
    public double GetTime() {
        return time;
    }
    
    public int GetType() {
        return type;
    }
}
