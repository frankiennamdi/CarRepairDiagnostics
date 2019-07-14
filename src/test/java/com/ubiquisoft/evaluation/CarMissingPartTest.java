package com.ubiquisoft.evaluation;

import com.ubiquisoft.evaluation.domain.Car;
import com.ubiquisoft.evaluation.domain.ConditionType;
import com.ubiquisoft.evaluation.domain.Part;
import com.ubiquisoft.evaluation.domain.PartType;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CarMissingPartTest {

  private final TestSupport testSupport = new TestSupport();


  @Test
  public void testMissingCarParts_withNoCarParts() {
    Car car = new Car();
    car.setMake("make");
    car.setModel("model");
    car.setYear("year");

    assertThat(car.getParts().isEmpty(), is(true));

    Map<PartType, Integer> missingParts = car.getMissingPartsMap();
    assertThat(missingParts.get(PartType.TIRE), is(4));
    assertThat(missingParts.get(PartType.ENGINE), is(1));
    assertThat(missingParts.get(PartType.ELECTRICAL), is(1));
    assertThat(missingParts.get(PartType.OIL_FILTER), is(1));
    assertThat(missingParts.get(PartType.FUEL_FILTER), is(1));
  }

  @Test
  public void testMissingCarParts_withSomeMissingParts() {
    List<Part> parts = new ArrayList<>(testSupport.newTires(ConditionType.GOOD, ConditionType.NEW));
    parts.add(testSupport.newPart(PartType.ENGINE, ConditionType.GOOD));
    Car car = new Car();
    car.setMake("make");
    car.setModel("model");
    car.setYear("year");
    car.setParts(parts);

    Map<PartType, Integer> missingParts = car.getMissingPartsMap();

    assertThat(missingParts.get(PartType.TIRE), is(2));
    assertThat(missingParts.get(PartType.ELECTRICAL), is(1));
    assertThat(missingParts.get(PartType.OIL_FILTER), is(1));
    assertThat(missingParts.get(PartType.FUEL_FILTER), is(1));
  }

  @Test
  public void testMissingCarParts_withCompleteCarParts() {

    List<Part> parts = new ArrayList<>(testSupport.newTires(
            ConditionType.GOOD, ConditionType.NEW, ConditionType.GOOD, ConditionType.GOOD));
    parts.add(testSupport.newPart(PartType.FUEL_FILTER, ConditionType.GOOD));
    parts.add(testSupport.newPart(PartType.ELECTRICAL, ConditionType.GOOD));
    parts.add(testSupport.newPart(PartType.ENGINE, ConditionType.GOOD));
    parts.add(testSupport.newPart(PartType.OIL_FILTER, ConditionType.GOOD));

    Car car = new Car();
    car.setMake("make");
    car.setModel("model");
    car.setYear("year");
    car.setParts(parts);

    Map<PartType, Integer> missingParts = car.getMissingPartsMap();
    assertThat(missingParts.isEmpty(), is(true));
  }

}
