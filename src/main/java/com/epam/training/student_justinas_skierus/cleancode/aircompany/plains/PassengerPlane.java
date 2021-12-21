package com.epam.training.student_justinas_skierus.cleancode.aircompany.plains;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
public class PassengerPlane extends Plane {

  private final @NonNull Integer passengersCapacity;
  
  public PassengerPlane(String model, Integer maxSpeed, Integer maxFlightDistance, Integer maxLoadCapacity, Integer passengersCapacity)
  {
	  super(model, maxSpeed, maxFlightDistance, maxLoadCapacity);
	  this.passengersCapacity = passengersCapacity;
  }

}
