package com.learnjava.bookshelf.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class AuthorDto {

    private int id;
    private String name;
    private String surname;
    private List<Integer> bookId;
}
