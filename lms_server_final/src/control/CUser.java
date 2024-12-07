package control;

import model.Dao;
import model.DaoFile;
import model.MUser;
import remoteInterface.IUser;
import valueObject.VUser;

public class CUser extends CControl implements IUser {

	public CUser(Dao dao) {
		super(dao);
	}

	public VUser getUser(String userId) {
		MUser mUser = (MUser) dao.getARow(userId, userId, MUser.class);
		if (mUser != null) {
			VUser vUser = new VUser(mUser.getUserId(), mUser.getName(), mUser.getAddress());
			return vUser;
		}
		return null;
	}
}
