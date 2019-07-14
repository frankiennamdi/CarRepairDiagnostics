package com.ubiquisoft.evaluation;

import com.ubiquisoft.evaluation.domain.ConditionType;
import com.ubiquisoft.evaluation.domain.Part;
import com.ubiquisoft.evaluation.domain.PartType;

import java.util.ArrayList;
import java.util.List;

class TestSupport {

  List<Part> newTires(ConditionType... conditionTypes) {
    List<Part> tires = new ArrayList<>();
    for (ConditionType conditionType : conditionTypes) {
      tires.add(newPart(PartType.TIRE, conditionType));
    }
    return tires;
  }

  Part newPart(PartType partType, ConditionType conditionType) {
    Part part = new Part();
    part.setCondition(conditionType);
    part.setInventoryId(partType.name().toLowerCase());
    part.setType(partType);
    return part;
  }
}
