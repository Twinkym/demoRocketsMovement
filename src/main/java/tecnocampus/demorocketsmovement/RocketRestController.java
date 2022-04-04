package tecnocampus.demorocketsmovement;


import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class RocketRestController {

    private RocketRepository rocketRepository;
    private PropellantRepository propellantRepository;

    public RocketRestController(RocketRepository rocketRepository, PropellantRepository propellantRepository) {
        this.rocketRepository = rocketRepository;
        this.propellantRepository = propellantRepository;
    }

    @PutMapping("/rockets/{rocketId}")
    public Rocket updateRepository(@PathVariable Long rocketId, @RequestBody Rocket rocket){
        return rocketRepository.save(rocket);
    }

    @PutMapping("/rockets/{rocketId}/propellants/{propellantId}")
    public Propellant updateMaxPower(@PathVariable Long propellantId, @RequestBody int maxPower, @PathVariable String rocketId) {
        Propellant propellant = propellantRepository.findById(propellantId).get();
        propellant.setMaxPower(maxPower);
        return propellantRepository.save(propellant);
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

    @DeleteMapping("/rockets")
    public List<Rocket> deleteRockets() {
        rocketRepository.deleteAll();
        return rocketRepository.findAll();
    }

    @DeleteMapping("/rockets/{rocketId}")
    public void deleteRocket(@PathVariable Long rocketId) { rocketRepository.deleteById(rocketId);}

    @DeleteMapping("/Propellant/propellants")
    public List<Propellant> deletePropellants(@PathVariable Long rocketId) {
        return null;
    }

    @DeleteMapping("/propellants/{propellantId}")
    public void deletePropellant(@PathVariable Long propellantId){
        propellantRepository.deleteById(propellantId);
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
