package com.gouveia.imdb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageResponseDTO<T> {
    private long totalElements;
    private int totalPages;
    private int currentPage;
    private int pageSize;
    private boolean last;
    private boolean first;
    private List<T> content;
}
