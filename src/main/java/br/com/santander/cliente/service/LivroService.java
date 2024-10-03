package br.com.santander.cliente.service;

import br.com.santander.cliente.entity.Livro;
import br.com.santander.cliente.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    public Livro salvar(Livro livro) {
        return livroRepository.save(livro);
    }

    public List<Livro> listaLivro() {
        return livroRepository.findAll();
    }

    public void removerPorId(Long id) {
        livroRepository.deleteById(id);
    }

    public Optional<Livro> bucarPorId(Long id) {
        return livroRepository.findById(id);
    }

    public List<Livro> buscarPorTitulo(String titulo) {
        return livroRepository.findByTituloContainingIgnoreCase(titulo);
    }

    public List<Livro> buscarPorAutor(String autor) {
        return livroRepository.findByAutorContainingIgnoreCase(autor);
    }

    public List<Livro> buscarPorAnoPublicacao(int anoPublicacao) {
        return livroRepository.findByAnoPublicacao(anoPublicacao);
    }
}
