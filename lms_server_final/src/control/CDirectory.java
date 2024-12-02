package control;

import java.util.Vector;

import model.Dao;
import model.MDirectory;
import model.MModel;
import remoteInterface.IDirectory;
import valueObject.VDirectory;

public class CDirectory implements IDirectory{

	public CDirectory() {
	}
	
	public Vector<VDirectory> getData(String fileName) {
		Dao dao = new Dao();
		Vector<MModel> mModels = dao.getModels(fileName, MDirectory.class);
		
		Vector<VDirectory> vDrectories = new Vector<VDirectory>();
		for (MModel mModel: mModels) {
			MDirectory mDirectory = (MDirectory) mModel;
			
			VDirectory vDirectory = new VDirectory(
				mDirectory.getName(),
				mDirectory.getFileName()
			);
			vDrectories.add(vDirectory);
		}		
		return vDrectories;
	}

}
