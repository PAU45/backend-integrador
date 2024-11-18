package com.tecsup.backend.Controllers;

import com.tecsup.backend.Model.Usuario;
import com.tecsup.backend.Repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Muestra la página de login
    @GetMapping("/login")
    public String login() {
        return "login"; // Vista del formulario de login
    }

    // Maneja el proceso de autenticación
    @PostMapping("/login")
    public String loginProcess(String username, String password, HttpSession session, Model model) {
        // Buscar usuario en la base de datos
        Usuario usuario = usuarioRepository.findByUsername(username).orElse(null);

        if (usuario != null && usuario.getPassword().equals(password)) {
            // Guardar usuario en la sesión
            session.setAttribute("usuario", usuario);
            return "redirect:/inicio"; // Redirigir al inicio después de login exitoso
        }

        // Si falla el login, mostrar un error
        model.addAttribute("error", "Usuario o contraseña incorrectos");
        return "login"; // Vuelve a la página de login con el error
    }

    // Maneja el cierre de sesión
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Invalida la sesión
        return "redirect:/login"; // Redirigir al login después de cerrar sesión
    }
}
