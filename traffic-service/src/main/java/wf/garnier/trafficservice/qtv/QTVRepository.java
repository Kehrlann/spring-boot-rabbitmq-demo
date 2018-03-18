package wf.garnier.trafficservice.qtv;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface QTVRepository extends JpaRepository<QTV, Long> {

    Collection<QTV> findByLocalisationContaining(String localisation);
}
