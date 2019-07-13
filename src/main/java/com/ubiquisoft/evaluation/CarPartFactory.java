package com.ubiquisoft.evaluation;

import com.ubiquisoft.evaluation.domain.ConditionType;
import com.ubiquisoft.evaluation.domain.Part;
import com.ubiquisoft.evaluation.domain.PartType;

class CarPartFactory {

  Part newPart(PartType partType, ConditionType conditionType) {
    Part tirePart = new Part();
    tirePart.setCondition(conditionType);
    tirePart.setInventoryId(partType.name().toLowerCase());
    tirePart.setType(partType);
    return tirePart;
  }
}
