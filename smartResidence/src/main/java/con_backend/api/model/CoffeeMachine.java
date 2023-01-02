package con_backend.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.PrePersist;

import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Entity
@Table(name = "coffee_machines")
@JsonPropertyOrder({"name", "id", "isOn", "coffee_time", "strength", "water_lvl", "add_water","current_power_usage", "serialNumber"})
public class CoffeeMachine {

    private final String NAME = "CoffeeMachine";

    @Id
    @GeneratedValue
    private Long id;
    private Boolean isOn;
    private String coffee_time;
    private String strength;
    private int water_lvl;
    private Boolean add_water;
    private int current_power_usage;
    private String serialNumber;
 
    public CoffeeMachine() {}

    public String getName() {
        return this.NAME;
    }

    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
 
    @Column(name = "isOn", nullable = false)
    public Boolean getIsOn() {
        return this.isOn;
    }
    public void setIsOn(Boolean isOn) {
        this.isOn = isOn;
    }
 
    @Column(name = "coffee_time", nullable = false)
    public String getCoffee_time() {
        return this.coffee_time;
    }
    public void setCoffee_time(String coffee_time) {
        this.coffee_time = coffee_time;
    }

    @Column(name = "strength", nullable = false)
    public String getStrength() {
        return this.strength;
    }
    public void setStrength(String strength) {
        this.strength = strength;
    }

    @Column(name = "water_lvl", nullable = false)
    public int getWater_lvl() {
        return this.water_lvl;
    }
    public void setWater_lvl(int water_lvl) {
        this.water_lvl = water_lvl;
    }

    @Column(name = "add_water", nullable = false)
    public Boolean getAdd_water() {
        return this.add_water;
    }
    public void setAdd_water(Boolean add_water) {
        this.add_water = add_water;
    }

    @Column(name = "current_power_usage", nullable = false)
    public int getCurrent_power_usage() {
        return this.current_power_usage;
    }
    public void setCurrent_power_usage(int powerUsage) {
        this.current_power_usage = powerUsage;
    }

    public String getSerialNumber() {
        return this.serialNumber;
    }
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    @PrePersist
    public void autoFill() {
        this.setSerialNumber(UUID.randomUUID().toString());
    }
}
