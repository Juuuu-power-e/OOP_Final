package control;

import model.Dao;
import model.DaoFile;
import model.MLogin;
import remoteInterface.ILogin;
import valueObject.VLogin;
import valueObject.VResult;

public class CLogin extends CControl implements ILogin {

	public CLogin(Dao dao) {
		super(dao);
	}

	public VResult login(VLogin vLogin) {
		log(vLogin);
		VResult vResult = null;
		
		MLogin mLogin = (MLogin) dao.getARow("UserId", vLogin.getUserId(), MLogin.class);
		if (mLogin != null) {
			if (vLogin.getPassword().contentEquals(mLogin.getPassword())) {
				System.out.println(vLogin.getPassword()+" "+vLogin.getUserId());
				vResult = new VResult();
			} else {
				// password mismatch
			}
		} else {
			// no userId
		}
		return vResult;
	}
}
