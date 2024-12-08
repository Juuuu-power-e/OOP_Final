package constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageManager {
    private static LanguageManager instance = new LanguageManager();
    private ResourceBundle bundle;
    private Locale currentLocale;
    private List<TextObserver> observers = new ArrayList<>();

    private LanguageManager() {
        setLocale(Locale.KOREA);
    }

    public static LanguageManager getInstance() {
        return instance;
    }

    public void setLocale(Locale locale) {
        currentLocale = locale;
        bundle = ResourceBundle.getBundle("messages", currentLocale);
        notifyObservers();
    }

    public String get(String key) {
        return bundle.getString(key);
    }

    public void registerObserver(TextObserver observer) {
        observers.add(observer);
    }

    public void unregisterObserver(TextObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        for (TextObserver observer : observers) {
            observer.onLanguageChanged();
        }
    }
}