package uz.sngroup.model.event;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import uz.sngroup.model.SaleEventSerializer;
import uz.sngroup.model.bys.Customer;
import uz.sngroup.model.bys.Product;
import uz.sngroup.model.sys.User;

import javax.persistence.*;
import java.util.Date;


@JsonSerialize(using = SaleEventSerializer.class)
@Entity
@Getter
@Setter
@Table(name = "saleevents")
public class SaleEvent {
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


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "Fk_product"))
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "Fk_user"))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "Fk_invoice"))
    private Invoice invoice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "Fk_customer"))
    private Customer customer;


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
