package dao;

import modelo.Aluno;
import modelo.Curso;
import factory.ConnectionFactory;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {
    private Connection conn;

    public AlunoDAO() {
        conn = new ConnectionFactory().getConnection();
    }

    // Adiciona novo aluno
    public void adiciona(Aluno aluno) {
        String sql = "INSERT INTO aluno (nome, cpf, email, data_nascimento, id_curso, ativo) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getCpf());
            stmt.setString(3, aluno.getEmail());
            stmt.setDate(4, Date.valueOf(aluno.getDataNascimento())); // Conversão de LocalDate para java.sql.Date
            stmt.setInt(5, aluno.getCurso().getId());
            stmt.setBoolean(6, aluno.isAtivo());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao adicionar aluno", ex);
        }
    }

    // Altera um aluno existente
    public void altera(Aluno aluno) {
        String sql = "UPDATE aluno SET nome=?, cpf=?, email=?, data_nascimento=?, id_curso=?, ativo=? WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getCpf());
            stmt.setString(3, aluno.getEmail());
            stmt.setDate(4, Date.valueOf(aluno.getDataNascimento()));
            stmt.setInt(5, aluno.getCurso().getId());
            stmt.setBoolean(6, aluno.isAtivo());
            stmt.setInt(7, aluno.getId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao alterar aluno", ex);
        }
    }

    // Remove um aluno
    public void remove(Aluno aluno) {
        String sql = "DELETE FROM aluno WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, aluno.getId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao remover aluno", ex);
        }
    }

    // Lista todos os alunos
    public List<Aluno> getLista() {
        List<Aluno> lista = new ArrayList<>();
        String sql = "SELECT * FROM aluno ORDER BY nome";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Aluno a = new Aluno();
                a.setId(rs.getInt("id"));
                a.setNome(rs.getString("nome"));
                a.setCpf(rs.getString("cpf"));
                a.setEmail(rs.getString("email"));
                a.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());
                a.setAtivo(rs.getBoolean("ativo"));
                
                // Carrega o curso associado (apenas ID)
                Curso c = new Curso();
                c.setId(rs.getInt("id_curso"));
                a.setCurso(c);
                
                lista.add(a);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao buscar lista de alunos", ex);
        }
        return lista;
    }

    // Busca aluno por ID
    public Aluno buscaPorId(int id) {
        String sql = "SELECT * FROM aluno WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Aluno a = new Aluno();
                    a.setId(rs.getInt("id"));
                    a.setNome(rs.getString("nome"));
                    a.setCpf(rs.getString("cpf"));
                    a.setEmail(rs.getString("email"));
                    a.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());
                    a.setAtivo(rs.getBoolean("ativo"));
                    
                    Curso c = new Curso();
                    c.setId(rs.getInt("id_curso"));
                    a.setCurso(c);
                    
                    return a;
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao buscar aluno por ID", ex);
        }
        return null;
    }

    // Lista alunos de um curso
    public List<Aluno> buscaPorCurso(int idCurso) {
        List<Aluno> lista = new ArrayList<>();
        String sql = "SELECT * FROM aluno WHERE id_curso=? ORDER BY nome";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCurso);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Aluno a = new Aluno();
                    a.setId(rs.getInt("id"));
                    a.setNome(rs.getString("nome"));
                    a.setCpf(rs.getString("cpf"));
                    a.setEmail(rs.getString("email"));
                    a.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());
                    a.setAtivo(rs.getBoolean("ativo"));
                    
                    Curso c = new Curso();
                    c.setId(rs.getInt("id_curso"));
                    a.setCurso(c);
                    
                    lista.add(a);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao buscar alunos por curso", ex);
        }
        return lista;
    }

    // Verifica se já existe CPF
    public boolean existeCpf(String cpf) {
        String sql = "SELECT COUNT(*) FROM aluno WHERE cpf=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao verificar CPF do aluno", ex);
        }
        return false;
    }

    // Desativa o aluno
    public void desativar(int id) {
        String sql = "UPDATE aluno SET ativo=false WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao desativar aluno", ex);
        }
    }

    // Reativa o aluno
    public void reativar(int id) {
        String sql = "UPDATE aluno SET ativo=true WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao reativar aluno", ex);
        }
    }
}