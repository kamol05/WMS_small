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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 1,max = 40,message = "max value 40")
    private String driverNumber;

    @Size(min = 1,max = 40,message = "max value 40")
    private String carNumber;

    @Size(min = 1,max = 40,message = "max value 40")
    private String carNumber2;

    @Size(min = 1,max = 40,message = "max value 40")
    @NotBlank(message = "this field may not be blank")
    @NotEmpty(message = "this field is required")
    private String nakNo;

    @Size(min = 1,max = 40,message = "max value 40")
    @NotBlank(message = "this field may not be blank")
    @NotEmpty(message = "this field is required")
    private String warehouseMan;

    @Size(min = 1,max = 40,message = "max value 40")
    private String description = " ";

    @Size(min = 1,max = 40,message = "max value 40")
    private String notes = " ";

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "Fk_user"))
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "Fk_customer"))
    private Customer customer;

    @JsonIgnore
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








