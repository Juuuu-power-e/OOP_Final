package control;

import model.Dao;
import model.MUser;
import remoteInterface.IUser;
import valueObject.VUser;

public class CUser implements IUser {

	public VUser getUser(String userId) {
		Dao dao = new Dao();
		MUser mUser = (MUser) dao.getAModel(userId, MUser.class, userId);
		if (mUser != null) {
			VUser vUser = new VUser(mUser.getUserId(), mUser.getName(), mUser.getAddress());
			return vUser;
		}
		return null;
	}
}
