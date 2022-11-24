package elderlysitter.capstone.controller;

import elderlysitter.capstone.dto.ResponseDTO;
import elderlysitter.capstone.dto.request.AddPaymentRequestDTO;
import elderlysitter.capstone.dto.response.PaymentResponseDTO;
import elderlysitter.capstone.entities.Elder;
import elderlysitter.capstone.entities.Payment;
import elderlysitter.capstone.enumCode.ErrorCode;
import elderlysitter.capstone.enumCode.SuccessCode;
import elderlysitter.capstone.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("paid")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ResponseDTO> paid(@RequestBody AddPaymentRequestDTO addPaymentRequestDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            PaymentResponseDTO paymentResponseDTO = paymentService.paid(addPaymentRequestDTO);
            responseDTO.setData(paymentResponseDTO);
            if (paymentResponseDTO != null) {
                responseDTO.setSuccessCode(SuccessCode.PAID_SUCCESS);
            } else {
                responseDTO.setErrorCode(ErrorCode.PAID_FAIL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.PAID_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }

    @PostMapping("paid")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ResponseDTO> paid(@RequestBody AddPaymentRequestDTO addPaymentRequestDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            PaymentResponseDTO paymentResponseDTO = paymentService.paid(addPaymentRequestDTO);
            responseDTO.setData(paymentResponseDTO);
            if (paymentResponseDTO != null) {
                responseDTO.setSuccessCode(SuccessCode.PAID_SUCCESS);
            } else {
                responseDTO.setErrorCode(ErrorCode.PAID_FAIL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.PAID_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }
}
