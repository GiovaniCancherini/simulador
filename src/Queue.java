public class Queue {
    int state;
    int cap;
    int maxServ;
    int minServ;
    int maxArr;
    int minArr;
    int loss;
    int serv;
    double[] times;

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

    public int getState() { return state; }
    public int getCapacity() { return cap; }
    public int getServers() { return serv; }
    public int getLoss() { return loss; }
    public void loss() { loss++; }
    public void in() { state++; }
    public void out() { state--; }
    public void accumulateTime(Event event, double TG) { 
        this.times[this.getState()] += event.getTime() - TG;
    }
}
