package com.dosmakhambetbaktiyar.module41.dto;


import com.dosmakhambetbaktiyar.module41.model.Customer;
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

    public static CustomerDto toDto(Customer entity){
        return CustomerDto.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .country(entity.getCountry())
                .build();
    }

    public Customer toEntity(){
        return Customer.builder()
                .id(this.id)
                .firstName(this.firstName)
                .lastName(this.lastName)
                .country(this.country)
                .build();
    }
}
