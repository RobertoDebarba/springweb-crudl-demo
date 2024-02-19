package br.com.cedup.trabalho1.controller.login;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cedup.trabalho1.model.login.LoginDAO;
import br.com.cedup.trabalho1.model.login.LoginEntity;
import br.com.cedup.trabalho1.model.usuario.UsuarioDAO;
import br.com.cedup.trabalho1.model.usuario.UsuarioEntity;

@RestController
@RequestMapping("/login/")
public class LoginController {

    @Autowired
    public LoginDAO loginDAO;

    @Autowired
    public UsuarioDAO usuarioDAO;

    @PostMapping
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> login(@RequestBody LoginDTO dto) {
        //Verificar se o usuário existe
        UsuarioEntity usuarioExiste = usuarioDAO.getByUsuario(dto.usuario, dto.senha);

        if (usuarioExiste != null) {
            //Gerar o token
            final String token = UUID.randomUUID().toString();

            //Gravar o token no banco
            LoginEntity loginEntity = new LoginEntity();
            loginEntity.token = token;
            loginEntity.usuario = usuarioExiste;

            loginDAO.add(loginEntity);

            //retornar o token
            return ResponseEntity.ok(token);
        } else {
            //retornar 401
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
