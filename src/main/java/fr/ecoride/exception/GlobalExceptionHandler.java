package fr.ecoride.exception;

import fr.ecoride.config.security.UtilisateurDetails;
import fr.ecoride.service.ILogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final ILogService logService;

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFound(NotFoundException e, @AuthenticationPrincipal UtilisateurDetails userDetails) {
        String pseudo = (userDetails != null && userDetails.getUtilisateur() != null)
                ? userDetails.getUtilisateur().getPseudo()
                : "ANONYME";
        logService.enregistrer("ERROR", e.getMessage(), pseudo);
        return buildResponse(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusiness(BusinessException e, @AuthenticationPrincipal UtilisateurDetails userDetails) {
        String pseudo = (userDetails != null && userDetails.getUtilisateur() != null)
                ? userDetails.getUtilisateur().getPseudo()
                : "ANONYME";
        logService.enregistrer("ERROR", e.getMessage(), pseudo);
        return buildResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgument(IllegalArgumentException e,@AuthenticationPrincipal UtilisateurDetails userDetails) {
        String pseudo = (userDetails != null && userDetails.getUtilisateur() != null)
                ? userDetails.getUtilisateur().getPseudo()
                : "ANONYME";
        logService.enregistrer("ERROR", e.getMessage(), pseudo);
        return buildResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneric(Exception e, @AuthenticationPrincipal UtilisateurDetails userDetails) {
        String pseudo = (userDetails != null && userDetails.getUtilisateur() != null)
                ? userDetails.getUtilisateur().getPseudo()
                : "ANONYME";
        logService.enregistrer("ERROR", e.getMessage(), pseudo);
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    private ResponseEntity<?> buildResponse(HttpStatus status, String message) {
        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now());
        error.put("message", message);
        return ResponseEntity.status(status).body(error);
    }
}
