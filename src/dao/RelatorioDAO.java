package dao;

import factory.ConnectionFactory;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import modelo.Aluno;
import modelo.Curso;

public class RelatorioDAO {
    private Connection connection;
    
    public RelatorioDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }
    
    public void gerarRelatorioAlunosAtivosPorCurso(String caminhoArquivo) {
        String sql = "SELECT c.nome as nome_curso, a.nome as nome_aluno, a.cpf, a.email " +
                     "FROM aluno a JOIN curso c ON a.id_curso = c.id " +
                     "WHERE a.ativo = true ORDER BY c.nome, a.nome";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery();
             FileWriter writer = new FileWriter(caminhoArquivo)) {
            
            writer.write("Relatório de Alunos Ativos por Curso\n");
            writer.write("====================================\n\n");
            
            String cursoAtual = "";
            while (rs.next()) {
                String curso = rs.getString("nome_curso");
                if (!curso.equals(cursoAtual)) {
                    cursoAtual = curso;
                    writer.write("\nCurso: " + curso + "\n");
                    writer.write("------------------------------------\n");
                    writer.write(String.format("%-30s %-15s %-30s\n", "Nome", "CPF", "Email"));
                }
                
                writer.write(String.format("%-30s %-15s %-30s\n", 
                    rs.getString("nome_aluno"),
                    rs.getString("cpf"),
                    rs.getString("email")));
            }
            
        } catch (SQLException | IOException e) {
            throw new RuntimeException("Erro ao gerar relatório", e);
        }
    }
    
    public void gerarRelatorioAlunosInativosPorCurso(String caminhoArquivo) {
        String sql = "SELECT c.nome as nome_curso, a.nome as nome_aluno, a.cpf, a.email " +
                     "FROM aluno a JOIN curso c ON a.id_curso = c.id " +
                     "WHERE a.ativo = false ORDER BY c.nome, a.nome";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery();
             FileWriter writer = new FileWriter(caminhoArquivo)) {
            
            writer.write("Relatório de Alunos Inativos por Curso\n");
            writer.write("======================================\n\n");
            
            String cursoAtual = "";
            while (rs.next()) {
                String curso = rs.getString("nome_curso");
                if (!curso.equals(cursoAtual)) {
                    cursoAtual = curso;
                    writer.write("\nCurso: " + curso + "\n");
                    writer.write("------------------------------------\n");
                    writer.write(String.format("%-30s %-15s %-30s\n", "Nome", "CPF", "Email"));
                }
                
                writer.write(String.format("%-30s %-15s %-30s\n", 
                    rs.getString("nome_aluno"),
                    rs.getString("cpf"),
                    rs.getString("email")));
            }
            
        } catch (SQLException | IOException e) {
            throw new RuntimeException("Erro ao gerar relatório", e);
        }
    }
    
    public void gerarRelatorioCursosComAlunos(String caminhoArquivo) {
        String sql = "SELECT c.id, c.nome as nome_curso, c.carga_horaria, c.limite_alunos, " +
                     "COUNT(a.id) as total_alunos " +
                     "FROM curso c LEFT JOIN aluno a ON c.id = a.id_curso AND a.ativo = true " +
                     "GROUP BY c.id, c.nome, c.carga_horaria, c.limite_alunos " +
                     "ORDER BY c.nome";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery();
             FileWriter writer = new FileWriter(caminhoArquivo)) {
            
            writer.write("Relatório de Cursos com Alunos\n");
            writer.write("==============================\n\n");
            
            writer.write(String.format("%-30s %-15s %-15s %-15s\n", 
                "Curso", "Carga Horária", "Limite", "Alunos"));
            writer.write("----------------------------------------------------------------\n");
            
            while (rs.next()) {
                writer.write(String.format("%-30s %-15d %-15d %-15d\n", 
                    rs.getString("nome_curso"),
                    rs.getInt("carga_horaria"),
                    rs.getInt("limite_alunos"),
                    rs.getInt("total_alunos")));
            }
            
        } catch (SQLException | IOException e) {
            throw new RuntimeException("Erro ao gerar relatório", e);
        }
    }
}