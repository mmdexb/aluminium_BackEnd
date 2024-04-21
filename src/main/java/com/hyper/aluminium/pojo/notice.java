package com.hyper.aluminium.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class notice {
    private Integer id;
    private String title;
    private String time;
    private String author;
    private String content;
}
