package elderlysitter.capstone.services;

import elderlysitter.capstone.dto.ServiceDTO;
import elderlysitter.capstone.dto.request.AddServiceRequestDTO;
import elderlysitter.capstone.dto.request.UpdateServiceRequestDTO;
import elderlysitter.capstone.entities.Service;

import java.util.List;

public interface ServiceService {
    Service addService (AddServiceRequestDTO addServiceRequestDTO);
    Service updateService(UpdateServiceRequestDTO updateServiceRequestDTO);

    Service activeService(Long id);

    Service deactivateService(Long id);

    List<Service>  getAllService();

    List<Service> getAllServiceByActive();

    Service getServiceById(Long id);

    List<ServiceDTO> reportService();
    List<Service> getAllServiceByCategoryID(Long id);

}
