package uz.sngroup.model.event;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import uz.sngroup.model.bys.Customer;
import uz.sngroup.model.sys.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String driverNumber;

    private String carNumber;

    private String carNumber2;

    private String nakNo;

    private String warehouseMan;

    private String description = " ";

    private String notes = " ";

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "Fk_user"))
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "Fk_customer"))
    private Customer customer;

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, mappedBy = "id")
    List<SaleEvent> events = new ArrayList<>();



    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    @Column(name = "date", nullable = false, updatable = false)
    private Date date;

    @UpdateTimestamp
    @Temporal(TemporalType.DATE)
    @Column(name = "modified", nullable = false)
    private Date modified;

}








