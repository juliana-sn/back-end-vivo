package br.purpletech.vivo.exceptions.handler;

import br.purpletech.vivo.exceptions.custom.auth.InvalidCredentialsException;
import br.purpletech.vivo.exceptions.custom.auth.InvalidTokenException;
import br.purpletech.vivo.exceptions.custom.auth.TokenExpiredException;
import br.purpletech.vivo.exceptions.custom.auth.UnauthorizedAccessException;
import br.purpletech.vivo.exceptions.custom.onboarding.OnboardingNotFoundException;
import br.purpletech.vivo.exceptions.custom.platform.PlatformNameAlreadyUsedException;
import br.purpletech.vivo.exceptions.custom.platform.PlatformNotFoundException;
import br.purpletech.vivo.exceptions.custom.step.OrderStepAlreadyUsedException;
import br.purpletech.vivo.exceptions.custom.step.StepNotFoundException;
import br.purpletech.vivo.exceptions.custom.task.TaskNotFoundException;
import br.purpletech.vivo.exceptions.custom.team.TeamNameAlreadyUsedException;
import br.purpletech.vivo.exceptions.custom.team.TeamNotFoundException;
import br.purpletech.vivo.exceptions.custom.user.*;
import br.purpletech.vivo.exceptions.model.StandardErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            UserNotFoundException.class,
            ManagerNotFoundException.class,
            BuddyNotFoundException.class,
            ChatNotFoundException.class,
            TeamNotFoundException.class,
            TaskNotFoundException.class,
            StepNotFoundException.class,
            PlatformNotFoundException.class,
            OnboardingNotFoundException.class
    })
    public ResponseEntity<StandardErrorResponse> handleNotFound(RuntimeException ex, HttpServletRequest request) {
        return buildError(HttpStatus.NOT_FOUND, ex, request);
    }

    @ExceptionHandler({
            EmailAlreadyUsedException.class,
            TeamNameAlreadyUsedException.class,
            OrderStepAlreadyUsedException.class,
            PlatformNameAlreadyUsedException.class
    })
    public ResponseEntity<StandardErrorResponse> handleConflict(RuntimeException ex, HttpServletRequest request) {
        return buildError(HttpStatus.CONFLICT, ex, request);
    }

    @ExceptionHandler({
            InvalidCredentialsException.class,
            TokenExpiredException.class,
            InvalidTokenException.class
    })
    public ResponseEntity<StandardErrorResponse> handleUnauthorized(RuntimeException ex, HttpServletRequest request) {
        return buildError(HttpStatus.UNAUTHORIZED, ex, request);
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<StandardErrorResponse> handleForbidden(UnauthorizedAccessException ex, HttpServletRequest request) {
        return buildError(HttpStatus.FORBIDDEN, ex, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardErrorResponse> handleGeneric(Exception ex, HttpServletRequest request) {
        return buildError(HttpStatus.INTERNAL_SERVER_ERROR, ex, request);
    }

    private ResponseEntity<StandardErrorResponse> buildError(HttpStatus status, Exception ex, HttpServletRequest request) {
        var body = new StandardErrorResponse(
                status.value(),
                ex.getMessage(),
                request.getRequestURI(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(body, status);
    }
}

