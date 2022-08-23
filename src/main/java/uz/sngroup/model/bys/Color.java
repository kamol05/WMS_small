package uz.sngroup.model.bys;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@Table(name = "colors")
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @Size(min = 1,max = 40,message = "max value 40")
    @NotBlank(message = "this field may not be blank")
    @NotEmpty(message = "this field is required")
    private String name;

    @Size(min = 1,max = 40,message = "max value 40")
    private String description = " ";

    @Size(min = 1,max = 40,message = "max value 40")
    private String notes = " ";
}
