package data;

public class Person {

	private int personid;
	private String firstname;
	private String lastname;
	private String location;
	private int height;
	private String slogan;
	private String picture;
	
	public Person() {
		super();
	}
	public Person(int personid, String firstname) {
		this.personid=personid;
		this.firstname=firstname;
	}
	
	public Person(int personid, String firstname, String lastname, String location, int height, String slogan,
			String picture) {
		super();
		this.personid = personid;
		this.firstname = firstname;
		this.lastname = lastname;
		this.location = location;
		this.height = height;
		this.slogan = slogan;
		this.picture = picture;
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
	 * @return the firstname
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * @param firstname the firstname to set
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * @return the lastname
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * @param lastname the lastname to set
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @return the slogan
	 */
	public String getSlogan() {
		return slogan;
	}

	/**
	 * @param slogan the slogan to set
	 */
	public void setSlogan(String slogan) {
		this.slogan = slogan;
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



}
