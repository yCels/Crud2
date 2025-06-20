package dao;

import modelo.Curso;
import factory.ConnectionFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CursoDAO {
    private Connection conn;

    public CursoDAO() {
        conn = new ConnectionFactory().getConnection();
    }

    // Adiciona novo curso
    public void adiciona(Curso curso) {
        String sql = "INSERT INTO curso (nome, carga_horaria, limite_alunos, ativo) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, curso.getNome());
            stmt.setInt(2, curso.getCargaHoraria());
            stmt.setInt(3, curso.getLimiteAlunos());
            stmt.setBoolean(4, curso.isAtivo());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao adicionar curso", ex);
        }
    }

    // Altera um curso existente
    public void altera(Curso curso) {
        String sql = "UPDATE curso SET nome=?, carga_horaria=?, limite_alunos=?, ativo=? WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, curso.getNome());
            stmt.setInt(2, curso.getCargaHoraria());
            stmt.setInt(3, curso.getLimiteAlunos());
            stmt.setBoolean(4, curso.isAtivo());
            stmt.setInt(5, curso.getId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao alterar curso", ex);
        }
    }

    // Remove um curso
    public void remove(Curso curso) {
        String sql = "DELETE FROM curso WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, curso.getId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao remover curso", ex);
        }
    }

    // Lista todos os cursos
    public List<Curso> getLista() {
        List<Curso> lista = new ArrayList<>();
        String sql = "SELECT * FROM curso ORDER BY nome";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Curso c = new Curso();
                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                c.setCargaHoraria(rs.getInt("carga_horaria"));
                c.setLimiteAlunos(rs.getInt("limite_alunos"));
                c.setAtivo(rs.getBoolean("ativo"));
                lista.add(c);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao buscar lista de cursos", ex);
        }
        return lista;
    }

    // Busca curso por ID
    public Curso buscaPorId(int id) {
        String sql = "SELECT * FROM curso WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Curso c = new Curso();
                    c.setId(rs.getInt("id"));
                    c.setNome(rs.getString("nome"));
                    c.setCargaHoraria(rs.getInt("carga_horaria"));
                    c.setLimiteAlunos(rs.getInt("limite_alunos"));
                    c.setAtivo(rs.getBoolean("ativo"));
                    return c;
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao buscar curso por ID", ex);
        }
        return null;
    }

    // Verifica se já existe curso com o mesmo nome
    public boolean existeNome(String nome) {
        String sql = "SELECT COUNT(*) FROM curso WHERE nome=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao verificar nome do curso", ex);
        }
        return false;
    }

    // Desativa o curso (ativo = false)
    public void desativar(int id) {
        String sql = "UPDATE curso SET ativo=false WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao desativar curso", ex);
        }
    }

    // Reativa o curso (ativo = true)
    public void reativar(int id) {
        String sql = "UPDATE curso SET ativo=true WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao reativar curso", ex);
        }
    }

    // Conta alunos ativos vinculados ao curso
    public int contarAlunosAtivos(int idCurso) {
        String sql = "SELECT COUNT(*) FROM aluno WHERE id_curso=? AND ativo=true";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCurso);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao contar alunos ativos do curso", ex);
        }
        return 0;
    }
}