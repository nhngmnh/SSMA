package com.example.smartShopping.repository;

import com.example.smartShopping.dto.response.FridgeResponse;
import com.example.smartShopping.entity.Fridge;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface FridgeRepository extends JpaRepository<Fridge, Long> {

    // Lấy tất cả đồ trong tủ lạnh của user
    List<Fridge> findByUserId(Long userId);

    // Lấy đồ trong tủ theo user + food
    Optional<Fridge> findByUserIdAndFoodId(Long userId, Long foodId);

    // Lấy đồ sắp hết hạn
    @Query("""
        SELECT f FROM Fridge f
        WHERE f.user.userId = :userId
          AND f.expiredDate BETWEEN :now AND :threshold
    """)
    List<Fridge> findExpiringSoon(
            @Param("userId") Long userId,
            @Param("now") LocalDateTime now,
            @Param("threshold") LocalDateTime threshold
    );

    Optional<Fridge> findByIdAndUserId(Long id, Long userId);
    // Xóa item theo user + food name
    @Modifying
    @Transactional
    @Query("""
        DELETE FROM Fridge f
        WHERE f.user.id = :userId
          AND LOWER(f.food.name) = LOWER(:foodName)
    """)
    int deleteByUserIdAndFoodName(@Param("userId") Long userId,
                                  @Param("foodName") String foodName);

    // Hoặc nếu muốn lấy danh sách trước
    List<Fridge> findByUserIdAndFood_NameIgnoreCase(Long userId, String foodName);
    // Lấy 1 item theo foodName và userId (để đảm bảo user chỉ xem đồ của mình)
    List<Fridge> findByFood_NameIgnoreCaseAndUserId(String foodName, Long userId);

    // Lấy theo groupId
    List<Fridge> findByGroupId(Long groupId);

    // Lấy theo food name và groupId
    List<Fridge> findByFood_NameIgnoreCaseAndGroupId(String foodName, Long groupId);

}
