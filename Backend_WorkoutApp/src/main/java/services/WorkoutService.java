package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
	// For testing purposes only, so rest call works
	// Not in use
	// @Path("/hello")
	// @GET
	// @Produces(MediaType.TEXT_PLAIN)
	// public String sayHello() {
	// return "Hello fellow!";
	// }

	// PERSON SERVICES

	// Reading person information from mysql database, getting person information to
	// an arraylist and return the list.
	// ViewStart, ViewPerson
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

	// Method for adding a new person to a database. This method is not used right
	// now, maybe later on.
	// Method gets the Person object from frontend and save its values to database.
	// @POST
	// @Path("/addperson")
	// @Consumes(MediaType.APPLICATION_JSON)
	// @Produces(MediaType.APPLICATION_JSON)
	// public ArrayList<Person> addPerson(Person person) {
	//
	// Connection conn = Connections.getConnection();
	// try {
	// PreparedStatement pstmt = conn.prepareStatement(
	// "insert into person(firstname, lastname, location, height, slogan, picture)
	// values(?, ?, ?, ?, ?, ?)");
	// pstmt.setString(1, person.getFirstname());
	// pstmt.setString(2, person.getLastname());
	// pstmt.setString(3, person.getLocation());
	// pstmt.setInt(4, person.getHeight());
	// pstmt.setString(5, person.getSlogan());
	// pstmt.setString(6, person.getPicture());
	// pstmt.executeUpdate();
	// } catch (SQLException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } finally {
	// try {
	// conn.close();
	// } catch (SQLException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	// ArrayList<Person> list = readPerson();
	// return list;
	//
	// }

	// Method for updating the existing person object. Method gets the changed
	// values to a person and adds them to a database based on persons personid.
	// Finally method reads all person information from a database and returns list
	// from these information to a frontend.
	// ViewEditProfile
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

	// MEASUREMENTS SERVICES
	// Method for reading persons measurements from a database. Gets personid as a
	// path parameter and reads measurement data based on this id.
	// Sgl-sentence limits the data so that we only get one value out, the latest
	// one. After reading measurements, they're added to a list and list
	// is sent to frontend.
	// Is called in method addMeas
	@GET
	@Path("/readmeas/{personid}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Measurements> readMeas(@PathParam("personid") int personid) {

		ArrayList<Measurements> list = new ArrayList<>();
		Connection conn = Connections.getConnection();
		try {
			String sql = "select * from person inner join measurements on person.personid=measurements.personid where person.personid=? order by measid desc limit 1";
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

	// Method for reading three latest measurements records from a database, working
	// quite same way as previous method.
	// ViewMeasHistory
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

	// Method for adding new measurements data to a database. Getting new
	// measurement values as an object and adding this objects values to a database.
	// Finally read all measurements from database and add these to a list and
	// finally send it to back.
	// ViewMeasAdd
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
	// Method reads all the exercises from a database and returns them as a list
	// ViewExercises
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

	// Method for updating values to an exercise-table in database gets updated
	// values as an list and by looping through the list adds
	// the changed values to right spots in the table. Returns exerciselist.
	// ViewExercises
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

	// method for re-sending checked exercises to next view in the app. Method gets
	// all the values from a table where checked is true (1) and returns those as a
	// list.
	// ViewDuringWorkout
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

	// Method for updating checked values back to a false. Gets checked values as an
	// list and updates them to false (0). Returns exercises as a list. Is used in
	// viewduringworkout.
	// ViewDuringWorkout
	@PUT
	@Path("/updatecheckedstofalse")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ArrayList<Exercise> setCheckedToFalse(ArrayList<Exercise> list) {

		Connection conn = Connections.getConnection();
		// System.out.println(list);
		int i = 0;

		try {

			while (i < list.size()) {

				Exercise exercise = list.get(i);
				PreparedStatement pstmt = conn.prepareStatement("update exercise set checked=? where exerciseid=?");

				pstmt.setBoolean(1, false);
				pstmt.setInt(2, exercise.getExerciseid());
				pstmt.executeUpdate();
				// System.out.println(exercise.getChecked());
				i++;

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

	// WORKOUT SERVICES
	// Method for reading workouts by personid. Gets personid as a path parameter
	// and based on that reads all workouts from a database and returns them as a
	// list.
	// ViewWorkoutHistory
	@GET
	@Path("/readworkouts/{personid}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Workout> readWorkouts(@PathParam("personid") int personid) {

		ArrayList<Workout> list = new ArrayList<>();
		Connection conn = Connections.getConnection();
		try {
			String sql = "select * from workout order by workoutid desc";
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet RS = stmt.executeQuery();

			while (RS.next()) {
				Workout workout = new Workout();
				workout.setWorkoutid(RS.getInt("workoutid"));
				workout.setDate(RS.getString("date"));
				workout.setPersonid(RS.getInt("personid"));

				list.add(workout);
				// System.out.println("onko se tämä " + list.toString());
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

	// Method for reading all exercises ordered so the first one is the latest and
	// passes these in list in return statement.
	// Is called in method deleteWorkout
	@GET
	@Path("/readallworkouts")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Workout> readAllWorkouts() {

		ArrayList<Workout> list = new ArrayList<>();
		Connection conn = Connections.getConnection();
		try {
			String sql = "select * from workout order by workoutid desc";
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet RS = stmt.executeQuery();

			while (RS.next()) {
				Workout workout = new Workout();
				workout.setWorkoutid(RS.getInt("workoutid"));
				workout.setDate(RS.getString("date"));
				workout.setPersonid(RS.getInt("personid"));

				list.add(workout);
				// System.out.println("onko se tämä " + list.toString());
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

	// Method reads only the latest exercise from a database and returns that as a
	// list
	// Is called in method addWorkout
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
				// System.out.println(list.toString());
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

	// Read workouts by workoutid, getting id as a path parameter and adds values to
	// a list for return.
	// Not in use
	// @GET
	// @Path("/readworkoutsbyid/{workoutid}")
	// @Produces(MediaType.APPLICATION_JSON)
	// public ArrayList<Workout> readWorkoutsByWorkoutid(@PathParam("workoutid") int
	// workoutid) {
	//
	// ArrayList<Workout> list = new ArrayList<>();
	// Connection conn = Connections.getConnection();
	// try {
	// String sql = "select * from workout where workoutid=?";
	// PreparedStatement stmt = conn.prepareStatement(sql);
	// stmt.setInt(1, workoutid);
	// ResultSet RS = stmt.executeQuery();
	//
	// while (RS.next()) {
	// Workout workout = new Workout();
	// workout.setWorkoutid(RS.getInt("workoutid"));
	// workout.setDate(RS.getString("date"));
	// workout.setPersonid(RS.getInt("personid"));
	// list.add(workout);
	//
	// }
	// } catch (SQLException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } finally {
	// try {
	// conn.close();
	// } catch (SQLException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	//
	// return list;
	// }

	// Method for adding new workout, gets workout as an object and read values from
	// it and finally saves them to a database. Finally
	// calls method for reading the latest workout and returns it in a list. Is used
	// in viewduringworkout.
	// ViewDuringWorkout
	@POST
	@Path("/addworkout")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Workout> addWorkout(Workout workout) {
		// System.out.println("toimiiko");
		ArrayList<Workout> list = new ArrayList<>();
		Connection conn = Connections.getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement("insert into workout(date, personid) values(?, ?)");
			pstmt.setString(1, workout.getDate());
			pstmt.setInt(2, workout.getPersonid());

			list.add(workout);
			// System.out.println(list);

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

	// Method for deleting workout from a database. Gets the deleting lines id as a
	// path parameter and then deletes it from a database, returns list from all
	// workouts
	// ViewWorkoutHistory
	@DELETE
	@Path("/deleteworkoutbyid/{workoutid}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Workout> deleteWorkout(@PathParam("workoutid") int workoutid) {
		System.out.println("pääseekö deleteen?" + workoutid);
		Connection conn = Connections.getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement("delete from workout where workoutid=?");
			pstmt.setInt(1, workoutid);
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
		ArrayList<Workout> list = readAllWorkouts();
		return list;
	}

	// WORKOUTEXERCISE SERVICES
	// Method for reading workoutexercises from a database by workoutid. When data
	// is got adds it to a list.
	// Then calls function splitList in return, finally returns list
	// Not in use
	// @GET
	// @Path("/readworkoutexercise/{workoutid}")
	// @Produces(MediaType.APPLICATION_JSON)
	// public ArrayList<WorkoutExercise>
	// readWorkoutExercises(@PathParam("workoutid") int workoutid) {
	//
	// ArrayList<WorkoutExercise> list = new ArrayList<>();
	// Connection conn = Connections.getConnection();
	// try {
	// String sql = "select * from workout inner join workoutexercise on
	// workout.workoutid = workoutexercise.workoutid "
	// + "inner join exercise on workoutexercise.exerciseid = exercise.exerciseid
	// where workoutexercise.workoutid=?";
	// PreparedStatement stmt = conn.prepareStatement(sql);
	// stmt.setInt(1, workoutid);
	// ResultSet RS = stmt.executeQuery();
	//
	// while (RS.next()) {
	// WorkoutExercise workoutexercise = new WorkoutExercise();
	// workoutexercise.setWorkoutexerciseid(RS.getInt("workoutexerciseid"));
	// workoutexercise.setWorkoutid(RS.getInt("workoutid"));
	// workoutexercise.setExerciseid(RS.getInt("exerciseid"));
	// workoutexercise.setReps(RS.getInt("reps"));
	// workoutexercise.setWeights(RS.getInt("weights"));
	// workoutexercise.setDuration(RS.getInt("duration"));
	// workoutexercise.setDate(RS.getString("date"));
	// workoutexercise.setPersonid(RS.getInt("personid"));
	// workoutexercise.setMovename(RS.getString("movename"));
	// workoutexercise.setPicture(RS.getString("picture"));
	//
	// list.add(workoutexercise);
	// }
	// } catch (SQLException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } finally {
	// try {
	// conn.close();
	// } catch (SQLException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	//
	// return splitList(list, 3);
	//
	// }

	// @GET
	// @Path("/readworkoutexercisespersonid/{personid}")
	// @Produces(MediaType.APPLICATION_JSON)
	// public ArrayList<WorkoutExercise>
	// readWorkoutExercisesByPersonId(@PathParam("personid") int personid) {
	//
	// ArrayList<WorkoutExercise> list = new ArrayList<>();
	// Connection conn = Connections.getConnection();
	// try {
	// String sql = "select * from workout inner join workoutexercise on
	// workout.workoutid = workoutexercise.workoutid "
	// + "inner join exercise on workoutexercise.exerciseid = exercise.exerciseid
	// where workout.personid=?";
	// PreparedStatement stmt = conn.prepareStatement(sql);
	// stmt.setInt(1, personid);
	// ResultSet RS = stmt.executeQuery();
	//
	// while (RS.next()) {
	// WorkoutExercise workoutexercise = new WorkoutExercise();
	// workoutexercise.setWorkoutexerciseid(RS.getInt("workoutexerciseid"));
	// workoutexercise.setWorkoutid(RS.getInt("workoutid"));
	// workoutexercise.setExerciseid(RS.getInt("exerciseid"));
	// workoutexercise.setReps(RS.getInt("reps"));
	// workoutexercise.setWeights(RS.getInt("weights"));
	// workoutexercise.setDuration(RS.getInt("duration"));
	// workoutexercise.setDate(RS.getString("date"));
	// workoutexercise.setPersonid(RS.getInt("personid"));
	// workoutexercise.setMovename(RS.getString("movename"));
	// workoutexercise.setPicture(RS.getString("picture"));
	//
	// list.add(workoutexercise);
	// }
	// } catch (SQLException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } finally {
	// try {
	// conn.close();
	// } catch (SQLException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	//
	// return splitList(list, 3);
	//
	// }

	// Method for reading workoutexercises from a database by workoutid. When data
	// is got adds it to a list.
	// Then calls function splitList in retunr finally returns list
	// ViewAfterWorkout, ViewWorkoutHistory
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

		return splitList(list, 3);

	}

	// Method for splitting workouts exercises into separate lists. Method takes
	// list and size as a parameter and then makes new arraylist for separated lines
	// Also temp list. In for loop read all data from list getted as a parameter and
	// then saves sublist values to a temp list. in if-clause adds temp-lists to a
	// separatedlist arraylist.
	// Finally returns separatedList.
	// is called in method readWorkoutExercisesById
	public static <T> ArrayList<T> splitList(ArrayList<WorkoutExercise> list, final int size) {

		// ArrayList for return
		ArrayList<T> separatedList = new ArrayList<>();

		// Temporary List for sublist
		java.util.List<WorkoutExercise> temp;

		for (int i = 0; i < list.size(); i += size) {

			temp = list.subList(i, Math.min(list.size(), i + size));
			if (temp.size() != 1) {
				separatedList.add((T) (temp));
			}
		}

		return separatedList;

	}

	/**
	 * @param list
	 * @return
	 */
	// Method for posting new workoutexercise to a database. Gets new values as a
	// list and inserts data then to a database in a for loop. So its goes through
	// the whole list.
	// Finally returns this list consist of new workoutexercises values. Is used in
	// viewduringworkout.
	// ViewDuringWorkout
	@POST
	@Path("/addworkoutexercise")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<WorkoutExercise> addWorkout(ArrayList<WorkoutExercise> list) {
//		System.out.println("toimiiko");
//		System.out.println("listan koko" + list.size());
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
				// System.out.println("testing");

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
