import java.util.PriorityQueue;

public class Simulacao {
    private static double prev = 0.5;

    private static class Planejador {
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

    private static class Queue {
        private int state;
        private int cap;
        private int maxServ;
        private int minServ;
        private int maxArr;
        private int minArr;
        private int loss;
        private int serv;
        private double[] times;

        public Queue(int cap, int serv, int maxArr, int minArr, int maxServ, int minServ) {
            this.state = 0;
            this.cap = cap;
            this.maxServ = maxServ;
            this.minServ = minServ;
            this.maxArr = maxArr;
            this.minArr = minArr;
            this.loss = 0;
            this.serv = serv;
            this.times = new double[cap + 1];

        }
    }
 
    public static double Next_rand() {
        prev = ((51749 * prev) + 28111) % 1299827;
        return (double) prev / 1299827;
    }

    private static class Event {
        public static final int ARRIVAL = 0;
        public static final int DEPARTURE = 1;

        private int tipo;
        private double tempo;

        public Event(int tipo, double tempo) {
            this.tipo = tipo;
            this.tempo = tempo;
        }
        
        public double getTempo() {
            return tempo;
        }
        
        public int getTipo() {
            return tipo;
        }
    }

    public static double[] Arrival(Event event, Queue queue, Planejador planner, double TG,int count) {
        queue.times[queue.state] += event.getTempo() - TG;
        TG = event.getTempo();

        if (queue.state < queue.cap) {
            queue.state++;
            if (queue.state <= queue.serv) {
                planner.addEvent(new Event(1, TG + (queue.minServ + (queue.maxServ - queue.minServ) * Next_rand())));
                count++;
            }
        } else {
            queue.loss++;
        }
            
        planner.addEvent(new Event(0, TG + (queue.minArr + (queue.maxArr - queue.minArr) * Next_rand())));
        double[] result = {count,TG};
        return result;
    }

    public static double[] Departure(Event event, Queue queue, Planejador planner, double TG,int count) {
        queue.times[queue.state] += event.getTempo() - TG;
        TG = event.getTempo();

        queue.state--;
        if (queue.state >= queue.serv) {
            planner.addEvent(new Event(1, TG + (queue.minServ + (queue.maxServ - queue.minServ) * Next_rand())));
            count++;
        }
        double[] result = {count,TG};

        return result;
    }

    public static void RunSim(Queue queue) {
        Planejador planner = new Planejador();
        double TG = 0;

        Event event1 = new Event(0, 2);
        planner.addEvent(event1);

        int count = 0;
        while (count < 100000) {
            Event nextEvent = planner.nextEvent();
            
            if (nextEvent.getTipo() == Event.ARRIVAL) {
                double[] result = Arrival(nextEvent, queue, planner, TG, count);    
                count = (int)result[0];
                TG = result[1];
            } else if (nextEvent.getTipo() == Event.DEPARTURE) {
                double[] result = Departure(nextEvent, queue, planner, TG, count);
                count = (int)result[0];
                TG = result[1];
            }
        }

        System.out.printf("\nFila G/G/%d/%d", queue.serv, queue.cap);
        System.out.println("\nEstado\t\tTempo\t\tProbabilidade(%)");

        for (int i = 0; i < queue.times.length; i++) {
            double time = queue.times[i];
            double probability = (time / TG) * 100;

            String output = String.format("%-8d\t%-10.2f\t%.2f%%\n", i, time, probability);
            output = output.replace(',', '.');
            System.out.print(output);
        }

        System.out.println("\nPerdas: " + queue.loss);
        System.out.println("Tempo Global: " + TG);
        System.out.println("\n");
    }

    public static void main(String[] args) {

        Queue queue1 = new Queue(5, 1, 2, 5, 3, 5);
        Queue queue2 = new Queue(5,2,2,5,3,5);

        RunSim(queue1);
        RunSim(queue2);
    }
}
