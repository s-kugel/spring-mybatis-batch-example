package com.s_kugel.aldra.batch.model;

import lombok.Builder;

@Builder(toBuilder = true)
public record BatchContext(String batchId, String batchName, String batchNameJP) {}
