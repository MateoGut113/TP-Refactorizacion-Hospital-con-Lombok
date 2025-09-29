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
@ToString (onlyExplicitlyIncluded = true)
@Builder

public class Hospital implements Serializable {
    @ToString.Include
    private final String nombre;
    @ToString.Include
    private final String direccion;
    @ToString.Include
    private final String telefono;
    @Builder.Default
    private final List<Departamento> departamentos = new ArrayList<>();
    @Builder.Default
    private final List<Paciente> pacientes = new ArrayList<>();

    private Hospital(String nombre, String direccion, String telefono) {
        this.nombre = validarString(nombre, "El nombre del hospital no puede ser nulo ni vacío");
        this.direccion = validarString(direccion, "La dirección no puede ser nula ni vacía");
        this.telefono = validarString(telefono, "El teléfono no puede ser nulo ni vacío");
        this.departamentos = new ArrayList<>();
        this.pacientes = new ArrayList<>();
    }

    public static class HospitalBuilder {
        public Hospital build() {
            return new Hospital(
                    nombre,
                    direccion,
                    telefono
            );
        }
    }

    public void agregarDepartamento(Departamento departamento) {
        if (departamento != null && !departamentos.contains(departamento)) {
            departamentos.add(departamento);
            departamento.setHospital(this);
        }
    }

    public void agregarPaciente(Paciente paciente) {
        if (paciente != null && !pacientes.contains(paciente)) {
            pacientes.add(paciente);
            paciente.setHospital(this);
        }
    }

    //Get personalizado, se mantiene
    public List<Departamento> getDepartamentos() {
        return Collections.unmodifiableList(departamentos);
    }

    //Get personalizado, se mantiene
    public List<Paciente> getPacientes() {
        return Collections.unmodifiableList(pacientes);
    }

    //Get personalizado, se mantiene
    List<Departamento> getInternalDepartamentos() {
        return departamentos;
    }

    //Get personalizado, se mantiene
    List<Paciente> getInternalPacientes() {
        return pacientes;
    }

    private String validarString(String valor, String mensajeError) {
        Objects.requireNonNull(valor, mensajeError);
        if (valor.trim().isEmpty()) {
            throw new IllegalArgumentException(mensajeError);
        }
        return valor;
    }
}
