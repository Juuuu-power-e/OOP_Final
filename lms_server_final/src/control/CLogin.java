package control;

import model.Dao;
import model.MLogin;
import remoteInterface.ILogin;
import valueObject.VLogin;
import valueObject.VResult;

public class CLogin implements ILogin {

 	public CLogin() {
	}
 	
	public VResult login(VLogin vLogin) {
		VResult vResult = null;
		
		Dao dao = new Dao();
		MLogin mLogin = (MLogin) dao.getAModel("UserId", MLogin.class, vLogin.getUserId());
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
