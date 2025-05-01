package co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.repository;

import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.entity.RecipeSupplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeSupplyRepository extends JpaRepository<RecipeSupplyEntity, Long> {

    List<RecipeSupplyEntity> findByRecipeId(Long recipeId);

    List<RecipeSupplyEntity> findBySupplyId(Long supplyId);

    @Query("SELECT rs FROM RecipeSupplyEntity rs WHERE rs.recipe.id = :recipeId AND rs.supply.id = :supplyId")
    Optional<RecipeSupplyEntity> findByRecipeIdAndSupplyId(@Param("recipeId") Long recipeId, @Param("supplyId") Long supplyId);
}