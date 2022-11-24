package con_backend.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "devices")
public class Device {

    enum State {
        ON,
        OFF
    }
    

    private long id;
    private String name;
    private State state;
 
    public Device() {
  
    }
 
    public Device(String name, State state) {
         this.name = name;
         this.state = state;
    }
 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
        public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
 
    @Column(name = "name", nullable = false)
    public String getname() {
        return name;
    }
    public void setname(String name) {
        this.name = name;
    }
 
    @Column(name = "state", nullable = false)
    public State getstate() {
        return state;
    }
    public void setState(State state) {
        this.state = state;
    }


    @Override
    public String toString() {
        return "Device [id=" + id + ", name=" + name + ", state=" + state
       + "]";
    }

}