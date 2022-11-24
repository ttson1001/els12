package elderlysitter.capstone.services;

import elderlysitter.capstone.dto.SitterDTO;
import elderlysitter.capstone.dto.request.ChangePasswordDTO;
import elderlysitter.capstone.dto.request.UpdateSalaryRequestDTO;
import elderlysitter.capstone.dto.request.UpdateSitterRequestDTO;
import elderlysitter.capstone.dto.response.SitterResponseDTO;
import elderlysitter.capstone.dto.response.SittersResponseDTO;
import elderlysitter.capstone.dto.response.UpdateSalaryResponseDTO;

import java.util.List;

public interface SitterService {
    List<SittersResponseDTO> getAllSitter();

    SitterDTO activate (Long id);

    SitterDTO deactivate(Long id);

    SitterResponseDTO getSitterById(Long id);

    SitterResponseDTO getSitterByEmail(String email);

    SitterDTO updateSitter (UpdateSitterRequestDTO updateSitterRequestDTO);

    SitterDTO changePassword(ChangePasswordDTO changePasswordDTO);

    UpdateSalaryResponseDTO updateSalary(UpdateSalaryRequestDTO updateSalaryRequestDTO);

    List<UpdateSalaryResponseDTO> getAllFormUpdateSalary();

    UpdateSalaryResponseDTO approveUpSalary(Long sitterId);

    UpdateSalaryResponseDTO rejectUpSalary(Long sitterId);
    


}
