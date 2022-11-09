package elderlysitter.capstone.servicesImpl;

import elderlysitter.capstone.dto.request.AddCustomerRequestDTO;
import elderlysitter.capstone.dto.request.ChangePasswordDTO;
import elderlysitter.capstone.dto.request.UpdateCustomerRequestDTO;
import elderlysitter.capstone.dto.response.CustomerResponseDTO;
import elderlysitter.capstone.entities.User;
import elderlysitter.capstone.enumCode.StatusCode;
import elderlysitter.capstone.repository.RoleRepository;
import elderlysitter.capstone.repository.UserRepository;
import elderlysitter.capstone.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public CustomerResponseDTO addCustomer(AddCustomerRequestDTO addCustomerRequestDTO) {
        CustomerResponseDTO responseDTO = null;
        try {
            User customer = User.builder()
                    .password(passwordEncoder.encode(addCustomerRequestDTO.getPassword()))
                    .fullName(addCustomerRequestDTO.getFullName())
                    .status(StatusCode.ACTIVATE.toString())
                    .email(addCustomerRequestDTO.getEmail())
                    .createDate(LocalDate.now())
                    .role(roleRepository.findByName("CUSTOMER"))
                    .build();
            customer = userRepository.save(customer);
            responseDTO = convertor(customer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseDTO;
    }

    @Override
    public CustomerResponseDTO updateCustomer(UpdateCustomerRequestDTO updateCustomerRequestDTO) {
        CustomerResponseDTO responseDTO = null;
        try {
            User customer = userRepository.findUserByEmail(updateCustomerRequestDTO.getEmail());
            customer.setFullName(updateCustomerRequestDTO.getFullName());
            customer.setDob(updateCustomerRequestDTO.getDob());
            customer.setGender(updateCustomerRequestDTO.getGender());
            customer.setPhone(updateCustomerRequestDTO.getPhone());
            customer.setAddress(updateCustomerRequestDTO.getAddress());
            customer.setEmail(updateCustomerRequestDTO.getEmail());
            customer.setFrontIdImgUrl(updateCustomerRequestDTO.getFrontIdImgUrl());
            customer.setBackIdImgUrl(updateCustomerRequestDTO.getBackIdImgUrl());
            customer.setAvatarImgUrl(updateCustomerRequestDTO.getAvatarImgUrl());
            customer = userRepository.save(customer);
            responseDTO = convertor(customer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseDTO;
    }

    @Override
    public CustomerResponseDTO banCustomer(Long id) {
        CustomerResponseDTO responseDTO = null;
        try {
            User customer = userRepository.findById(id).get();
            customer.setStatus(StatusCode.DEACTIVATE.toString());
            customer = userRepository.save(customer);
            responseDTO = convertor(customer);
        }catch (Exception e){
            e.printStackTrace();
        }
        return responseDTO;
    }

    @Override
    public CustomerResponseDTO unbanCustomer(Long id) {
        CustomerResponseDTO responseDTO = null;
        try {
            User customer = userRepository.findById(id).get();
            customer.setStatus(StatusCode.ACTIVATE.toString());
            customer = userRepository.save(customer);
            responseDTO = convertor(customer);
        }catch (Exception e){
            e.printStackTrace();
        }
        return responseDTO;
    }

    @Override
    public List<CustomerResponseDTO> getAllCustomer() {
        List<CustomerResponseDTO> responseDTOS = null;
        try {
            List<User> customers = userRepository.findAllByRole_Name("CUSTOMER");
            for (User customer: customers) {
                    CustomerResponseDTO responseDTO = convertor(customer);
                    responseDTOS.add(responseDTO);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return responseDTOS;
    }

    @Override
    public CustomerResponseDTO getById(Long id) {
        CustomerResponseDTO responseDTO = null;
        try {
            User customer = userRepository.findById(id).get();
            responseDTO = convertor(customer);
        }catch (Exception e){
            e.printStackTrace();
        }
        return responseDTO;
    }

    @Override
    public CustomerResponseDTO getByEmail(String email) {
        CustomerResponseDTO responseDTO = null;
        try {
            User customer = userRepository.findUserByEmail(email);
            responseDTO = convertor(customer);
        }catch (Exception e){
            e.printStackTrace();
        }
        return responseDTO;
    }

    @Override
    public CustomerResponseDTO changePassword(ChangePasswordDTO changePasswordDTO) {
        CustomerResponseDTO responseDTO = null;
        try {
            User customer = userRepository.findUserByEmail(changePasswordDTO.getEmail());
            Boolean check = passwordEncoder.matches(changePasswordDTO.getOldPassword(), customer.getPassword());
            if(check == true){
                customer.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
                responseDTO = convertor( userRepository.save(customer));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return responseDTO;
    }


    private CustomerResponseDTO convertor(User customer) {
        CustomerResponseDTO responseDTO = null;
        try {
            responseDTO = CustomerResponseDTO.builder()
                    .fullName(customer.getFullName())
                    .dob(customer.getDob())
                    .address(customer.getAddress())
                    .gender(customer.getGender())
                    .phone(customer.getPhone())
                    .address(customer.getAddress())
                    .email(customer.getEmail())
                    .frontIdImgUrl(customer.getFrontIdImgUrl())
                    .backIdImgUrl(customer.getBackIdImgUrl())
                    .avatarImgUrl(customer.getAvatarImgUrl())
                    .status(customer.getStatus())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseDTO;
    }
}
