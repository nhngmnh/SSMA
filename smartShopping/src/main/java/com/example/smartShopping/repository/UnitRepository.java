package com.example.smartShopping.repository;



import com.example.smartShopping.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UnitRepository extends JpaRepository<Unit, Long> {
    Optional<Unit> findByUnitName(String unitName);
    // thêm method tìm kiếm theo unitName (ignore case, partial match)
    List<Unit> findByUnitNameContainingIgnoreCase(String unitName);
}
