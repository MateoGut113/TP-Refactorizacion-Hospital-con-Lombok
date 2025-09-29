package org.jcr.entidades;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Objects;

@Setter
@Getter
@ToString
@Builder

public class Matricula implements Serializable {
    private final String numero;

    private Matricula(String numero) {
        this.numero = validarMatricula(numero);
    }

    public static class MatriculaBuilder {
        public Matricula build() {
            return new Matricula(
                    numero
            );
        }
    }
    private String validarMatricula(String numero) {
        Objects.requireNonNull(numero, "El número de matrícula no puede ser nulo");
        if (!numero.matches("MP-\\d{4,6}")) {
            throw new IllegalArgumentException("Formato de matrícula inválido. Debe ser como MP-12345");
        }
        return numero;
    }

}
