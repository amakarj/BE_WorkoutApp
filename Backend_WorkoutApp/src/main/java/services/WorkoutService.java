package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import conn.Connections;
import data.Exercise;
import data.Measurements;
import data.Person;
import data.Workout;
import data.WorkoutExercise;

@Path("/workoutservice")
public class WorkoutService {

// PERSON SERVICES
	@GET
	@Path("/readperson")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Person> readPerson() {

		ArrayList<Person> list = new ArrayList<>();

		Connection conn = Connections.getConnection();
		try {
			Statement stmt = conn.createStatement();
			ResultSet RS = stmt.executeQuery("select * from person");
			while (RS.next()) {
				Person person = new Person();
				person.setPersonid(RS.getInt("personid"));
				person.setFirstname(RS.getString("firstname"));
				person.setLastname(RS.getString("lastname"));
				person.setLocation(RS.getString("location"));
				person.setHeight(RS.getInt("height"));
				person.setSlogan(RS.getString("slogan"));
				person.setPicture(RS.getString("picture"));
				
				
				//creating list
				list.add(person);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return list;

	}

	@POST
	@Path("/addperson")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Person> addPerson(Person person) {

		Connection conn = Connections.getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement(
					"insert into person(firstname, lastname, location, height, slogan, picture) values(?, ?, ?, ?, ?, ?)");
			pstmt.setString(1, person.getFirstname());
			pstmt.setString(2, person.getLastname());
			pstmt.setString(3, person.getLocation());
			pstmt.setInt(4, person.getHeight());
			pstmt.setString(5, person.getSlogan());
			pstmt.setString(6, person.getPicture());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		ArrayList<Person> list = readPerson();
		return list;

	}

	@PUT
	@Path("/updateperson")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ArrayList<Person> updatePerson(Person person) {
		Connection conn = Connections.getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement(
					"update person set firstname=?, lastname=?, location=?, height=?, slogan=?, picture=? where id=?");
			pstmt.setString(1, person.getFirstname());
			pstmt.setString(2, person.getLastname());
			pstmt.setString(3, person.getLocation());
			pstmt.setInt(4, person.getHeight());
			pstmt.setString(5, person.getSlogan());
			pstmt.setString(6, person.getPicture());
			pstmt.setInt(7, person.getPersonid());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		ArrayList<Person> list = readPerson();
		return list;
	}

	// MEASUREMENTS SERVICES
	@GET
	@Path("/readmeas/{personid}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Measurements> readMeas(@PathParam("personid") int personid) {

		ArrayList<Measurements> list = new ArrayList<>();
		Connection conn = Connections.getConnection();
		try {
			String sql = "select * from person inner join measurements on person.personid=measurements.personid where person.personid=?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, personid);
			ResultSet RS = stmt.executeQuery();

		
			while (RS.next()) {
				Measurements meas = new Measurements();
				meas.setPersonid(RS.getInt("personid"));
				meas.setMeasid(RS.getInt("measid"));
				meas.setWeight(RS.getInt("weight"));
				meas.setChest(RS.getInt("chest"));
				meas.setWaist(RS.getInt("waist"));
				meas.setHip(RS.getInt("hip"));
				meas.setBicep(RS.getInt("bicep"));
				meas.setThigh(RS.getInt("thigh"));
				meas.setDate(RS.getString("date"));
				list.add(meas);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return list;

	}
	
	
	@POST
	@Path("/addmeas")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Measurements> addMeas(Measurements meas) {

		Connection conn = Connections.getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement(
					"insert into measurements(weight, chest, waist, hip, bicep, thigh, date, personid) values(?, ?, ?, ?, ?, ?, ?, ?)");
			pstmt.setInt(1, meas.getWeight());
			pstmt.setInt(2, meas.getChest());
			pstmt.setInt(3, meas.getWaist());
			pstmt.setInt(4, meas.getHip());
			pstmt.setInt(5, meas.getBicep());
			pstmt.setInt(6, meas.getThigh());
			pstmt.setString(7, meas.getDate());
			pstmt.setInt(8, meas.getWeight());
			pstmt.setInt(9, meas.getPersonid());
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		ArrayList<Measurements> list = readMeas(meas.getPersonid());
		return list;

	}
	
	// EXERCISE SERVICES
	@GET
	@Path("/readexercises")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Exercise> readExercises() {

		ArrayList<Exercise> list = new ArrayList<>();
		Connection conn = Connections.getConnection();
		try {
			String sql = "select * from exercise";
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet RS = stmt.executeQuery();

		
			while (RS.next()) {
				Exercise exercise = new Exercise();
				exercise.setExerciseid(RS.getInt("exerciseid"));
				exercise.setMovename(RS.getString("movename"));
				exercise.setMovepic(RS.getString("picture"));
				exercise.setChecked(RS.getInt("checked"));
				list.add(exercise);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return list;

	}
	
	// WORKOUT SERVICES
	
	@GET
	@Path("/readworkouts/{personid}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Workout> readWorkouts(@PathParam("personid") int personid) {

		ArrayList<Workout> list = new ArrayList<>();
		Connection conn = Connections.getConnection();
		try {
			String sql = "select * from workout";
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet RS = stmt.executeQuery();

		
			while (RS.next()) {
				Workout workout = new Workout();
				workout.setWorkoutid(RS.getInt("workoutid"));
				workout.setDate(RS.getString("date"));
				workout.setPersonid(RS.getInt("personid"));
				
				list.add(workout);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return list;

	}
	// Read workouts by workoutid
	@GET
	@Path("/readworkoutsbyid/{workoutid}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Workout> readWorkoutsByWorkoutid(@PathParam("workoutid") int workoutid) {

		ArrayList<Workout> list = new ArrayList<>();
		Connection conn = Connections.getConnection();
		try {
			String sql = "select * from workout where workoutid=?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, workoutid);
			ResultSet RS = stmt.executeQuery();

		
			while (RS.next()) {
				Workout workout = new Workout();
				workout.setWorkoutid(RS.getInt("workoutid"));
				workout.setDate(RS.getString("date"));
				workout.setPersonid(RS.getInt("personid"));
				
				list.add(workout);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return list;

	}
	
	// WORKOUTEXERCISE SERVICES
	
		@GET
		@Path("/readworkoutexercise/{workoutid}")
		@Produces(MediaType.APPLICATION_JSON)
		public ArrayList<WorkoutExercise> readWorkoutExercises(@PathParam("workoutid") int workoutid) {

			ArrayList<WorkoutExercise> list = new ArrayList<>();
			Connection conn = Connections.getConnection();
			try {
				String sql = "select * from workoutexercise where workoutid=?";
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setInt(1, workoutid);
				ResultSet RS = stmt.executeQuery();

			
				while (RS.next()) {
					WorkoutExercise workoutexercise = new WorkoutExercise();
					workoutexercise.setWorkoutexerciseid(RS.getInt("workoutexerciseid"));
					workoutexercise.setWorkoutid(RS.getInt("workoutid"));
					workoutexercise.setExerciseid(RS.getInt("exerciseid"));
					workoutexercise.setReps(RS.getInt("reps"));
					workoutexercise.setWeights(RS.getInt("weights"));
					workoutexercise.setDuration(RS.getInt("duration"));
					
					list.add(workoutexercise);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			return list;

		}

}
