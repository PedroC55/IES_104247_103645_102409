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
@Table(name = "refrigerators")
@JsonPropertyOrder({"name", "id", "airFilter_change_date", "content_list", "current_power_usage", "serialNumber"})
public class Refrigerator {

    private final String NAME = "Refrigerator";

    @Id
    @GeneratedValue
    private Long id;
    private String airFilter_change_date;
    private String content_list;
    private int current_power_usage;
    private String serialNumber;
 
    public Refrigerator() {}

    public String getName() {
        return this.NAME;
    }


    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
 
    @Column(name = "airFilter_change_date", nullable = false)
    public String getAirFilter_change_date() {
        return this.airFilter_change_date;
    }
    public void setAirFilter_change_date(String airFilter_change_date) {
        this.airFilter_change_date = airFilter_change_date;
    }
 
    @Column(name = "content_list", nullable = false)
    public String getContent_list() {
        return this.content_list;
    }
    public void setContent_list(String content_list) {
        this.content_list = content_list;
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
