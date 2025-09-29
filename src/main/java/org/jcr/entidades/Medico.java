package org.jcr.entidades;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder; //No recomendable por las validaciones internas
// y complicaciones con a la hora de crear una historia clinica

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString(callSuper = true, onlyExplicitlyIncluded = true) //Mostrará los atributos incluidos, y los de la clase padre.

public class Medico extends Persona implements Serializable {
    @ToString.Include
    private final Matricula matricula;
    @ToString.Include
    private final EspecialidadMedica especialidad;
    private Departamento departamento;
    private final List<Cita> citas = new ArrayList<>();

    public Medico(String nombre, String apellido, String dni, LocalDate fechaNacimiento,
                  TipoSangre tipoSangre, String numeroMatricula, EspecialidadMedica especialidad) {
        super(nombre, apellido, dni, fechaNacimiento, tipoSangre);
        this.matricula = Matricula.builder().numero(numeroMatricula).build();
        this.especialidad = Objects.requireNonNull(especialidad, "La especialidad no puede ser nula");
    }

    //Set personalizado, se mantiene
    public void setDepartamento(Departamento departamento) {
        if (this.departamento != departamento) {
            this.departamento = departamento;
        }
    }

    public void addCita(Cita cita) {
        this.citas.add(cita);
    }

    //Get personalizado, se mantiene
    public List<Cita> getCitas() {
        return Collections.unmodifiableList(new ArrayList<>(citas));
    }

}
