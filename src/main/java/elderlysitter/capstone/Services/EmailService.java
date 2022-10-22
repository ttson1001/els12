package elderlysitter.capstone.Services;

import elderlysitter.capstone.dto.EmailDTO;

public interface EmailService {
    String sendSimpleMail(EmailDTO details);
}

