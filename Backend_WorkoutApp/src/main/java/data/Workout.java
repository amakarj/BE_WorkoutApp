package data;

public class Workout {

	private int workoutid;
	private int personid;
	private String date;

	public Workout() {
		super();
	}

	/**
	 * @param workoutid
	 * @param date
	 */
	public Workout(int workoutid, String date, int personid) {
		super();
		this.workoutid = workoutid;
		this.date = date;
		this.personid = personid;
	}

	/**
	 * @return the workoutid
	 */
	public int getWorkoutid() {
		return workoutid;
	}

	/**
	 * @param workoutid the workoutid to set
	 */
	public void setWorkoutid(int workoutid) {
		this.workoutid = workoutid;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	public int getPersonid() {
		return personid;
	}

	public void setPersonid(int personid) {
		this.personid = personid;
	}

}
