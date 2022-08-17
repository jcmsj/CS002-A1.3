/** 
 * @author Jean Carlo M. San Juan
 * @since 8/17/2022
 * An object that simulates the traditional network capabilities of a phone.
 */
public class MobilePhone {
    private float load = 0; //Default
    public final char network;
    public final float ratePerText = 1;
    public final float ratePerCall;
    public static final int maxTextSize = 160;
    public static final char defaultNetwork = 's';
    public static final float defaultRatePerCall = 3;

    public MobilePhone() {
        this.network = defaultNetwork;
        this.ratePerCall = defaultRatePerCall;
    }

    public MobilePhone(float load) {
        this.network = defaultNetwork;
        this.ratePerCall = defaultRatePerCall;
        this.reload(load);
    }

    public MobilePhone(char network) {
        switch(network) {
            case 'g':
                ratePerCall = 7;
            break;
            case 't':
                ratePerCall = 5;
            break;
            default:
                network = defaultNetwork;
                ratePerCall = defaultRatePerCall;
        }
        this.network = network;
    }
    
    public MobilePhone(float load, char network) {
        switch(network) {
            case 'g':
                this.ratePerCall = 7;
            break;
            case 't':
                this.ratePerCall = 5;
            break;
            default:
                network = defaultNetwork;
                this.ratePerCall = defaultRatePerCall;
        }
        this.network = network;
        this.reload(load); //Handle negative here
    }

    public float checkBalance() {
        return this.load;
    }

    public boolean call(float minutes, char network) {
        float rate = this.isSameNetwork(network) ? this.ratePerCall: this.ratePerCall * 2;
        return this.reduce(minutes * rate);
    }

    public boolean text(int length, char network) {
        float rate = this.isSameNetwork(network) ? this.ratePerText: this.ratePerText * 2;
        float cost = ((float) length / MobilePhone.maxTextSize) * rate;
        return this.reduce(cost);
    }
    /* @helper */
    public boolean isSameNetwork(char network) {
        return this.network == network;
    }

    public void reload(float amount) {
        this.load += amount < 0 ? 0:amount;
    }

    public boolean reduce(float amount) {
        if (amount >= 0 && this.load >= amount) {
            this.load -= amount;
            return true;
        }

        return false;
    }
}