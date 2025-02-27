package io.github.mtbarr.assemblyvoting.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CpfUtilTest {

  @Test
  void isValidCpf_withValidCpf_returnsTrue() {
    assertTrue(CpfUtil.isValidCpf("12345678909"));
  }

  @Test
  void isValidCpf_withInvalidCpfLength_returnsFalse() {
    assertFalse(CpfUtil.isValidCpf("1234567890"));
  }

  @Test
  void isValidCpf_withAllSameDigits_returnsFalse() {
    assertFalse(CpfUtil.isValidCpf("11111111111"));
  }

  @Test
  void isValidCpf_withInvalidCheckDigits_returnsFalse() {
    assertFalse(CpfUtil.isValidCpf("12345678900"));
  }

  @Test
  void isValidCpf_withNonNumericCharacters_returnsFalse() {
    assertFalse(CpfUtil.isValidCpf("1234567890a"));
  }
}