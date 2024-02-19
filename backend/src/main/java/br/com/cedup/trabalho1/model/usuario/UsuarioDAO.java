package br.com.cedup.trabalho1.model.usuario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.cedup.trabalho1.ConnectionSingleton;

@Component
public class UsuarioDAO {

    @Autowired
    public ConnectionSingleton connectionSingleton;

    /**
     * Verifica se um usuário existe com base no usuario e senha
     */
    public UsuarioEntity getByUsuario(String usuario, String senha) {
        final String sql = "SELECT * FROM usuario WHERE usuario = ? AND senha = ?";
        try (final PreparedStatement preparedStatement = connectionSingleton.getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, usuario);
            preparedStatement.setString(2, senha);
            try (final ResultSet result = preparedStatement.executeQuery()) {

                if (!result.next()) {
                    return null;
                }

                final UsuarioEntity entity = new UsuarioEntity();
                entity.id = result.getInt(1);
                entity.usuario = result.getString(2);
                entity.senha = result.getString(3);

                return entity;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
