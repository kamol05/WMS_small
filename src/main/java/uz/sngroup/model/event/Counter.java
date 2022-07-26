package uz.sngroup.model.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Counter {
    @Id
    private Long id = 1L;

    private Integer serial;

    private Integer ean;

}


