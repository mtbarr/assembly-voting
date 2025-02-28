package io.github.mtbarr.assemblyvoting.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CpfUtilTest {

  @Test
  @DisplayName("Should return true if valid cpf")
  void shouldReturnTrueIfValidCpf() {
    assertTrue(CpfUtil.isCpf("12345678909"));
  }

  @Test
  @DisplayName("Should return false if invalid cpf length")
  void shouldReturnFalseIfInvalidCpfLength() {
    assertFalse(CpfUtil.isCpf("1234567890"));
  }

  @Test
  @DisplayName("Should return false if all digits are the same")
  void shouldReturnFalseIfAllDigitsAreTheSame() {
    assertFalse(CpfUtil.isCpf("11111111111"));
  }

  @Test
  @DisplayName("Should return false if invalid check digits")
  void shouldReturnFalseIfInvalidCheckDigits() {
    assertFalse(CpfUtil.isCpf("12345678900"));
  }

  @Test
  @DisplayName("Should return false if non numeric characters")
  void shouldReturnFalseIfNonNumericCharacters() {
    assertFalse(CpfUtil.isCpf("1234567890a"));
  }
}