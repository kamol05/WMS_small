package uz.sngroup.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Barcode{
    private Long quality;
    private Long gramm;
    private Long color;

    @Max(value = 1000,message = "max value 1000")
    @Min(value = 10,message = "min value 10")
    private Integer width;

    @Max(value = 100_000,message = "max value 100_000")
    @Min(value = 10,message = "min value 10")
    private Integer height;

    @Max(value = 1000,message = "max value 1000")
    @Min(value = 0,message = "min value 0")
    private Double weight;

    @Max(value = 100_000,message = "max value 100_00")
    @Min(value = 0,message = "min value 0")
    private Integer pieces = 0;

    @Max(value = 100,message = "max value 100")
    @Min(value = 0,message = "min value 0")
    private Integer quantity = 0;
}
