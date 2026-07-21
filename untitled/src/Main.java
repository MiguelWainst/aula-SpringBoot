import java.util.*;
import java.util.function.BiConsumer;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        List<Pessoa> pessoas = Arrays.asList(
            new Pessoa("Alice", 25),
            new Pessoa("Bob", 30),
            new Pessoa("Charlie", 35),
            new Pessoa("David", 40),
            new Pessoa("Eve", 45)
        );
        BiConsumer<Pessoa, Integer> printPersonInfo = (p, age) -> System.out.println(p.getNome() + " - " + age);
        pessoas.stream().filter(p -> p.getIdade() > 30).forEach(p -> printPersonInfo.accept(p, p.getIdade()));
    }
}