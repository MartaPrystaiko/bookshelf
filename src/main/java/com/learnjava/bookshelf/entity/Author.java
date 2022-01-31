package com.learnjava.bookshelf.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 25)
    private String name;
    @Column(length = 50)
    private String surname;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate birthDate;

    @ManyToMany(mappedBy = "authors")
    private List<Book> books;
}
