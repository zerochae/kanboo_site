package com.kanboo.www.dto.project;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SocketDTO {
    private String id;
    private String text;
    private String date;
    private String alarm;
    private String textAreaText;
}
