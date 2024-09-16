package com.tiptop.users.repos;

import com.tiptop.users.entities.PRIZE;
import com.tiptop.users.entities.Ticket;
import com.tiptop.users.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface ITicketRepository extends JpaRepository<Ticket, Long> {

	Ticket findByTicketNumber(Number ticketNumber);

    @Query("SELECT t FROM Ticket t WHERE t.user.id = :userId")
    List<Ticket> findTicketsByUserId(@Param("userId") Long userId);
    @Query("SELECT t, t.user FROM Ticket t")
    List<Object[]> findAllTicketsWithUsers();

    @Query(value = "SELECT t.user_id FROM Ticket t ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Long findRandomUser();

    Collection<Ticket> findByUser(User user);

    Collection<Ticket> findTicketByPrize(String prize);
}
