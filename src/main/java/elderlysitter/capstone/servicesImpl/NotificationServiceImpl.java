package elderlysitter.capstone.servicesImpl;

import elderlysitter.capstone.dto.MyNotificationDTO;
import elderlysitter.capstone.dto.NotificationResponseDTO;
import elderlysitter.capstone.entities.Booking;
import elderlysitter.capstone.entities.User;
import elderlysitter.capstone.repository.BookingRepository;
import elderlysitter.capstone.repository.UserRepository;
import elderlysitter.capstone.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    UserRepository userRepository;
    private final String uri = "https://fcm.googleapis.com/fcm/send";
    private final String key = "Authorization";
    private final String value = "key=AAAA3DkXm68:APA91bFojmlMVxQcyjb1MAH5U_d-M6JCKa4FQzj7Z02XnZuGJ8tAIZpfMoIMSiCsJKczwXL-F8GzITTh1lM-LVPIRr9Ah4Pln1K3ZK-AoQFkV779mVu98mp_pC0ImkHDEke8-sGWQLtW";
    @Override
    public NotificationResponseDTO sendNotification(Long userId, String title, String body) {
        RestTemplate restTemplate = new RestTemplate();
        NotificationResponseDTO responseDTO = null;
        try {
            User user = userRepository.findById(userId).get();
            MyNotificationDTO myNotificationDTO = MyNotificationDTO.builder()
                    .title(title)
                    .body(body)
                    .build();
            responseDTO = NotificationResponseDTO.builder()
                    .to(user.getToken())
                    .notification(myNotificationDTO)
                    .build();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set(key,value);
            HttpEntity<NotificationResponseDTO> request = new HttpEntity<>(responseDTO,httpHeaders);
            restTemplate.postForObject(uri,request,NotificationResponseDTO.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return responseDTO;
    }
}
