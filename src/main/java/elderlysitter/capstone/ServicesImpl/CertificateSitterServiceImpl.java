package elderlysitter.capstone.ServicesImpl;

import elderlysitter.capstone.Services.CertificateSitterService;
import elderlysitter.capstone.entities.CertificateSitter;
import elderlysitter.capstone.repository.CertificateSitterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CertificateSitterServiceImpl implements CertificateSitterService {

    @Autowired
    CertificateSitterRepository certificateSitterRepository;


    @Override
    public List<CertificateSitter> getAllCertificateByEmail(String email) {
        return certificateSitterRepository.findAllBySitterProfile_User_Email(email);
    }
}
