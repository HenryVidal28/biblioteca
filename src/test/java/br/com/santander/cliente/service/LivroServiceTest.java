package br.com.santander.cliente.service;

import br.com.santander.cliente.LivroService;
import br.com.santander.cliente.entity.Livro;
import br.com.santander.cliente.repository.LivroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LivroServiceTest {

    @Mock
    private LivroRepository livroRepository;

    @InjectMocks
    private LivroService livroService;

    private Livro livro1;
    private Livro livro2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        livro1 = new Livro(1L, "Livro A", "Autor A", 2020, "1111");
        livro2 = new Livro(2L, "Livro B", "Autor B", 2021, "22222");
    }

    @Test
    void testSalvar() {
        when(livroRepository.save(livro1)).thenReturn(livro1);
        Livro resultado = livroService.salvar(livro1);
        assertEquals(livro1, resultado);
        verify(livroRepository, times(1)).save(livro1);
    }



    @Test
    void testListaLivro() {
        when(livroRepository.findAll()).thenReturn(Arrays.asList(livro1, livro2));
        List<Livro> livros = livroService.listaLivro();
        assertEquals(2, livros.size());
        assertTrue(livros.contains(livro1));
        assertTrue(livros.contains(livro2));
        verify(livroRepository, times(1)).findAll();
    }

    @Test
    void testBuscarPorId() {
        when(livroRepository.findById(1L)).thenReturn(Optional.of(livro1));
        Optional<Livro> resultado = livroService.bucarPorId(1L);
        assertTrue(resultado.isPresent());
        assertEquals(livro1, resultado.get());
        verify(livroRepository, times(1)).findById(1L);
    }

    @Test
    void testRemoverPorId() {
        doNothing().when(livroRepository).deleteById(1L);
        livroService.removerPorId(1L);
        verify(livroRepository, times(1)).deleteById(1L);
    }

    @Test
    void testBuscarPorTitulo() {
        when(livroRepository.findByTituloContainingIgnoreCase("Livro")).thenReturn(Arrays.asList(livro1, livro2));
        List<Livro> livros = livroService.buscarPorTitulo("Livro");
        assertEquals(2, livros.size());
        verify(livroRepository, times(1)).findByTituloContainingIgnoreCase("Livro");
    }

    @Test
    void testBuscarPorAutor() {
        when(livroRepository.findByAutorContainingIgnoreCase("Autor A")).thenReturn(Arrays.asList(livro1));
        List<Livro> livros = livroService.buscarPorAutor("Autor A");
        assertEquals(1, livros.size());
        assertTrue(livros.contains(livro1));
        verify(livroRepository, times(1)).findByAutorContainingIgnoreCase("Autor A");
    }

    @Test
    void testBuscarPorAnoPublicacao() {
        when(livroRepository.findByAnoPublicacao(2020)).thenReturn(Arrays.asList(livro1));
        List<Livro> livros = livroService.buscarPorAnoPublicacao(2020);
        assertEquals(1, livros.size());
        assertTrue(livros.contains(livro1));
        verify(livroRepository, times(1)).findByAnoPublicacao(2020);
    }
}

