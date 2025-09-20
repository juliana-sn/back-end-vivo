package br.purpletech.vivo.exceptions.custom.onboarding;

public class OnboardingNotFoundException extends RuntimeException {
    public OnboardingNotFoundException() {
        super("Onboarding não encontrado.");
    }
}

