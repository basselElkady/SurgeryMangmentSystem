package edu.miu.cs.cs489.lesson6.citylibraryapp.Controller;

import edu.miu.cs.cs489.lesson6.citylibraryapp.DTO.Response.AddressListResponse;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Server.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/adsweb/api/v1/addresses")
@AllArgsConstructor
public class AddressController {

        private final AddressService addressService;


        // Get all addresses with patient data sorted by city
//        @GetMapping(produces = "application/json")
//        public ResponseEntity<AddressListResponse> getAllAddresses() {
//            AddressListResponse response = addressService.getAddressList();
//            if (response == null) {
//                return ResponseEntity.noContent().build();
//            }
//            return ResponseEntity.ok(response);
//        }


}
