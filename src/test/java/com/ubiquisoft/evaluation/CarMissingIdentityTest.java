package com.ubiquisoft.evaluation;

import com.ubiquisoft.evaluation.domain.Car;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

public class CarMissingIdentityTest {

  @Test
  public void testValidCarIdentity() {
    Car car = new Car();
    car.setMake("make");
    car.setModel("model");
    car.setYear("year");
    List<String> missingFields = car.getMissingIdentityFields();
    assertThat(missingFields.isEmpty(), is(true));
  }

  @Test
  public void testValidCarIdentity_withMissingMake() {
    Car car = new Car();
    car.setModel("model");
    car.setYear("year");
    List<String> missingFields = car.getMissingIdentityFields();
    assertThat(missingFields.isEmpty(), is(false));
    assertThat(missingFields, contains("make"));
  }

  @Test
  public void testValidCarIdentity_withMissingModel() {
    Car car = new Car();
    car.setMake("make");
    car.setYear("year");
    List<String> missingFields = car.getMissingIdentityFields();
    assertThat(missingFields.isEmpty(), is(false));
    assertThat(missingFields, contains("model"));
  }

  @Test
  public void testValidCarIdentity_withMissingYear() {
    Car car = new Car();
    car.setMake("make");
    car.setModel("model");
    List<String> missingFields = car.getMissingIdentityFields();
    assertThat(missingFields.isEmpty(), is(false));
    assertThat(missingFields, contains("year"));
  }

  @Test
  public void testValidCarIdentity_withAllMissing() {
    Car car = new Car();
    List<String> missingFields = car.getMissingIdentityFields();
    assertThat(missingFields.isEmpty(), is(false));
    assertThat(missingFields, containsInAnyOrder("make", "model", "year"));
  }
}
