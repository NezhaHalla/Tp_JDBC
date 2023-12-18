package com.test.tp10;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class main {
	
    private static final String URL = "jdbc:mysql://localhost:3306/tp_jdbc";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static void main(String[] args) {
    	
        Connection connexion = null;
        Statement statement = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connexion = DriverManager.getConnection(URL, USER, PASSWORD);
            statement = connexion.createStatement();

            //afficherEtudiants(statement);

            //insererEtudiant(connexion);

           //mettreAJourEtudiant(connexion);

           supprimerEtudiant(connexion);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            // Fermeture des ressources
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connexion != null) {
                    connexion.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println("Done !");
        }
    }
/* throws SQLException is used in a method declaration to indicate 
 * that the method may throw an exception of type SQLException*/
    
    private static void afficherEtudiants(Statement statement) throws SQLException {
        String sql = "SELECT id, nom, prenom, age FROM etudiants";
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String nom = resultSet.getString("nom");
            String prenom = resultSet.getString("prenom");
            int age = resultSet.getInt("age");

            System.out.println("ID : " + id);
            System.out.println("Nom : " + nom);
            System.out.println("Prenom : " + prenom);
            System.out.println("Age : " + age);
        }
        resultSet.close();
    }

    private static void insererEtudiant(Connection connexion) throws SQLException {
        String sql = "INSERT INTO etudiants (nom, prenom, age) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = connexion.prepareStatement(sql);

        preparedStatement.setString(1, "Nezha");
        preparedStatement.setString(2, "Halla");
        preparedStatement.setInt(3, 20);

        int lignesModifiees = preparedStatement.executeUpdate();
        if (lignesModifiees > 0) {
            System.out.println("L'étudiant a été inséré avec succès !");
        } else {
            System.out.println("Échec de l'insertion de l'étudiant.");
        }
        preparedStatement.close();
    }

    private static void mettreAJourEtudiant(Connection connexion) throws SQLException {
        String sql = "UPDATE etudiants SET age = ? WHERE nom = ?";
        PreparedStatement preparedStatement = connexion.prepareStatement(sql);

        preparedStatement.setInt(1, 25); // Mettre à jour l'âge de l'étudiant
        preparedStatement.setString(2, "Ouba"); // Spécifier le nom de l'étudiant à mettre à jour

        int lignesModifiees = preparedStatement.executeUpdate();
        if (lignesModifiees > 0) {
            System.out.println("L'étudiant a été mis à jour avec succès !");
        } else {
            System.out.println("Échec de la mise à jour de l'étudiant.");
        }
        preparedStatement.close();
    }

    private static void supprimerEtudiant(Connection connexion) throws SQLException {
        String sql = "DELETE FROM etudiants WHERE nom = ?";
        PreparedStatement preparedStatement = connexion.prepareStatement(sql);

        preparedStatement.setString(1, "Roe"); // Spécifier le nom de l'étudiant à supprimer

        int lignesModifiees = preparedStatement.executeUpdate();
        if (lignesModifiees > 0) {
            System.out.println("L'étudiant a été supprimé avec succès !");
        } else {
            System.out.println("Échec de la suppression de l'étudiant.");
        }
        preparedStatement.close();
    }
}
