package br.purpletech.vivo.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@Entity(name = "tb_onboardings")
public class Onboarding {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dt_begin;
    private LocalDate dt_end;
    private boolean active;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private User manager;

    @ManyToOne
    @JoinColumn(name = "buddy_id")
    private User buddy;

    @OneToOne
    @JoinColumn(name = "collaborator_id")
    private User collaborator;
}