package elderlysitter.capstone.ServicesImpl;

import elderlysitter.capstone.Services.RoleService;
import elderlysitter.capstone.entities.Role;
import elderlysitter.capstone.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleRepository roleRepository;

    @Override
    public Role findByName(String name) {
        Role role = new Role();
        try {
            role = roleRepository.findByName(name);
        }catch (Exception e){
            e.printStackTrace();
            return role;
        }
        return role;
    }
}
