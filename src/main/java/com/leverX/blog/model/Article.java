package com.leverX.blog.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="article")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String title;

    @Column
    private String text;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ArticleStatus articleStatus;

    @ManyToOne(fetch = FetchType.EAGER) //извлекается полностью в момент извл. родителя
    @JoinColumn(name = "author_id")  //
    private User user;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "article_tag",
            joinColumns = @JoinColumn(name = "article_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
    private Set<Tag> tags = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "article")
    private List<Comment> comments;

    public boolean isPublic() {
        return this.articleStatus == ArticleStatus.PUBLIC;
    }
}
