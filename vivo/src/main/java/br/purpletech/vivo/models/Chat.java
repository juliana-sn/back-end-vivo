package br.purpletech.vivo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "tb_chats")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties({"lastName", "email", "password", "position", "telephone", "reports", "role", "team", "onboarding"})
    @JoinColumn(name = "sender_id")
    private User participant1;

    @ManyToOne
    @JsonIgnoreProperties({"lastName", "email", "password", "position", "telephone", "reports", "role", "team", "onboarding"})
    @JoinColumn(name = "receiver_id")
    private User participant2;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
    private List<Message> messages  = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public User getParticipant1() {
        return participant1;
    }

    public void setParticipant1(User participant1) {
        this.participant1 = participant1;
    }

    public User getParticipant2() {
        return participant2;
    }

    public void setParticipant2(User participant2) {
        this.participant2 = participant2;
    }
}
