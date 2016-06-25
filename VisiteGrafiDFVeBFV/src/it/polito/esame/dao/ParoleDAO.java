package it.polito.esame.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.esame.model.Parola;

/**
 * DAO class for interacting with the "parole" data-set
 * 
 * @author Fulvio
 *
 */
public class ParoleDAO {

	/**
	 * Query and return the whole list of words in the dictionary.
	 * 
	 * @return a list of {@link Parola} beans representing the whole dictionary
	 */
	public List<Parola> getAllParola() {

		/*
		 * RICORDA: all'esame, per vedere solo se funziona puoi anche mettere al 
		 * termine della query LIMIT 0 1000 per dire di raccogliere solo le prime 1000
		 */
		final String sql = "SELECT idParola, nome FROM parola ORDER BY nome";

		List<Parola> result = new ArrayList<Parola>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			ResultSet rs = st.executeQuery() ;
			
			while(rs.next()) {
				
				Parola p = new Parola( rs.getInt("idParola"),
						rs.getString("nome") ) ;
				result.add(p) ;
			}
			
			rs.close() ;
			st.close() ;
			conn.close() ;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}
	
	/**
	 * Query and return the list of words in the dictionary that have a specific length.
	 * 
	 * @param length the length of the words you are interested in
	 * @return a list of {@link Parola} beans representing all-and-only the words with the given length.
	 */
	public List<Parola> getAllParolaWithLength(int length) {

		/*
		 * RICORDA che per indicare la lunghezza di una parola basta usare CHAR_LENGTH()!!!
		 */
		final String sql = "SELECT idParola, nome FROM parola WHERE CHAR_LENGTH(nome)=? ORDER BY nome";

		List<Parola> result = new ArrayList<Parola>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, length) ;
			
			ResultSet rs = st.executeQuery() ;
			
			while(rs.next()) {
				
				Parola p = new Parola( rs.getInt("idParola"),
						rs.getString("nome") ) ;
				result.add(p) ;
			}
			
			rs.close() ;
			st.close() ;
			conn.close() ;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}


}
