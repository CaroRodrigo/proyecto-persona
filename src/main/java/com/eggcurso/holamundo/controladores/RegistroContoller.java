
package com.eggcurso.holamundo.controladores;

import com.eggcurso.holamundo.excepciones.WebException;
import com.eggcurso.holamundo.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/registro")
public class RegistroContoller {
    
    @Autowired
    private UsuarioServicio usuarioServicio;
    
    @GetMapping("")
    public String registro(){
        return "registro";
    }
    
    @PostMapping("")
    public String registrosave(Model model,@RequestParam String username, 
                               @RequestParam String password, @RequestParam String password2 
                               , @RequestParam String dni){
        try {
            usuarioServicio.save(username, password, password2, dni);
            return "redirect:/";
        } catch (WebException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("username", username);
            return "registro";
        }
    }
}
