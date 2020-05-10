package com.master4.entities;


import com.master4.validators.UniqueTagName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
@Entity
@Table(name="tags")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @UniqueTagName
    @Size(min = 5, message = "the name must contains 5 characters")
    @Column(name = "name", nullable = false, unique = true, length = 150)
    private String  title;

    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @Column(name = "modified")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modified ;


    @ManyToMany(mappedBy="tagList")
    List<Article> ArticleList;


    @Transient
    private Boolean used;

    public Tag(long id) {
        this.id=id;
    }
}
