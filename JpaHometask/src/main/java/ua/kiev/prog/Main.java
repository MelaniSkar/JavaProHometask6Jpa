package ua.kiev.prog;

import ua.kiev.prog.Entity.Dish;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.util.*;

public class Main {

    private static  EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;

    public static void main(String... args) {

        entityManagerFactory = Persistence.createEntityManagerFactory("MenuApp");
        entityManager = entityManagerFactory.createEntityManager();

        Scanner scanner = new Scanner(System.in);
        //TODO:считывать параметры и добавлять блюдо в меню
        while (scanner.hasNext())
        {
            String name = scanner.next();
            if (name.equals("exit"))
                break;
            addDishToMenu(name, scanner.nextBigDecimal(), scanner.nextInt(), scanner.nextBigDecimal());
        }

        System.out.println("dishes with price from 10 to 20");
        printDishes(getDishesPriceFromTo(BigDecimal.valueOf(10), BigDecimal.valueOf(20)));
        System.out.println("dishes with discount");
        printDishes(getDishesWithDiscount());
        System.out.println("dishes with summary weight less than 1000g");
        printDishes(getDishesListWithWeightLessThan(1000));

    }

    private static void addDishToMenu(String name, BigDecimal price, Integer weight, BigDecimal discount) {

        entityManager.getTransaction().begin();
        try {

            Dish dish = new Dish(name, price, weight, discount);
            entityManager.persist(dish);

            entityManager.getTransaction().commit();

        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        }

    }

    private static void printDishes(List<Dish> dishes) {
        for (Dish dish : dishes) {
            System.out.println(dish.getName() + " " + dish.getPrice() + " " + dish.getWeight() + " "
                    + dish.getDiscount());
        }
    }

    private static List<Dish> getDishesPriceFromTo(BigDecimal from, BigDecimal to) {

        entityManager.getTransaction().begin();

        return entityManager.createQuery("select dish from Dish dish where dish.price > :from and dish.price < :to")
                .setParameter("from", from).setParameter("to", to)
                .getResultList();
    }

    private static List<Dish> getDishesWithDiscount() {

        return entityManager.createQuery("select dish from Dish dish where dish.discount > 0").getResultList();

    }

    private static List<Dish> getDishesListWithWeightLessThan(Integer weight) {

        List<Dish> dishes =  entityManager.createQuery("select dish from Dish dish").getResultList();
        List<Dish> result = new ArrayList<>();
        Integer sumWeight = 0;

        for (Dish dish : dishes) {
            if (dish.getWeight() <= weight - sumWeight) {
                result.add(dish);
                sumWeight += dish.getWeight();
            }
        }

        return result;

    }

}
