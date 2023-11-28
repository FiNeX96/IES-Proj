package mysweethome.MSHbackend.Sensors;

import com.rabbitmq.client.Channel;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;

public class TemperatureSensor {

    Channel broker_queue = null;
    String queue_name = null, device_category, name;
    private static final ObjectMapper MAPPER = new ObjectMapper();
    Random RANDOM = new Random();
    int lower_bound = 20, higher_bound = 25; // may be changed by user
    String uniqueID = UUID.randomUUID().toString();


    public TemperatureSensor(String name, Channel queue, String queue_name, String device_category) {
        this.broker_queue = queue;
        this.queue_name = queue_name;
        this.device_category = device_category;
        this.name = name;
    }

    public void run() {

        try {
            // send register message
            String register_msg = MAPPER
                    .writeValueAsString(Map.of("register_msg", "1",
                            "device_category", device_category, "name", name, "device_id", uniqueID));
            broker_queue.basicPublish("", this.queue_name, null, register_msg.getBytes());
            // System.out.println(" [TemperatureSensor] Sent '" + register_msg + "'");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        while (true) {
            // send normal messages every 3sec
            try {
                String message = MAPPER.writeValueAsString(
                        Map.of(
                                "timestamp", String.valueOf(System.currentTimeMillis()), "sensor_information",
                                String.valueOf(RANDOM.nextInt(higher_bound - lower_bound + 1) + lower_bound), "device_id", uniqueID));
                broker_queue.basicPublish("", this.queue_name, null, message.getBytes());
                // System.out.println(" [TemperatureSensor] Sent '" + message + "'");
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int getLower_bound() {
        return lower_bound;
    }

    public void setLower_bound(int lower_bound) {
        this.lower_bound = lower_bound;
    }

    public int getHigher_bound() {
        return higher_bound;
    }

    public void setHigher_bound(int higher_bound) {
        this.higher_bound = higher_bound;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
