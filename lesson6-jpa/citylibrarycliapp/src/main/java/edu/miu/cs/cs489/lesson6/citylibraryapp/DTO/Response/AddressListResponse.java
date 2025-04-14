package edu.miu.cs.cs489.lesson6.citylibraryapp.DTO.Response;

import edu.miu.cs.cs489.lesson6.citylibraryapp.model.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddressListResponse {
    List<Address> addressList;
}
