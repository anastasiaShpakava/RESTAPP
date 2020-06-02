package com.leverX.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column
    private String password;

    @Column()
    private String email;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column (name="role")
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    private List <Article> articles;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    private List <Comment> comments;

    public boolean isAdmin() {
        return this.userRole == UserRole.ADMINISTRATOR;
    }
}
