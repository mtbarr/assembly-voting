package io.github.mtbarr.assemblyvoting.util;

public class CpfUtil {

  public static boolean isValidCpf(String cpf) {
    if (cpf.length() != 11 || cpf.chars().allMatch(c -> c == cpf.charAt(0))) {
      return false;
    }

    try {
      int sm = 0, peso = 10;

      for (int i = 0; i < 9; i++) {
        sm += (cpf.charAt(i) - '0') * peso--;
      }

      char dig10 = calcularDigito(sm);

      sm = 0;
      peso = 11;
      for (int i = 0; i < 10; i++) {
        sm += (cpf.charAt(i) - '0') * peso--;
      }

      char dig11 = calcularDigito(sm);

      return dig10 == cpf.charAt(9) && dig11 == cpf.charAt(10);
    } catch (Exception e) {
      return false;
    }
  }

  private static char calcularDigito(int soma) {
    int r = 11 - (soma % 11);
    return (r >= 10) ? '0' : (char) (r + '0');
  }
}
