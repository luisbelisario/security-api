package com.reservei.securityapi.securityapi.domain.model;

import com.reservei.securityapi.securityapi.domain.record.UserData;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Table(name = "_user")
@Entity(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "public_id")
    private String publicId;
    private String email;
    private String password;
    private String role;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @Column(name = "deleted_at")
    private LocalDate deletedAt;

    public static User toUser(UserData data) {
        User user = new User();
        user.setPublicId(data.publicId());
        user.setEmail(data.email());
        user.setPassword(data.password());
        user.setRole(data.role());
        user.setCreatedAt(LocalDate.now());

        return user;
    }

    public static User updateUser(User user, UserData data) {
        user.setEmail(data.email());
        user.setPassword(data.password());
        user.setUpdatedAt(LocalDate.now());

        return user;
    }
}
