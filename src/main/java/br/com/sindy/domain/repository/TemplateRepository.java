package br.com.sindy.domain.repository;

import br.com.sindy.domain.entity.Template;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TemplateRepository extends CrudRepository<Template, String> {
    @Query(
            "SELECT et FROM Template et WHERE et.name = :name AND et.deleted = false"
    )
    Optional<Template> findByName(@Param("name") String name);

    @Query(
            "SELECT et FROM Template et WHERE et.name IN :names AND et.deleted = false"
    )
    List<Template> findByNames(@Param("names") List<String> names);
}
