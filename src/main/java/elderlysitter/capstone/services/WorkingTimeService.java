package elderlysitter.capstone.services;

import elderlysitter.capstone.dto.ReduceDateDTO;
import elderlysitter.capstone.dto.WorkingTimeDTO;
import elderlysitter.capstone.entities.WorkingTime;

import java.util.List;

public interface WorkingTimeService {
    List<WorkingTimeDTO> reduceDateFromBooking(ReduceDateDTO reduceDateDTO);
    List<WorkingTimeDTO> getAllWorkingTimeByBooking_IdAndStatus(Long bookingId, String Status);
}
