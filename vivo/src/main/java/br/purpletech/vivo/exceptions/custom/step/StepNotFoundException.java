package br.purpletech.vivo.exceptions.custom.step;

public class StepNotFoundException extends RuntimeException {
    public StepNotFoundException() {
        super("Etapa não encontrada.");
    }
}
