package com.dosmakhambetbaktiyar.module41.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("customer")
public class Customer implements Serializable {
    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private String country;
}
