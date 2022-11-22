package elderlysitter.capstone.controller;

import elderlysitter.capstone.dto.ReduceDateDTO;
import elderlysitter.capstone.dto.ResponseDTO;
import elderlysitter.capstone.dto.WorkingTimeDTO;
import elderlysitter.capstone.dto.request.DateRequestDTO;
import elderlysitter.capstone.entities.Role;
import elderlysitter.capstone.entities.WorkingTime;
import elderlysitter.capstone.enumCode.ErrorCode;
import elderlysitter.capstone.enumCode.SuccessCode;
import elderlysitter.capstone.jwt.JwtConfig;
import elderlysitter.capstone.repository.RoleRepository;
import elderlysitter.capstone.repository.UserRepository;
import elderlysitter.capstone.services.BookingService;
import elderlysitter.capstone.services.ServiceService;
import elderlysitter.capstone.services.UserService;
import elderlysitter.capstone.services.WorkingTimeService;
import elderlysitter.capstone.servicesImpl.SitterServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import java.util.List;

@RestController
@RequestMapping("time")
public class WorkingTimeController {
    @Autowired
    WorkingTimeService workingTimeService;

    @GetMapping("get-all-by-booking-id/{bookingId}/{status}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ResponseDTO> test(@PathVariable Long bookingId, @PathVariable String status) {

        ResponseDTO responseDTO = new ResponseDTO();
        try {
            List<WorkingTimeDTO> workingTimeList = workingTimeService.getAllWorkingTimeByBooking_IdAndStatus(bookingId, status);
            responseDTO.setData(workingTimeList);
            responseDTO.setSuccessCode(SuccessCode.FIND_ALL_WORKING_TIME_SUCCESS);
            if(workingTimeList.isEmpty()){
                responseDTO.setErrorCode(ErrorCode.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.FIND_ALL_WORKING_TIME_ERROR);
        }

        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("reduce-date")
    @PreAuthorize("hasRole('CUSTOMER')")
    private ResponseEntity<ResponseDTO> reduceDateFromBooking(@RequestBody ReduceDateDTO reduceDateDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            List<WorkingTimeDTO> workingTimeList = workingTimeService.reduceDateFromBooking(reduceDateDTO);
            responseDTO.setData(workingTimeList);
            if (workingTimeList != null) {
                responseDTO.setSuccessCode(SuccessCode.REDUCE_SUCCESS);
            } else {
                responseDTO.setErrorCode(ErrorCode.REDUCE_FAIL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.REDUCE_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }






}
