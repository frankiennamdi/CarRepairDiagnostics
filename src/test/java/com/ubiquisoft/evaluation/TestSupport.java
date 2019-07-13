package com.ubiquisoft.evaluation;

import com.ubiquisoft.evaluation.domain.ConditionType;
import com.ubiquisoft.evaluation.domain.Part;
import com.ubiquisoft.evaluation.domain.PartType;

import java.util.ArrayList;
import java.util.List;

public class TestSupport {

  private final CarPartFactory carPartFactory = new CarPartFactory();


  public Part newPart(PartType partType, ConditionType conditionType) {
    return carPartFactory.newPart(partType, conditionType);
  }

  public List<Part> newTires(ConditionType... conditionTypes) {
    List<Part> tires = new ArrayList<>();
    for (ConditionType conditionType : conditionTypes) {
      tires.add(newPart(PartType.TIRE, conditionType));
    }
    return tires;
  }
}
