package com.comak.sce.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @Builder
public class ErrorDto {
    private LocalDateTime timestamp;
    private int status;
    private String path;
    private List<String> errors;
}
