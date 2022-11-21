package elderlysitter.capstone.servicesImpl;

import elderlysitter.capstone.dto.ReduceDateDTO;
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
    public List<WorkingTime> reduceDateFromBooking(ReduceDateDTO reduceDateDTO) {
        List<WorkingTime> workingTimes = new ArrayList<>();
        try {
            workingTimes = workingTimeRepository.findAllByBooking_Id(reduceDateDTO.getBookingId());
            List<LocalDate> localDates = reduceDateDTO.getDate();
            for (WorkingTime workingTime1 : workingTimes
            ) {
                for (LocalDate date : localDates
                ) {
                    if(workingTime1.getEndDateTime().toLocalDate().isEqual(date)){
                        workingTime1.setStatus(StatusCode.CANCEL.toString());
                        workingTimeRepository.save(workingTime1);
                    }
                }
            }
            workingTimes = workingTimeRepository.findAllByBooking_Id(reduceDateDTO.getBookingId());
        }catch (Exception e){
            e.printStackTrace();
        }
        return workingTimes;

    }

    @Override
    public List<WorkingTime> getAllWorkingTimeByBooking_IdAndStatus(Long bookingId, String status) {
        List<WorkingTime> workingTimes = new ArrayList<>();
        try {
            workingTimes = workingTimeRepository.findAllByBooking_IdAndStatus(bookingId,status);
        }catch (Exception e){
            e.printStackTrace();
        }
        return workingTimes;
    }
}
