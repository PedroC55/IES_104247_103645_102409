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
@Table(name = "light_bulbs")
@JsonPropertyOrder({"name", "id", "isOn", "location", "brightnessLvl", "color", "current_power_usage", "serialNumber"})
public class LightBulb {

    private final String NAME = "LightBulb";

    @Id
    @GeneratedValue
    private Long id;
    private Boolean isOn;
    private String location;
    private int brightnessLvl;
    private String color;
    private int current_power_usage;
    private String serialNumber;
 
    public LightBulb() {
  
    }

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
 
    @Column(name = "location", nullable = false)
    public String getLocation() {
        return this.location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
 
    @Column(name = "color", nullable = false)
    public String getColor() {
        return this.color;
    }
    public void setColor(String color) {
        this.color = color;
    }

    @Column(name = "brightnessLvl", nullable = false)
    public int getBrightnessLvl() {
        return this.brightnessLvl;
    }
    public void setBrightnessLvl(int brightnessLvl) {
        this.brightnessLvl = brightnessLvl;
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
