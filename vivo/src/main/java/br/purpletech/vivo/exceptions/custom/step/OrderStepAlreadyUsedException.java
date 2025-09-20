package br.purpletech.vivo.exceptions.custom.step;
public class OrderStepAlreadyUsedException extends RuntimeException {
    public OrderStepAlreadyUsedException() {
        super("Essa número de ordem de etapa já está sendo usado.");
    }
}
