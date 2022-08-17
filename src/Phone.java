import javax.swing.JOptionPane;

/**
 * @author Jean Carlo M. San Juan
 * @since 8/17/2022
 * A simple GUI for MobilePhone class.
 */
public class Phone extends MobilePhone {
    enum Action { //Represents the possible user states
        await,
        load,
        text,
        call,
        exit
    }
    protected Action action = Action.await;
    public static final  String insufficient = "You have insufficient load to do the operation.";
    public static void main(String[] args) {
        Phone phone = new Phone();
        phone.start();
    }

    /* @helper */
    private static String ask(String message) {
        return JOptionPane.showInputDialog(null, message);
    }  
    /* @helper */
    private static boolean isNullishString(String s) {
        return s == null || s.isBlank();
    }
    /* @helper */
    private static void show(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    public void start() {
        do {
            this.action = askActionWithPreviewBalance();
            switch(this.action) {
                case call:
                    dialCall();
                    break;
                case load:
                    addLoad();
                    break;
                case text:
                    writeText();
                    break;
                default://ignore
                    break; 
            }
        }while(this.action != Action.exit); //unless exit flag
    }

    public Action askActionWithPreviewBalance() {
        String raw = ask("What would you like to do?\nBalance: " + this.checkBalance() + "\n[1] load\n[2] text\n[3] call\n[4] exit");
        if (raw == null) {
            raw = "4";
        }

        switch(raw.toLowerCase()) {
            case "1": case "load":
                return Action.load;
            case "2": case "text":
                return Action.text;
            case "3": case "call":
                return Action.call;
            case "4": case "exit":
                return Action.exit;
            default:
                return Action.await;
        }
    }
    public void addLoad() {
        String raw = ask("How much to reload?");
        if (isNullishString(raw)) {
            return;
        }
        
        try {
            int amount = Integer.parseInt(raw);
            this.reload(amount);
        } catch (Exception e) {
            invalidNumber();
        }
    }

    private void invalidNumber() {
        show("Please enter a positive number");
    }
    public void dialCall() {
        String raw = askNetwork();
        if (isNullishString(raw)) {
            return;
        }
        char network = raw.charAt(0);
        raw = ask("Enter estimated call time:");

        float length;
        try {
            length = Float.parseFloat(raw);
        } catch (Exception e) {
            invalidNumber();
            return;
        }
        show(this.call(length, network) ? "Dialing network...":insufficient);
    }

    public String askNetwork() {
        return ask("Enter network:");
    }

    public void writeText() {
        String raw = ask("Enter text:");
        if (isNullishString(raw)) {
            show("Can't send empty text");
            return;
        }
        String text = raw;
        raw = askNetwork();
        if (isNullishString(raw)) {
            show("Invalid network");
            return;
        }
        char network = raw.charAt(0);
        show("Status: " +
            (this.text(text.length(), network) ? "sent": insufficient)
        );
    }
}