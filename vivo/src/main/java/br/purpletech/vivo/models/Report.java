package br.purpletech.vivo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity(name = "reports")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate createdAt = LocalDate.now();

    private int feeling; // de 1 a 4

    private String question;

    private String event;

    private String comment;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "collaborator_id")
    private User collaborator;

    @ManyToOne
    @JsonIgnore
    @JoinColumn (name = "onboarding_id")
    private Onboarding onboarding;

    public Long getId() {
        return id;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public int getFeeling() {
        return feeling;
    }

    public void setFeeling(int feeling) {
        this.feeling = feeling;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public User getCollaborator() {
        return collaborator;
    }

    public void setCollaborator(User collaborator) {
        this.collaborator = collaborator;
    }

    public Onboarding getOnboarding() {
        return onboarding;
    }

    public void setOnboarding(Onboarding onboarding) {
        this.onboarding = onboarding;
    }
}
