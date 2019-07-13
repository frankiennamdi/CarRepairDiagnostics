package com.ubiquisoft.evaluation;

import com.ubiquisoft.evaluation.domain.Car;

@FunctionalInterface
public interface Validator {
  boolean validate(Car car);
}
