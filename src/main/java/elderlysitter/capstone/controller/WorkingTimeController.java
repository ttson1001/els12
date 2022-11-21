package elderlysitter.capstone.controller;

import elderlysitter.capstone.dto.ReduceDateDTO;
import elderlysitter.capstone.dto.ResponseDTO;
import elderlysitter.capstone.dto.response.BookingImgResponseDTO;
import elderlysitter.capstone.entities.WorkingTime;
import elderlysitter.capstone.enumCode.ErrorCode;
import elderlysitter.capstone.enumCode.SuccessCode;
import elderlysitter.capstone.services.WorkingTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("working-time")
public class WorkingTimeController {
    @Autowired
    WorkingTimeService workingTimeService;

    @PutMapping("reduce-date")
    @PreAuthorize("hasRole('CUSTOMER')")
    private ResponseEntity<ResponseDTO> reduceDateFromBooking(@RequestBody ReduceDateDTO reduceDateDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            List<WorkingTime> workingTimeList = workingTimeService.reduceDateFromBooking(reduceDateDTO);
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

    @GetMapping("by-status-and-booking-id/{bookingId}/{status}")
    @PreAuthorize("hasRole('CUSTOMER')")
    private ResponseEntity<ResponseDTO> getAllWorkingTimeByBooking_IdAndStatus(@PathVariable Long bookingId, @PathVariable String status) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            List<WorkingTime> workingTimeList = workingTimeService.getAllWorkingTimeByBooking_IdAndStatus(bookingId, status);
            responseDTO.setData(workingTimeList);
            if (workingTimeList != null) {
                responseDTO.setSuccessCode(SuccessCode.FIND_ALL_WORKING_TIME_SUCCESS);
            } else {
                responseDTO.setErrorCode(ErrorCode.FIND_ALL_WORKING_TIME_FAIL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.FIND_ALL_WORKING_TIME_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }



}
