package control;

import java.util.Vector;

import model.DataAccessObject;
import model.MGangjwa;
import model.MModel;
import remoteInterface.IGangjwa;
import valueObject.VGangjwa;

public class CGangjwa implements IGangjwa{
	public CGangjwa() {
	}
	
	public Vector<VGangjwa> getData(String fileName) {
		DataAccessObject dataAccessObject = new DataAccessObject();
		Vector<MModel> mModels = dataAccessObject.getModels(fileName, MGangjwa.class);
		
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
