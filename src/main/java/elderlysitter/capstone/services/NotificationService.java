package elderlysitter.capstone.services;

import elderlysitter.capstone.dto.MyNotificationDTO;
import elderlysitter.capstone.dto.NotificationResponseDTO;

public interface NotificationService {
    NotificationResponseDTO sendNotification(Long bookingId, String title, String body);
}
