package br.com.cedup.trabalho1.controller.pessoa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.cedup.trabalho1.model.login.LoginDAO;
import br.com.cedup.trabalho1.model.pessoa.PessoaDAO;
import br.com.cedup.trabalho1.model.pessoa.PessoaEntity;
import br.com.cedup.trabalho1.model.pessoa.PessoaRepository;

/**
 * Controller de pessoa alterando a implementação de cada método entre
 * versões com DAO e com respository, visando exemplificar as
 * duas formas.
 */
@RestController
@RequestMapping("/pessoa/")
@CrossOrigin(origins = "*")
public class PessoaController {

    // Injeta a dependencia
    @Autowired
    public LoginDAO loginDAO;

    @Autowired
    public PessoaConverter pessoaConverter;

    @Autowired
    public PessoaDAO pessoaDAO;

    @Autowired
    public PessoaRepository pessoaRepository;

    @GetMapping()
    public ResponseEntity<List<PessoaDTO>> getPessoas(@RequestParam(value = "nome", required = false) String nome, //
                                                      @RequestParam(value = "idademin", required = false) Integer idademin, //
                                                      @RequestParam(value = "idademax", required = false) Integer idademax, //
                                                      @RequestHeader(HttpHeaders.AUTHORIZATION) String header) {
        // Autorização ---
        if (!loginDAO.isTokenValido(header)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        // ---

        /*
        Versão com respository

        Iterable<PessoaEntity> iterable = pessoaRepository.findAll();
        List<PessoaEntity> pessoas = Streamable.of(iterable).toList();
         */

        return ResponseEntity.ok(pessoaConverter.toDTO(pessoaDAO.getAll(nome, idademin, idademax)));
    }

    @GetMapping("byIdade")
    public ResponseEntity<List<PessoaDTO>> getPessoasByIdade(@RequestParam(value = "idade", required = false) Integer idade, //
                                                             @RequestHeader(HttpHeaders.AUTHORIZATION) String header) {
        // Autorização ---
        if (!loginDAO.isTokenValido(header)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        // ---

        List<PessoaEntity> pessoas = pessoaRepository.findByIdadeGreaterThan(idade);

        return ResponseEntity.ok(pessoaConverter.toDTO(pessoas));
    }

    @GetMapping("{id}")
    public ResponseEntity<PessoaDTO> getPessoa(@PathVariable int id, @RequestHeader(HttpHeaders.AUTHORIZATION) String header) {
        // Autorização ---
        if (!loginDAO.isTokenValido(header)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        // ---

        /*
        Versão com DAO

        PessoaEntity entity = pessoaDAO.getById(id);
         */

        Optional<PessoaEntity> entity = pessoaRepository.findById(id);

        if (entity.isEmpty()) {
            return ResponseEntity.notFound().build(); //404
        }

        return ResponseEntity.ok().body(pessoaConverter.toDTO(entity.get()));
        //        return ResponseEntity.status(HttpStatus.OK).body(new PessoaConverter().toDTO(entity));
    }

    @PostMapping()
    public ResponseEntity<PessoaDTO> postPessoa(@RequestBody PessoaDTO dto, @RequestHeader(HttpHeaders.AUTHORIZATION) String header) {
        // Autorização ---
        if (!loginDAO.isTokenValido(header)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        // ---

        PessoaEntity entity = pessoaConverter.toEntity(dto);
        entity = pessoaRepository.save(entity);
        return ResponseEntity.ok().body(pessoaConverter.toDTO(entity));
    }

    @PutMapping("{id}")
    public ResponseEntity<PessoaDTO> putPessoa(@RequestBody PessoaDTO dto, @PathVariable int id, //
                                               @RequestHeader(HttpHeaders.AUTHORIZATION) String header) {
        // Autorização ---
        if (!loginDAO.isTokenValido(header)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        // ---

        PessoaEntity entity = pessoaDAO.update(pessoaConverter.toEntity(dto), id);
        if (entity == null) {
            return ResponseEntity.notFound().build(); //404
        }
        return ResponseEntity.ok().body(pessoaConverter.toDTO(entity));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<PessoaDTO> deletePessoa(@PathVariable int id, @RequestHeader(HttpHeaders.AUTHORIZATION) String header) {
        // Autorização ---
        if (!loginDAO.isTokenValido(header)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        // ---

        PessoaEntity entity = pessoaDAO.delete(id);
        if (entity == null) {
            return ResponseEntity.notFound().build(); //404
        }
        return ResponseEntity.ok().body(pessoaConverter.toDTO(entity));
    }
}
