import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Model model = initialModel();
        TeaProgram program = new TeaProgram(model);
        program.run();
    }

    static class Model {
        List<String> state;
        List<String> choices;
        int cursor;

        Model() {
            state = new ArrayList<>();
            choices = new ArrayList<>();
            choices.add("MicrosoftVB");
            choices.add("Java.Util.Random");
            choices.add("glibc");
            choices.add("numericalRecips");
            cursor = 0;
        }
    }

    static Model initialModel() {
        return new Model();
    }

    static double[] generateNumbers(int nNumbers, String method) {
        switch (method) {
            case "MicrosoftVB":
                return randomGenerator.MicrosoftVB(nNumbers);
            case "Java.Util.Random":
                return randomGenerator.JavaUtilRandom(nNumbers);
            case "glibc":
                return randomGenerator.Glibc(nNumbers);
            case "numericalRecips":
                return randomGenerator.NumericalRecips(nNumbers);
            default:
                return randomGenerator.Glibc(nNumbers);
        }
    }

    static void generateFile(double[] numbers, String title) {
        if (!title.endsWith(".txt")) {
            title += ".txt";
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(title))) {
            for (double number : numbers) {
                writer.write(number + " ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class TeaProgram {
        Model model;

        TeaProgram(Model model) {
            this.model = model;
        }

        void run() {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println(view());
                String input = scanner.nextLine();
                update(input);
                if (input.equals("q")) {
                    break;
                }
            }
            scanner.close();
        }

        String view() {
            StringBuilder sb = new StringBuilder();
            switch (model.state.size()) {
                case 0:
                    sb.append("Qual método devemos usar para gerar os números aleatórios?\n\n");
                    for (int i = 0; i < model.choices.size(); i++) {
                        String choice = model.choices.get(i);
                        String cursor = (model.cursor == i) ? ">" : " ";
                        String checked = (model.state.size() > 0 && model.state.get(0).equals(choice)) ? "x" : " ";
                        sb.append(String.format("%s [%s] %s\n", cursor, checked, choice));
                    }
                    break;
                case 1:
                    sb.append("Quantos números devemos gerar?\n\n");
                    sb.append(String.format("Gerando números pseudo-aleatórios com o método %s", model.state.get(0)));
                    break;
                case 2:
                    sb.append("Qual o nome do arquivo que devemos gerar?\n\n");
                    sb.append(String.format("Gerando %s números pseudo-aleatórios com o método %s", model.state.get(1), model.state.get(0)));
                    break;
            }
            if (model.state.size() > 0) {
                sb.append("\nPressione 'q' para sair, ou 'b' para voltar\n");
            } else {
                sb.append("\nPressione q para sair.\n");
            }
            return sb.toString();
        }

        void update(String input) {
            switch (input) {
                case "ctrl+c":
                case "q":
                    return;
                case "up":
                case "k":
                    if (model.cursor > 0) {
                        model.cursor--;
                    }
                    break;
                case "down":
                case "j":
                    if (model.cursor < model.choices.size() - 1) {
                        model.cursor++;
                    }
                    break;
                case "enter":
                case " ":
                    model.state.add(model.choices.get(model.cursor));
                    break;
                case "b":
                    if (!model.state.isEmpty()) {
                        model.state.remove(model.state.size() - 1);
                    }
                    break;
            }
        }
    }

    // Placeholder for randomGenerator class and its methods
    static class randomGenerator {
        static double[] MicrosoftVB(int nNumbers) {
            // Implement this
            return new double[0];
        }

        static double[] JavaUtilRandom(int nNumbers) {
            // Implement this
            return new double[0];
        }

        static double[] Glibc(int nNumbers) {
            // Implement this
            return new double[0];
        }

        static double[] NumericalRecips(int nNumbers) {
            // Implement this
            return new double[0];
        }
    }
}
