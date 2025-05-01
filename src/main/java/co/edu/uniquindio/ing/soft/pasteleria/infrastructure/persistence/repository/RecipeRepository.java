package co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.repository;

import co.edu.uniquindio.ing.soft.pasteleria.domain.enums.Status;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.entity.RecipeEntity;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.entity.SupplierEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<RecipeEntity, Long> {

    List<RecipeEntity> findByStatus(Status status);

    List<RecipeEntity> findByNameContainingIgnoreCase(String name);

//    Optional<RecipeEntity> findByRecipeID(String supplierID);

    Page<RecipeEntity> findAll(Specification<RecipeEntity> spec, Pageable pageable);

    @Query("SELECT r FROM RecipeEntity r JOIN r.recipeSupplies rs WHERE rs.supply.id = :supplyId")
    List<RecipeEntity> findBySupplyId(@Param("supplyId") Long supplyId);
}