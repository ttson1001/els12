package elderlysitter.capstone.Services;

import elderlysitter.capstone.dto.ServiceDTO;
import elderlysitter.capstone.entities.Service;

import java.util.List;

public interface ServiceService {
    Service createService(ServiceDTO serviceDTO);

    Service updateService(ServiceDTO serviceDTO);

    List<Service> getAllService();
}

