package com.gitlab.pdftk_java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import org.junit.Test;
import java.io.IOException;

public class FormTest extends BlackBox {
  @Test
  public void dump_data_fields() throws IOException {
    pdftk("test/files/form.pdf", "dump_data_fields");
    String expectedData = slurp("test/files/form.data");
    assertEquals(expectedData, systemOut.getLog());
  }

  @Test
  public void generate_fdf() throws IOException {
    pdftk("test/files/form.pdf", "generate_fdf", "output", "-");
    byte[] expectedData = slurpBytes("test/files/form.fdf");
    assertArrayEquals(expectedData, systemOut.getLogAsBytes());
  }

  @Test
  public void generate_fdf_issue88() throws IOException {
    pdftk("test/files/issue88.pdf", "generate_fdf", "output", "-");
    byte[] expectedData = slurpBytes("test/files/issue88.fdf");
    assertArrayEquals(expectedData, systemOut.getLogAsBytes());
  }

  @Test
  public void fill_from_fdf() throws IOException {
    pdftk("test/files/form.pdf", "fill_form", "test/files/form-filled.fdf", "output", "-");
  }

  @Test
  public void dump_data_fields_utf8_options() throws IOException {
    pdftk("test/files/form-pdfdocencoding.pdf", "dump_data_fields_utf8");
    String expectedData = slurp("test/files/form-pdfdocencoding.data");
    assertEquals(expectedData, systemOut.getLog());
  }

  @Test
  public void generate_fdf_utf8_options() throws IOException {
    pdftk("test/files/form-pdfdocencoding.pdf", "generate_fdf", "output", "-");
    byte[] expectedData = slurpBytes("test/files/form-pdfdocencoding.fdf");
    assertArrayEquals(expectedData, systemOut.getLogAsBytes());
  }

  @Test
  public void fill_from_fdf_utf8_options() throws IOException {
    pdftk("test/files/form-pdfdocencoding.pdf", "fill_form", "test/files/form-pdfdocencoding-filled.fdf", "output", "-");
  }

  @Test
  public void replace_font_ttf() throws IOException {
    pdftk("test/files/form.pdf", "fill_form", "test/files/form-filled.fdf", "output", "-", "replacement_font", "test/files/D-DIN.ttf");
  }

  @Test
  public void replace_font_cff() throws IOException {
    pdftk("test/files/form.pdf", "fill_form", "test/files/form-filled.fdf", "output", "-", "replacement_font", "test/files/D-DIN.otf");
  }

};
