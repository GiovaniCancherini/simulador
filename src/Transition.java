public class Transition {
    public String target;
    public double probability;

    public Transition(String target, double probability) {
        this.target = target;
        this.probability = probability;
    }

    public String getTarget() {
        return target;
    }

    public double getProbability() {
        return probability;
    }
}