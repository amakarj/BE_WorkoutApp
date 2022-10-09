package data;

public class WorkoutExercise {
	
	private int workoutid;
	private int exerciseid;
	private int workoutexerciseid;
	private int reps;
	private int weights;
	private int duration;
	private int personid;
	private String date;
	private String movename;
	private String picture;
	
	
	

	public WorkoutExercise() {
		super();
	}


	/**
	 * @param workoutexerciseid
	 * @param reps
	 * @param weights
	 * @param duration
	 */
	public WorkoutExercise(int workoutexerciseid, int reps, int weights, int duration) {
		super();
		this.workoutexerciseid = workoutexerciseid;
		this.reps = reps;
		this.weights = weights;
		this.duration = duration;
	}


	


	/**
	 * @param workoutid
	 * @param exerciseid
	 * @param workoutexerciseid
	 * @param reps
	 * @param weights
	 * @param duration
	 * @param personid
	 * @param date
	 * @param movename
	 * @param picture
	 */
	public WorkoutExercise(int workoutid, int exerciseid, int workoutexerciseid, int reps, int weights, int duration,
			int personid, String date, String movename, String picture) {
		super();
		this.workoutid = workoutid;
		this.exerciseid = exerciseid;
		this.workoutexerciseid = workoutexerciseid;
		this.reps = reps;
		this.weights = weights;
		this.duration = duration;
		this.personid = personid;
		this.date = date;
		this.movename = movename;
		this.picture = picture;
	}


	/**
	 * @return the workoutexerciseid
	 */
	public int getWorkoutexerciseid() {
		return workoutexerciseid;
	}


	/**
	 * @param workoutexerciseid the workoutexerciseid to set
	 */
	public void setWorkoutexerciseid(int workoutexerciseid) {
		this.workoutexerciseid = workoutexerciseid;
	}


	/**
	 * @return the reps
	 */
	public int getReps() {
		return reps;
	}


	/**
	 * @param reps the reps to set
	 */
	public void setReps(int reps) {
		this.reps = reps;
	}


	/**
	 * @return the weights
	 */
	public int getWeights() {
		return weights;
	}


	/**
	 * @param weights the weights to set
	 */
	public void setWeights(int weights) {
		this.weights = weights;
	}


	/**
	 * @return the duration
	 */
	public int getDuration() {
		return duration;
	}


	/**
	 * @param duration the duration to set
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}


	public int getWorkoutid() {
		return workoutid;
	}


	public void setWorkoutid(int workoutid) {
		this.workoutid = workoutid;
	}


	public int getExerciseid() {
		return exerciseid;
	}


	public void setExerciseid(int exerciseid) {
		this.exerciseid = exerciseid;
	}


	/**
	 * @return the personid
	 */
	public int getPersonid() {
		return personid;
	}


	/**
	 * @param personid the personid to set
	 */
	public void setPersonid(int personid) {
		this.personid = personid;
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
	 * @return the picture
	 */
	public String getPicture() {
		return picture;
	}


	/**
	 * @param picture the picture to set
	 */
	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	public String toString() {
		return workoutexerciseid + "workoutid " + workoutid +" " + exerciseid +" " + reps + " " +weights +" " + duration +" " + date +" " + personid +" " + movename +" " + picture;
	}
	

}
