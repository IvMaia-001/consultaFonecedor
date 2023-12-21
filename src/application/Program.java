package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import dbconection.DB;

public class Program {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
			
		try {

	        conn = DB.getconnection();
	        while (true) {
	            System.out.print("Código de Produto (ou 'exit' para sair): ");
	            String input = sc.nextLine();

	            if (input.equalsIgnoreCase("exit")) {
	                break; // Saída do loop se 'exit' for inserido 
	            }

	            Long codProduct = null;
	            try {
	                codProduct = Long.parseLong(input);
	            } catch (NumberFormatException e) {
	                System.out.println("Por favor, insira um número válido.");
	                continue; // Volta ao início do loop para novo input
	            }

	            st = conn.prepareStatement("SELECT DESC_PRODUTO, FABRICANTE FROM PRODUTOS WHERE PRODUTO = ?");
	            st.setLong(1, codProduct);

	            rs = st.executeQuery();

	            if (rs.next()) {
	                System.out.println(rs.getString("DESC_PRODUTO") + ", " + rs.getString("FABRICANTE"));
	            } else {
	                System.out.println("Nenhum produto encontrado com o código informado.");
	            }
	            System.out.println();
	        }
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		finally {
			DB.closeResultSet(rs);
			DB.closeStatament(st);
			DB.closeConnection();
		}

		sc.close();
	}	
}
