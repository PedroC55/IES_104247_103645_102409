package con_backend.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.codec.digest.DigestUtils;

@Entity
@Table(name = "vacuum_cleaners")
public class Vacuum {

    private static final String NAME = "Vacuum";

    private long id;
    private Boolean isOn;
    private String currentLocation;
    private String cleaningMode;
    private int remainingBattery;
    private String serialNumber;
 
    public Vacuum() {
  
    }

    public Vacuum(long id, Boolean isOn, String currentLocation, String cleaningMode, int remainingBattery,
		    String serialNumber) {
	this.id = id;
	this.isOn = isOn;
	this.currentLocation = currentLocation; 
	this.cleaningMode = cleaningMode;
	this.remainingBattery = remainingBattery;
	this.serialNumber = serialNumber;
    }
 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }
    public void setId(long id) {
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

    @Column(name = "serialNumber", nullable = false)
    public String getSerialNumber() {
        return this.serialNumber;
    }
    public void setSerialNumber(String serialNumber) {
        String final_serial = DigestUtils.sha256Hex(NAME + serialNumber);
        this.serialNumber = final_serial;
    }

    @Override
    public String toString() {
        return "Vacuum [id=" + this.id + ", serial_number=" + this.serialNumber + "]";
    }

}
