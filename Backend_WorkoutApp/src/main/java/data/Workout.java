package data;

public class Workout {

	private int workoutid;
	private String date;

	public Workout() {
		super();
	}

	/**
	 * @param workoutid
	 * @param date
	 */
	public Workout(int workoutid, String date) {
		super();
		this.workoutid = workoutid;
		this.date = date;
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

}
