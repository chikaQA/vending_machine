package typePayment;
import payment.Payment;

import java.util.Random;
import java.util.Scanner;

public class CreditCard implements Payment {
    private String cardNumber;
    private String validityPeriod;
    private String cvv;
    private int otp;
    private int amount;

    public CreditCard(int amount) {
        this.amount = amount;
    }

    public static void validateCardNumber(CreditCard creditCard) {
        while (creditCard.cardNumber == null) {
            print("Введите номер карты: ");
            String cardNumber2 = fromConsole();
            if (!cardNumber2.isEmpty() && !cardNumber2.isBlank()) {
                if (cardNumber2.length() == 16) {
                    try {
                        creditCard.setCardNumber(String.valueOf(Long.parseLong(cardNumber2)));
                    } catch (Exception e) {
                        print("Введите корректные данные");
                    }

                } else {
                    print("Карта должна быть 16 значений вы ввели " + cardNumber2.length());
                }
            } else {
                print("Поле не может быть пустым. Введите данные корректно");
            }
        }
    }

    public static void validateValidityPeriod(CreditCard creditCard) {
        while (creditCard.validityPeriod == null) {
            print("Введите срок действия карты [месяц/год]: ");
            String period = fromConsole();
            if (!period.isEmpty() && !period.isBlank()) {
                try {
                    String[] validityPeriod = period.split("/");
                    if (validityPeriod.length == 2 && Integer.parseInt(validityPeriod[1]) > 25 && Integer.parseInt(validityPeriod[0]) < 12) {
                        creditCard.setValidityPeriod(period);
                    } else {
                        print("Введите корректные данные");
                    }
                } catch (ArithmeticException e) {
                    print("Введите корректные данные");
                }
            } else {
                print("Поле не может быть пустым. Введите данные корректно");
            }
        }
    }

    public static void validateCVV(CreditCard creditCard) {
        while (creditCard.cvv == null) {
            print("Введите cvv код на задней части экрана: ");
            String cvv = fromConsole();
            if (!cvv.isEmpty() && !cvv.isBlank()) {
                if (cvv.length() == 3) {
                    try {
                        Integer.parseInt(cvv);
                        creditCard.setCvv(cvv);
                    } catch (Exception e) {
                        print("Введите корректные данные");
                    }
                } else {
                    print("Введите трехзначное число корректно");
                }
            } else {
                print("Поле не может быть пустым. Введите данные корректно");
            }
        }
    }

    public static int validateOTP(CreditCard creditCard) {
        print("Для подтверждения оплаты введите SMS-код из 5 цифр, отправленыый на ваш мобильный номер");
        Random random = new Random();
        int otpCode = random.nextInt(10000, 99999);
        print("Вам пришло SMS-для оплаты " + otpCode);
        while (true) {
            try {
                String otp = fromConsole();
                int otpSms = Integer.parseInt(otp);
                if (!otp.isEmpty() && !otp.isBlank()) {
                    if (otp.length() == 5 && otpSms == otpCode) {
                        creditCard.setOtp(otpSms);
                        return otpSms;
                    } else {
                        print("Неправильно введен ОТП код ");
                    }
                } else {
                    print("Поле не может быть пустым. Введите данные корректно");
                }
            } catch (Exception e) {
                print("Неправильно введен ОТП код");
            }

        }
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setValidityPeriod(String validityPeriod) {
        this.validityPeriod = validityPeriod;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    private static String fromConsole() {
        return new Scanner(System.in).nextLine();
    }

    public void setOtp(int otp) {
        this.otp = otp;
    }

    private static void print(String msg) {
        System.out.println(msg);
    }
}