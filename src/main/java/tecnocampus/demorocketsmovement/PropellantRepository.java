package tecnocampus.demorocketsmovement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropellantRepository extends JpaRepository <Propellant, String>{
}
