package com.anibalventura.user_management_api.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  private String name;

  @Column(unique = true, nullable = false)
  private String email;

  @Column(nullable = false)
  private String password;

  private LocalDateTime created;
  private LocalDateTime modified;
  private LocalDateTime lastLogin;

  @Column(length = 500)
  private String token;

  private boolean isActive;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Phone> phones;

  @PrePersist
  protected void onCreate() {
    created = lastLogin = LocalDateTime.now();
    modified = created;
    isActive = true;
  }

  @PreUpdate
  protected void onUpdate() {
    modified = LocalDateTime.now();
  }
}