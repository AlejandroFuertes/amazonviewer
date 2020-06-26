package com.capacitacion.java.amazon.viewer.basic.db;

import java.sql.Connection;
import java.sql.DriverManager;
import static com.capacitacion.java.amazon.viewer.basic.db.DataBase.*;

public interface IDBConnection {

	default Connection connectToDB() {
		Connection connection = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(URL+DB+COMPLEMENTS, USER, PASSWORD);
			
			if(connection != null) {
				System.out.println("Se establecio la conexion");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return connection;
		}
	}
}
