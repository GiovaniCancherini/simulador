package simulador;

public class Simulador {
    private double tempoAtual;

    public void chegada(Fila fila) {
        contabilizaTempo();
        if (fila.estaVazia()) {
            fila.adicionaFila(1.0); // Adiciona um elemento qualquer para representar a chegada
            if (fila.getQtdeNaFila() <= fila.getNumeroServidores()) {
                agendaSaida();
            }
        } else {
            // Aqui você pode implementar o comportamento em caso de fila cheia
            // por exemplo, fila.adicionaPerda();
        }
        agendaChegada();
    }

    public void saida(Fila fila) {
        contabilizaTempo();
        fila.removeFila();
    }

    private void contabilizaTempo() {
        tempoAtual += 1.0; // Incrementa o tempo em 1 unidade (aqui, pode ser a unidade de tempo que você estiver utilizando)
    }

    private void agendaChegada() {
        // Aqui você pode implementar a lógica de agendar a próxima chegada
        // por exemplo, você pode calcular o próximo tempo de chegada com base em alguma distribuição
        // e chamar chegada(fila) com esse novo tempo
    }

    private void agendaSaida() {
        // Aqui você pode implementar a lógica de agendar a próxima saída
        // por exemplo, você pode calcular o próximo tempo de saída com base no tempo de atendimento
        // e chamar saida(fila) com esse novo tempo
    }
}
