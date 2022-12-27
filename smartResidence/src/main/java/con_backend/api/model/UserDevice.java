package con_backend.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_device")
public class UserDevice {

    private long id;
    private long UserId;
    private long DeviceId;
    private String DeviceType;
    private String DeviceSerialNumber;

    public UserDevice() {}

    public UserDevice(long UserId, Long DeviceId, String DeviceType, String DeviceSerialNumber) {
	    this.UserId = UserId;
            this.DeviceId = DeviceId;
	    this.DeviceType = DeviceType;
	    this.DeviceSerialNumber = DeviceSerialNumber;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
        public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    @Column(name = "user_id")
    public long getUserId() {
        return this.UserId;
    }
    public void setUserId(long UserId) {
        this.UserId = UserId;
    }

    @Column(name = "device_id")
    public long getDeviceId() {
        return this.DeviceId;
    }
    public void setDeviceId(long DeviceId) {
        this.DeviceId = DeviceId;
    }

    @Column(name = "device_type")
    public String getDeviceType() {
        return this.DeviceType;
    }
    public void setDeviceType(String DeviceType) {
        this.DeviceType = DeviceType;
    }

    @Column(name = "device_serial")
    public String getDeviceSerialNumber() {
        return this.DeviceSerialNumber;
    }
    public void setDeviceSerialNumber(String DeviceSerialNumber) {
        this.DeviceSerialNumber = DeviceSerialNumber;
    }
}
