package dao;

import factory.ConnectionFactory;
import modelo.Aluno;
import modelo.Curso;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {
    private Connection connection;
    
    public AlunoDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }
    
    public void adiciona(Aluno aluno) {
        String sql = "INSERT INTO aluno (nome, cpf, email, data_nascimento, id_curso, ativo) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getCpf());
            stmt.setString(3, aluno.getEmail());
            stmt.setDate(4, Date.valueOf(aluno.getDataNascimento()));
            stmt.setInt(5, aluno.getCurso().getId());
            stmt.setBoolean(6, aluno.isAtivo());
            
            stmt.execute();
            
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                aluno.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public List<Aluno> getLista() {
        String sql = "SELECT a.*, c.nome as nome_curso FROM aluno a LEFT JOIN curso c ON a.id_curso = c.id";
        List<Aluno> alunos = new ArrayList<>();
        
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Aluno aluno = new Aluno();
                aluno.setId(rs.getInt("id"));
                aluno.setNome(rs.getString("nome"));
                aluno.setCpf(rs.getString("cpf"));
                aluno.setEmail(rs.getString("email"));
                aluno.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());
                aluno.setAtivo(rs.getBoolean("ativo"));
                
                Curso curso = new Curso();
                curso.setId(rs.getInt("id_curso"));
                curso.setNome(rs.getString("nome_curso"));
                aluno.setCurso(curso);
                
                alunos.add(aluno);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        
        return alunos;
    }
    
    public void altera(Aluno aluno) {
        String sql = "UPDATE aluno SET nome=?, cpf=?, email=?, data_nascimento=?, id_curso=?, ativo=? WHERE id=?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getCpf());
            stmt.setString(3, aluno.getEmail());
            stmt.setDate(4, Date.valueOf(aluno.getDataNascimento()));
            stmt.setInt(5, aluno.getCurso().getId());
            stmt.setBoolean(6, aluno.isAtivo());
            stmt.setInt(7, aluno.getId());
            
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public void remove(Aluno aluno) {
        String sql = "DELETE FROM aluno WHERE id=?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, aluno.getId());
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public Aluno buscaPorId(int id) {
        String sql = "SELECT a.*, c.nome as nome_curso FROM aluno a LEFT JOIN curso c ON a.id_curso = c.id WHERE a.id=?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Aluno aluno = new Aluno();
                aluno.setId(rs.getInt("id"));
                aluno.setNome(rs.getString("nome"));
                aluno.setCpf(rs.getString("cpf"));
                aluno.setEmail(rs.getString("email"));
                aluno.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());
                aluno.setAtivo(rs.getBoolean("ativo"));
                
                Curso curso = new Curso();
                curso.setId(rs.getInt("id_curso"));
                curso.setNome(rs.getString("nome_curso"));
                aluno.setCurso(curso);
                
                return aluno;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        
        return null;
    }
    
    public List<Aluno> buscaPorCurso(int idCurso) {
        String sql = "SELECT a.*, c.nome as nome_curso FROM aluno a LEFT JOIN curso c ON a.id_curso = c.id WHERE a.id_curso=?";
        List<Aluno> alunos = new ArrayList<>();
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idCurso);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Aluno aluno = new Aluno();
                aluno.setId(rs.getInt("id"));
                aluno.setNome(rs.getString("nome"));
                aluno.setCpf(rs.getString("cpf"));
                aluno.setEmail(rs.getString("email"));
                aluno.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());
                aluno.setAtivo(rs.getBoolean("ativo"));
                
                Curso curso = new Curso();
                curso.setId(rs.getInt("id_curso"));
                curso.setNome(rs.getString("nome_curso"));
                aluno.setCurso(curso);
                
                alunos.add(aluno);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        
        return alunos;
    }
    
    public void desativar(int id) {
        String sql = "UPDATE aluno SET ativo=false WHERE id=?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public void reativar(int id) {
        String sql = "UPDATE aluno SET ativo=true WHERE id=?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public boolean existeCpf(String cpf) {
        String sql = "SELECT COUNT(*) FROM aluno WHERE cpf=?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cpf);
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