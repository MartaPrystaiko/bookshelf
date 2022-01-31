package com.learnjava.bookshelf.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    //    @NotBlank -----  don`t matter, because we work with BookDto
    @Column(nullable = false, length = 50)
    private String title;

    @ManyToMany//(fetch = FetchType.EAGER)
    @JoinTable(name = "book_category",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories;

    @PositiveOrZero
    private double price;

    @ManyToMany//(fetch = FetchType.EAGER)        n
    @JoinTable(name = "book_author",
    joinColumns = @JoinColumn(name = "book_id"),
    inverseJoinColumns = @JoinColumn(name = "author_id"))
    private List<Author> authors;
}
