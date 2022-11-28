package elderlysitter.capstone.servicesImpl;

import elderlysitter.capstone.dto.WorkingTimeDTO;
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
import java.util.List;

@Service
public class TestServiceImpl {
    @Autowired
    UserRepository userRepository;

    @Autowired
    WorkingTimeRepository workingTimeRepository;

    private String bookingSitter(AddBookingRequestDTO addBookingRequestDTO) {
        boolean checkCreateDate = true;
        boolean checkWorkingTime;
        boolean checkStatus;
        int checkDate = 0;
        LocalDate now = LocalDate.now();
        List<User> sitters = userRepository.findAllByRole_NameAndStatus("SITTER", "ACTIVATE");
        for (User user : sitters) {
            checkDate = user.getCreateDate().compareTo(now);
            if (checkDate < 0) {
                checkCreateDate = false;
            }
            List<WorkingTime> workingTimes = workingTimeRepository.findAllByBooking_User_IdAndStatus(user.getId(),"ACTIVATE");
            List<AddWorkingTimesRequestDTO> addWorkingTimesRequestDTOs = addBookingRequestDTO.getAddWorkingTimesDTOList();
            for (AddWorkingTimesRequestDTO addWorkingTimesRequestDTO : addWorkingTimesRequestDTOs) {
                for (WorkingTime workingTime: workingTimes) {
                    workingTime
                }
            }

        }
        return "son";
    }

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

        if((dateTimeS1.isBefore(dateTime1)==true&& dateTimeS2.isBefore(dateTime1)==true)|| dateTimeS1.isAfter(dateTime2) == true){
            System.out.println("dc phép đặc lịch");
        }

    }
}
