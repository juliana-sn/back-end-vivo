package br.purpletech.vivo.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity(name = "tb_onboardings")
@Getter
@Setter
public class Onboarding {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dt_begin;
    private LocalDate dt_end;

    @ManyToOne
    @JoinColumn(name = "buddy_id")
    private User manager;

    @ManyToOne
    @JoinColumn(name = "buddy_id")
    private User buddy;

    @OneToOne
    @JoinColumn(name = "collaborator_id")
    private User collaborator;
}
