package com.tecsup.backend.Controllers;

import com.tecsup.backend.Model.Categoria;
import com.tecsup.backend.Model.Usuario;
import com.tecsup.backend.Service.PreferenciaService;
import com.tecsup.backend.Service.CategoriaService;
import com.tecsup.backend.Service.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UsuarioController {

    @Autowired
    private PreferenciaService preferenciaService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private SessionManager sessionManager; // Servicio para gestionar la sesión

    // Página de inicio después de iniciar sesión
    @GetMapping("/inicio")
    public String inicio(Model model) {
        // Obtener el usuario autenticado desde tu sistema de sesión
        Usuario usuario = sessionManager.getCurrentUser();

        if (usuario == null) {
            return "redirect:/login"; // Si no está autenticado, redirige al login
        }

        // Obtener las categorías preferidas del usuario
        List<Categoria> categorias = preferenciaService.obtenerPreferenciasDeUsuario(usuario);

        // Si no tiene preferencias, redirige al formulario de preferencias
        if (categorias == null || categorias.isEmpty()) {
            return "redirect:/preferencias";
        }

        // Agregar al modelo las categorías y el usuario
        model.addAttribute("categorias", categorias);
        model.addAttribute("usuario", usuario);

        return "inicio"; // Retorna la vista de inicio
    }

    // Mapeo para mostrar el formulario de preferencias
    // Mapeo para mostrar el formulario de preferencias
    @GetMapping("/preferencias")
    public String mostrarFormularioPreferencias(HttpSession session, Model model) {
        // Obtener el usuario desde la sesión
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            return "redirect:/login"; // Si no está autenticado, redirige al login
        }

        // Obtener todas las categorías disponibles
        List<Categoria> categorias = categoriaService.findAll();
        model.addAttribute("categorias", categorias); // Añadir las categorías al modelo

        return "preferencias"; // Retornar la vista de preferencias
    }

    // Mapeo para guardar las preferencias del usuario
    @PostMapping("/preferencias")
    public String guardarPreferencias(@RequestParam List<Long> categoryIds, HttpSession session) {
        // Obtener el usuario desde la sesión
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            return "redirect:/login"; // Si no está autenticado, redirige al login
        }

        // Guardar las preferencias del usuario
        preferenciaService.guardarPreferencias(usuario, categoryIds);

        return "redirect:/inicio"; // Redirige al inicio después de guardar las preferencias
    }
}