import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class tlakaplikacija {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/blood_pressure_app";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "pass123";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Upiši ime hrane: ");
        String userInput = scanner.nextLine();

        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String sql = "SELECT * FROM foods WHERE food_name = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, userInput);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                
                        String foodName = resultSet.getString("food_name");
                        boolean isGoodForBP = resultSet.getBoolean("is_good_for_bp");

                        System.out.println("Ime hrane: " + foodName);
                        System.out.println("Dobro za ukoliko imate visok tlak: " + convertToIstina(isGoodForBP));
                    } else {
                        System.out.println("Nije pronađeno u databazi.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    private static String convertToIstina(boolean value) {
        return value ? "istina" : "nije istina";
    }
}
