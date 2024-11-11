package com.example.jpql.jpql;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Embeddable
public class Address {

    private String city;
    private String street;
    private String zipcode;
}
