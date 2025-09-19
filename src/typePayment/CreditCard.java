package typePayment;

import helper.HelperMethod;
import payment.Payment;

import java.util.Random;

public class CreditCard implements Payment {
    private String cardNumber;
    private String validityPeriod;
    private String cvv;
    private int otp;
    private int amount;


    public CreditCard(int amount) {
        this.amount = amount;
    }



    public static void validateCardNumber(CreditCard creditCard){
        while (creditCard.cardNumber==null) {
            HelperMethod.print("Введите номер карты: ");
            String cardNumber2 = HelperMethod.fromConsole();
            if(!cardNumber2.isEmpty()&&!cardNumber2.isBlank()){
                if(cardNumber2.length()==16){
                    try{
                        creditCard.setCardNumber(String.valueOf(Long.parseLong(cardNumber2)));
                    }catch (Exception e){
                        HelperMethod.print("Введите корректные данные");
                    }

                }else{
                    HelperMethod.print("Карта должна быть 16 значений вы ввели "+cardNumber2.length());
                }
            }else {
                HelperMethod.print("Поле не может быть пустым. Введите данные корректно");
            }
        }
    }
    public static void validateValidityPeriod(CreditCard creditCard){
        while (creditCard.validityPeriod==null) {
            HelperMethod.print("Введите срок действия карты [месяц/год]: ");
            String period = HelperMethod.fromConsole();
            if(!period.isEmpty()&&!period.isBlank()){
                try{
                    String[] validityPeriod = period.split("/");
                    if(validityPeriod.length==2&&Integer.parseInt(validityPeriod[1])>25&&Integer.parseInt(validityPeriod[0])<12){
                        creditCard.setValidityPeriod(period);
                    }else {
                        HelperMethod.print("Введите корректные данные");
                    }
                }catch (ArithmeticException e){
                    HelperMethod.print("Введите корректные данные");
                }
            }else {
                HelperMethod.print("Поле не может быть пустым. Введите данные корректно");
            }
        }
    }
    public static void validateCVV(CreditCard creditCard){
        while (creditCard.cvv==null) {
            HelperMethod.print("Введите cvv код на задней части экрана: ");
            String cvv = HelperMethod.fromConsole();
            if(!cvv.isEmpty()&&!cvv.isBlank()){
                if (cvv.length()==3) {
                    try {
                        Integer.parseInt(cvv);
                         creditCard.setCvv(cvv);
                    } catch (Exception e) {
                        HelperMethod.print("Введите корректные данные");
                    }
                }else {
                    HelperMethod.print("Введите трехзначное число корректно");
                }
            }else {
                HelperMethod.print("Поле не может быть пустым. Введите данные корректно");
            }
        }
    }

    public static int validateOTP(CreditCard creditCard){
        HelperMethod.print("Введите SMS-код из 5 цифр, отправленыый на ваш мобильный номер");
        Random random = new Random();
        int otpCode =random.nextInt(10000,99999);
        HelperMethod.print("Вам пришло SMS-для оплаты "+otpCode);
        while (true) {
            try{
                String otp = HelperMethod.fromConsole();
                int otpC = Integer.parseInt(otp);
                if(!otp.isEmpty()&&!otp.isBlank()){
                    if (otp.length()==5&&otpC==otpCode) {
                       return otpC;
                    }else {
                        HelperMethod.print("Неправильно введен ОТП код ");
                    }
                }else {
                    HelperMethod.print("Поле не может быть пустым. Введите данные корректно");
                }
            }catch (Exception e){
                HelperMethod.print("Неправильно введен ОТП код");
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

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getValidityPeriod() {
        return validityPeriod;
    }

    public void setValidityPeriod(String validityPeriod) {
        this.validityPeriod = validityPeriod;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public int getOtp() {
        return otp;
    }

    public void setOtp(int otp) {
        this.otp = otp;
    }
}
