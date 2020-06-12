package com.leverX.blog.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * This enum is for storing user's roles
 *
 * @author Shpakova A.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

}
