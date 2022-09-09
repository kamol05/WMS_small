package uz.sngroup.model.bys;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Getter
@Setter
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @Size(min = 1,max = 50,message = "max value 50")
    @NotBlank(message = "this field may not be blank")
    @NotEmpty(message = "this field is required")
    private String name;

    @Max(value = 100_000,message = "max value 100_000")
    @Min(value = 0,message = "min value 0")
    private Integer code = 0;

    @Size(min = 1,max = 40,message = "max value 40")
    private String city = " ";

    @Size(min = 1,max = 40,message = "max value 40")
    private String description = " ";

    @Size(min = 1,max = 40,message = "max value 40")
    private String notes = " ";


}
