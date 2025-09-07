package br.purpletech.vivo.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long id;
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(unique = true)
    private String email;

    private String password;
    private String position;
    private String telephone;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne
    @JsonIgnoreProperties({"users", "platforms"})
    @JoinColumn(name = "id_team")
    private Team team;

    @OneToMany(mappedBy = "sender")
    private List<Message> messages = new ArrayList<>();

    @ManyToMany
    @JsonIgnoreProperties({"active", "users"})
    @JoinTable(name = "users_onboardings", joinColumns = @JoinColumn(name = "id_user"), inverseJoinColumns = @JoinColumn(name = "id_onboarding"))
    private Set<Onboarding> onboarding = new HashSet<>();

    @OneToMany(mappedBy = "collaborator")
    @OrderBy("createdAt DESC")
    private List<Report> reports;

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

    public List<Report> getReports() {
        return reports;
    }

    public void setReports(List<Report> reports) {
        this.reports = reports;
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
