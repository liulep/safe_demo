package com.yue.xss.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayParam {
    private String from;

    private Double money;

    private String to;

}
