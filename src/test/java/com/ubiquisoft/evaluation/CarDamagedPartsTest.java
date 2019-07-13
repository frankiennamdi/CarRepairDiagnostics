package com.ubiquisoft.evaluation;

import com.ubiquisoft.evaluation.domain.Car;
import com.ubiquisoft.evaluation.domain.ConditionType;
import com.ubiquisoft.evaluation.domain.Part;
import com.ubiquisoft.evaluation.domain.PartType;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CarDamagedPartsTest {

  private final TestSupport testSupport = new TestSupport();

  @Test
  public void testGetDamagedParts_withDamagedParts() {
    Car car = new Car();
    car.setMake("make");
    car.setModel("model");
    car.setYear("year");

    List<Part> parts = new ArrayList<>(testSupport.newTires(ConditionType.FLAT, ConditionType.NEW));
    parts.add(testSupport.newPart(PartType.ENGINE, ConditionType.GOOD));
    car.setParts(parts);

    List<Part> damagedParts = car.getDamagedParts();
    assertThat(damagedParts.size(), is(1));
    assertThat(damagedParts.get(0).getType(), is(PartType.TIRE));
    assertThat(damagedParts.get(0).getCondition(), is(ConditionType.FLAT));
  }

  @Test
  public void testGetDamagedParts_withGoodParts() {
    Car car = new Car();
    car.setMake("make");
    car.setModel("model");
    car.setYear("year");

    List<Part> parts = new ArrayList<>(testSupport.newTires(ConditionType.NEW, ConditionType.NEW));
    parts.add(testSupport.newPart(PartType.ENGINE, ConditionType.GOOD));
    car.setParts(parts);

    List<Part> damagedParts = car.getDamagedParts();
    assertThat(damagedParts.size(), is(0));
  }
}
