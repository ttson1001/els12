package elderlysitter.capstone.services;

import elderlysitter.capstone.dto.request.AddReportRequestDTO;
import elderlysitter.capstone.dto.response.ReportResponseDTO;

public interface ReportService {
    ReportResponseDTO reportCustomer(AddReportRequestDTO addReportRequestDTO);
}
