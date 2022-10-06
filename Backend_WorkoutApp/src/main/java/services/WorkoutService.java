package services;

import java.awt.List;
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

				// creating list
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
					"update person set firstname=?, lastname=?, location=?, height=?, slogan=?, picture=? where personid=?");
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

	// testing testing
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

	
	@GET
	@Path("/readlastthree")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Measurements> readLastThree() {

		ArrayList<Measurements> list = new ArrayList<>();
		Connection conn = Connections.getConnection();
		try {
			String sql = "select * from measurements order by measid desc limit 3;";
			PreparedStatement stmt = conn.prepareStatement(sql);
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
			pstmt.setInt(8, meas.getPersonid());

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
				exercise.setChecked(RS.getBoolean("checked"));
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

	@PUT
	@Path("/updateexercises")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ArrayList<Exercise> updateExercises(ArrayList<Exercise> list) {
		Connection conn = Connections.getConnection();
		// System.out.println(list);
		int i = 0;
		int id = 1;

		try {

			while (i < list.size()) {

				Exercise exercise = list.get(i);
				PreparedStatement pstmt = conn
						.prepareStatement("update exercise set movename=?, picture=?, checked=? where exerciseid=?");
				pstmt.setString(1, exercise.getMovename());
				pstmt.setString(2, exercise.getMovepic());
				pstmt.setBoolean(3, exercise.isChecked());
				pstmt.setInt(4, id);
				pstmt.executeUpdate();
				i++;
				id++;

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
		ArrayList<Exercise> exerciselist = readExercises();
		// System.out.println(readExercises());
		return exerciselist;
	}

	@GET
	@Path("/readcheckedexercises")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Exercise> readCheckedExercises() {

		ArrayList<Exercise> list = new ArrayList<>();
		Connection conn = Connections.getConnection();
		try {
			String sql = "select * from exercise where checked = 1";
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet RS = stmt.executeQuery();

			while (RS.next()) {
				Exercise exercise = new Exercise();
				exercise.setExerciseid(RS.getInt("exerciseid"));
				exercise.setMovename(RS.getString("movename"));
				exercise.setMovepic(RS.getString("picture"));
				exercise.setChecked(RS.getBoolean("checked"));
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
				System.out.println(list.toString());
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

	@GET
	@Path("/readlastworkout")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Workout> readLastWorkout() {

		ArrayList<Workout> list = new ArrayList<>();
		Connection conn = Connections.getConnection();
		try {
			String sql = "select * from workout order by workoutid desc limit 1";
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet RS = stmt.executeQuery();

			while (RS.next()) {
				Workout workout = new Workout();
				workout.setWorkoutid(RS.getInt("workoutid"));
				workout.setDate(RS.getString("date"));
				workout.setPersonid(RS.getInt("personid"));

				list.add(workout);
				System.out.println(list.toString());
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

	@POST
	@Path("/addworkout")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Workout> addWorkout(Workout workout) {
		System.out.println("toimiiko");
		ArrayList<Workout> list = new ArrayList<>();
		Connection conn = Connections.getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement("insert into workout(date, personid) values(?, ?)");
			pstmt.setString(1, workout.getDate());
			pstmt.setInt(2, workout.getPersonid());

			list.add(workout);
			System.out.println(list);

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

		ArrayList<Workout> list1 = readLastWorkout();
		return list1;

	}

	// WORKOUTEXERCISE SERVICES

	@GET
	@Path("/readworkoutexercise/{workoutid}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<WorkoutExercise> readWorkoutExercises(@PathParam("workoutid") int workoutid) {

		ArrayList<WorkoutExercise> list = new ArrayList<>();
		Connection conn = Connections.getConnection();
		try {
			String sql = "select * from workout inner join workoutexercise on workout.workoutid = workoutexercise.workoutid "
					+ "inner join exercise on workoutexercise.exerciseid = exercise.exerciseid where workoutexercise.workoutid=?";
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
				workoutexercise.setDate(RS.getString("date"));
				workoutexercise.setPersonid(RS.getInt("personid"));
				workoutexercise.setMovename(RS.getString("movename"));
				workoutexercise.setPicture(RS.getString("picture"));

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
	
	@GET
	@Path("/readworkoutexercisesbyid/{workoutid}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<WorkoutExercise> readWorkoutExercisesById(@PathParam("workoutid") int workoutid) {

		ArrayList<WorkoutExercise> list = new ArrayList<>();
		Connection conn = Connections.getConnection();
		try {
			String sql = "select * from workout inner join workoutexercise on workout.workoutid = workoutexercise.workoutid "
					+ "inner join exercise on workoutexercise.exerciseid = exercise.exerciseid where workoutexercise.workoutid=?";
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
				workoutexercise.setDate(RS.getString("date"));
				workoutexercise.setPersonid(RS.getInt("personid"));
				workoutexercise.setMovename(RS.getString("movename"));
				workoutexercise.setPicture(RS.getString("picture"));

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
		splitList(list, 3);
		System.out.println(splitList(list, 3).toString());
		System.out.println(splitList(list, 3).stream().toString());
		return list;

	}
	
	public static <T> ArrayList<T> splitList(ArrayList<WorkoutExercise> list, final int size) {
		
		//Palautetaan ArrayList
		ArrayList<T> separatedList = new ArrayList<>(); 
		
		//Väliaikainen sublista List
		java.util.List<WorkoutExercise> temp;
		
		for (int i = 0; i < list.size(); i++) {
			
			temp = list.subList(i, Math.min(list.size(), i+size));
			if (temp.size() != 1) {
				separatedList.add((T) (temp));
			}
		}
		
		System.out.println(separatedList);
		return separatedList;
		
	}


	/**
	 * @param list
	 * @return
	 */
	@POST
	@Path("/addworkoutexercise")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<WorkoutExercise> addWorkout(ArrayList<WorkoutExercise> list) {
		System.out.println("toimiiko");
		System.out.println("listan koko" + list.size());
		int koko = list.size();
		// ArrayList<WorkoutExercise> list = new ArrayList<>();
		Connection conn = Connections.getConnection();
		try {
			for (int i = 0; i < koko; i++) {
			PreparedStatement pstmt = conn.prepareStatement(
					"insert into workoutexercise(reps,weights,duration,workoutid,exerciseid) values(?, ?, ?, ?, ?)");
			
				WorkoutExercise workoutexercise = list.get(i);
				
				pstmt.setInt(1, workoutexercise.getReps());
				pstmt.setInt(2, workoutexercise.getWeights());
				pstmt.setInt(3, workoutexercise.getDuration());
				pstmt.setInt(4, workoutexercise.getWorkoutid());
				pstmt.setInt(5, workoutexercise.getExerciseid());

				list.add(workoutexercise);
				System.out.println("testing");

				pstmt.executeUpdate();
				pstmt.close();
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
