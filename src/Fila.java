package simulador;

import java.util.ArrayList;
import java.util.List;

public class Fila {
    private int tempoChegada;
    private int tempoAtendimento;
    private int numeroServidores;
    private int capacidade;
    private int qtdeNaFila;
    private List<Float> fila;

    public Fila(int tempoChegada, int tempoAtendimento, int numeroServidores, int capacidade) {
        this.tempoChegada = tempoChegada;
        this.tempoAtendimento = tempoAtendimento;
        this.numeroServidores = numeroServidores;
        this.capacidade = capacidade;
        this.fila = new ArrayList<>(capacidade);
        this.qtdeNaFila = 0;
    }

    public boolean estaVazia() {
        return qtdeNaFila == 0;
    }

    public boolean estaCheia() {
        return qtdeNaFila == capacidade;
    }

    public void adicionaFila(float elemento) {
        if (!estaCheia()) {
            fila.add(elemento);
            qtdeNaFila++;
        } else {
            System.out.println("A fila está cheia. Não é possível adicionar mais elementos.");
        }
    }

    public void removeFila() {
        if (!estaVazia()) {
            fila.remove(0);
            qtdeNaFila--;
        } else {
            System.out.println("A fila está vazia. Não é possível remover elementos.");
        }
    }
}
