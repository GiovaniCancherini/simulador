import java.util.LinkedList;
import java.util.Queue;

public class App {
    static class Cliente {
        int numero;
        double tempoDeSaida;

        Cliente(int numero, double tempoDeSaida) {
            this.numero = numero;
            this.tempoDeSaida = tempoDeSaida;
        }
    }

    // Parâmetros do simulador
    static int a = 1664525;
    static int c = 1013904223;
    static int M = (int) Math.pow(2, 32);
    static int previous = 0;
    static double currentTime = 0.0;
    static double endTime = 100000;
    static Queue<Cliente> queue = new LinkedList<>();
    static int totalPerdidos = 0;
    static int totalChegadas = 0;
    static int maxQueueSize = 5;    // Capacidade máxima ajustada para 5
    static int servers = 2;         // Número de servidores    G/G/1/5 = 1    G/G/2/5 = 2
    static int numeroCliente = 0;
    static double[] estadoTempo = new double[maxQueueSize + 1]; // Array para armazenar o tempo acumulado em cada estado

    // Função para gerar números pseudoaleatórios entre 0 e 1
    public static double nextRandom() {
        previous = (a * previous + c) % M;
        return (double) previous / M;
    }

    // Gera um tempo aleatório dentro do intervalo especificado
    public static double generateTime(double min, double max) {
        return min + (max - min) * nextRandom();
    }

    // Simula a chegada de um cliente
    public static void arrival() {
        if (queue.size() < maxQueueSize) {
            totalChegadas++;
            numeroCliente++;
            double tempoDeSaida = currentTime + generateTime(3, 5);
            queue.add(new Cliente(numeroCliente, tempoDeSaida));
            System.out.println("Cliente " + numeroCliente + " chegou no tempo: " + currentTime);
        } else {
            totalPerdidos++;
            System.out.println("Cliente perdido no tempo: " + currentTime);
        }
    }

    // Simula a saída de um cliente
    public static void departure() {
        if (!queue.isEmpty()) {
            Cliente cliente = queue.poll();
            System.out.println("Cliente " + cliente.numero + " saiu no tempo: " + currentTime);
        }
    }

    public static void main(String[] args) {
        int estadoAtual = 0;
        double ultimoTempoDeMudanca = 0.0;

        while (currentTime < endTime) {
            estadoTempo[estadoAtual] += currentTime - ultimoTempoDeMudanca;
            ultimoTempoDeMudanca = currentTime;

            if (!queue.isEmpty() && queue.peek().tempoDeSaida <= currentTime) {
                estadoAtual = Math.max(0, estadoAtual - 1);
                departure();
            } else {
                estadoAtual = Math.min(maxQueueSize, estadoAtual + 1);
                currentTime += generateTime(2, 5);
                arrival();
            }
        }

        estadoTempo[estadoAtual] += currentTime - ultimoTempoDeMudanca;

        // Cálculo e impressão da distribuição de probabilidades
        System.out.println("Distribuicao de probabilidades:");
        for (int i = 0; i < estadoTempo.length; i++) {
            double probabilidade = estadoTempo[i] / currentTime;
            System.out.println("Estado " + i + ": " + probabilidade);     /*Estado 0 = Fila Vazia
                                                                            Estado 1 = 1 Cliente 
                                                                            Estado 2 = 2 Clientes
                                                                            Estado 3 = 3 Clientes
                                                                            Estado 4 = 4 Clientes
                                                                            Estado 5 = Fila cheia */
        }
        System.out.println("Numero de chegadas: " + totalChegadas);
        System.out.println("Total de clientes perdidos: " + totalPerdidos);
    }
}
