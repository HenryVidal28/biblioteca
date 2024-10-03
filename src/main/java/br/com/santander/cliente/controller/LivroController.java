package br.com.santander.cliente.controller;

import br.com.santander.cliente.entity.Livro;
import br.com.santander.cliente.LivroService;
import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/livro")
public class LivroController {

    @Autowired
    private LivroService livroService;

    @Autowired
    private ModelMapper modelMapper;

    @Operation(description = "Salva um livro exigindo o preenchimento de todos os campos")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Livro salvar(@RequestBody Livro livro) {
        return livroService.salvar(livro);
    }

    @Operation(description = "Lista todos os livros cadastrados")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Livro> listaLivro() {
        return livroService.listaLivro();
    }

    @Operation(description = "Remove um livro cadastrado passando um ID")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerLivro(@PathVariable("id") Long id) {
        livroService.bucarPorId(id)
                .map(livro -> {
                    livroService.removerPorId(livro.getId());
                    return Void.TYPE;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado"));
    }

    @Operation(description = "Atualiza um livro passando seu ID e montando um body com o(s) campos atualizados")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarLivro(@PathVariable("id") Long id, @RequestBody Livro livro) {
        livroService.bucarPorId(id)
                .map(livroBase -> {
                    modelMapper.map(livro, livroBase);
                    livroService.salvar(livroBase);
                    return Void.TYPE;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado"));
    }

    @Operation(description = "Busca um livro por seu ID")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Livro buscarLivroPorId(@PathVariable("id") Long id) {
        return livroService.bucarPorId(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado"));
    }

    @Operation(description = "Busca um livro por título, autor ou ano de publicação")
    @GetMapping("/buscar")
    @ResponseStatus(HttpStatus.OK)
    public List<Livro> buscarLivro(@RequestParam(required = false) String titulo, @RequestParam(required = false) String autor, @RequestParam(required = false) Integer anoPublicacao) {

        if (titulo != null) {
            return livroService.buscarPorTitulo(titulo);
        } else if (autor != null) {
            return livroService.buscarPorAutor(autor);
        } else if (anoPublicacao != null) {
            return livroService.buscarPorAnoPublicacao(anoPublicacao);
        } else {
            return livroService.listaLivro();
        }
    }
}
