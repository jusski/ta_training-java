package com.epam.training.student_justinas_skierus.cleancode.aircompany;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.epam.training.student_justinas_skierus.cleancode.aircompany.exceptions.AirportException;
import com.epam.training.student_justinas_skierus.cleancode.aircompany.models.ExperimentalType;
import com.epam.training.student_justinas_skierus.cleancode.aircompany.models.MilitaryType;
import com.epam.training.student_justinas_skierus.cleancode.aircompany.planes.ExperimentalPlane;
import com.epam.training.student_justinas_skierus.cleancode.aircompany.planes.MilitaryPlane;
import com.epam.training.student_justinas_skierus.cleancode.aircompany.planes.PassengerPlane;
import com.epam.training.student_justinas_skierus.cleancode.aircompany.planes.Plane;
import com.epam.training.student_justinas_skierus.cleancode.aircompany.security.ClassificationLevel;

public class AirportTest {
  private static List<Plane> planes;

  @BeforeMethod
  static void beforeMethod() {
    planes = Arrays.asList(
            new PassengerPlane("Boeing-737", 900, 12000, 60500, 164),
            new PassengerPlane("Boeing-737-800", 940, 12300, 63870, 192),
            new PassengerPlane("Boeing-747", 980, 16100, 70500, 242),
            new PassengerPlane("Airbus A320", 930, 11800, 65500, 188),
            new PassengerPlane("Airbus A330", 990, 14800, 80500, 222),
            new PassengerPlane("Embraer 190", 870, 8100, 30800, 64),
            new PassengerPlane("Sukhoi Superjet 100", 870, 11500, 50500, 140),
            new PassengerPlane("Bombardier CS300", 920, 11000, 60700, 196),
            new MilitaryPlane("B-1B Lancer", 1050, 21000, 80000, MilitaryType.BOMBER),
            new MilitaryPlane("B-2 Spirit", 1030, 22000, 70000, MilitaryType.BOMBER),
            new MilitaryPlane("B-52 Stratofortress", 1000, 20000, 80000, MilitaryType.BOMBER),
            new MilitaryPlane("F-15", 1500, 12000, 10000, MilitaryType.FIGHTER),
            new MilitaryPlane("F-22", 1550, 13000, 11000, MilitaryType.FIGHTER),
            new MilitaryPlane("C-130 Hercules", 650, 5000, 110000, MilitaryType.TRANSPORT),
            new ExperimentalPlane("Bell X-14", 277, 482, 500, ExperimentalType.HIGH_ALTITUDE, ClassificationLevel.SECRET),
            new ExperimentalPlane("Ryan X-13 Vertijet", 560, 307, 500, ExperimentalType.VERTICAL_TAKEOFF_AND_LANDING, ClassificationLevel.TOP_SECRET));
  }
  
  @Test
  public void testGetTransportMilitaryPlanes() {
    Airport airport = new Airport(planes);
    List<MilitaryPlane> transportMilitaryPlanes = airport.getTransportMilitaryPlanes();

    Assert.assertTrue(transportMilitaryPlanes.stream()
        .map(MilitaryPlane::getType).allMatch(MilitaryType.TRANSPORT::equals));
  }

  @Test
  public void testGetPassengerPlaneWithMaxCapacity() throws AirportException {
    PassengerPlane expected = planes.stream()
    .filter(PassengerPlane.class::isInstance)
    .map(PassengerPlane.class::cast)
    .max(Comparator.comparingInt(PassengerPlane::getPassengersCapacity)).get();
    
    Airport airport = new Airport(planes);
    PassengerPlane actual = airport.getPassengerPlaneWithMaxCapacity();
   
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testSortByMaxLoadCapacity() {
    ArrayList<Plane> expected = new ArrayList<Plane>(planes);
    Collections.sort(expected, Comparator.comparingInt(Plane::getMaxLoadCapacity));
    
    Airport airport = new Airport(planes);
    airport.sortByMaxLoadCapacity();
    List<? extends Plane> actual = airport.getPlanes();
    
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testHasAtLeastOneBomberInMilitaryPlanes() {
    Airport airport = new Airport(planes);
    List<MilitaryPlane> bomberMilitaryPlanes = airport.getBomberMilitaryPlanes();

    boolean anyMatch = bomberMilitaryPlanes.stream()
        .map(MilitaryPlane::getType)
        .anyMatch(MilitaryType.BOMBER::equals);
    Assert.assertTrue(anyMatch);
  }

  @Test
  public void testExperimentalPlanesHasClassificationLevelHigherThanUnclassified() {
    Airport airport = new Airport(planes);
    List<ExperimentalPlane> experimentalPlanes = airport.getExperimentalPlanes();
   
   
    boolean allMatch = experimentalPlanes.stream()
    .map(ExperimentalPlane::getClassificationLevel)
    .allMatch(e -> e.ordinal() > ClassificationLevel.UNCLASSIFIED.ordinal());
    
    Assert.assertTrue(allMatch);
  }
}
