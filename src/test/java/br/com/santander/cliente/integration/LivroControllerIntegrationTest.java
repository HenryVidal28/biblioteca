package br.com.santander.cliente.integration;

import br.com.santander.cliente.entity.Livro;
import br.com.santander.cliente.repository.LivroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class LivroControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private LivroRepository livroRepository;

    @BeforeEach
    public void setUp() {
        Livro livro = Livro.builder()
                .titulo("O Auto da Compadecida")
                .autor("Adriano Suassuana")
                .anoPublicacao(2003)
                .isbn(" 9788522001408")
                .build();

        livroRepository.save(livro);
    }

    @Test
    public void deveListarLivros() {
        String url = "http://localhost:" + port + "/livro";
        ResponseEntity<Livro[]> response = restTemplate.getForEntity(url, Livro[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isGreaterThan(0);
    }

    @Test
    public void deveSalvarUmLivro() {
        Livro novoLivro = Livro.builder()
                .titulo("Clean Code")
                .autor("Robert C. Martin")
                .anoPublicacao(2008)
                .isbn("978-0132350884")
                .build();

        String url = "http://localhost:" + port + "/livro";
        ResponseEntity<Livro> response = restTemplate.postForEntity(url, novoLivro, Livro.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull();
        assertThat(response.getBody().getTitulo()).isEqualTo("Clean Code");
    }

    @Test
    public void deveRemoverUmLivroPorId() {
        Livro livroSalvo = livroRepository.save(
                Livro.builder()
                        .titulo("Refactoring")
                        .autor("Martin Fowler")
                        .anoPublicacao(1999)
                        .isbn("978-0201485677")
                        .build());

        String url = "http://localhost:" + port + "/livro/" + livroSalvo.getId();
        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.DELETE, null, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(livroRepository.findById(livroSalvo.getId())).isEmpty();
    }

    @Test
    public void deveAtualizarUmLivroPorId() {
        Livro livroSalvo = livroRepository.save(
                Livro.builder()
                        .titulo("The Pragmatic Programmer")
                        .autor("Andy Hunt")
                        .anoPublicacao(1999)
                        .isbn("978-0201616224")
                        .build());

        Livro livroAtualizado = Livro.builder()
                .titulo("The Pragmatic Programmer - Updated")
                .autor("Andy Hunt")
                .anoPublicacao(2019)
                .isbn("978-0135957059")
                .build();

        String url = "http://localhost:" + port + "/livro/" + livroSalvo.getId();
        restTemplate.put(url, livroAtualizado);

        Optional<Livro> livroBuscado = livroRepository.findById(livroSalvo.getId());

        assertThat(livroBuscado).isPresent();
        assertThat(livroBuscado.get().getTitulo()).isEqualTo("The Pragmatic Programmer - Updated");
        assertThat(livroBuscado.get().getAnoPublicacao()).isEqualTo(2019);
    }

    @Test
    public void deveBuscarLivroPorId() {
        Livro livroSalvo = livroRepository.save(
                Livro.builder()
                        .titulo("Effective Java")
                        .autor("Joshua Bloch")
                        .anoPublicacao(2017)
                        .isbn("978-0134685991")
                        .build());

        String url = "http://localhost:" + port + "/livro/" + livroSalvo.getId();
        ResponseEntity<Livro> response = restTemplate.getForEntity(url, Livro.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getTitulo()).isEqualTo("Effective Java");
        assertThat(response.getBody().getAutor()).isEqualTo("Joshua Bloch");
    }

    @Test
    public void deveBuscarLivrosPorTitulo() {
        livroRepository.save(
                Livro.builder()
                        .titulo("Design Patterns")
                        .autor("Erich Gamma")
                        .anoPublicacao(1994)
                        .isbn("978-0201633610")
                        .build());

        String url = "http://localhost:" + port + "/livro/buscar?titulo=Design Patterns";
        ResponseEntity<Livro[]> response = restTemplate.getForEntity(url, Livro[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isGreaterThan(0);
        assertThat(response.getBody()[0].getTitulo()).isEqualTo("Design Patterns");
    }

    @Test
    public void deveBuscarLivrosPorAutor() {
        livroRepository.save(
                Livro.builder()
                        .titulo("Clean Architecture")
                        .autor("Robert C. Martin")
                        .anoPublicacao(2017)
                        .isbn("978-0134494166")
                        .build());

        String url = "http://localhost:" + port + "/livro/buscar?autor=Robert C. Martin";
        ResponseEntity<Livro[]> response = restTemplate.getForEntity(url, Livro[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isGreaterThan(0);
        assertThat(response.getBody()[0].getAutor()).isEqualTo("Robert C. Martin");
    }

    @Test
    public void deveBuscarLivrosPorAnoPublicacao() {
        livroRepository.save(
                Livro.builder()
                        .titulo("Code Complete")
                        .autor("Steve McConnell")
                        .anoPublicacao(2004)
                        .isbn("978-0735619678")
                        .build());

        String url = "http://localhost:" + port + "/livro/buscar?anoPublicacao=2004";
        ResponseEntity<Livro[]> response = restTemplate.getForEntity(url, Livro[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isGreaterThan(0);
        assertThat(response.getBody()[0].getAnoPublicacao()).isEqualTo(2004);
    }
}
