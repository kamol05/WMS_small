package uz.sngroup.model.event;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import uz.sngroup.model.EventSerializer;
import uz.sngroup.model.bys.Product;
import uz.sngroup.model.sys.User;
import javax.persistence.*;
import java.util.Date;

@JsonSerialize(using = EventSerializer.class)
@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer serial;

    private String ean;

    private Integer width;

    private Integer height;

    private Double m2;

    private Integer count = 1;

    private Integer pieces = 0;

    private Double weight = 0.0;

    private String description = " ";

    private String notes = " ";

    @Enumerated(value = EnumType.STRING)
    private EventType eventType;


    @JsonUnwrapped
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "Fk_product"))
    private Product product;


    @JsonUnwrapped
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "Fk_user"))
    private User user;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @Column(name = "date",nullable = false, updatable = false)
    private Date date;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @Column(name = "modified",nullable = false)
    private Date modified;

}
