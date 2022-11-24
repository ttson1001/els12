package elderlysitter.capstone.services;

import elderlysitter.capstone.dto.request.AddReportRequestDTO;
import elderlysitter.capstone.dto.response.ReportResponseDTO;

import java.util.List;

public interface ReportService {
    ReportResponseDTO reportCustomer(AddReportRequestDTO addReportRequestDTO);

    List<ReportResponseDTO> getAllReportCustomer();
}
