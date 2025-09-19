package typePayment;

import payment.Payment;

public class Cash implements Payment {
    private int amount;

    public Cash(int amount) {
        this.amount = amount;
    }
    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public void setAmount(int amount) {
        this.amount = amount;
    }
}
