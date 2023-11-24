package mysweethome.MSHbackend.Models;

import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
import javax.validation.constraints.NotBlank;

@ToString
@Document("sensors")
public class DataSource extends Device {

    @Id
    private String ID;
    @NotBlank
    private int device_category;
    @NotBlank
    private String device_location;

    public DataSource(String device_id, int device_category, String device_location) {
        this.ID = device_id;
        this.device_category = device_category;
        this.device_location = device_location;
    }

    public DataSource() {
    }

    public String getDevice_id() {
        return ID;
    }

    public void setDevice_id(String device_id) {
        this.ID = device_id;
    }

    public int getDevice_category() {
        return device_category;
    }

    public void setDevice_category(int device_category) {
        this.device_category = device_category;
    }

    public String getDevice_location() {
        return device_location;
    }

    public void setDevice_location(String device_location) {
        this.device_location = device_location;
    }

}
