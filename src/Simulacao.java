import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Simulacao {
    public static void runSimulation(QueueNetwork network, int loops) {
        Scheduler eventScheduler = new Scheduler();

        for (String name : network.queues.keySet()) {
            Queue queue = network.getQueue(name);
            double interArrivalTime = -Math.log(network.getQueue(name).arrivalRate) / network.getQueue(name).arrivalRate;
            double nextArrivalTime = interArrivalTime;
            Event arrivalEvent = new Event(Event.ARRIVAL, name, nextArrivalTime);
            eventScheduler.scheduleEvent(arrivalEvent);
        }

        for (int i = 0; i < loops; i++) {
            Event nextEvent = eventScheduler.nextEvent();
            if (nextEvent.getType() == Event.ARRIVAL) {
                Queue sourceQueue = network.getQueue(nextEvent.getSource());
                sourceQueue.processArrival(nextEvent.getTime());
                ArrayList<Transition> transitions = network.getTransitions(nextEvent.getSource());
                double randomNumber = Math.random();
                double sum = 0;
                for (Transition transition : transitions) {
                    sum += transition.getProbability();
                    if (randomNumber < sum) {
                        String target = transition.getTarget();
                        Queue targetQueue = network.getQueue(target);
                        double serviceTime = targetQueue.minServiceTime + (targetQueue.maxServiceTime - targetQueue.minServiceTime) * Math.random();
                        double departureTime = nextEvent.getTime() + serviceTime;
                        Event departureEvent = new Event(Event.DEPARTURE, target, departureTime);
                        eventScheduler.scheduleEvent(departureEvent);
                        break;
                    }
                }
            } else if (nextEvent.getType() == Event.DEPARTURE) {
                Queue sourceQueue = network.getQueue(nextEvent.getSource());
                sourceQueue.processDeparture(nextEvent.getTime());
            }
        }

        System.out.println("Resultados da Simulação:\n");
        for (String name : network.queues.keySet()) {
            Queue queue = network.getQueue(name);
            System.out.printf("Fila %s:\n", name);
            System.out.println("Estado\t\tTempo\t\tProbabilidade(%)");
            double total = 0;
            for (int j = 0; j <= queue.capacity; j++) {
                total += queue.times[j];
            }
            for (int j = 0; j <= queue.capacity; j++) {
                double time = queue.times[j];
                double probability = (time / total) * 100;
                System.out.printf("%-8d\t%-10.2f\t%.2f%%\n", j, time, probability);
            }
            System.out.println("Perdas: " + queue.getLoss());
            System.out.println("Tempo Global: " + Scheduler.getGlobalTime());
            System.out.println();
        }
    }

    public static void main(String[] args) {
        // Dados de entrada
        Map<String, Integer> capacities = new HashMap<>();
        capacities.put("TRIAGEM", 10);
        capacities.put("CONSULTORIOS", 15);
        capacities.put("LABORATORIO", 20);

        Map<String, Integer> servers = new HashMap<>();
        servers.put("TRIAGEM", 2);
        servers.put("CONSULTORIOS", 3);
        servers.put("LABORATORIO", 2);

        Map<String, Double> arrivalRates = new HashMap<>();
        arrivalRates.put("TRIAGEM", 5.0);
        arrivalRates.put("CONSULTORIOS", 2.0);
        arrivalRates.put("LABORATORIO", 3.0);

        Map<String, Double> serviceTimes = new HashMap<>();
        serviceTimes.put("TRIAGEM", 5.0);
        serviceTimes.put("CONSULTORIOS", 10.0);
        serviceTimes.put("LABORATORIO", 8.0);

        ArrayList<Transition> transitionsTRIAGEM = new ArrayList<>();
        transitionsTRIAGEM.add(new Transition("CONSULTORIOS", 0.7));
        transitionsTRIAGEM.add(new Transition("LABORATORIO", 0.2));

        ArrayList<Transition> transitionsCONSULTORIOS = new ArrayList<>();
        transitionsCONSULTORIOS.add(new Transition("TRIAGEM", 0.5));
        transitionsCONSULTORIOS.add(new Transition("LABORATORIO", 0.3));

        ArrayList<Transition> transitionsLABORATORIO = new ArrayList<>();
        transitionsLABORATORIO.add(new Transition("TRIAGEM", 0.2));

        Map<String, ArrayList<Transition>> transitions = new HashMap<>();
        transitions.put("TRIAGEM", transitionsTRIAGEM);
        transitions.put("CONSULTORIOS", transitionsCONSULTORIOS);
        transitions.put("LABORATORIO", transitionsLABORATORIO);

        QueueNetwork network = new QueueNetwork(capacities, servers, arrivalRates, serviceTimes, transitions);

        runSimulation(network, 10000);
    }
}
