import java.util.PriorityQueue;

public class Scheduler {
    public static double globalTime = 0;
    public static PriorityQueue<Event> eventQueue = new PriorityQueue<>();

    public static void scheduleEvent(Event event) {
        eventQueue.add(event);
    }

    public static Event nextEvent() {
        Event event = eventQueue.poll();
        globalTime = event.getTime();
        return event;
    }

    public static double getGlobalTime() {
        return globalTime;
    }
}
