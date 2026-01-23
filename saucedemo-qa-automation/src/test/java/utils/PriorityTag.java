package utils;

import com.aventstack.extentreports.ExtentTest;

public class PriorityTag {



    public static void alta(ExtentTest test) {
        test.assignCategory("PRIORIDADE: ALTA");
        test.info("<span style='color:#d32f2f; font-weight:bold;'>● PRIORIDADE ALTA</span>");
    }

    public static void media(ExtentTest test) {
        test.assignCategory("PRIORIDADE: MÉDIA");
        test.info("<span style='color:#f57c00; font-weight:bold;'>● PRIORIDADE MÉDIA</span>");
    }

    public static void baixa(ExtentTest test) {
        test.assignCategory("PRIORIDADE: BAIXA");
        test.info("<span style='color:#388e3c; font-weight:bold;'>● PRIORIDADE BAIXA</span>");
    }


    public static void cenario(ExtentTest test, String descricao) {
        test.info("<b>Cenário:</b> " + descricao);
    }


    public static void bugDetectado(ExtentTest test, String mensagem) {
        test.warning("<b>DETECÇÃO DE BUG:</b> " + mensagem);
    }
}