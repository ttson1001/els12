package elderlysitter.capstone.servicesImpl;

import elderlysitter.capstone.dto.request.AddBookingRequestDTO;
import elderlysitter.capstone.dto.request.AddBookingServiceRequestDTO;
import elderlysitter.capstone.dto.request.AddWorkingTimesRequestDTO;
import elderlysitter.capstone.entities.SitterService;
import elderlysitter.capstone.entities.User;
import elderlysitter.capstone.entities.WorkingTime;
import elderlysitter.capstone.repository.UserRepository;
import elderlysitter.capstone.repository.WorkingTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TestServiceImpl {
    @Autowired
    UserRepository userRepository;

    @Autowired
    WorkingTimeRepository workingTimeRepository;

    private User randomSitter(AddBookingRequestDTO addBookingRequestDTO, String email) {

        LocalDate now = LocalDate.now();
        List<User> sitters = userRepository.findAllByRole_NameAndStatus("SITTER", "ACTIVATE");
        sitters.stream().sorted(Comparator.comparing(User::getCreateDate).reversed()).collect(Collectors.toList());
        List<AddBookingServiceRequestDTO> addBookingServiceRequestDTOS = addBookingRequestDTO.getAddBookingServiceRequestDTOS();
        List<User> users = new ArrayList<>();
        for (User sitter : sitters) {
            if (!sitter.getEmail().equalsIgnoreCase(email)) {
                boolean checkDate = false;
                boolean checkCreateDate = false;
                boolean checkWorkingTime = false;
                int countWorkingTime = 0;
                boolean checkService = false;
                int count = 0;
                if (sitter.getToken() != null) {
                    // check date < 10 days
                    if ((sitter.getCreateDate().plusDays(10).compareTo(now)) >= 0) {
                        checkService = true;
                    }
                    //
                    List<WorkingTime> workingTimes = workingTimeRepository.findAllByBooking_User_IdAndStatus(sitter.getId(), "ACTIVATE");
                    List<AddWorkingTimesRequestDTO> addWorkingTimesRequestDTOs = addBookingRequestDTO.getAddWorkingTimesDTOList();
                    for (AddWorkingTimesRequestDTO addWorkingTimesRequestDTO : addWorkingTimesRequestDTOs) {
                        for (WorkingTime workingTime : workingTimes) {
                            if (addWorkingTimesRequestDTO.getStartDateTime().toLocalDate().equals(workingTime.getStartDateTime().toLocalDate())) {
                                boolean check1 = addWorkingTimesRequestDTO.getStartDateTime().isBefore(workingTime.getStartDateTime());
                                boolean check2 = addWorkingTimesRequestDTO.getStartDateTime().isBefore(workingTime.getEndDateTime());
                                boolean check3 = addWorkingTimesRequestDTO.getStartDateTime().isAfter(workingTime.getStartDateTime());
                                if (!((check1 == true && check2 == true) || check3 == true)) {
                                    countWorkingTime++;
                                }
                            }
                        }
                    }

                    if(countWorkingTime > 0) {
                        checkWorkingTime = true;
                    }

                    List<SitterService> sitterServices = sitter.getSitterProfile().getSitterService();
                    for (SitterService sitterService : sitterServices) {
                        for (AddBookingServiceRequestDTO addbookingServiceRequestDTO : addBookingServiceRequestDTOS) {
                            if (addbookingServiceRequestDTO.getId() == sitterService.getService().getId())
                                count = count + 1;
                        }
                    }

                    if((count>0 && count <= addBookingServiceRequestDTOS.size())){
                        checkService = true;
                    }

                    if(checkCreateDate == true)
                    if(checkCreateDate == true && checkDate == true && checkService == true && checkWorkingTime == true){
                        return sitter;
                    }
                }
            }
        }

        return null;
    }

    public static void main(String[] args) {
//        String s = "2022-11-28";
//        String now ="2022-12-08";
//        LocalDate now_ = LocalDate.parse(now);
//        LocalDate date = LocalDate.now();
//        LocalDate dateS = LocalDate.parse(s).plusDays(10);

        List<LocalDate> localDates = new ArrayList<>();
        String sitterStartTime = "2022-11-28T06:02:00";
        String sitterEndTime = "2022-12-28T07:01:00";
        LocalDateTime dateTimeS1 = LocalDateTime.parse(sitterStartTime);
        LocalDateTime dateTimeS2 = LocalDateTime.parse(sitterEndTime);

        String time1 = "2022-01-28T08:58:00";
        String time2 = "2022-02-28T10:58:00";
        LocalDateTime dateTime1 = LocalDateTime.parse(time1);
        LocalDateTime dateTime2 = LocalDateTime.parse(time2);
        System.out.println("ngày bắt đầu vs ngày bắt đầu : " + dateTimeS1.isBefore(dateTime1));
        System.out.println("ngày kết thúc vs ngày bắt đầu :" + dateTimeS2.isBefore(dateTime1));

        System.out.println("ngày bắt đầu vs ngày kết thúc : " + dateTimeS1.isAfter(dateTime2));

        if (!((dateTimeS1.isBefore(dateTime1) == true && dateTimeS2.isBefore(dateTime1) == true) || dateTimeS1.isAfter(dateTime2) == true)) {
            System.out.println("khong dc phép đặc lịch");
        }
        localDates.add(dateTime1.toLocalDate());
        localDates.add(dateTime2.toLocalDate());
        localDates.add(dateTimeS2.toLocalDate());
        localDates.add(dateTimeS1.toLocalDate());

        Collections.sort(localDates);
        for (LocalDate date: localDates
             ) {
            System.out.println(date);
        }
    }
}
