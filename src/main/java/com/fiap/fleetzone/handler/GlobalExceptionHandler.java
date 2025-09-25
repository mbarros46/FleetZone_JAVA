
package com.fiap.fleetzone.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ModelAndView handleDataIntegrityViolation(DataIntegrityViolationException ex, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        logger.error("Erro de integridade de dados: {}", ex.getMessage(), ex);
        
        String userMessage = determineUserFriendlyMessage(ex);
        String redirectUrl = determineRedirectUrl(request);
        
        redirectAttributes.addFlashAttribute("errorMessage", userMessage);
        
        return new ModelAndView("redirect:" + redirectUrl);
    }

    @ExceptionHandler(SQLException.class)
    public ModelAndView handleSQLException(SQLException ex, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        logger.error("Erro SQL: {}", ex.getMessage(), ex);
        
        String userMessage = "Ocorreu um erro no banco de dados. Tente novamente em alguns instantes.";
        String redirectUrl = determineRedirectUrl(request);
        
        redirectAttributes.addFlashAttribute("errorMessage", userMessage);
        
        return new ModelAndView("redirect:" + redirectUrl);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneric(Exception ex) {
        logger.error("Erro não tratado: {}", ex.getMessage(), ex);
        Map<String, String> error = new HashMap<>();
        error.put("mensagem", "Erro inesperado: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    private String determineUserFriendlyMessage(DataIntegrityViolationException ex) {
        String message = ex.getMessage().toLowerCase();
        String rootCauseMessage = "";
        
        if (ex.getRootCause() != null) {
            rootCauseMessage = ex.getRootCause().getMessage().toLowerCase();
        }
        
        // Violações de chave estrangeira
        if (message.contains("foreign key") || rootCauseMessage.contains("foreign key") ||
            message.contains("constraint") && (message.contains("fk_") || rootCauseMessage.contains("fk_"))) {
            
            if (message.contains("moto") || rootCauseMessage.contains("moto")) {
                return "Não é possível excluir este item pois existem motos associadas a ele.";
            } else if (message.contains("patio") || rootCauseMessage.contains("patio")) {
                return "Não é possível excluir este item pois existem pátios associados a ele.";
            } else if (message.contains("filial") || rootCauseMessage.contains("filial")) {
                return "Não é possível excluir este item pois existem filiais associadas a ele.";
            }
            return "Não é possível excluir este item pois existem outros registros dependentes dele.";
        }
        
        // Violações de chave única
        if (message.contains("unique") || rootCauseMessage.contains("unique") ||
            message.contains("duplicate") || rootCauseMessage.contains("duplicate")) {
            
            if (message.contains("placa") || rootCauseMessage.contains("placa")) {
                return "Já existe uma moto cadastrada com esta placa.";
            } else if (message.contains("email") || rootCauseMessage.contains("email")) {
                return "Este e-mail já está sendo utilizado por outro usuário.";
            } else if (message.contains("nome") || rootCauseMessage.contains("nome")) {
                return "Já existe um registro com este nome.";
            }
            return "Os dados informados já estão sendo utilizados por outro registro.";
        }
        
        // Violações de not null
        if (message.contains("not null") || rootCauseMessage.contains("not null")) {
            return "Todos os campos obrigatórios devem ser preenchidos.";
        }
        
        return "Ocorreu um erro ao processar os dados. Verifique as informações e tente novamente.";
    }

    private String determineRedirectUrl(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        
        if (requestURI.contains("/motos")) {
            return "/motos";
        } else if (requestURI.contains("/patios")) {
            return "/patios";
        } else if (requestURI.contains("/filiais")) {
            return "/filiais";
        } else if (requestURI.contains("/relatorios")) {
            return "/relatorios/motos-por-patio";
        } else if (requestURI.contains("/operacoes")) {
            return "/operacoes/transferir-moto";
        }
        
        return "/";
    }
}
