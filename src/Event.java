public class Event {
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
