package mysweethome.MSHbackend.Repositories;
import mysweethome.MSHbackend.Models.Alert;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AlertRepository extends MongoRepository<Alert, String> {

    // adicionar + cenas conforme for preciso
    
}