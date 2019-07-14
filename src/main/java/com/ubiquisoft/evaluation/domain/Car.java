package com.ubiquisoft.evaluation.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Car {

  private String year;
  private String make;
  private String model;

  private List<Part> parts;

  private final Map<PartType, Integer> requiredCarPartsCount;

  public Car() {
    requiredCarPartsCount = new HashMap<>();
    requiredCarPartsCount.put(PartType.ENGINE, 1);
    requiredCarPartsCount.put(PartType.ELECTRICAL, 1);
    requiredCarPartsCount.put(PartType.FUEL_FILTER, 1);
    requiredCarPartsCount.put(PartType.OIL_FILTER, 1);
    requiredCarPartsCount.put(PartType.TIRE, 4);
  }

  public List<Part> getDamagedParts() {
    return getParts().stream().filter(part -> !part.isInWorkingCondition()).collect(Collectors.toList());
  }

  public Map<PartType, Integer> getMissingPartsMap() {
    if (getParts().isEmpty()) {

      return requiredCarPartsCount;

    } else {

      Map<PartType, List<Part>> currentCarParts =
              getParts().stream().collect(Collectors.groupingBy(Part::getType, Collectors.toList()));

      Map<PartType, Integer> missingCarParts = new HashMap<>();

      for (Map.Entry<PartType, Integer> requirePartsEntry : requiredCarPartsCount.entrySet()) {

        List<Part> currentTypeParts = currentCarParts.get(requirePartsEntry.getKey());
        int requiredCount = requiredCarPartsCount.get(requirePartsEntry.getKey());

        if (currentTypeParts == null) {

          missingCarParts.put(requirePartsEntry.getKey(), requiredCount);

        } else {

          int currentCount = currentTypeParts.size();
          if (currentCount < requiredCount) {
            missingCarParts.put(requirePartsEntry.getKey(), requiredCount - currentCount);
          }
        }
      }
      return missingCarParts;
    }
  }

  public List<String> getMissingIdentityFields() {

    List<String> missingIdentityFields = new ArrayList<>();

    if (isNullOrEmpty(getMake())) {
      missingIdentityFields.add("make");
    }
    if (isNullOrEmpty(getModel())) {
      missingIdentityFields.add("model");
    }

    if (isNullOrEmpty(getYear())) {
      missingIdentityFields.add("year");
    }
    return missingIdentityFields;
  }

  private boolean isNullOrEmpty(String value) {
    return value == null || value.isEmpty();
  }

  @Override
  public String toString() {
    return "Car{" +
            "year='" + year + '\'' +
            ", make='" + make + '\'' +
            ", model='" + model + '\'' +
            ", parts=" + parts +
            '}';
  }

  /* --------------------------------------------------------------------------------------------------------------- */
  /*  Getters and Setters *///region
  /* --------------------------------------------------------------------------------------------------------------- */

  public String getYear() {
    return year;
  }

  public void setYear(String year) {
    this.year = year;
  }

  public String getMake() {
    return make;
  }

  public void setMake(String make) {
    this.make = make;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public List<Part> getParts() {
    return (parts != null) ? parts : new ArrayList<>();
  }

  public void setParts(List<Part> parts) {
    this.parts = parts;
  }
  /* --------------------------------------------------------------------------------------------------------------- */
  /*  Getters and Setters End *///endregion
  /* --------------------------------------------------------------------------------------------------------------- */
}
