package observer.observerswing.model;

public class Person implements Observer{
    public static String COLD = "frio";
    public static String WARM = "calido";
    public static String HOT = "calido";
    public static String HELL = "caluroso";
    public static String PRE = "La temperatura es ";
    
    private String name;
    private String code;
    private boolean isSubscribed;
    private String message;

    public Person(String name, String code, boolean isSubscribed) {
        this.name = name;
        this.code = code;
        this.isSubscribed = isSubscribed;
    }
    
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the isSubscribed
     */
    public boolean isIsSubscribed() {
        return isSubscribed;
    }

    /**
     * @param isSubscribed the isSubscribed to set
     */
    public void setIsSubscribed(boolean isSubscribed) {
        this.isSubscribed = isSubscribed;
    }
    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
    
    private String getTemperatureLabel(double temperature) {
        if (temperature <= 10) {
            return COLD;
        } else if (temperature <= 20) {
            return WARM;
        } else if (temperature <= 30) {
            return HOT;
        } else {
            return HELL;
        }
    }
    @Override
    public void update(double temperature) {
        this.setMessage(PRE +getTemperatureLabel(temperature)+": "+ temperature + "°C");
    }
    
}
