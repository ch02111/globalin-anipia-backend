package com.afivestudio.anipia.animation.query.dto;

import java.time.LocalDate;

public record DateRange(
        LocalDate start,
        LocalDate end
) {

}
