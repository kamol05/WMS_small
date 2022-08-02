package uz.sngroup.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Barcode{
    private Long quality;
    private Long gramm;
    private Long color;
    private Integer width;
    private Integer height;
    private Integer weight;
    private Integer pieces = 0;
    private Integer quantity = 0;
}
