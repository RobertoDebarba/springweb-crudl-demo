package br.com.cedup.trabalho1.model.login;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import br.com.cedup.trabalho1.ConnectionSingleton;

@Component
//@Service
public class LoginDAO {

    @Autowired
    public ConnectionSingleton connectionSingleton;

    public void add(LoginEntity loginEntity) {
        final String sql = "INSERT INTO login (token, usuario) values (?, ?)";
        try (final PreparedStatement preparedStatement = connectionSingleton.getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, loginEntity.token);
            preparedStatement.setInt(2, loginEntity.usuario.id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isTokenValido(String token) {
        final String sql = "SELECT COUNT(*) FROM login WHERE token = ?";
        try (final PreparedStatement preparedStatement = connectionSingleton.getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, token);
            try (final ResultSet result = preparedStatement.executeQuery()) {
                result.next();

                return result.getBoolean(1);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
