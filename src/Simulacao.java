public class Simulacao {
    private static double prev = 0.5;

    public static double Next_rand() {
        prev = ((51749 * prev) + 28111) % 1299827;
        return (double) prev / 1299827;
    }

    public static double[] Arrival(Event event, Queue queue, Scheduler schedule, double TG, int count) {
        queue.times[queue.getState()] += event.getTime() - TG;
        TG = event.getTime();

        if (queue.getState() < queue.getCapacity()) {
            queue.in();
            if (queue.getState() <= queue.getServers()) {
                schedule.addEvent(new Event(1, TG + (queue.minServ + (queue.maxServ - queue.minServ) * Next_rand())));
                count++;
            }
        } else {
            queue.loss();
        }
            
        schedule.addEvent(new Event(0, TG + (queue.minArr + (queue.maxArr - queue.minArr) * Next_rand())));
        double[] result = {count,TG};
        return result;
    }

    public static double[] Departure(Event event, Queue queue, Scheduler schedule, double TG, int count) {
        queue.times[queue.getState()] += event.getTime() - TG;
        TG = event.getTime();

        queue.out();
        if (queue.getState() >= queue.getServers()) {
            schedule.addEvent(new Event(1, TG + (queue.minServ + (queue.maxServ - queue.minServ) * Next_rand())));
            count++;
        }
        double[] result = {count,TG};

        return result;
    }

    public static void RunSim(Queue queue) {
        Scheduler schedule = new Scheduler();
        double TG = 0;

        Event event1 = new Event(0, 2);
        schedule.addEvent(event1);

        int count = 0;
        while (count < 100000) {
            Event nextEvent = schedule.nextEvent();
            
            if (nextEvent.getType() == Event.ARRIVAL) {
                double[] result = Arrival(nextEvent, queue, schedule, TG, count);    
                count = (int)result[0];
                TG = result[1];
            } else if (nextEvent.getType() == Event.DEPARTURE) {
                double[] result = Departure(nextEvent, queue, schedule, TG, count);
                count = (int)result[0];
                TG = result[1];
            }
        }

        System.out.printf("\nFila G/G/%d/%d", queue.serv, queue.getCapacity());
        System.out.println("\nEstado\t\tTempo\t\tProbabilidade(%)");

        for (int i = 0; i < queue.times.length; i++) {
            double time = queue.times[i];
            double probability = (time / TG) * 100;

            String output = String.format("%-8d\t%-10.2f\t%.2f%%\n", i, time, probability);
            output = output.replace(',', '.');
            System.out.print(output);
        }

        System.out.println("\nPerdas: " + queue.getLoss());
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
