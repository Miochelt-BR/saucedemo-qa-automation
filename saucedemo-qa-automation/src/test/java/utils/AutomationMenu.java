package utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class AutomationMenu {

    private static final String RESET = "\u001B[0m";
    private static final String BOLD = "\u001B[1m";
    private static final String CYAN = "\u001B[36m";
    private static final String WHITE = "\u001B[37m";
    private static final String BRIGHT_CYAN = "\u001B[96m";

    public static void exibirBoasVindas() {
        String dataHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy | HH:mm:ss"));
        String user = buscarNomeCompleto();

        // Moldura Superior
        System.out.println("\n" + CYAN + "┌" + "─".repeat(55) + "┐" + RESET);

        // Cabeçalho Centralizado
        System.out.println(CYAN + "│" + RESET + BOLD + BRIGHT_CYAN + "    AUTOMATION FRAMEWORK " + RESET + "::" + WHITE + " SAUCEDEMO V1.0   " + RESET + CYAN + "│" + RESET);

        System.out.println(CYAN + "├" + "─".repeat(55) + "┤" + RESET);


        imprimirLinha("USUÁRIO", user);
        imprimirLinha("DATA/HORA", dataHora);
        imprimirLinha("SISTEMA", System.getProperty("os.name"));
        imprimirLinha("ENGINE", "Selenium 4 + Java 21");

        System.out.println(CYAN + "├" + "─".repeat(55) + "┤" + RESET);


        System.out.print(WHITE + "  Initializing neural modules" + RESET);
        animarPontos();

        System.out.println(CYAN + "  ❯ " + RESET + WHITE + "Driver Status: " + RESET + BRIGHT_CYAN + "ONLINE" + RESET);
        System.out.println(CYAN + "  ❯ " + RESET + WHITE + "Environment:   " + RESET + BRIGHT_CYAN + "READY" + RESET);

        System.out.println(CYAN + "└" + "─".repeat(55) + "┘" + RESET);

        System.out.println(BOLD + BRIGHT_CYAN + "  🚀 INICIANDO TESTES AGORA..." + RESET + "\n");
    }

    private static String buscarNomeCompleto() {
        String userDefault = System.getProperty("user.name").toUpperCase();
        try {

            Process process = Runtime.getRuntime().exec("net user " + System.getProperty("user.name"));
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {

                if (line.contains("Nome completo") || line.contains("Full Name")) {
                    String[] partes = line.split("  +");
                    if (partes.length > 1) {
                        return partes[1].trim().toUpperCase();
                    }
                }
            }
        } catch (Exception e) {

            return userDefault;
        }
        return userDefault;
    }

    private static void imprimirLinha(String label, String valor) {

        System.out.printf(CYAN + "│" + RESET + WHITE + "  %-12s " + RESET + CYAN + "❯ " + RESET + "%-37s " + CYAN + "│\n" + RESET, label, valor);
    }

    private static void animarPontos() {
        try {
            for(int i = 0; i < 3; i++) {
                System.out.print(BRIGHT_CYAN + "." + RESET);
                Thread.sleep(250);
            }
            System.out.println();
        } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
}