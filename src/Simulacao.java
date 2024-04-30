public class Simulacao {
    public static Queue queue1, queue2;
    public static double prev = 0.5;

    public static double Next_rand() {
        prev = ((51749 * prev) + 28111) % 1299827;
        return (double) prev / 1299827;
    }

    public static double[] Arrival(Event event, Queue queue, Scheduler schedule, double TG, int count) {
        queue.AccumulateTime(event, TG);
        TG = event.GetTime();
        if (queue.GetState() < queue.GetCapacity()) {
            queue.In();
            if (queue.GetState() <= queue.GetServers()) {
                schedule.AddEvent(new Event(1, TG + (queue.minServ + (queue.maxServ - queue.minServ) * Next_rand())));
                count++;
            }
        } else {
            queue.Loss();
        }
        schedule.AddEvent(new Event(0, TG + (queue.minArr + (queue.maxArr - queue.minArr) * Next_rand())));
        double[] result = {count,TG};
        return result;
    }

    public static double[] Departure(Event event, Queue queue, Scheduler schedule, double TG, int count) {
        queue.AccumulateTime(event, TG);
        TG = event.GetTime();
        queue.Out();
        if (queue.GetState() >= queue.GetServers()) {
            schedule.AddEvent(new Event(1, TG + (queue.minServ + (queue.maxServ - queue.minServ) * Next_rand())));
            count++;
        }
        double[] result = {count,TG};
        return result;
    }

    public static void RunSim(Queue queue, int loops) {
        Scheduler schedule = new Scheduler();
        double TG = 0;

        Event event1 = new Event(0, 2);
        schedule.AddEvent(event1);

        int count = 0;
        while (count < loops) {
            Event nextEvent = schedule.NextEvent();
            
            if (nextEvent.GetType() == Event.ARRIVAL) {
                double[] result = Arrival(nextEvent, queue, schedule, TG, count);    
                count = (int)result[0];
                TG = result[1];
            } else if (nextEvent.GetType() == Event.DEPARTURE) {
                double[] result = Departure(nextEvent, queue, schedule, TG, count);
                count = (int)result[0];
                TG = result[1];
            }
        }

        System.out.printf("\nFila G/G/%d/%d", queue.serv, queue.GetCapacity());
        System.out.println("\nEstado\t\tTempo\t\tProbabilidade(%)");

        for (int i = 0; i < queue.times.length; i++) {
            double time = queue.times[i];
            double probability = (time / TG) * 100;

            String output = String.format("%-8d\t%-10.2f\t%.2f%%\n", i, time, probability);
            output = output.replace(',', '.');
            System.out.print(output);
        }

        System.out.println("\nPerdas: " + queue.GetLoss());
        System.out.println("Tempo Global: " + TG);
        System.out.println("\n");
    }

    public static void main(String[] args) {
        queue1 = new Queue(5, 1, 2, 5, 3, 5);
        queue2 = new Queue(5,2,2,5,3,5);
        
        RunSim(queue1, 10000);
        RunSim(queue2, 10000);
    }
}
