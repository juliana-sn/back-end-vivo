package br.purpletech.vivo.exceptions.custom.step;

public class StepTaskNotFoundException extends RuntimeException {
    public StepTaskNotFoundException() {
        super("Tarefa associada à etapa não foi encontrada.");
    }
}

