package br.purpletech.vivo.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "tb_users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastName;

    @Column(unique = true)
    private String email;

    private String password;
    private String position;
    private String telephone;

    @Enumerated(EnumType.STRING)
    private Role role; // collaborator, buddy or manager

    @ManyToOne
    @JsonIgnoreProperties({"users", "platforms"}) // para não ocorrer replicação infinita no json
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToMany
    @JoinTable(name = "user_onboarding", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "onboarding_id"))
    private Set<Onboarding> onboarding = new HashSet<>(); //add controle para colaborador ter somente um


    public Long getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Set<Onboarding> getOnboarding() {
        return onboarding;
    }

    public void setOnboarding(Set<Onboarding> onboarding) {
        this.onboarding = onboarding;
    }
}
