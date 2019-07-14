package com.ubiquisoft.evaluation;

import org.junit.rules.ExternalResource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SystemOutputRule extends ExternalResource {

  private PrintStream previousOutputConsole;

  private PrintStream previousErrorConsole;

  private ByteArrayOutputStream newOutputConsole;

  @Override
  protected void before() {
    previousOutputConsole = System.out;
    previousErrorConsole = System.err;

    newOutputConsole = new ByteArrayOutputStream();

    System.setOut(new PrintStream(newOutputConsole));
    System.setErr(new PrintStream(newOutputConsole));
  }

  @Override
  protected void after() {
    System.setOut(previousOutputConsole);
    System.setErr(previousErrorConsole);
    System.out.println(newOutputConsole.toString());
  }

  List<String> getOutputStringLines() {
    return new ArrayList<>(Arrays.asList(newOutputConsole.toString().split("\n")));
  }
}
