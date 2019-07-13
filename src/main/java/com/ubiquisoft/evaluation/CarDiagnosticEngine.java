package com.ubiquisoft.evaluation;

import com.ubiquisoft.evaluation.domain.Car;
import com.ubiquisoft.evaluation.domain.ConditionType;
import com.ubiquisoft.evaluation.domain.Part;
import com.ubiquisoft.evaluation.domain.PartType;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CarDiagnosticEngine {

  private final Validator missingIdentityFieldsValidator = (Car car) -> {
    List<String> missingIdentities = car.getMissingIdentityFields();
    if (!missingIdentities.isEmpty()) {
      missingIdentities.forEach(this::printMissingIdentity);
      return false;
    }
    return true;
  };

  private final Validator missingPartsValidator = (Car car) -> {
    Map<PartType, Integer> missingParts = car.getMissingPartsMap();
    if (!missingParts.isEmpty()) {
      missingParts.forEach((this::printMissingPart));
      return false;
    }
    return true;
  };

  private final Validator damagedPartsValidator = (Car car) -> {
    List<Part> damagedParts = car.getDamagedParts();
    if (!damagedParts.isEmpty()) {
      damagedParts.forEach(part -> printDamagedPart(part.getType(), part.getCondition()));
      return false;
    }
    return true;
  };


  public void executeDiagnostics(Car car) {
    /*
     * Implement basic diagnostics and print results to console.
     *
     * The purpose of this method is to find any problems with a car's data or parts.
     *
     * Diagnostic Steps:
     *      First   - Validate the 3 data fields are present, if one or more are
     *                then print the missing fields to the console
     *                in a similar manner to how the provided methods do.
     *
     *      Second  - Validate that no parts are missing using the 'getMissingPartsMap' method in the Car class,
     *                if one or more are then run each missing part and its count through the provided missing part method.
     *
     *      Third   - Validate that all parts are in working condition, if any are not
     *                then run each non-working part through the provided damaged part method.
     *
     *      Fourth  - If validation succeeds for the previous steps then print something to the console informing the user as such.
     * A damaged part is one that has any condition other than NEW, GOOD, or WORN.
     *
     * Important:
     *      If any validation fails, complete whatever step you are actively one and end diagnostics early.
     *
     * Treat the console as information being read by a user of this application. Attempts should be made to ensure
     * console output is as least as informative as the provided methods.
     */
    List<Validator> validators = Arrays.asList(
            missingIdentityFieldsValidator,
            missingPartsValidator,
            damagedPartsValidator
    );

    for (Validator validator : validators) {
      if (!validator.validate(car)) {
        return;
      }
    }

    System.out.println("Car Is In Good Working Condition");
  }

  private void printMissingIdentity(String missingIdentity) {
    if (missingIdentity == null) {
      throw new IllegalArgumentException("missingIdentity must not be null");
    }
    System.out.println(String.format("Missing Identity(s) Detected: %s", missingIdentity));
  }

  private void printMissingPart(PartType partType, Integer count) {
    if (partType == null) throw new IllegalArgumentException("PartType must not be null");
    if (count == null || count <= 0) throw new IllegalArgumentException("Count must be greater than 0");

    System.out.println(String.format("Missing Part(s) Detected: %s - Count: %s", partType, count));
  }

  private void printDamagedPart(PartType partType, ConditionType condition) {
    if (partType == null) throw new IllegalArgumentException("PartType must not be null");
    if (condition == null) throw new IllegalArgumentException("ConditionType must not be null");

    System.out.println(String.format("Damaged Part Detected: %s - Condition: %s", partType, condition));
  }

  public static void main(String[] args) throws JAXBException {

    // Build new Diagnostics Engine and execute on deserialized car object.

    CarDiagnosticEngine diagnosticEngine = new CarDiagnosticEngine();

    Car car = diagnosticEngine.loadCarFromXml("SampleCar.xml");

    diagnosticEngine.executeDiagnostics(car);

  }


  public Car loadCarFromXml(String xmlFile) throws JAXBException {
    // Load classpath resource
    InputStream xml = ClassLoader.getSystemResourceAsStream(xmlFile);

    // Verify resource was loaded properly
    if (xml == null) {
      System.err.println("An error occurred attempting to load " + xmlFile);

      System.exit(1);
    }

    // Build JAXBContext for converting XML into an Object
    JAXBContext context = JAXBContext.newInstance(Car.class, Part.class);
    Unmarshaller unmarshaller = context.createUnmarshaller();

    return (Car) unmarshaller.unmarshal(xml);
  }
}
