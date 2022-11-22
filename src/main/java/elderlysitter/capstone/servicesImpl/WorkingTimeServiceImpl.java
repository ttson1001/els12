package elderlysitter.capstone.servicesImpl;

import elderlysitter.capstone.dto.ReduceDateDTO;
import elderlysitter.capstone.dto.WorkingTimeDTO;
import elderlysitter.capstone.entities.WorkingTime;
import elderlysitter.capstone.enumCode.StatusCode;
import elderlysitter.capstone.repository.WorkingTimeRepository;
import elderlysitter.capstone.services.WorkingTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Service
public class WorkingTimeServiceImpl implements WorkingTimeService {
    @Autowired
    private WorkingTimeRepository workingTimeRepository;

    @Override
    public List<WorkingTimeDTO> reduceDateFromBooking(ReduceDateDTO reduceDateDTO) {
        List<WorkingTimeDTO> workingTimeDTOS = new ArrayList<>();
        try {
        List<WorkingTime>   workingTimes = workingTimeRepository.findAllByBooking_Id(reduceDateDTO.getBookingId());
            List<LocalDate> localDates = reduceDateDTO.getDate();
            for (WorkingTime workingTime : workingTimes
            ) {
                for (LocalDate date : localDates
                ) {
                    if(workingTime.getEndDateTime().toLocalDate().isEqual(date)){
                        workingTime.setStatus(StatusCode.CANCEL.toString());
                        workingTimeRepository.save(workingTime);
                    }
                }
            }
            workingTimes = workingTimeRepository.findAllByBooking_Id(reduceDateDTO.getBookingId());
            for (WorkingTime workingTime: workingTimes
            ) {
                WorkingTimeDTO workingTimeDTO = WorkingTimeDTO.builder()
                        .bookingId(workingTime.getBooking().getId())
                        .Date(workingTime.getStartDateTime().toLocalDate())
                        .status(workingTime.getStatus())
                        .build();
                workingTimeDTOS.add(workingTimeDTO);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return workingTimeDTOS;

    }

    @Override
    public List<WorkingTimeDTO> getAllWorkingTimeByBooking_IdAndStatus(Long bookingId, String status) {
        List<WorkingTimeDTO> workingTimeDTOS = new ArrayList<>();
        try {
        List<WorkingTime>   workingTimes = workingTimeRepository.findAllByBooking_Id(bookingId);
            for (WorkingTime workingTime: workingTimes
                 ) {
                WorkingTimeDTO workingTimeDTO = WorkingTimeDTO.builder()
                        .bookingId(workingTime.getBooking().getId())
                        .Date(workingTime.getStartDateTime().toLocalDate())
                        .status(workingTime.getStatus())
                        .build();
                workingTimeDTOS.add(workingTimeDTO);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return workingTimeDTOS;
    }
}
