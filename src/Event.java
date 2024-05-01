public class Event implements Comparable<Event> {
    public static final int ARRIVAL = 0;
    public static final int DEPARTURE = 1;

    public int type;
    public String source;
    public double time;

    public Event(int type, String source, double time) {
        this.type = type;
        this.source = source;
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public String getSource() {
        return source;
    }

    public double getTime() {
        return time;
    }

    @Override
    public int compareTo(Event other) {
        return Double.compare(this.time, other.time);
    }
}
