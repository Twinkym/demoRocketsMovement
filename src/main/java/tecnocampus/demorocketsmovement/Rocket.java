package tecnocampus.demorocketsmovement;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Rocket {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "rocket")
    @JsonManagedReference
    private List<Propellant> propellants = new ArrayList<>();

    public Rocket() {
    }

    public Rocket(Long id, String name, ArrayList<Integer> propellants) throws Exception {
        this.id = id;
        this.name = name;
        createPropellants(propellants);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    private void createPropellants(ArrayList<Integer> propellants) throws Exception {
        checkNumberOfPropellants(propellants);
        for (Integer current : propellants) {
            this.propellants.add(new Propellant(current));
        }
    }

    private void checkNumberOfPropellants(List propellants) throws Exception {
        if (propellants.size() == 0) throw new Exception("The number of propellants can't be empty. ");
    }

    public Long getId() {
        return id;
    }

    public List<Propellant> getPropellants() {
        return this.propellants;
    }


    public void setPropellants(List<Propellant> propellant) {
        this.propellants = propellants;
    }


    public void ThrottlePropellants() {
        for (Propellant propellant : propellants) {
            propellant.throttle();
        }
    }

    public void brakePropellants() {
        for (Propellant propellant : propellants) {
            propellant.brake();
        }
    }

    @JsonProperty("currentPower")
    public int currentPower() {
        return propellants.stream().mapToInt(Propellant::getCurrentPower).sum();
    }

    @Override
    public String toString() {
        return id + ": " + propellants;
    }
}