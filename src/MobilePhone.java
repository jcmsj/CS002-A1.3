public class MobilePhone {
    private float load = 0;
    public final char network = 's';
    public final float ratePerText = 3;
    public final float ratePerCall = 5;
    static final int maxTextSize = 160;
    public float checkBalance() {
        return this.load;
    }

    public void call(float minutes, char network) {
        float rate = network == this.network ? this.ratePerCall: this.ratePerCall * 2;
        this.load -= minutes * rate;
    }

    public void text(int length, char network) {
        float rate = network == this.network ? this.ratePerText: this.ratePerText * 2;
        this.load -= ((float) length / MobilePhone.maxTextSize) * rate;
    }

    public void reload(float amount) {
        this.load += amount;
    }
}