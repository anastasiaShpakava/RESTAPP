package com.leverX.blog.model;

import lombok.Data;
import javax.persistence.*;

@Entity
@Data
@Table(name="user_role")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

}
