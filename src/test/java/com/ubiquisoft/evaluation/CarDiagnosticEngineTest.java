package com.ubiquisoft.evaluation;

import com.ubiquisoft.evaluation.domain.Car;
import com.ubiquisoft.evaluation.domain.ConditionType;
import com.ubiquisoft.evaluation.domain.Part;
import com.ubiquisoft.evaluation.domain.PartType;
import org.junit.Rule;
import org.junit.Test;

import javax.xml.bind.JAXBException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

public class CarDiagnosticEngineTest {

  @Rule
  public SystemOutputRule systemOutputRule = new SystemOutputRule();

  private final TestSupport testSupport = new TestSupport();

  @Test
  public void testExecuteDiagnostics_withCarWithDamagedParts() throws JAXBException {
    CarDiagnosticEngine carDiagnosticEngine = new CarDiagnosticEngine();
    Car car = carDiagnosticEngine.loadCarFromXml("SampleCar.xml");
    carDiagnosticEngine.executeDiagnostics(car);
    List<String> output = systemOutputRule.getOutputStringLines();
    assertThat(output, containsInAnyOrder(
            "Damaged Part Detected: ENGINE - Condition: USED",
            "Damaged Part Detected: ELECTRICAL - Condition: NO_POWER",
            "Damaged Part Detected: TIRE - Condition: FLAT",
            "Damaged Part Detected: OIL_FILTER - Condition: CLOGGED"
    ));
  }

  @Test
  public void testExecuteDiagnostics_withCarWithMissingIdentity() {

    CarDiagnosticEngine carDiagnosticEngine = new CarDiagnosticEngine();
    Car car = new Car();
    car.setMake("make");
    car.setYear("year");
    carDiagnosticEngine.executeDiagnostics(car);
    List<String> output = systemOutputRule.getOutputStringLines();
    assertThat(output, containsInAnyOrder("Missing Identity(s) Detected: model"
    ));
  }

  @Test
  public void testExecuteDiagnostics_withCarWithMissingParts() {
    CarDiagnosticEngine carDiagnosticEngine = new CarDiagnosticEngine();
    Car car = new Car();
    car.setMake("make");
    car.setModel("model");
    car.setYear("year");

    List<Part> parts = new ArrayList<>(testSupport.newTires(ConditionType.GOOD, ConditionType.NEW));
    parts.add(testSupport.newPart(PartType.ENGINE, ConditionType.GOOD));
    car.setParts(parts);
    carDiagnosticEngine.executeDiagnostics(car);
    List<String> output = systemOutputRule.getOutputStringLines();
    assertThat(output, containsInAnyOrder(
            "Missing Part(s) Detected: OIL_FILTER - Count: 1",
            "Missing Part(s) Detected: FUEL_FILTER - Count: 1",
            "Missing Part(s) Detected: ELECTRICAL - Count: 1",
            "Missing Part(s) Detected: TIRE - Count: 2"
    ));
  }

  @Test
  public void testExecuteDiagnostics_withCarWithCompleteGoodParts() {

    CarDiagnosticEngine carDiagnosticEngine = new CarDiagnosticEngine();
    Car car = new Car();
    car.setMake("make");
    car.setModel("model");
    car.setYear("year");

    List<Part> parts = new ArrayList<>(
            testSupport.newTires(ConditionType.GOOD, ConditionType.NEW, ConditionType.GOOD, ConditionType.GOOD));
    parts.add(testSupport.newPart(PartType.FUEL_FILTER, ConditionType.GOOD));
    parts.add(testSupport.newPart(PartType.ELECTRICAL, ConditionType.GOOD));
    parts.add(testSupport.newPart(PartType.ENGINE, ConditionType.GOOD));
    parts.add(testSupport.newPart(PartType.OIL_FILTER, ConditionType.GOOD));
    car.setParts(parts);

    carDiagnosticEngine.executeDiagnostics(car);

    List<String> output = systemOutputRule.getOutputStringLines();
    assertThat(output, containsInAnyOrder("Car Is In Good Working Condition"));
  }
}
