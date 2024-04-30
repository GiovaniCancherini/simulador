import java.util.PriorityQueue;

public class Scheduler {
    private PriorityQueue<Event> eventQueue;

    public Scheduler() {
        this.eventQueue = new PriorityQueue<>((e1, e2) -> Double.compare(e1.GetTime(), e2.GetTime()));
    }

    public Event NextEvent() {
        return eventQueue.poll();
    }
    
    public void AddEvent(Event event) {
        eventQueue.offer(event);
    }
}
