package entity.dtos;


import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendaRequestDTO {

    private LocalDate data;
    private Integer quantidade;
    private Long clienteId;
    private Long produtoId;
}