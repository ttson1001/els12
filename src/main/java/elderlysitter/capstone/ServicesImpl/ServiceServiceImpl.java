package elderlysitter.capstone.ServicesImpl;

import elderlysitter.capstone.Services.ServiceService;
import elderlysitter.capstone.dto.ServiceRequestDTO;
import elderlysitter.capstone.entities.Category;
import elderlysitter.capstone.entities.Service;
import elderlysitter.capstone.repository.CategoryRepository;
import elderlysitter.capstone.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceService {
    @Autowired
    ServiceRepository serviceRepository;

    @Autowired
    CategoryRepository categoryRepository;


    @Override
    public Service createService(ServiceRequestDTO serviceRequestDTO) {

        Service newService = serviceRepository.save(Service.builder()
                        .name(serviceRequestDTO.getName())
                        .price(serviceRequestDTO.getPrice())
                        .description(serviceRequestDTO.getDescription())
                        .url(serviceRequestDTO.getUrl())
                        .sitterRequirement(serviceRequestDTO.getSitterRequirement())
                        .duration(serviceRequestDTO.getDuration())
                        .status("ACTIVE")
                        .category(categoryRepository.findById(serviceRequestDTO.getCategoryID()).get())
                        .build());
        return newService;
    }

    public Service updateService(ServiceRequestDTO serviceRequestDTO){
        Service oldService = serviceRepository.findById(serviceRequestDTO.getId()).get();
        oldService.setName(serviceRequestDTO.getName());
        oldService.setDescription(serviceRequestDTO.getDescription());
        oldService.setPrice(serviceRequestDTO.getPrice());
        oldService.setUrl(serviceRequestDTO.getUrl());
        oldService.setSitterRequirement(serviceRequestDTO.getSitterRequirement());
        oldService.setDuration(serviceRequestDTO.getDuration());
        oldService.setCategory(categoryRepository.findById(serviceRequestDTO.getCategoryID()).get());

        return serviceRepository.save(oldService);
    }

    public List<Service> getAllService(){
        try {
            ArrayList<Service> services = (ArrayList<Service>) serviceRepository.findAll();
            return  services;
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Category> getAllCategory() {
        try {
            ArrayList<Category> list = (ArrayList<Category>) categoryRepository.findAll();
            return list;
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Service removeService(Long id) {
        Service service = serviceRepository.findById(id).get();
        try {
            service.setStatus("DEACTIVE");
            return service;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Service activeService(Long id) {
        Service service = serviceRepository.findById(id).get();
        try {
            service.setStatus("ACTIVE");
            return service;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }



}
