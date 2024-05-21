package com.ecampus.lms.entity;

import com.ecampus.lms.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "\"DUTNE_UTENTE\"", schema = "public")
@NamedQueries({
        @NamedQuery(name = "findByRole", query = "SELECT u FROM UtenteEntity u WHERE u.ruolo = :role"),
})
public class UtenteEntity {
    @Id
    @SequenceGenerator(name = "DUTNE_PK_ID_GENERATOR", sequenceName = "\"DUTNE_PK_ID_GENERATOR\"", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DUTNE_PK_ID_GENERATOR")
    @Column(name = "\"DUTNE_PK_ID\"", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "\"DUTNE_NOME\"", nullable = false, length = Integer.MAX_VALUE)
    private String nome;

    @NotNull
    @Column(name = "\"DUTNE_COGNOME\"", nullable = false, length = Integer.MAX_VALUE)
    private String cognome;

    @NotNull
    @Column(name = "\"DUTNE_PASSWORD\"", nullable = false, length = Integer.MAX_VALUE)
    private String password;

    @NotNull
    @Column(name = "\"DUTNE_EMAIL\"", nullable = false, length = Integer.MAX_VALUE)
    private String email;

    @NotNull
    @Column(name = "\"DUTNE_FK_TTRUT\"", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole ruolo;

    @Size(max = 16)
    @NotNull
    @Column(name = "\"DUTNE_CF\"", nullable = false, length = 16)
    private String codiceFiscale;

    @ManyToMany(mappedBy = "studenti")
    private Set<CorsoEntity> corsi = new LinkedHashSet<>();

    @OneToMany(mappedBy = "docente")
    private Set<CorsoEntity> cattedra = new LinkedHashSet<>();

}