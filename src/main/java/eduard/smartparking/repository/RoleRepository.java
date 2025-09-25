package eduard.smartparking.repository;

import eduard.smartparking.model.ERole;
import eduard.smartparking.model.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Integer> {
    Optional<Role> findByName(ERole name);

}
