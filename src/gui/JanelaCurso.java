package gui;

import dao.AlunoDAO;
import javax.swing.JOptionPane;
import modelo.Curso;
import dao.CursoDAO;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.ArrayList;
import modelo.Aluno;

/**
 *
 * @author CELSO
 */
public class JanelaCurso extends javax.swing.JFrame {

    private CursoDAO cursoDAO = new CursoDAO();
    private Curso cursoSelecionado = null;
    private JanelaAluno janelaAluno;
    private List<Curso> listaCursos = new ArrayList<>();

    /**
     * Creates new form JanelaCurso
     */
    public JanelaCurso(JanelaAluno janelaAluno) {
        this.janelaAluno = janelaAluno; // Salva a referência da janela de alunos
        initComponents();
        DefaultTableModel model = new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Nome", "Carga Horária", "Limite", "Ativo"}
        ) {
            boolean[] canEdit = new boolean[]{false, false, false, false, false};

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };
        tblCursos.setModel(model);
        atualizarTabela();
        carregarCursosNoComboBox();

        bntverAlunos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntverAlunosActionPerformed(evt);
            }
        });
    }

    private void exportarAlunosInativosPorCurso() {
        CursoDAO cursoDAO = new CursoDAO();
        AlunoDAO alunoDAO = new AlunoDAO();
        List<Curso> cursos = cursoDAO.getLista();

        StringBuilder relatorio = new StringBuilder();
        for (Curso curso : cursos) {
            relatorio.append("Curso: ").append(curso.getNome()).append("\n");
            List<Aluno> alunos = alunoDAO.getLista();
            boolean temInativo = false;
            for (Aluno aluno : alunos) {
                if (aluno.getCurso() != null && aluno.getCurso().getId() == curso.getId() && !aluno.isAtivo()) {
                    relatorio.append("  - ").append(aluno.getNome()).append(" (CPF: ").append(aluno.getCpf()).append(")\n");
                    temInativo = true;
                }
            }
            if (!temInativo) {
                relatorio.append("  Nenhum aluno inativo neste curso.\n");
            }
            relatorio.append("\n");
        }

        // Salvar em arquivo
        try (java.io.FileWriter fw = new java.io.FileWriter("relatorio_alunos_inativos.txt")) {
            fw.write(relatorio.toString());
            javax.swing.JOptionPane.showMessageDialog(this, "Relatório de alunos inativos exportado com sucesso!");
        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(this, "Erro ao exportar: " + ex.getMessage());
        }
    }

    private void exportarAlunosAtivosPorCurso() {
        CursoDAO cursoDAO = new CursoDAO();
        AlunoDAO alunoDAO = new AlunoDAO();
        List<Curso> cursos = cursoDAO.getLista();

        StringBuilder relatorio = new StringBuilder();
        for (Curso curso : cursos) {
            relatorio.append("Curso: ").append(curso.getNome()).append("\n");
            List<Aluno> alunos = alunoDAO.getLista(); // Busca todos alunos
            boolean temAtivo = false;
            for (Aluno aluno : alunos) {
                if (aluno.getCurso() != null && aluno.getCurso().getId() == curso.getId() && aluno.isAtivo()) {
                    relatorio.append("  - ").append(aluno.getNome()).append(" (CPF: ").append(aluno.getCpf()).append(")\n");
                    temAtivo = true;
                }
            }
            if (!temAtivo) {
                relatorio.append("  Nenhum aluno ativo neste curso.\n");
            }
            relatorio.append("\n");
        }

        // Salvar em arquivo
        try (java.io.FileWriter fw = new java.io.FileWriter("relatorio_alunos_ativos.txt")) {
            fw.write(relatorio.toString());
            javax.swing.JOptionPane.showMessageDialog(this, "Relatório de alunos ativos exportado com sucesso!");
        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(this, "Erro ao exportar: " + ex.getMessage());
        }
    }

    private void carregarCursosNoComboBox() {
        cbCursoSelecionar.removeAllItems();
        listaCursos = cursoDAO.getLista(); // Usa o método já existente no seu DAO
        for (Curso curso : listaCursos) {
            cbCursoSelecionar.addItem(curso.getNome());
        }
    }

    private void atualizarTabela() {
        CursoDAO dao = new CursoDAO();
        List<Curso> cursos = dao.getLista();
        DefaultTableModel model = (DefaultTableModel) tblCursos.getModel();
        model.setRowCount(0); // Limpa a tabela
        for (Curso c : cursos) {
            model.addRow(new Object[]{
                c.getId(),
                c.getNome(),
                c.getCargaHoraria(),
                c.getLimiteAlunos(),
                c.isAtivo() ? "Sim" : "Não"
            });
        }
    }

    private void limparCampos() {
        txtNomeCurso.setText("");
        txtLimiteAlunos.setText("");
        txtCargaHoraria.setText("");
        chkAtivo.setSelected(true);
        tblCursos.clearSelection();
    }

    private void atualizarTabelaComLista(List<Curso> lista) {
        DefaultTableModel model = (DefaultTableModel) tblCursos.getModel();
        model.setRowCount(0);
        for (Curso c : lista) {
            model.addRow(new Object[]{
                c.getId(),
                c.getNome(),
                c.getCargaHoraria(),
                c.getLimiteAlunos(),
                c.isAtivo() ? "Sim" : "Não"
            });
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        txtNomeCurso = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtCargaHoraria = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtLimiteAlunos = new javax.swing.JTextField();
        chkAtivo = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCursos = new javax.swing.JTable();
        bntNovo = new javax.swing.JButton();
        bntExcluir = new javax.swing.JButton();
        bntEditar = new javax.swing.JButton();
        bntSalvar = new javax.swing.JButton();
        bntInativar = new javax.swing.JButton();
        bntReativar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtConsultaCurso = new javax.swing.JTextField();
        bntConsultarCurso = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        cbCursoSelecionar = new javax.swing.JComboBox<>();
        bntverAlunos = new javax.swing.JButton();
        bntExportarAtivo = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("Nome curso");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, 20));

        txtNomeCurso.setText("nome curso");
        txtNomeCurso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomeCursoActionPerformed(evt);
            }
        });
        getContentPane().add(txtNomeCurso, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 20, -1, -1));

        jLabel2.setText("Carga horaria");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, -1, -1));

        txtCargaHoraria.setText("carga horaria");
        getContentPane().add(txtCargaHoraria, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 60, -1, -1));

        jLabel3.setText("Limite Alunos");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 20, -1, -1));

        txtLimiteAlunos.setText("limite alunos");
        txtLimiteAlunos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLimiteAlunosActionPerformed(evt);
            }
        });
        getContentPane().add(txtLimiteAlunos, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 20, -1, -1));

        chkAtivo.setText("Ativo");
        getContentPane().add(chkAtivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 50, -1, -1));

        tblCursos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nome Curso", "Carga Horaria", "limite Alunos", "Ativo"
            }
        ));
        jScrollPane1.setViewportView(tblCursos);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 100, 400, 130));

        bntNovo.setText("Novo");
        bntNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntNovoActionPerformed(evt);
            }
        });
        getContentPane().add(bntNovo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, -1, -1));

        bntExcluir.setText("Excluir");
        bntExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntExcluirActionPerformed(evt);
            }
        });
        getContentPane().add(bntExcluir, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 240, -1, -1));

        bntEditar.setText("Editar");
        bntEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntEditarActionPerformed(evt);
            }
        });
        getContentPane().add(bntEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 240, -1, -1));

        bntSalvar.setText("Salvar");
        bntSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntSalvarActionPerformed(evt);
            }
        });
        getContentPane().add(bntSalvar, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 240, -1, -1));

        bntInativar.setText("Inativar");
        bntInativar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntInativarActionPerformed(evt);
            }
        });
        getContentPane().add(bntInativar, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 240, -1, -1));

        bntReativar.setText("Reativar");
        bntReativar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntReativarActionPerformed(evt);
            }
        });
        getContentPane().add(bntReativar, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 240, -1, -1));

        jLabel4.setText("buscar");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, -1, -1));

        txtConsultaCurso.setText("jTextField1");
        txtConsultaCurso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtConsultaCursoActionPerformed(evt);
            }
        });
        getContentPane().add(txtConsultaCurso, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 290, -1, -1));

        bntConsultarCurso.setText("buscar");
        bntConsultarCurso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntConsultarCursoActionPerformed(evt);
            }
        });
        getContentPane().add(bntConsultarCurso, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 290, -1, -1));

        jLabel5.setText("ver curso");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 310, 50, -1));

        cbCursoSelecionar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        getContentPane().add(cbCursoSelecionar, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 310, -1, -1));

        bntverAlunos.setText("ver");
        bntverAlunos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntverAlunosActionPerformed(evt);
            }
        });
        getContentPane().add(bntverAlunos, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 310, -1, -1));

        bntExportarAtivo.setText("Alunos ativos");
        bntExportarAtivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntExportarAtivoActionPerformed(evt);
            }
        });
        getContentPane().add(bntExportarAtivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 380, -1, -1));

        jButton1.setText("Alunos Inativos");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 380, 110, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNomeCursoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomeCursoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomeCursoActionPerformed

    private void txtLimiteAlunosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLimiteAlunosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLimiteAlunosActionPerformed

    private void bntSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntSalvarActionPerformed
        // TODO add your handling code here:
        String nome = txtNomeCurso.getText().trim();
        String cargaStr = txtCargaHoraria.getText().trim();
        String limiteStr = txtLimiteAlunos.getText().trim();
        if (nome.length() < 3) {
            JOptionPane.showMessageDialog(this, "Nome deve ter pelo menos 3 caracteres.");
            return;
        }
        int carga;
        try {
            carga = Integer.parseInt(cargaStr);
            if (carga < 20) {
                JOptionPane.showMessageDialog(this, "Carga horária mínima é 20.");
                return;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Carga horária inválida.");
            return;
        }
        int limite;
        try {
            limite = Integer.parseInt(limiteStr);
            if (limite < 1) {
                JOptionPane.showMessageDialog(this, "Limite de alunos deve ser pelo menos 1.");
                return;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Limite de alunos inválido.");
            return;
        }
        boolean ativo = chkAtivo.isSelected();
        CursoDAO dao = new CursoDAO();
        Curso c = new Curso();
        c.setNome(nome);
        c.setCargaHoraria(carga);
        c.setLimiteAlunos(limite);
        c.setAtivo(ativo);
        int row = tblCursos.getSelectedRow();
        if (row == -1) {
            dao.adiciona(c);
            JOptionPane.showMessageDialog(this, "Curso cadastrado!");
        } else {
            int id = (int) tblCursos.getValueAt(row, 0);
            c.setId(id);
            dao.altera(c);
            JOptionPane.showMessageDialog(this, "Curso alterado!");
        }
        atualizarTabela();
        limparCampos();


    }//GEN-LAST:event_bntSalvarActionPerformed

    private void bntNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntNovoActionPerformed
        // TODO add your handling code here
        limparCampos();


    }//GEN-LAST:event_bntNovoActionPerformed

    private void bntEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntEditarActionPerformed
        // TODO add your handling code here:
        int row = tblCursos.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um curso para editar.");
            return;
        }
        int id = (int) tblCursos.getValueAt(row, 0);
        CursoDAO dao = new CursoDAO();
        Curso c = dao.buscaPorId(id);
        if (c != null) {
            txtNomeCurso.setText(c.getNome());
            txtCargaHoraria.setText(String.valueOf(c.getCargaHoraria()));
            txtLimiteAlunos.setText(String.valueOf(c.getLimiteAlunos()));
            chkAtivo.setSelected(c.isAtivo());
        }
    }//GEN-LAST:event_bntEditarActionPerformed

    private void bntExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntExcluirActionPerformed
        // TODO add your handling code here:
        int row = tblCursos.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um curso para excluir.");
            return;
        }
        int id = (int) tblCursos.getValueAt(row, 0);
        CursoDAO dao = new CursoDAO();
        Curso c = dao.buscaPorId(id);
        if (c != null) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Excluir o curso? Esta ação é irreversível.",
                    "Confirmação", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dao.remove(c);
                atualizarTabela();
                // Atualiza também os alunos
                if (janelaAluno != null) {
                    janelaAluno.atualizarTabela();
                }
                limparCampos();
                JOptionPane.showMessageDialog(this, "Curso excluído.");
            }
        }
    }//GEN-LAST:event_bntExcluirActionPerformed

    private void bntInativarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntInativarActionPerformed
        // TODO add your handling code here:
        int row = tblCursos.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um curso para inativar.");
            return;
        }
        int id = (int) tblCursos.getValueAt(row, 0);
        CursoDAO dao = new CursoDAO();
        dao.desativar(id);
        atualizarTabela();
        JOptionPane.showMessageDialog(this, "Curso inativado.");
    }//GEN-LAST:event_bntInativarActionPerformed

    private void bntReativarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntReativarActionPerformed
        // TODO add your handling code here:
        int row = tblCursos.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um curso para reativar.");
            return;
        }
        int id = (int) tblCursos.getValueAt(row, 0);
        CursoDAO dao = new CursoDAO();
        dao.reativar(id);
        atualizarTabela();
        JOptionPane.showMessageDialog(this, "Curso reativado.");
    }//GEN-LAST:event_bntReativarActionPerformed

    private void txtConsultaCursoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtConsultaCursoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtConsultaCursoActionPerformed

    private void bntConsultarCursoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntConsultarCursoActionPerformed
        // TODO add your handling code here:
        String termo = txtConsultaCurso.getText().trim();
        if (termo.isEmpty()) {
            atualizarTabela();
            return;
        }

        CursoDAO cursoDAO = new CursoDAO();
        List<Curso> lista = cursoDAO.buscarPorNome(termo); // Você vai criar este método no DAO!
        atualizarTabelaComLista(lista);

    }//GEN-LAST:event_bntConsultarCursoActionPerformed

    private void bntverAlunosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntverAlunosActionPerformed
        // TODO add your handling code here:
        int indice = cbCursoSelecionar.getSelectedIndex();
        if (indice == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um curso!");
            return;
        }
        Curso cursoSelecionado = listaCursos.get(indice);

        dao.AlunoDAO alunoDAO = new dao.AlunoDAO();
        List<modelo.Aluno> alunos = alunoDAO.getLista(); // Busca todos alunos

        StringBuilder sb = new StringBuilder();
        for (modelo.Aluno aluno : alunos) {
            if (aluno.getCurso() != null && aluno.getCurso().getId() == cursoSelecionado.getId()) {
                sb.append(aluno.getNome()).append(" - CPF: ").append(aluno.getCpf()).append("\n");
            }
        }
        if (sb.length() == 0) {
            sb.append("Nenhum aluno cadastrado neste curso.");
        }
        JOptionPane.showMessageDialog(this, sb.toString(), "Alunos do Curso: " + cursoSelecionado.getNome(), JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_bntverAlunosActionPerformed

    private void bntExportarAtivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntExportarAtivoActionPerformed
        // TODO add your handling code here:
        exportarAlunosAtivosPorCurso();
    }//GEN-LAST:event_bntExportarAtivoActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        exportarAlunosInativosPorCurso();
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JanelaCurso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JanelaCurso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JanelaCurso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JanelaCurso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JanelaCurso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JanelaCurso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JanelaCurso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JanelaCurso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the forms */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JanelaAluno janelaAluno = new JanelaAluno();
                JanelaCurso janelaCurso = new JanelaCurso(janelaAluno);
                janelaAluno.setVisible(true);
                janelaCurso.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bntConsultarCurso;
    private javax.swing.JButton bntEditar;
    private javax.swing.JButton bntExcluir;
    private javax.swing.JButton bntExportarAtivo;
    private javax.swing.JButton bntInativar;
    private javax.swing.JButton bntNovo;
    private javax.swing.JButton bntReativar;
    private javax.swing.JButton bntSalvar;
    private javax.swing.JButton bntverAlunos;
    private javax.swing.JComboBox<String> cbCursoSelecionar;
    private javax.swing.JCheckBox chkAtivo;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTable tblCursos;
    private javax.swing.JTextField txtCargaHoraria;
    private javax.swing.JTextField txtConsultaCurso;
    private javax.swing.JTextField txtLimiteAlunos;
    private javax.swing.JTextField txtNomeCurso;
    // End of variables declaration//GEN-END:variables
}
