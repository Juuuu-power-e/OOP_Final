package control;

import java.util.Vector;

import model.Dao;
import model.MGangjwa;
import model.MModel;
import remoteInterface.IResult;
import valueObject.VGangjwa;

public class CResult implements IResult{

	public CResult() {		
	}
	
	public void save(String fileName, Vector<VGangjwa> vGangjwas) {
		Vector<MModel> mGangjwas = new Vector<MModel>();
		for (VGangjwa vGangjwa: vGangjwas) {
			MGangjwa mGangjwa = new MGangjwa();
			
			mGangjwa.setId(vGangjwa.getId());
			mGangjwa.setName(vGangjwa.getName());
			mGangjwa.setLecturer(vGangjwa.getLecturer());
			mGangjwa.setCredit(vGangjwa.getCredit());
			mGangjwa.setTime(vGangjwa.getTime());
			
			mGangjwas.add(mGangjwa);
		}
		Dao Dao = new Dao();
		Dao.save(fileName, mGangjwas);
	}

	public Vector<VGangjwa> get(String fileName) {
		Dao dao = new Dao();
		Vector<MModel> mModels = dao.getModels(fileName, MGangjwa.class);
		
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
