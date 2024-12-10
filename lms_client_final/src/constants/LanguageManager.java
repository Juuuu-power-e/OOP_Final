package constants;

import mainFrame.MyComponent;

import java.util.Locale;
import java.util.ResourceBundle;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageManager {
    private static LanguageManager instance = new LanguageManager();
    private ResourceBundle bundle;
    private Locale currentLocale;
    private List<MyComponent> observers = new ArrayList<>();

    private LanguageManager() {
        setLocale(Locale.KOREAN); // 기본 로케일을 한국어로 설정
    }

    public static LanguageManager getInstance() {
        return instance;
    }

    public void setLocale(Locale locale) {
        currentLocale = locale;
        bundle = ResourceBundle.getBundle("messages", currentLocale);
        notifyObservers(); // 언어 변경 시 모든 Observer에 알림
    }

    public String get(String key) {
        return bundle.getString(key);
    }

    public void registerObserver(MyComponent observer) {
        observers.add(observer);
    }

    public void unregisterObserver(MyComponent observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        for (MyComponent observer : observers) {
            observer.updateText();
        }
    }
}
