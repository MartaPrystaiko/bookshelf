package com.learnjava.bookshelf.dto;

import lombok.*;

import java.util.List;
import java.util.Set;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {

    private int id;
    private String title;
    private Set<Integer> categoriesId;
    private double price;
    private List<Integer> authorsId;
}
