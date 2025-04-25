package SendOTP;

import java.util.Random;

public class GenerateTransactionID {
    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    private String transactionID;

    public GenerateTransactionID() {
        long timestamp = System.currentTimeMillis();
        int randomNum = new Random().nextInt(9000) + 1000;
        String transactionId = "TXN" + timestamp + randomNum;
        this.transactionID = transactionId;
    }
}
