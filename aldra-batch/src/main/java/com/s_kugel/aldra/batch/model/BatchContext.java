package com.s_kugel.aldra.batch.model;

import java.time.LocalDate;
import lombok.Builder;

@Builder(toBuilder = true)
public record BatchContext(
    String batchId,
    String batchName,
    String batchNameJP,
    LocalDate basisDate,
    LocalDate previousBasisDate,
    LocalDate nextBasisDate,
    LocalDate startOfMonth,
    LocalDate endOfMonth,
    Boolean businessDateFlag) {}
