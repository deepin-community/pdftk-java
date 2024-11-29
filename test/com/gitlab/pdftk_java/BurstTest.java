package com.gitlab.pdftk_java;

import org.junit.Test;

import java.io.IOException;

public class BurstTest extends BlackBox {
  @Test
  public void burst_issue18() throws IOException {
    String pattern = tmpDirectory.getRoot().getPath() + "/page%04d.pdf";
    pdftk("test/files/issue18.pdf", "burst", "output", pattern);
  }

  @Test
  public void burst_issue90() throws IOException {
    String pattern = tmpDirectory.getRoot().getPath() + "/page%04d.pdf";
    pdftk("test/files/issue90.pdf", "burst", "output", pattern);
  }
};
