import java.util.PriorityQueue;

public class Planejador {
    private PriorityQueue<Event> eventQueue;

    public Planejador() {
        this.eventQueue = new PriorityQueue<>((e1, e2) -> Double.compare(e1.getTempo(), e2.getTempo()));
    }

    public Event nextEvent() {
        return eventQueue.poll();
    }
    
    public void addEvent(Event event) {
        eventQueue.offer(event);
    }
}
