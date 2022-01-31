package com.learnjava.bookshelf.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class BookPage {

    List<BookDto> books;
    private long totalElements;
    private int currentPage;
    private boolean isLast;

}
