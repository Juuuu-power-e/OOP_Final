package control;

import aspect.LogManager;
import model.Dao;
import valueObject.VValueObject;

import java.util.Arrays;

public class CControl {

    protected Dao dao;
    protected LogManager logManager;

    public CControl(Dao dao) {
        this.dao = dao;
        this.logManager = LogManager.getInstance();
    }

    protected void log(String message) {
        logManager.log(message);
    }
    protected void log(VValueObject valueObject) {
        log(Arrays.toString(valueObject.toLogMessage()));
    }
}
