package com.clientes.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data @Entity @Table(name = "clientes")
public class Cliente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "no puede estar vacio")
    @Size(min = 4, max = 20)
    @Column(nullable = false)
    private String nombre;

    @NotEmpty(message = "no puede estar vacio")
    private String apellido;

    @NotEmpty(message = "no puede estar vacio")
    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "create_At")
    @Temporal(TemporalType.DATE)
    private Date createAt;

    @PrePersist
    public void prePersist() {
        createAt = new Date();
    }

    public static final long serialVersionUID = 1L;

}
