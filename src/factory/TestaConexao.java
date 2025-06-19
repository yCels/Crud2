
package factory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class TestaConexao {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        Connection connection = new  ConnectionFactory().getConnection();
        System.out.println("Conexao aberta!");
        connection.close();
    }
    
}
