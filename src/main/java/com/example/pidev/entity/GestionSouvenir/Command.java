    package com.example.pidev.entity.GestionSouvenir;

    import com.example.pidev.entity.User.User;
    import jakarta.persistence.*;
    import lombok.AllArgsConstructor;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;

    import java.time.LocalDateTime;
    import java.util.ArrayList;
    import java.util.List;

    @Entity
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public class Command {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        private User user;

        @OneToMany(mappedBy = "command", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<CommandLine> commandLines = new ArrayList<>();

        private double total;
        private LocalDateTime createdAt;

        @Enumerated(EnumType.STRING)
        @Column(length = 20)
        private CommandStatus status;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public List<CommandLine> getCommandLines() {
            return commandLines;
        }

        public void setCommandLines(List<CommandLine> commandLines) {
            this.commandLines = commandLines;
        }

        public double getTotal() {
            return total;
        }

        public void setTotal(double total) {
            this.total = total;
        }

        public LocalDateTime getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
        }

        public CommandStatus getStatus() {
            return status;
        }

        public void setStatus(CommandStatus status) {
            this.status = status;
        }

        @PrePersist
        protected void onCreate() {
            this.createdAt = LocalDateTime.now();
            this.status = CommandStatus.PENDING;
        }

        // Méthode pour calculer le total
        public void calculateTotal() {
            this.total = commandLines.stream()
                    .mapToDouble(CommandLine::getTotalPrice)
                    .sum();
        }
    }

