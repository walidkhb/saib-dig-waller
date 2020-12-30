package sa.com.saib.dig.wlt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sa.com.saib.dig.wlt.domain.Authority;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {}
