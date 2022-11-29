package elderlysitter.capstone.servicesImpl;

import elderlysitter.capstone.dto.request.AddBookingRequestDTO;
import elderlysitter.capstone.dto.request.AddWorkingTimesRequestDTO;
import elderlysitter.capstone.entities.User;
import elderlysitter.capstone.entities.WorkingTime;
import elderlysitter.capstone.repository.UserRepository;
import elderlysitter.capstone.repository.WorkingTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TestServiceImpl {
    @Autowired
    UserRepository userRepository;

    @Autowired
    WorkingTimeRepository workingTimeRepository;

    private List<User> randomSitter(AddBookingRequestDTO addBookingRequestDTO) {
        int checkDate = 0;
        LocalDate now = LocalDate.now();
        List<User> sitters = userRepository.findAllByRole_NameAndStatus("SITTER", "ACTIVATE");
        List<User> users = new ArrayList<>();
        for (User sitter : sitters) {
            checkDate = sitter.getCreateDate().compareTo(now);
            if (sitter.getToken() != null) {
                if (checkDate >= 0) {
                    List<WorkingTime> workingTimes = workingTimeRepository.findAllByBooking_User_IdAndStatus(sitter.getId(), "ACTIVATE");
                    List<AddWorkingTimesRequestDTO> addWorkingTimesRequestDTOs = addBookingRequestDTO.getAddWorkingTimesDTOList();
                    for (AddWorkingTimesRequestDTO addWorkingTimesRequestDTO : addWorkingTimesRequestDTOs) {
                        for (WorkingTime workingTime : workingTimes) {
                            if (addWorkingTimesRequestDTO.getStartDateTime().toLocalDate().equals(workingTime.getStartDateTime().toLocalDate())) {
                                boolean check1 = addWorkingTimesRequestDTO.getStartDateTime().isBefore(workingTime.getStartDateTime());
                                boolean check2 = addWorkingTimesRequestDTO.getStartDateTime().isBefore(workingTime.getEndDateTime());
                                boolean check3 = addWorkingTimesRequestDTO.getStartDateTime().isAfter(workingTime.getStartDateTime());
                                if ((check1 == true && check2 == true) || check3 == true) {
                                    users.add(sitter);
                                }
                            }
                        }
                    }
                }
            }
        }
        return users;
    }

//    public User random(List<E>){
//
//    }

    public static void main(String[] args) {
//        String s = "2022-11-28";
//        String now ="2022-12-08";
//        LocalDate now_ = LocalDate.parse(now);
//        LocalDate date = LocalDate.now();
//        LocalDate dateS = LocalDate.parse(s).plusDays(10);
        String sitterStartTime = "2022-11-28T12:02:00";
        String sitterEndTime = "2022-11-28T13:01:00";
        LocalDateTime dateTimeS1 = LocalDateTime.parse(sitterStartTime);
        LocalDateTime dateTimeS2 = LocalDateTime.parse(sitterEndTime);

        String time1 = "2022-11-28T08:58:00";
        String time2 = "2022-11-28T10:58:00";
        LocalDateTime dateTime1 = LocalDateTime.parse(time1);
        LocalDateTime dateTime2 = LocalDateTime.parse(time2);
        System.out.println("ngày bắt đầu vs ngày bắt đầu : " + dateTimeS1.isBefore(dateTime1));
        System.out.println("ngày kết thúc vs ngày bắt đầu :" + dateTimeS2.isBefore(dateTime1));

        System.out.println("ngày bắt đầu vs ngày kết thúc : " + dateTimeS1.isAfter(dateTime2));

        if ((dateTimeS1.isBefore(dateTime1) == true && dateTimeS2.isBefore(dateTime1) == true) || dateTimeS1.isAfter(dateTime2) == true) {
            System.out.println("dc phép đặc lịch");
        }

    }
}
