package elderlysitter.capstone.controller;

import elderlysitter.capstone.Services.CertificateSitterService;
import elderlysitter.capstone.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("certificate")
public class CertificateSitterController {
    @Autowired
    CertificateSitterService certificateSitterService;

    @GetMapping("{candidateEmail}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> getcer(@PathVariable String candidateEmail) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(certificateSitterService.getAllCertificateByEmail(candidateEmail));
        return ResponseEntity.ok().body(responseDTO);
    }
}
