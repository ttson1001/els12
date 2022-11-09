package elderlysitter.capstone.controller;

import elderlysitter.capstone.dto.ResponseDTO;
import elderlysitter.capstone.dto.request.AddCustomerRequestDTO;
import elderlysitter.capstone.dto.request.ChangePasswordDTO;
import elderlysitter.capstone.dto.request.UpdateCustomerRequestDTO;
import elderlysitter.capstone.dto.response.CustomerResponseDTO;
import elderlysitter.capstone.dto.response.CustomersResponseDTO;
import elderlysitter.capstone.entities.User;
import elderlysitter.capstone.enumCode.ErrorCode;
import elderlysitter.capstone.enumCode.SuccessCode;
import elderlysitter.capstone.services.CustomerService;
import elderlysitter.capstone.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import java.util.List;

@RestController
@RequestMapping("customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private UserService userService;

    @PostMapping("add")
    @PermitAll
    public ResponseEntity<ResponseDTO> addCustomer(@RequestBody AddCustomerRequestDTO addCustomerRequestDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            User customer = userService.findByEmail(addCustomerRequestDTO.getEmail());
            if (customer == null) {
                CustomerResponseDTO customerResponseDTO = customerService.addCustomer(addCustomerRequestDTO);
                if (customerResponseDTO != null) {
                    responseDTO.setData(customerResponseDTO);
                    responseDTO.setSuccessCode(SuccessCode.ADD_CUSTOMER_SUCCESS);
                } else {
                    responseDTO.setErrorCode(ErrorCode.ADD_CUSTOMER_FAIL);
                }
            } else {
                responseDTO.setErrorCode(ErrorCode.DUPLICATE_EMAIL);
            }

        } catch (Exception e) {
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.ADD_CUSTOMER_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("update")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ResponseDTO> updateCustomer(@RequestBody UpdateCustomerRequestDTO updateCustomerRequestDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            CustomerResponseDTO customerResponseDTO = customerService.updateCustomer(updateCustomerRequestDTO);
            if (customerResponseDTO != null) {
                responseDTO.setData(customerResponseDTO);
                responseDTO.setSuccessCode(SuccessCode.UPDATE_CUSTOMER_SUCCESS);
            } else {
                responseDTO.setErrorCode(ErrorCode.UPDATE_CUSTOMER_FAIL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.UPDATE_CUSTOMER_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("ban/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> banCustomer(@PathVariable Long id) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            CustomerResponseDTO customerResponseDTO = customerService.banCustomer(id);
            if (customerResponseDTO != null) {
                responseDTO.setData(customerResponseDTO);
                responseDTO.setSuccessCode(SuccessCode.BAN_CUSTOMER_SUCCESS);
            } else {
                responseDTO.setErrorCode(ErrorCode.BAN_CUSTOMER_FAIL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.BAN_CUSTOMER_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("unban/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> unbanCustomer(@PathVariable Long id) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            CustomerResponseDTO customerResponseDTO = customerService.unbanCustomer(id);
            if (customerResponseDTO != null) {
                responseDTO.setData(customerResponseDTO);
                responseDTO.setSuccessCode(SuccessCode.UNBAN_CUSTOMER_SUCCESS);
            } else {
                responseDTO.setErrorCode(ErrorCode.UNBAN_CUSTOMER_FAIL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.UNBAN_CUSTOMER_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("customers")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> getAllCustomer() {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            List<CustomersResponseDTO> customersResponseDTO = customerService.getAllCustomer();
            if (customersResponseDTO != null) {
                responseDTO.setData(customersResponseDTO);
                responseDTO.setSuccessCode(SuccessCode.FIND_ALL_CUSTOMER_SUCCESS);
            } else {
                responseDTO.setErrorCode(ErrorCode.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.FIND_ALL_CUSTOMER_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("get-by-id/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> getById(@PathVariable Long id) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            CustomerResponseDTO customerResponseDTO = customerService.getById(id);
            if (customerResponseDTO != null) {
                responseDTO.setData(customerResponseDTO);
                responseDTO.setSuccessCode(SuccessCode.FIND_CUSTOMER_SUCCESS);
            } else {
                responseDTO.setErrorCode(ErrorCode.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.FIND_CUSTOMER_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("get-by-email/{email}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ResponseDTO> getByEmail(@PathVariable String email) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            CustomerResponseDTO customerResponseDTO = customerService.getByEmail(email);
            if (customerResponseDTO != null) {
                responseDTO.setData(customerResponseDTO);
                responseDTO.setSuccessCode(SuccessCode.FIND_CUSTOMER_SUCCESS);
            } else {
                responseDTO.setErrorCode(ErrorCode.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.FIND_CUSTOMER_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("change-password")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ResponseDTO> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            CustomerResponseDTO customerResponseDTO = customerService.changePassword(changePasswordDTO);
            if (customerResponseDTO != null) {
                responseDTO.setData(customerResponseDTO);
                responseDTO.setSuccessCode(SuccessCode.CHANGE_PASSWORD_SUCCESS);
            } else {
                responseDTO.setErrorCode(ErrorCode.CHANGE_PASSWORD_FAIL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.CHANGE_PASSWORD_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }

}
