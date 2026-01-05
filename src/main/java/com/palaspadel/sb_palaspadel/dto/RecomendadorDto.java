
package com.palaspadel.sb_palaspadel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description: DTO para transferir datos del recomendador de palas
 * <p>
 * Created by Andres on 2026
 *
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecomendadorDto {
    private String nivel;            // PRINCIPIANTE, INTERMEDIO, AVANZADO
    private String estilo;           // DEFENSIVO, EQUILIBRADO, OFENSIVO
    private boolean lesionesPadel;   // ALGUNA LESION TRONCO SUPERIOR RELACIONADA CON PADEL
    private Double presupuesto;      // máximo precio
    private String formaPreferida;   // opcional: REDONDA, LAGRIMA, DIAMANTE
}
