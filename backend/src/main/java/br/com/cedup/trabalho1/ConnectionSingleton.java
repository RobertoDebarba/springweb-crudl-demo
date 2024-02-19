package br.com.cedup.trabalho1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class ConnectionSingleton {

    private Connection connection;

    /**
     * Obtém a conexão ativa com o banco.
     * Caso não exista nenhuma conexão ativa ainda, cria uma nova.
     */
    public Connection getConnection() throws SQLException {

        if (connection == null) {
            connection = DriverManager.getConnection( //
                                                      "jdbc:mysql://localhost:3306/trabalho1spring", //
                                                      "root", //
                                                      "root");
        }

        return connection;
    }

}
