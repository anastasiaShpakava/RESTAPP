package com.leverX.blog.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;


/**
 * This class is for storing user's data
 *
 * @author Shpakova A.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column
    private String login;

    @Column
    private String password;

    @Column
    @Email
    private String email;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "role",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "role_id"))
//    private Collection<Role> roles;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
//    private List<Article> articles;
//
//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
//    private List<Comment> comments;
}
