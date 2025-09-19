import enums.ActionLetter;
import model.*;
import payment.Payment;
import typePayment.Cash;
import typePayment.CoinAcceptor;
import typePayment.CreditCard;
import util.UniversalArray;
import util.UniversalArrayImpl;

import java.util.Scanner;

public class AppRunner {

    private final UniversalArray<Product> products = new UniversalArrayImpl<>();

    private Payment coinAcceptor;
    private Payment[] payments;
    private String paymentName;
    private CreditCard creditCard;


    private static boolean isExit = false;

    private AppRunner() {
        products.addAll(new Product[]{
                new Water(ActionLetter.B, 20),
                new CocaCola(ActionLetter.C, 50),
                new Soda(ActionLetter.D, 30),
                new Snickers(ActionLetter.E, 80),
                new Mars(ActionLetter.F, 80),
                new Pistachios(ActionLetter.G, 130)
        });
        creditCard = new CreditCard(100);
        payments = new Payment[]{creditCard, new Cash(100), new CoinAcceptor(100)};
    }

    public static void run() {
        AppRunner app = new AppRunner();
        while (!isExit) {
            app.startSimulation();
        }
    }

    private void startSimulation() {
        print("В автомате доступны:");
        showProducts(products);

        coinAcceptor = choosePayment();

        print(paymentName + " на сумму: " + coinAcceptor.getAmount());

        UniversalArray<Product> allowProducts = new UniversalArrayImpl<>();
        allowProducts.addAll(getAllowedProducts().toArray());

        chooseAction(allowProducts);


    }

    private UniversalArray<Product> getAllowedProducts() {
        UniversalArray<Product> allowProducts = new UniversalArrayImpl<>();

        for (int i = 0; i < products.size(); i++) {
            if (coinAcceptor.getAmount() >= products.get(i).getPrice()) {
                allowProducts.add(products.get(i));
            }
        }
        return allowProducts;
    }

    private void chooseAction(UniversalArray<Product> products) {
        print(" a - Пополнить баланс");
        showActions(products);
        print(" h - Выйти");
        String action = fromConsole();
        if (("1".equalsIgnoreCase(action) || "a".equalsIgnoreCase(action)) && !action.isEmpty() && !action.isBlank()) {
            coinAcceptor.setAmount(coinAcceptor.getAmount() + 10);
            print("Вы пополнили баланс на 10");
            return;
        }
        boolean notFound = false;
        try {
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getActionLetter().equals(ActionLetter.valueOf(action.toUpperCase()))) {
                    if(coinAcceptor.equals(creditCard)){
                        CreditCard.validateOTP(creditCard);
                    }
                    coinAcceptor.setAmount(coinAcceptor.getAmount() - products.get(i).getPrice());
                    print("Вы купили " + products.get(i).getName());
                    notFound = true;
                    break;
                }
            }
            if (!notFound && !"h".equalsIgnoreCase(action)) {
                if (products.size()==0&&"2".equals(action) ){
                    isExit = true;
                }else {
                    throw new ArithmeticException();
                }
            }
        } catch (IllegalArgumentException e) {
            if ("h".equalsIgnoreCase(action) || action.equals(String.valueOf(products.size() + 2))) {
                isExit = true;
            } else {
                boolean comandByNumber = false;
                    for (int i = 0; i < products.size(); i++) {
                        if (action.equals(String.valueOf(i + 2))) {
                            comandByNumber = true;
                            coinAcceptor.setAmount(coinAcceptor.getAmount() - products.get(i).getPrice());
                            print("Вы купили " + products.get(i).getName());
                            break;
                        }
                }if(!comandByNumber){
                    print("Недопустимая буква или цифра. Попробуйте еще раз.");
                    chooseAction(products);
                }
            }
        } catch (ArithmeticException e) {
            print("У вас не достатчно денег. Пополните баланс и попробуйте еще раз");
        }


    }


    private Payment choosePayment() {
        print("Пожалуйста выберите способ оплаты: ");
        System.out.printf("k - Кредитная карта%nt - Наличными%ns - Монетами%n");
        Payment payment = null;
        while (payment == null) {
            String typePayment = fromConsole();
            if (!typePayment.isEmpty() && !typePayment.isBlank()) {
                if (typePayment.length() == 1) {
                    switch (typePayment) {
                        case "k", "1":
                            payment = payments[0];
                            CreditCard.validateCardNumber(creditCard);
                            CreditCard.validateValidityPeriod(creditCard);
                            CreditCard.validateCVV(creditCard);
                            paymentName = "Кредитная карта";
                            break;
                        case "t", "2":
                            payment = payments[1];
                            paymentName = "Наличными";
                            break;
                        case "s", "3":
                            payment = payments[2];
                            paymentName = "Монета";
                            break;
                        default:
                            print("Недопустимая буква или цифра. Попробуйте еще раз.");
                            break;
                    }
                } else {
                    print("Можно ввести только одну команду");
                }
            } else {
                print("Поле не может быть пустым. Введите данные корректно");
            }
        }
        return payment;
    }

    private void showActions(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(String.format(" %s - %s", products.get(i).getActionLetter().getValue(), products.get(i).getName()));
        }
    }

    private String fromConsole() {
        return new Scanner(System.in).nextLine();
    }

    private void showProducts(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(products.get(i).toString());
        }
    }

    private static void print(String msg) {
        System.out.println(msg);
    }

}
