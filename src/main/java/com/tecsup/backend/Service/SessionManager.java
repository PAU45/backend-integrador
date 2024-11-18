package com.tecsup.backend.Service;

import com.tecsup.backend.Model.Usuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionManager {

    @Autowired
    private HttpSession session;

    // Método para obtener el usuario actual de la sesión
    public Usuario getCurrentUser() {
        return (Usuario) session.getAttribute("usuario");
    }

    // Método para establecer el usuario en la sesión
    public void setCurrentUser(Usuario usuario) {
        session.setAttribute("usuario", usuario);
    }

    // Método para eliminar el usuario de la sesión (cerrar sesión)
    public void removeCurrentUser() {
        session.removeAttribute("usuario");
    }
}
