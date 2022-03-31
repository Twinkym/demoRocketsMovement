package tecnocampus.demorocketsmovement;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Propellant {

   @Id
   @GeneratedValue
    private Long id;
    private int maxPower;
    private int currentPower = 0;
    private int POWER_STEP = 10;

    @ManyToOne
    @JsonBackReference
    private Rocket rocket;

    public Propellant() {
    }

    public Propellant( int maxPower) throws Exception {
        checkMaxPower(maxPower);
        this.maxPower = maxPower;
    }

    private void checkMaxPower(int maxPower) throws Exception {
        if (maxPower <= 0) throw new Exception("THe Power must be greater than 0. ");
    }

    public Integer getCurrentPower() {
        return currentPower;
    }

    public void throttle() {
        currentPower += POWER_STEP;
        if (currentPower > maxPower){
            currentPower = maxPower;
        }
    }

    public void brake() {
        currentPower -= POWER_STEP;
        if (currentPower < 0) {
            currentPower = 0;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMaxPower() {
        return maxPower;
    }

    public void setMaxPower(int maxPower) {
        this.maxPower = maxPower;
    }

    public void setCurrentPower(Integer currentPower) {
        this.currentPower = currentPower;
    }

    public Rocket getRocket() {
        return rocket;
    }

    public void setRocket(Rocket rocket) {
        this.rocket = rocket;
    }

    @Override
    public String toString() {
        return "" + maxPower;

    }
}
