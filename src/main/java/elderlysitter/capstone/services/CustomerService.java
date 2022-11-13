package elderlysitter.capstone.services;

import elderlysitter.capstone.dto.request.AddCustomerRequestDTO;
import elderlysitter.capstone.dto.request.ChangePasswordDTO;
import elderlysitter.capstone.dto.request.UpdateCustomerRequestDTO;
import elderlysitter.capstone.dto.response.CustomerResponseDTO;
import elderlysitter.capstone.dto.response.CustomersResponseDTO;

import java.util.List;

public interface CustomerService {
    CustomerResponseDTO addCustomer(AddCustomerRequestDTO addCustomerRequestDTO);

    CustomerResponseDTO updateCustomer(UpdateCustomerRequestDTO updateCustomerRequestDTO);

    CustomerResponseDTO banCustomer(Long id);

    CustomerResponseDTO unbanCustomer(Long id);

    List<CustomersResponseDTO> getAllCustomer();

    CustomerResponseDTO getById(Long id);

    CustomerResponseDTO getByEmail(String email);

    CustomerResponseDTO changePassword(ChangePasswordDTO changePasswordDTO);

}
