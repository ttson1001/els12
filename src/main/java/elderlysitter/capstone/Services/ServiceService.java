package elderlysitter.capstone.Services;

import elderlysitter.capstone.dto.ServiceRequestDTO;
import elderlysitter.capstone.entities.Category;
import elderlysitter.capstone.entities.Service;

import java.util.List;

public interface ServiceService {
    Service createService(ServiceRequestDTO serviceRequestDTO);

    Service updateService(ServiceRequestDTO serviceRequestDTO);

    List<Service> getAllService();

    List<Category> getAllCategory();

    Service removeService(Long id);

    Service activeService(Long id);
}

