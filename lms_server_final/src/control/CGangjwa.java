package control;

import java.util.Vector;

import model.Dao;
import model.DaoFile;
import model.MGangjwa;
import model.MModel;
import remoteInterface.IGangjwa;
import valueObject.VGangjwa;

public class CGangjwa extends CControl implements IGangjwa{
	public CGangjwa(Dao dao) {
		super(dao);
	}
	
	public Vector<VGangjwa> getData(String fileName) {
		Vector<MModel> mModels = dao.getRows(fileName, MGangjwa.class);

		Vector<VGangjwa> vGangjwas = new Vector<VGangjwa>();
		for (MModel mModel: mModels) {
			MGangjwa mGangjwa = (MGangjwa) mModel;

			VGangjwa vGangjwa = new VGangjwa();
			vGangjwa.setId(mGangjwa.getId());
			vGangjwa.setName(mGangjwa.getName());
			vGangjwa.setLecturer(mGangjwa.getLecturer());
			vGangjwa.setCredit(mGangjwa.getCredit());
			vGangjwa.setTime(mGangjwa.getTime());
			vGangjwas.add(vGangjwa);
		}
		return vGangjwas;
	}

}
