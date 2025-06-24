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

@Entity(name = "tb_onboardings")
public class Onboarding {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dt_begin;
    private LocalDate dt_end;
    private boolean active;

    @ManyToOne
    @JsonIgnoreProperties({"password", "onboarding"}) // para não ocorrer replicação infinita no json
    @JoinColumn(name = "manager_id")
    private User manager;

    @ManyToOne
    @JsonIgnoreProperties({"password", "onboarding"})
    @JoinColumn(name = "buddy_id")
    private User buddy;

    @OneToOne
    @JsonIgnoreProperties({"password", "onboarding"})
    @JoinColumn(name = "collaborator_id")
    private User collaborator;

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

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    public User getBuddy() {
        return buddy;
    }

    public void setBuddy(User buddy) {
        this.buddy = buddy;
    }

    public User getCollaborator() {
        return collaborator;
    }

    public void setCollaborator(User collaborator) {
        this.collaborator = collaborator;
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
}