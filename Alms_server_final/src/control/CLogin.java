package control;

import model.DataAccessObject;
import model.MLogin;
import remoteInterface.ILogin;
import valueObject.VLogin;
import valueObject.VResult;

public class CLogin implements ILogin {

 	public CLogin() {
	}
 	
	public VResult login(VLogin vLogin) {
		VResult vResult = null;
		
		DataAccessObject dataAccessObject = new DataAccessObject();
		MLogin mLogin = (MLogin) dataAccessObject.getAModel("UserId", MLogin.class, vLogin.getUserId());
		if (mLogin != null) {
			if (vLogin.getPassword().contentEquals(mLogin.getPassword())) {
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
