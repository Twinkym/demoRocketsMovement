package tecnocampus.demorocketsmovement;


import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class RocketRestController {

    private RocketRepository rocketRepository;
    private PropellantRepository propellantRepository;

    public RocketRestController(RocketRepository rocketRepository) {
        this.rocketRepository = rocketRepository;
        this.propellantRepository = null;
    }

    @GetMapping("/rockets")
    public List<Rocket> getAllRockets(){
       return rocketRepository.findAll();
    }

    @GetMapping("/rockets/{id}")
    public Rocket getRocket(@PathVariable Long id){
        return rocketRepository.findById(id).get();
    }

    @PostMapping("/rockets")
    public Rocket createRocket(@RequestBody Rocket rocket){
        return rocketRepository.save(rocket);
    }

    @PostMapping("/rockets/{id}/propellants")
    public Propellant createPropellant(@PathVariable Long id,  @RequestBody Propellant propellant){
        Rocket rocket = rocketRepository.getById((id));
        propellant.setRocket(rocket);
        return propellantRepository.save(propellant);
    }

    @GetMapping("/rockets/{id}/propellants")
    public List<Propellant> getPropellants(@PathVariable Long id){
        Rocket rocket = rocketRepository.getById(id);
        return rocket.getPropellants();
    }

    @PostMapping("/rockets/{id}/movement")
    public Rocket moveRocket(@PathVariable Long id, @RequestBody Movement movement){
        Rocket rocket = rocketRepository.findById(id).get();

        for (int i = 0; i < movement.getTimes(); i++) {
            if (movement.getMovementType().equals(Movement.ACCELERATE)){
                rocket.ThrottlePropellants();
            }else if (movement.getMovementType().equals(Movement.DECELERATE)){
                rocket.brakePropellants();
            }

        }

         propellantRepository.saveAll(rocket.getPropellants());
        return rocket;
    }


}
