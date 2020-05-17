package pl.javastart.sellegro.auction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AuctionRepository extends JpaRepository<Auction, Long> {

    List<Auction> findFirst4ByOrderByPriceDesc();

    List<Auction> findAllByOrderByTitle();

    List<Auction> findAllByOrderByPrice();

    List<Auction> findAllByOrderByColor();

    List<Auction> findAllByOrderByEndDate();

    @Query("SELECT a FROM Auction a " +
            "WHERE LOWER(a.title) LIKE LOWER(CONCAT('%', :title, '%'))" +
            " AND LOWER(a.carMake) LIKE LOWER(CONCAT('%', :carMake, '%'))" +
            " AND LOWER(a.carModel) LIKE LOWER(CONCAT('%', :carModel, '%'))" +
            " AND LOWER(a.color) LIKE LOWER(CONCAT('%', :color, '%'))")
    List<Auction> filtered(@Param("title") String title, @Param("carMake") String carMake, @Param("carModel") String carModel, @Param("color") String color);

    @Transactional
    @Modifying
    @Query("UPDATE Auction a SET a.title = concat(a.carMake, ' ', a.carModel)")
    void updateTitle();
}
