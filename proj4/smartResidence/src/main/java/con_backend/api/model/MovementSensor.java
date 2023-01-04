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
@Table(name = "movement_sensors")
@JsonPropertyOrder({"name", "id", "isOn", "location", "trigger_dates", "current_power_usage", "serialNumber"})
public class MovementSensor {

    private final String NAME = "MovementSensor";

    @Id
    @GeneratedValue
    private Long id;
    private Boolean isOn;
    private String location;
    private String trigger_dates;
    private int current_power_usage;
    private String serialNumber;
 
    public MovementSensor() {}

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

    @Column(name = "trigger_dates", nullable = false)
    public String getTrigger_dates() {
        return this.trigger_dates;
    }
    public void setTrigger_dates(String trigger_dates) {
        this.trigger_dates = trigger_dates;
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
