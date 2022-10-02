package data;

public class WorkoutExercise {
	
	private int workoutid;
	private int exerciseid;
	private int workoutexerciseid;
	private int reps;
	private int weights;
	private int duration;
	

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

}
