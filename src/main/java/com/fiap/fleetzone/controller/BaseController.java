package com.fiap.fleetzone.controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Classe base para controllers com métodos auxiliares comuns
 */
public abstract class BaseController {

    /**
     * Prepara o modelo para formulários de criação
     */
    protected void prepareFormModel(Model model, Object entity, String title, String action) {
        model.addAttribute(getEntityName(), entity);
        model.addAttribute("titulo", title);
        model.addAttribute("action", action);
    }

    /**
     * Prepara o modelo para formulários de edição
     */
    protected void prepareEditFormModel(Model model, Object entity, String title, String action) {
        prepareFormModel(model, entity, title, action);
    }

    /**
     * Adiciona mensagem de sucesso
     */
    protected void addSuccessMessage(RedirectAttributes redirectAttributes, String message) {
        redirectAttributes.addFlashAttribute("successMessage", message);
    }

    /**
     * Adiciona mensagem de erro
     */
    protected void addErrorMessage(RedirectAttributes redirectAttributes, String message) {
        redirectAttributes.addFlashAttribute("errorMessage", message);
    }

    /**
     * Verifica se há erros de validação e prepara o modelo se necessário
     */
    protected boolean hasValidationErrors(BindingResult result, Model model, String title, String action) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", title);
            model.addAttribute("action", action);
            return true;
        }
        return false;
    }

    /**
     * Monta mensagem de sucesso para criação
     */
    protected String buildCreateSuccessMessage(String entityType, String entityName) {
        return entityType + " '" + entityName + "' cadastrado(a) com sucesso!";
    }

    /**
     * Monta mensagem de sucesso para atualização
     */
    protected String buildUpdateSuccessMessage(String entityType, String entityName) {
        return entityType + " '" + entityName + "' atualizado(a) com sucesso!";
    }

    /**
     * Monta mensagem de sucesso para exclusão
     */
    protected String buildDeleteSuccessMessage(String entityType, String entityName) {
        return entityType + " '" + entityName + "' excluído(a) com sucesso!";
    }

    /**
     * Monta mensagem de erro para exclusão com constraint
     */
    protected String buildDeleteErrorMessage(String entityType) {
        return "Erro ao excluir " + entityType.toLowerCase() + ". Verifique se não há registros associados.";
    }

    /**
     * Retorna o nome da entidade para uso no modelo (deve ser implementado pelas subclasses)
     */
    protected abstract String getEntityName();

    /**
     * Retorna o tipo da entidade para mensagens (deve ser implementado pelas subclasses)
     */
    protected abstract String getEntityType();

    /**
     * Retorna a URL de redirecionamento após operações (deve ser implementado pelas subclasses)
     */
    protected abstract String getRedirectUrl();
}