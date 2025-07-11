/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gui;

import javax.swing.JOptionPane;
import gui.JanelaAluno;
import gui.JanelaCurso;

/**
 *
 * @author CELSO
 */
public class JanelaPrincipal extends javax.swing.JFrame {

    private JanelaAluno janelaAluno;
    private JanelaCurso janelaCurso;

    /**
     * Creates new form JanelaPrincipal
     */
    public JanelaPrincipal() {
        initComponents();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        bntJanelaCurso = new javax.swing.JButton();
        bntJanelaAluno = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Janela Principal");
        jLabel1.setToolTipText("");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 151, -1));

        bntJanelaCurso.setText("gerenciar curso");
        bntJanelaCurso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntJanelaCursoActionPerformed(evt);
            }
        });
        getContentPane().add(bntJanelaCurso, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 90, -1, -1));

        bntJanelaAluno.setText("gerenciar aluno");
        bntJanelaAluno.setToolTipText("");
        bntJanelaAluno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntJanelaAlunoActionPerformed(evt);
            }
        });
        getContentPane().add(bntJanelaAluno, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 50, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bntJanelaCursoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntJanelaCursoActionPerformed
        // TODO add your handling code here:
        if (janelaAluno == null || !janelaAluno.isDisplayable()) {
            janelaAluno = new JanelaAluno();
        }
        // Cria a janela de curso passando a janelaAluno como parâmetro
        if (janelaCurso == null || !janelaCurso.isDisplayable()) {
            janelaCurso = new JanelaCurso(janelaAluno);
        }
        janelaCurso.setVisible(true);


    }//GEN-LAST:event_bntJanelaCursoActionPerformed

    private void bntJanelaAlunoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntJanelaAlunoActionPerformed
        // TODO add your handling code here:
        if (janelaAluno == null || !janelaAluno.isDisplayable()) {
            janelaAluno = new JanelaAluno();
        }
        janelaAluno.setVisible(true);

    }//GEN-LAST:event_bntJanelaAlunoActionPerformed

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
            java.util.logging.Logger.getLogger(JanelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JanelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JanelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JanelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JanelaPrincipal().setVisible(true);
            }
        });

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bntJanelaAluno;
    private javax.swing.JButton bntJanelaCurso;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
