import java.util.HashMap;
/** 
 * @author Jean Carlo M. San Juan
 * @since 8/18/2022
 * An object that simulates the traditional network capabilities of a phone.
 */
public class MobilePhone {
    protected float load = 0;
    public final char network;
    public final float ratePerText = 1;
    public final float ratePerCall;
    public static final int maxTextSize = 160;
    public static final char defaultNetwork = 's';
    public static HashMap<Character, Float> networks = new HashMap<Character, Float>();
    static {
        networks.put('s', (float) 3);
        networks.put('g', (float) 5);
        networks.put('t', (float) 7);
    }
    public static final float defaultRatePerCall = 3;

    private static Float getCallRate(char network) {
        return networks.get(network);
    }
    public MobilePhone() {
        this.network = defaultNetwork;
        this.ratePerCall = getCallRate(this.network);
    }

    public MobilePhone(float load) {
        this();
        this.reload(load);
    }

    public static boolean isValidNetwork(char network) {
        return networks.containsKey(network);
    }
    public MobilePhone(char network) {
        if (!isValidNetwork(network)) {
            network = defaultNetwork;
        }

        this.network = network;
        ratePerCall = networks.get(network).floatValue(); //SAFE
    }
    
    public MobilePhone(float load, char network) {
        this(network);
        this.reload(load);
    }

    public float checkBalance() {
        return this.load;
    }

    /** @return transaction result */
    public boolean call(float minutes, char network) {
        float rate = this.isSameNetwork(network) ? this.ratePerCall: this.ratePerCall * 2;
        return this.reduce(minutes * rate);
    }

    /** @return transaction result */
    public boolean text(int length, char network) {
        if (length > MobilePhone.maxTextSize || !isValidNetwork(network))
            return false;

        float rate = this.isSameNetwork(network) ? this.ratePerText: this.ratePerText * 2;
        return this.reduce(rate);
    }
    /* @helper */
    public boolean isSameNetwork(char network) {
        return this.network == network;
    }

    /** @return transaction result */
    public boolean reload(float amount) {
        if (amount < 0)
            return false;

        this.load += amount;
        return true;
    }

    /** @return transaction result */
    public boolean reduce(float amount) {
        if (amount >= 0 && this.load >= amount) {
            this.load -= amount;
            return true;
        }

        return false;
    }
}