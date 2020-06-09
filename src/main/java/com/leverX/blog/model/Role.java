package com.leverX.blog.model;

import lombok.Data;
import javax.persistence.*;

/**
 * This enum is for storing user's roles
 *
 * @author Shpakova A.
 */
@Entity
@Data
@Table(name="role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

}
