package edu.miu.cs.cs489.lesson6.citylibraryapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer addressId; // address_id
    private String street;
    private String city;
    private String state;
    @Column(length = 16)
    private String zipCode;
    @OneToOne(mappedBy = "address")
    @JsonIgnore
    private Patient patient;

    public Address( String old, String oldCity, String oldState, String number) {
        this.street = old;
        this.city = oldCity;
        this.state = oldState;
        this.zipCode = number;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Address{");
        sb.append("addressId=").append(addressId);
        sb.append(", street='").append(street).append('\'');
        sb.append(", city='").append(city).append('\'');
        sb.append(", state='").append(state).append('\'');
        sb.append(", zipCode='").append(zipCode).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
