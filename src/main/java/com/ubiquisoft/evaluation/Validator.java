package com.ubiquisoft.evaluation;

import com.ubiquisoft.evaluation.domain.Car;

/**
 * Interface for validators of {@link Car}
 */
@FunctionalInterface
public interface Validator {

  /**
   * Validates the properties of a car.
   *
   * @param car the car to validate
   * @return true if the properties of the car passes the test of this validator, false otherwise
   */
  boolean validate(Car car);
}
