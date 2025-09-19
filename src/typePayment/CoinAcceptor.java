package typePayment;

import payment.Payment;

public class CoinAcceptor implements Payment {
    private int amount;

    public CoinAcceptor(int amount) {
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
