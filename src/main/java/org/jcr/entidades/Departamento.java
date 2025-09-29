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

//@Setter --> No recomendable, variables 'final' y uso de set personalizado
@Getter
@ToString (onlyExplicitlyIncluded = true)
@Builder

public class Departamento implements Serializable {
    @ToString.Include
    private final String nombre;
    private final EspecialidadMedica especialidad;
    @Builder.Default
    private Hospital hospital = null;
    @Builder.Default
    private final List<Medico> medicos = new ArrayList<>();
    @Builder.Default
    private final List<Sala> salas = new ArrayList<>();

    private Departamento(String nombre, EspecialidadMedica especialidad) {
        this.nombre = validarString(nombre, "El nombre del departamento no puede ser nulo ni vacío");
        this.especialidad = Objects.requireNonNull(especialidad, "La especialidad no puede ser nula");
        this.hospital = null;
        this.medicos = new ArrayList<>();
        this.salas = new ArrayList<>();
    }

    public static class DepartamentoBuilder {
        public Departamento build() {
            return new Departamento(
                    nombre,
                    especialidad
            );
        }
    }

    //Set personalizado, se mantiene
    public void setHospital(Hospital hospital) {
        if (this.hospital != hospital) {
            if (this.hospital != null) {
                this.hospital.getInternalDepartamentos().remove(this);
            }
            this.hospital = hospital;
            if (hospital != null) {
                hospital.getInternalDepartamentos().add(this);
            }
        }
    }

    public void agregarMedico(Medico medico) {
        if (medico != null && !medicos.contains(medico)) {
            medicos.add(medico);
            medico.setDepartamento(this);
        }
    }

    public Sala crearSala(String numero, String tipo) {
        //Sala sala = new Sala(numero, tipo, this);  //--> Ya no se usa, es privado
        Sala sala = Sala.builder().numero(numero).tipo(tipo).departamento(this).build();
        salas.add(sala);
        return sala;
    }

    //Get personalizado, se mantiene
    public List<Medico> getMedicos() {
        return Collections.unmodifiableList(medicos);
    }

    //Get personalizado, se mantiene
    public List<Sala> getSalas() {
        return Collections.unmodifiableList(salas);
    }

    /*  --> Es buena opcion en caso de que se maneje mas de 1 hospital.
    @Override
    public String toString() {
        return "Departamento{" +
                "nombre='" + nombre + '\'' +
                ", especialidad=" + especialidad.getDescripcion() +
                ", hospital=" + (hospital != null ? hospital.getNombre() : "null") +
                '}';
    }
     */

    private String validarString(String valor, String mensajeError) {
        Objects.requireNonNull(valor, mensajeError);
        if (valor.trim().isEmpty()) {
            throw new IllegalArgumentException(mensajeError);
        }
        return valor;
    }
}
