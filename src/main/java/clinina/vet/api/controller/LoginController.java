package clinina.vet.api.controller;

import clinina.vet.api.funcionario.Funcionario;
import clinina.vet.api.funcionario.FuncionarioDTO;
import clinina.vet.api.funcionario.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @GetMapping
    public FuncionarioDTO login(@RequestParam String usuario, @RequestParam String senha) {

        System.out.println(usuario);
        System.out.println(senha);
        Optional<Funcionario> funcionario = this.funcionarioRepository.findByUsuarioAndSenha(usuario, senha);

        if (funcionario.isPresent()) {
            System.out.println(funcionario.get().getUsuario());
            System.out.println(funcionario.get().getGrupo());
            return new FuncionarioDTO(funcionario.get().getUsuario(), funcionario.get().getGrupo());
        }

        return null;
    }
}
