package uz.sngroup.repository.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.sngroup.model.event.Event;
import uz.sngroup.model.event.EventType;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> getBySerial(Integer serial);
    Optional<Event> getBySerialAndEventType(Integer serial, EventType type);

    @Query(value = "select\n" +
            "       product_id as id,\n" +
            "       sum( m2) as m2,\n" +
            "       count(*) as count,\n" +
            "       sum(weight) as weight,\n" +
            "       sum(pieces) as pieces,\n" +
            "       clock_timestamp () as date,\n" +
            "       clock_timestamp () as modified,\n" +
            "       avg(1) as serial,\n" +
            "       avg(2) as ean,\n" +
            "       avg(3) as width,\n" +
            "       avg(4) as height,\n" +
            "       avg(5) as description,\n" +
            "       avg(7) as notes,\n" +
            "       avg(1) as user_id,\n" +
            "       'IN' as event_type,\n" +
            "       product_id as product_id\n" +
            "from events\n" +
            "where serial not in (select serial from events where event_type = 'SALE')\n" +
            "group by product_id\n" +
            "order by id asc",
            nativeQuery = true )
    List<Event> selectGroupByProductId();

    List<Event> getAllByProduct_Id(Long id);

    @Query(value = "select * " +
                    "from events " +
                    "where product_id = ?1 and event_type = 'IN' and serial not in " +
                    "(" +
                    "select serial from events where product_id = ?1 and event_type = 'SALE'" +
                    ")",
            nativeQuery = true)
    List<Event> getAllByProductIDSpecial(Long id);

    Optional<Event> getBySerialAndEventTypeNot(Integer serial, EventType type);
    Integer getTopByOrderBySerial();
    Optional<Event> getBySerialAndEventTypeNotIn(Integer serial, List<EventType> type);
}
