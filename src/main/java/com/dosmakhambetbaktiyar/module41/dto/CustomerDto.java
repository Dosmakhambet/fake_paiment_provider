package com.dosmakhambetbaktiyar.module41.dto;


import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerDto implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private String country;
}
