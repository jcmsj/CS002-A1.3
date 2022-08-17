public class MobilePhone {
    private float load = 0; //Default
    public final char network;
    public final float ratePerText = 1;
    public final float ratePerCall;
    public static final int maxTextSize = 160;

    public MobilePhone(float load, char network) {
        this.load = load;
        switch(network) {
            case 'g':
                this.ratePerCall = 7;
            break;
            case 't':
                this.ratePerCall = 5;
            break;
            default:
                network = 's';
                this.ratePerCall = 3;
        }
        this.network = network;
    }

    public float checkBalance() {
        return this.load;
    }

    public void call(float minutes, char network) {
        float rate = this.isSameNetwork(network) ? this.ratePerCall: this.ratePerCall * 2;
        this.reduce(minutes * rate);
    }

    public void text(int length, char network) {
        float rate = this.isSameNetwork(network) ? this.ratePerText: this.ratePerText * 2;
        float cost = ((float) length / MobilePhone.maxTextSize) * rate;
        this.reduce(cost);
    }

    /* @helper */
    public boolean isSameNetwork(char network) {
        return this.network == network;
    }

    public void reload(float amount) {
        this.load += amount < 0 ? 0:amount;
    }

    public void reduce(float amount) throws IllegalArgumentException {
        if (amount >= 0 && this.load >= amount) {
            this.load -= amount;
            return;
        }

        throw new IllegalArgumentException("Invalid amount or balance");
    }
}