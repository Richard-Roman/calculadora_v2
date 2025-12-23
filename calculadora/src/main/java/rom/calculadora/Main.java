package rom.calculadora;

import org.springframework.boot.SpringApplication;

/**
 * Punto de entrada para la aplicación Calculadora Web con Spring Boot.
 * La aplicación se ejecuta en http://localhost:8080
 */
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(CalculadoraApplication.class, args);
    }
}