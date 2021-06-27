package com.socialbox.dto;

import com.socialbox.model.Tmdb;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TmdbDTO {
    private Integer page;
    private List<Tmdb> results;
    private Integer total_pages;
    private Integer total_results;
}
