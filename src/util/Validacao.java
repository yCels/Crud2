package util;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;

public class Validacao {
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    
    public static boolean validarNome(String nome) {
        return nome != null && nome.trim().length() >= 3;
    }
    
    public static boolean validarCpf(String cpf) {
        return cpf != null && cpf.matches("\\d{11}");
    }
    
    public static boolean validarEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }
    
    public static boolean validarDataNascimento(LocalDate dataNascimento) {
        if (dataNascimento == null) return false;
        return Period.between(dataNascimento, LocalDate.now()).getYears() >= 16;
    }
    
    public static boolean validarCargaHoraria(int cargaHoraria) {
        return cargaHoraria >= 20;
    }
    
    public static boolean validarLimiteAlunos(int limiteAlunos) {
        return limiteAlunos >= 1;
    }
}