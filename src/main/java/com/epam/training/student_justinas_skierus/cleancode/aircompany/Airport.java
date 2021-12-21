package com.epam.training.student_justinas_skierus.cleancode.aircompany;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.epam.training.student_justinas_skierus.cleancode.aircompany.exceptions.AirportException;
import com.epam.training.student_justinas_skierus.cleancode.aircompany.models.MilitaryType;
import com.epam.training.student_justinas_skierus.cleancode.aircompany.planes.ExperimentalPlane;
import com.epam.training.student_justinas_skierus.cleancode.aircompany.planes.MilitaryPlane;
import com.epam.training.student_justinas_skierus.cleancode.aircompany.planes.PassengerPlane;
import com.epam.training.student_justinas_skierus.cleancode.aircompany.planes.Plane;

import lombok.ToString;

/**
 * @version: 1.1
 * 
 * @author Vitali Shulha
 * @date 4-Jan-2019
 */

@ToString
public class Airport {
  private List<? extends Plane> planes;

  public Airport(List<? extends Plane> planes) {
    this.planes = planes;
  }

  public List<PassengerPlane> getPassengerPlanes() {
    return getPlanesByType(PassengerPlane.class);
  }

  public List<MilitaryPlane> getMilitaryPlanes() {
    return getPlanesByType(MilitaryPlane.class);
  }

  public List<ExperimentalPlane> getExperimentalPlanes() {
    return getPlanesByType(ExperimentalPlane.class);
  }

  public <T> List<T> getPlanesByType(Class<T> clazz) {
    return planes.stream().filter(clazz::isInstance).map(clazz::cast).collect(Collectors.toList());
  }

  public List<MilitaryPlane> getTransportMilitaryPlanes() {
    return getMilitaryPlanes(MilitaryType.TRANSPORT);
  }

  public List<MilitaryPlane> getBomberMilitaryPlanes() {
    return getMilitaryPlanes(MilitaryType.BOMBER);
  }

  public List<MilitaryPlane> getMilitaryPlanes(MilitaryType militaryType) {
    return getMilitaryPlanes().stream().filter(e -> e.getType() == militaryType)
        .collect(Collectors.toList());
  }

  public PassengerPlane getPassengerPlaneWithMaxCapacity() throws AirportException {
    return getPassengerPlanes().stream()
        .max(Comparator.comparingInt(PassengerPlane::getPassengersCapacity))
        .orElseThrow(() -> new AirportException("Airport has 0 passenger planes."));
  }

  public Airport sortByMaxDistance() {
    Collections.sort(planes, Comparator.comparingInt(Plane::getMaxFlightDistance));
    return this;
  }

  public Airport sortByMaxSpeed() {
    Collections.sort(planes, Comparator.comparingInt(Plane::getMaxSpeed));
    return this;
  }

  public Airport sortByMaxLoadCapacity() {
    Collections.sort(planes, Comparator.comparingInt(Plane::getMaxLoadCapacity));
    return this;
  }

  public List<? extends Plane> getPlanes() {
    return planes;
  }

}
