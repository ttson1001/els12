package elderlysitter.capstone.services;

import elderlysitter.capstone.dto.ReduceDateDTO;
import elderlysitter.capstone.entities.WorkingTime;

import java.util.List;

public interface WorkingTimeService {
    List<WorkingTime> reduceDateFromBooking(ReduceDateDTO reduceDateDTO);
    List<WorkingTime> getAllWorkingTimeByBooking_IdAndStatus(Long bookingId,String Status);
}
