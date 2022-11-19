package elderlysitter.capstone.controller;

import elderlysitter.capstone.dto.ResponseDTO;
import elderlysitter.capstone.dto.request.AddRatingRequestDTO;
import elderlysitter.capstone.dto.request.AddReportRequestDTO;
import elderlysitter.capstone.dto.response.RatingResponseDTO;
import elderlysitter.capstone.dto.response.ReportResponseDTO;
import elderlysitter.capstone.enumCode.ErrorCode;
import elderlysitter.capstone.enumCode.SuccessCode;
import elderlysitter.capstone.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("report")
public class ReportController {
    @Autowired
    ReportService reportService;

    @PostMapping()
    @PreAuthorize("hasRole('SITTER')")
    public ResponseEntity<ResponseDTO> reportCustomer(@RequestBody AddReportRequestDTO addReportRequestDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            ReportResponseDTO reportResponseDTO = reportService.reportCustomer(addReportRequestDTO);
            responseDTO.setData(reportResponseDTO);
            if (reportResponseDTO != null) {
                responseDTO.setSuccessCode(SuccessCode.REPORT_SUCCESS);
            } else {
                responseDTO.setErrorCode(ErrorCode.REPORT_FAIL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.REPORT_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }
}
