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
}
