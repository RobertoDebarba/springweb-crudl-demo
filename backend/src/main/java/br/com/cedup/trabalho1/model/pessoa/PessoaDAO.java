package br.com.cedup.trabalho1.model.pessoa;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.cedup.trabalho1.ConnectionSingleton;

@Component
public class PessoaDAO {

    @Autowired
    public ConnectionSingleton connectionSingleton;

    public List<PessoaEntity> getAll(String nomePesquisa, Integer idademin, Integer idademax) {
        String sql = "SELECT * FROM pessoa WHERE true ";
        if (nomePesquisa != null) {
            sql += " AND nome like ? ";
        }
        if (idademin != null) {
            sql += " AND idade >= ?";
        }
        if (idademax != null) {
            sql += " AND idade <= ?";
        }

        try (final PreparedStatement preparedStatement = connectionSingleton.getConnection().prepareStatement(sql)) {
            int index = 1;
            if (nomePesquisa != null) {
                preparedStatement.setString(index++, "%" + nomePesquisa + "%");
            }
            if (idademin != null) {
                preparedStatement.setInt(index++, idademin);
            }
            if (idademax != null) {
                preparedStatement.setInt(index, idademax);
            }

            try (final ResultSet resultadoProdutos = preparedStatement.executeQuery()) {

                List<PessoaEntity> resultadoComTodasAsPessoas = new ArrayList<>();

                while (resultadoProdutos.next()) {
                    int id = resultadoProdutos.getInt("id");
                    String nome = resultadoProdutos.getString("nome");
                    int idade = resultadoProdutos.getInt("idade");

                    PessoaEntity pessoaQueAcabeiDeObterDoBanco = new PessoaEntity(id, nome, idade);
                    resultadoComTodasAsPessoas.add(pessoaQueAcabeiDeObterDoBanco);
                }

                return resultadoComTodasAsPessoas;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public PessoaEntity getById(int idFilter) {
        final String sql = "SELECT * FROM pessoa WHERE id = ?";
        try (final PreparedStatement preparedStatement = connectionSingleton.getConnection().prepareStatement(sql)) {
            preparedStatement.setInt(1, idFilter);

            try (final ResultSet resultadoProdutos = preparedStatement.executeQuery()) {
                if (!resultadoProdutos.next()) {
                    return null;
                }

                int id = resultadoProdutos.getInt("id");
                String nome = resultadoProdutos.getString("nome");
                int idade = resultadoProdutos.getInt("idade");

                return new PessoaEntity(id, nome, idade);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public PessoaEntity add(PessoaEntity entity) {
        final String sql = "INSERT INTO pessoa (nome, idade) values (?, ?)";
        try (final PreparedStatement preparedStatement = connectionSingleton.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, entity.nome);
            preparedStatement.setInt(2, entity.idade);
            preparedStatement.executeUpdate();

            // Obtém o ID gerado (auto_increment) do produto e atualiza o objeto original
            try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                rs.next();
                entity.id = rs.getInt(1);
                return entity;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public PessoaEntity update(PessoaEntity entity, int id) {
        final String sql = "UPDATE pessoa SET nome = ?, idade = ? WHERE id = ?";
        try (final PreparedStatement preparedStatement = connectionSingleton.getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, entity.nome);
            preparedStatement.setInt(2, entity.idade);
            preparedStatement.setInt(3, id);
            int linhasAlteradas = preparedStatement.executeUpdate();

            if (linhasAlteradas == 0) {
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        entity.id = id;
        return entity;
    }

    public PessoaEntity delete(int id) {
        final PessoaEntity pessoaASerApagada = getById(id);

        final String sql = "DELETE FROM pessoa WHERE id = ?";
        try (final PreparedStatement preparedStatement = connectionSingleton.getConnection().prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            int linhasAlteradas = preparedStatement.executeUpdate();

            if (linhasAlteradas == 0) {
                return null;
            }

            return pessoaASerApagada;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
