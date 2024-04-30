import java.util.PriorityQueue;

public class Scheduler {
    private PriorityQueue<Event> eventQueue;

    public Scheduler() {
        this.eventQueue = new PriorityQueue<>((e1, e2) -> Double.compare(e1.getTime(), e2.getTime()));
    }

    public Event nextEvent() {
        return eventQueue.poll();
    }
    
    public void addEvent(Event event) {
        eventQueue.offer(event);
    }
}
