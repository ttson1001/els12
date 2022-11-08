package elderlysitter.capstone.services;

import elderlysitter.capstone.dto.EmailDTO;

public interface EmailService {
    String sendSimpleMail(EmailDTO dto);
}
