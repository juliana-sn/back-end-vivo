package br.purpletech.vivo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity(name = "tb_users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private String password;
    private String position;
    private String tel;
    private String role; // collaborator, buddy or manager

    @ManyToOne
    private User manager;

    @ManyToOne
    private User buddy;

    @ManyToOne
    private Team team;
}
