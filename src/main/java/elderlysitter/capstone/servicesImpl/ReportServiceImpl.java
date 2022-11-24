package elderlysitter.capstone.servicesImpl;

import elderlysitter.capstone.dto.SitterDTO;
import elderlysitter.capstone.dto.request.AddReportRequestDTO;
import elderlysitter.capstone.dto.response.CustomerResponseDTO;
import elderlysitter.capstone.dto.response.ReportResponseDTO;
import elderlysitter.capstone.entities.Booking;
import elderlysitter.capstone.entities.Report;
import elderlysitter.capstone.repository.BookingRepository;
import elderlysitter.capstone.repository.ReportRepository;
import elderlysitter.capstone.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    ReportRepository reportRepository;

    @Autowired
    BookingRepository bookingRepository;

    @Override
    public ReportResponseDTO reportCustomer(AddReportRequestDTO addReportRequestDTO) {
        ReportResponseDTO reportResponseDTO = null;
        try {
            Booking booking = bookingRepository.findById(addReportRequestDTO.getBookingId()).get();
            CustomerResponseDTO customerResponseDTO = CustomerResponseDTO.builder()
                    .fullName(booking.getUser().getFullName())
                    .dob(booking.getUser().getDob())
                    .gender(booking.getUser().getGender())
                    .phone(booking.getUser().getPhone())
                    .email(booking.getUser().getEmail())
                    .build();
            Report report = Report.builder()
                    .booking(booking)
                    .customer(booking.getUser())
                    .comment(addReportRequestDTO.getComment())
                    .build();
            report = reportRepository.save(report);
            reportResponseDTO = ReportResponseDTO.builder()
                    .id(report.getId())
                    .comment(report.getComment())
                    .customerResponseDTO(customerResponseDTO)
                    .build();
        }catch (Exception e){
            e.printStackTrace();
        }
        return reportResponseDTO;
    }

    @Override
    public List<ReportResponseDTO> getAllReportCustomer() {
        List<ReportResponseDTO> reportResponseDTOS = new ArrayList<>();
        try {
            List<Report> reports = reportRepository.findAll();
            for (Report report: reports
                 ) {
                CustomerResponseDTO customerResponseDTO = CustomerResponseDTO.builder()
                        .fullName(report.getCustomer().getFullName())
                        .dob(report.getCustomer().getDob())
                        .gender(report.getCustomer().getGender())
                        .phone(report.getCustomer().getPhone())
                        .email(report.getCustomer().getEmail())
                        .build();
                ReportResponseDTO reportResponseDTO = ReportResponseDTO.builder()
                        .id(report.getId())
                        .comment(report.getComment())
                        .customerResponseDTO(customerResponseDTO)
                        .build();
                reportResponseDTOS.add(reportResponseDTO);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return reportResponseDTOS;
    }
}
