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
@Table(name = "vacuum_cleaners")
@JsonPropertyOrder({"name", "id", "isOn", "currentLocation", "cleaningMode", "remainingBattery", "current_power_usage", "serialNumber"})
public class Vacuum {

    private final String NAME = "Vacuum";

    @Id
    @GeneratedValue
    private Long id;

    private Boolean isOn;
    private String currentLocation;
    private String cleaningMode;
    private int remainingBattery;
    private int current_power_usage;
    private String serialNumber;
 
    public Vacuum() {
  
    }

    public Vacuum(Boolean isOn, String currentLocation, String cleaningMode, int remainingBattery) {
        this.isOn = isOn;
        this.currentLocation = currentLocation; 
        this.cleaningMode = cleaningMode;
        this.remainingBattery = remainingBattery;
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
 
    @Column(name = "currentLocation", nullable = false)
    public String getCurrentLocation() {
        return this.currentLocation;
    }
    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }
 
    @Column(name = "cleaningMode", nullable = false)
    public String getCleaningMode() {
        return this.cleaningMode;
    }
    public void setCleaningMode(String cleaningMode) {
        this.cleaningMode = cleaningMode;
    }

    @Column(name = "remainingBattery", nullable = false)
    public int getRemainingBattery() {
        return this.remainingBattery;
    }
    public void setRemainingBattery(int remainingBattery) {
        this.remainingBattery = remainingBattery;
    }

    @Column(name = "current_power_usage", nullable = false)
    public int getCurrent_power_usage() {
        return this.current_power_usage;
    }
    public void setCurrent_power_usage(int current_power_usage) {
        this.current_power_usage = current_power_usage;
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

    @Override
    public String toString() {
        return "Vacuum [id=" + this.id + ", serial_number=" + this.serialNumber.toString() + "]";
    }

}
