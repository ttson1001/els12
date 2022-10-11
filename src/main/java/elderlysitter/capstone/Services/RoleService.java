package elderlysitter.capstone.Services;

import elderlysitter.capstone.entities.Role;

public interface RoleService {
    Role findByName(String name);
}
