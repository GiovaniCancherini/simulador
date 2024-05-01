import java.util.Random;

public class Queue {
    public String name;
    public int capacity;
    public int numServers;
    public double minServiceTime;
    public double maxServiceTime;
    public double arrivalRate;
    public int state;
    public int loss;
    public double[] times;
    public Random random;

    public Queue(String name, int capacity, int numServers, double minServiceTime, double maxServiceTime, double arrivalRate) {
        this.name = name;
        this.capacity = capacity;
        this.numServers = numServers;
        this.minServiceTime = minServiceTime;
        this.maxServiceTime = maxServiceTime;
        this.arrivalRate = arrivalRate;
        this.state = 0;
        this.loss = 0;
        this.times = new double[capacity + 1];
        this.random = new Random();
    }

    public void processArrival(double time) {
        accumulateTime(time);
        if (state < capacity) {
            state++;
            if (state <= numServers) {
                double serviceTime = minServiceTime + (maxServiceTime - minServiceTime) * random.nextDouble();
                double departureTime = time + serviceTime;
                Event departureEvent = new Event(Event.DEPARTURE, name, departureTime);
                Scheduler.scheduleEvent(departureEvent);
            }
        } else {
            loss++;
        }
        double interArrivalTime = -Math.log(random.nextDouble()) / arrivalRate;
        double nextArrivalTime = time + interArrivalTime;
        Event arrivalEvent = new Event(Event.ARRIVAL, name, nextArrivalTime);
        Scheduler.scheduleEvent(arrivalEvent);
    }

    public void processDeparture(double time) {
        accumulateTime(time);
        state--;
        if (state >= numServers) {
            double serviceTime = minServiceTime + (maxServiceTime - minServiceTime) * random.nextDouble();
            double departureTime = time + serviceTime;
            Event departureEvent = new Event(Event.DEPARTURE, name, departureTime);
            Scheduler.scheduleEvent(departureEvent);
        }
    }

    public void accumulateTime(double time) {
        times[state] += time - Scheduler.getGlobalTime();
    }

    public String getName() {
        return name;
    }

    public double[] getTimes() {
        return times;
    }

    public int getLoss() {
        return loss;
    }
}