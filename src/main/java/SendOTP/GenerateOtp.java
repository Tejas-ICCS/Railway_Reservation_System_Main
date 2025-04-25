package SendOTP;

import java.util.concurrent.ThreadLocalRandom;

public class GenerateOtp {
    public int getOpt() {
        return opt;
    }

    public void setOpt(int opt) {
        this.opt = opt;
    }

    private int opt;
    public GenerateOtp() {
        int otp = ThreadLocalRandom.current().nextInt(100000,1000000);
        this.setOpt(otp);
    }
}
