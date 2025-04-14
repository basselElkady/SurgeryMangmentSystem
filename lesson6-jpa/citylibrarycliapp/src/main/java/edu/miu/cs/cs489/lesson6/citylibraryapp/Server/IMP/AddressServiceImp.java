package edu.miu.cs.cs489.lesson6.citylibraryapp.Server.IMP;

import edu.miu.cs.cs489.lesson6.citylibraryapp.DTO.Response.AddressListResponse;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Server.AddressService;
import edu.miu.cs.cs489.lesson6.citylibraryapp.model.Address;
import edu.miu.cs.cs489.lesson6.citylibraryapp.model.Patient;
import edu.miu.cs.cs489.lesson6.citylibraryapp.repository.AddressRepository;
import edu.miu.cs.cs489.lesson6.citylibraryapp.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImp implements AddressService {

    private AddressRepository addressRepository;

    @Override
    public AddressListResponse getAddressList() {
        List<Address> addresses = addressRepository.findAllAddress();
        if(addresses.isEmpty()) {
            return null;
        }
        return new AddressListResponse(addresses);
    }
}
