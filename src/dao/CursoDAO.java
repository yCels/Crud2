package dao;

import factory.ConnectionFactory;
import modelo.Curso;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CursoDAO {
    private Connection connection;
    
    public CursoDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }
    
    public void adiciona(Curso curso) {
        String sql = "INSERT INTO curso (nome, carga_horaria, limite_alunos, ativo) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, curso.getNome());
            stmt.setInt(2, curso.getCargaHoraria());
            stmt.setInt(3, curso.getLimiteAlunos());
            stmt.setBoolean(4, curso.isAtivo());
            
            stmt.execute();
            
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                curso.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public List<Curso> getLista() {
        String sql = "SELECT * FROM curso";
        List<Curso> cursos = new ArrayList<>();
        
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Curso curso = new Curso();
                curso.setId(rs.getInt("id"));
                curso.setNome(rs.getString("nome"));
                curso.setCargaHoraria(rs.getInt("carga_horaria"));
                curso.setLimiteAlunos(rs.getInt("limite_alunos"));
                curso.setAtivo(rs.getBoolean("ativo"));
                
                cursos.add(curso);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        
        return cursos;
    }
    
    public List<Curso> getListaAtivos() {
        String sql = "SELECT * FROM curso WHERE ativo=true";
        List<Curso> cursos = new ArrayList<>();
        
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Curso curso = new Curso();
                curso.setId(rs.getInt("id"));
                curso.setNome(rs.getString("nome"));
                curso.setCargaHoraria(rs.getInt("carga_horaria"));
                curso.setLimiteAlunos(rs.getInt("limite_alunos"));
                curso.setAtivo(true);
                
                cursos.add(curso);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        
        return cursos;
    }
    
    public void altera(Curso curso) {
        String sql = "UPDATE curso SET nome=?, carga_horaria=?, limite_alunos=?, ativo=? WHERE id=?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, curso.getNome());
            stmt.setInt(2, curso.getCargaHoraria());
            stmt.setInt(3, curso.getLimiteAlunos());
            stmt.setBoolean(4, curso.isAtivo());
            stmt.setInt(5, curso.getId());
            
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public void remove(Curso curso) {
        String sql = "DELETE FROM curso WHERE id=?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, curso.getId());
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public Curso buscaPorId(int id) {
        String sql = "SELECT * FROM curso WHERE id=?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Curso curso = new Curso();
                curso.setId(rs.getInt("id"));
                curso.setNome(rs.getString("nome"));
                curso.setCargaHoraria(rs.getInt("carga_horaria"));
                curso.setLimiteAlunos(rs.getInt("limite_alunos"));
                curso.setAtivo(rs.getBoolean("ativo"));
                
                return curso;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        
        return null;
    }
    
    public void desativar(int id) {
        String sql = "UPDATE curso SET ativo=false WHERE id=?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public void reativar(int id) {
        String sql = "UPDATE curso SET ativo=true WHERE id=?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public int contarAlunosAtivos(int idCurso) {
        String sql = "SELECT COUNT(*) FROM aluno WHERE id_curso=? AND ativo=true";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idCurso);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        
        return 0;
    }
    
    public boolean existeNome(String nome) {
        String sql = "SELECT COUNT(*) FROM curso WHERE nome=?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        
        return false;
    }
}