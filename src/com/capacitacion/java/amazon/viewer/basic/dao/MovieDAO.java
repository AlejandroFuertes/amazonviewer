package com.capacitacion.java.amazon.viewer.basic.dao;

import static com.capacitacion.java.amazon.viewer.basic.db.DataBase.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.capacitacion.java.amazon.viewer.basic.db.IDBConnection;
import com.capacitacion.java.amazon.viewer.basic.model.Movie;

/**
 * <h1>MovieDAO</h1>
 * 
 * Implementa metodos CRUD
 * <p>
 * 
 * @author Fuertes Alejandro
 * @version 1.1
 * @since 2017
 */

public interface MovieDAO extends IDBConnection {

	default ArrayList<Movie> read() {

		ArrayList<Movie> movies = new ArrayList<Movie>();

		try (Connection connection = connectToDB()) {

			String query = "SELECT * FROM " + TMOVIE;
			PreparedStatement preparedStatement = connection.prepareStatement(query);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Movie movie = new Movie(rs.getString(TMOVIE_TITLE), rs.getString(TMOVIE_GENRE),
						rs.getString(TMOVIE_CREATOR), Integer.valueOf(rs.getString(TMOVIE_DURATION)),
						Short.valueOf(rs.getString(TMOVIE_YEAR)));

				movie.setId(Integer.valueOf(rs.getString(TMOVIE_ID)));
				movie.setViewed(
						getMovieViewed(preparedStatement, connection, Integer.valueOf(rs.getString(TMOVIE_ID))));
				movies.add(movie);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return movies;
	}

	/* APARTIR DE JAVA 9 SE PUEDE ESTABLECER METODOS PRIVATE EN LAS INTERFACES */
	default boolean getMovieViewed(PreparedStatement preparedStatement, Connection connection, int id_movie) {

		boolean viewed = false;
		String query = "SELECT * FROM " + TVIEWED + " WHERE " + TVIEWED_ID_MATERIAL + " = ?" + " AND "
				+ TVIEWED_ID_ELEMENT + "= ?" + " AND " + TVIEWED_ID_USER + "= ?";

		ResultSet rs = null;

		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, TMATERIAL_ID[0]);
			preparedStatement.setInt(2, id_movie);
			preparedStatement.setInt(3, TUSER_ID);

			rs = preparedStatement.executeQuery();

			viewed = rs.next();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return viewed;
	}

	default Movie setMovieViewed(Movie movie) {

		Date date = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String currentTime = sf.format(date);

		try (Connection connection = connectToDB()) {
			Statement statement = connection.createStatement();
			String query = "INSERT INTO " + TVIEWED + "(" + TVIEWED_ID_MATERIAL + ", " + TVIEWED_ID_ELEMENT + ", "
					+ TVIEWED_ID_USER + ", " + TVIEWED_DATEVIEWED + ")" + " VALUE(" + TMATERIAL_ID[0] + ", "
					+ movie.getId() + ", " + TUSER_ID + ", '" + currentTime + "')";

			// devuelve la cantidad de rows afectadas
			if (statement.executeUpdate(query) > 0) {
				System.out.println("Se marco en visto");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return movie;
	}

//	default ArrayList<Movie> readByDate(Date date) {
//
//		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
//		String dateString = sf.format(date);
//		
//		ArrayList<Movie> movies = new ArrayList<Movie>();
//
//		try (Connection connection = connectToDB()) {
//
//			String query = "SELECT * FROM " + TVIEWED + " WHERE " + TVIEWED_DATEVIEWED + " =? ";
//
//			PreparedStatement ps = connection.prepareStatement(query);
//			ps.setDate(1, (java.sql.Date) date);
//
//			ResultSet rs = ps.executeQuery();
//
//			while (rs.next()) {
//
//				Movie movie = new Movie(rs.getString(TMOVIE_TITLE), rs.getString(TMOVIE_GENRE),
//						rs.getString(TMOVIE_CREATOR), Integer.valueOf(rs.getString(TMOVIE_DURATION)),
//						Short.valueOf(rs.getString(TMOVIE_YEAR)));
//
//				movie.setId(Integer.valueOf(rs.getString(TVIEWED_ID)));
//				
//				movies.add(movie);
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return movies;
//	}

}
