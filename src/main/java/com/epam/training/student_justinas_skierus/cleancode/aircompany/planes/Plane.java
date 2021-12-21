package com.epam.training.student_justinas_skierus.cleancode.aircompany.planes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
abstract public class Plane {
  private final @NonNull String model;
  private final @NonNull Integer maxSpeed;
  private final @NonNull Integer maxFlightDistance;
  private final @NonNull Integer maxLoadCapacity;
}
