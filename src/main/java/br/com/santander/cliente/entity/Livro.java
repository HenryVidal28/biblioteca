package br.com.santander.cliente.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Livro implements Serializable {

    @Id
    @GeneratedValue
    private Long  id;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name="autor", nullable = false)
    private String autor;

    @Column(name="anoPublicacao", nullable = false)
    private int anoPublicacao;

    @Column(name="isbn", nullable = false)
    private String isbn;
}
