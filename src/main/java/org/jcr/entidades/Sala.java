package org.jcr.entidades;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
@ToString (exclude = "citas")
@Builder

public class Sala implements Serializable {
    private final String numero;
    private final String tipo;
    private final Departamento departamento;
    @Builder.Default
    private final List<Cita> citas = new ArrayList<>();

    private Sala(String numero, String tipo, Departamento departamento) {
        this.numero = validarString(numero, "El número de sala no puede ser nulo ni vacío");
        this.tipo = validarString(tipo, "El tipo de sala no puede ser nulo ni vacío");
        this.departamento = Objects.requireNonNull(departamento, "El departamento no puede ser nulo");
        this.citas = new ArrayList<>();
    }

    public static class SalaBuilder {
        public Sala build() {
            return new Sala(
                    numero,
                    tipo,
                    departamento
            );
        }
    }


    public void addCita(Cita cita) {
        this.citas.add(cita);
    }

    //Get personalizado, se mantiene
    public List<Cita> getCitas() {
        return Collections.unmodifiableList(new ArrayList<>(citas));
    }

    private String validarString(String valor, String mensajeError) {
        Objects.requireNonNull(valor, mensajeError);
        if (valor.trim().isEmpty()) {
            throw new IllegalArgumentException(mensajeError);
        }
        return valor;
    }

}
