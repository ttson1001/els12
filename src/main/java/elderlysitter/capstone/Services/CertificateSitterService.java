package elderlysitter.capstone.Services;

import elderlysitter.capstone.entities.CertificateSitter;

import java.util.List;

public interface CertificateSitterService {
    List<CertificateSitter> getAllCertificateByEmail(String email);
}
