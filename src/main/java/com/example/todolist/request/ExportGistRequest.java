package com.example.todolist.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.Serializable;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExportGistRequest implements Serializable {
    private static final long serialversionUID = -1L;

    private String description;
    @JsonProperty("public")
    private boolean pub = true;
    Map<String, Content> files;
}
