
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QueueNetwork {
    Map<String, Queue> queues;
    private Map<String, ArrayList<Transition>> transitions;

    public QueueNetwork(Map<String, Integer> capacities, Map<String, Integer> servers, Map<String, Double> arrivalRates, Map<String, Double> serviceTimes, Map<String, ArrayList<Transition>> transitions) {
        this.queues = new HashMap<>();
        this.transitions = transitions;

        for (String name : capacities.keySet()) {
            int capacity = capacities.get(name);
            int numServers = servers.get(name);
            double arrivalRate = arrivalRates.get(name);
            double minServiceTime = serviceTimes.get(name);
            double maxServiceTime = serviceTimes.get(name) * 2;

            Queue queue = new Queue(name, capacity, numServers, minServiceTime, maxServiceTime, arrivalRate);
            queues.put(name, queue);
        }
    }

    public Queue getQueue(String name) {
        return queues.get(name);
    }

    public ArrayList<Transition> getTransitions(String source) {
        return transitions.get(source);
    }
}