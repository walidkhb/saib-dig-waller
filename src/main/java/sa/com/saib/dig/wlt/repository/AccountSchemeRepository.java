package sa.com.saib.dig.wlt.repository;

import sa.com.saib.dig.wlt.domain.AccountScheme;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AccountScheme entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AccountSchemeRepository extends JpaRepository<AccountScheme, Long> {
}
