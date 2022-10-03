package data;

public class Exercise {
	
	private int exerciseid;
	private String movename;
	private String movepic;
	private int checked;
	
//	Hei
	public Exercise() {
		super();
	}

	/**
	 * @param exerciseid
	 * @param movename
	 * @param movepic
	 * @param checked
	 */
	public Exercise(int exerciseid, String movename, String movepic, int checked) {
		super();
		this.exerciseid = exerciseid;
		this.movename = movename;
		this.movepic = movepic;
		this.checked = checked;
	}

	/**
	 * @return the exerciseid
	 */
	public int getExerciseid() {
		return exerciseid;
	}

	/**
	 * @param exerciseid the exerciseid to set
	 */
	public void setExerciseid(int exerciseid) {
		this.exerciseid = exerciseid;
	}

	/**
	 * @return the movename
	 */
	public String getMovename() {
		return movename;
	}

	/**
	 * @param movename the movename to set
	 */
	public void setMovename(String movename) {
		this.movename = movename;
	}

	/**
	 * @return the movepic
	 */
	public String getMovepic() {
		return movepic;
	}

	/**
	 * @param movepic the movepic to set
	 */
	public void setMovepic(String movepic) {
		this.movepic = movepic;
	}

	/**
	 * @return the checked
	 */
	public int isChecked() {
		return checked;
	}

	/**
	 * @param checked the checked to set
	 */
	public void setChecked(int checked) {
		this.checked = checked;
	}

}
