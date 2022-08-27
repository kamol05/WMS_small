package uz.sngroup.model.bys;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;


@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonUnwrapped
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "Fk_quality"))
    private Quality quality;

    @JsonUnwrapped
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "Fk_color"))
    private Color color;

    @JsonUnwrapped
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "Fk_gramm"))
    private Gramm gramm;

    @Size(min = 1,max = 40,message = "max value 40")
    private String ean;

    @Size(min = 1,max = 40,message = "max value 40")
    private String name;

    @Size(min = 1,max = 40,message = "max value 40")
    private String description = " ";

    @Size(min = 1,max = 40,message = "max value 40")
    private String notes = " ";



}
