package br.purpletech.vivo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "onboardings")
public class Onboarding {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_onboarding")
    private Long id;

    private LocalDate dt_begin;
    private LocalDate dt_end;
    private boolean active;

    @ManyToMany(mappedBy = "onboarding")
    @JsonIgnoreProperties("onboarding")
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "onboarding")
    @JsonIgnoreProperties("onboarding")
    private List<Step> steps;

    @OneToMany(mappedBy = "onboarding")
    @OrderBy("createdAt DESC")
    private List<Report> reports  = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public LocalDate getDt_begin() {
        return dt_begin;
    }

    public void setDt_begin(LocalDate dt_begin) {
        this.dt_begin = dt_begin;
    }

    public LocalDate getDt_end() {
        return dt_end;
    }

    public void setDt_end(LocalDate dt_end) {
        this.dt_end = dt_end;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public List<Report> getReports() {
        return reports;
    }

    public void setReports(List<Report> reports) {
        this.reports = reports;
    }

    public Step getCurrentStep() {
        if (steps == null) return null;
        return steps.stream()
                .filter(Step::isInProgress)
                .findFirst()
                .orElse(null);
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}