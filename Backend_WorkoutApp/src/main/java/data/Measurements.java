package data;

public class Measurements {
	
	private int personid;
	private int measid;
	private int weight;
	private int chest;
	private int waist;
	private int hip;
	private int bicep;
	private int thigh;
	private String date;

	public Measurements() {
		super();
	}

	/**
	 * @param measid
	 * @param weight
	 * @param chest
	 * @param waist
	 * @param hip
	 * @param bicep
	 * @param thigh
	 * @param date
	 */
	public Measurements(int personid, int measid, int weight, int chest, int waist, int hip, int bicep, int thigh, String date) {
		super();
		this.personid = personid;
		this.measid = measid;
		this.weight = weight;
		this.chest = chest;
		this.waist = waist;
		this.hip = hip;
		this.bicep = bicep;
		this.thigh = thigh;
		this.date = date;
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
	 * @return the measid
	 */
	public int getMeasid() {
		return measid;
	}

	/**
	 * @param measid the measid to set
	 */
	public void setMeasid(int measid) {
		this.measid = measid;
	}

	/**
	 * @return the weight
	 */
	public int getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}

	/**
	 * @return the chest
	 */
	public int getChest() {
		return chest;
	}

	/**
	 * @param chest the chest to set
	 */
	public void setChest(int chest) {
		this.chest = chest;
	}

	/**
	 * @return the waist
	 */
	public int getWaist() {
		return waist;
	}

	/**
	 * @param waist the waist to set
	 */
	public void setWaist(int waist) {
		this.waist = waist;
	}

	/**
	 * @return the hip
	 */
	public int getHip() {
		return hip;
	}

	/**
	 * @param hip the hip to set
	 */
	public void setHip(int hip) {
		this.hip = hip;
	}

	/**
	 * @return the bicep
	 */
	public int getBicep() {
		return bicep;
	}

	/**
	 * @param bicep the bicep to set
	 */
	public void setBicep(int bicep) {
		this.bicep = bicep;
	}

	/**
	 * @return the thigh
	 */
	public int getThigh() {
		return thigh;
	}

	/**
	 * @param thigh the thigh to set
	 */
	public void setThigh(int thigh) {
		this.thigh = thigh;
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
