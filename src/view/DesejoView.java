package view;
import controller.DesejoController;
import model.Desejo;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class DesejoView extends JFrame {
    private JTextField txtNome, txtPreco;
    private JTable tabela;
    private DefaultTableModel modelo;
    private DesejoController controller;

    public DesejoView() {
        controller = new DesejoController();
        configurarJanela();
        configurarComponentes();
    }

    private void configurarJanela() {
        setTitle("Lista de Desejos");
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
    }

    private void configurarComponentes() {
        JPanel painelLateral = new JPanel();
        painelLateral.setLayout(new GridLayout(3, 1, 10, 10));
        painelLateral.setBorder(BorderFactory.createEmptyBorder(40, 20, 40, 20));

        JButton btnAdicionar = new JButton("Adicionar");
        JButton btnAtualizar = new JButton("Atualizar");
        JButton btnRemover = new JButton("Deletar");

        painelLateral.add(btnAdicionar);
        painelLateral.add(btnAtualizar);
        painelLateral.add(btnRemover);

        add(painelLateral, BorderLayout.WEST);

        JPanel painelCentral = new JPanel(new BorderLayout(10, 10));

        JPanel painelForm = new JPanel(new GridLayout(2, 2, 8, 8));

        painelForm.add(new JLabel("Nome do Desejo:"));
        txtNome = new JTextField();
        painelForm.add(txtNome);

        painelForm.add(new JLabel("Preço (R$):"));
        txtPreco = new JTextField();
        painelForm.add(txtPreco);

        painelCentral.add(painelForm, BorderLayout.NORTH);

        modelo = new DefaultTableModel(new Object[]{"ID", "Nome", "Preço (R$)"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabela = new JTable(modelo);
        tabela.getTableHeader().setReorderingAllowed(false);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.setRowHeight(24);

        JScrollPane scrollPane = new JScrollPane(tabela);
        painelCentral.add(scrollPane, BorderLayout.CENTER);

        add(painelCentral, BorderLayout.CENTER);

        tabela.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int linha = tabela.getSelectedRow();
                if (linha >= 0) {
                    txtNome.setText(modelo.getValueAt(linha, 1).toString());
                    txtPreco.setText(modelo.getValueAt(linha, 2).toString());
                }
            }
        });

        btnAdicionar.addActionListener(e -> adicionarDesejo());
        btnAtualizar.addActionListener(e -> atualizarDesejo());
        btnRemover.addActionListener(e -> removerDesejo());
    }

    private void adicionarDesejo() {
        try {
            String nome = txtNome.getText().trim();
            double preco = Double.parseDouble(txtPreco.getText().trim().replace(",", "."));

            if (nome.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Digite o nome do desejo!");
                return;
            }

            controller.addDesejo(new Desejo(0, nome, preco));
            limparCampos();
            atualizarTabela(controller.listar());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Preço inválido! Use números (ex: 29.90)");
        }
    }

    private void atualizarDesejo() {
        int linha = tabela.getSelectedRow();
        if (linha >= 0) {
            try {
                int id = (int) modelo.getValueAt(linha, 0);
                String nome = txtNome.getText().trim();
                double preco = Double.parseDouble(txtPreco.getText().trim());

                if (controller.atualizarDesejo(id, new Desejo(id, nome, preco))) {
                    limparCampos();
                    atualizarTabela(controller.listar());
                    JOptionPane.showMessageDialog(this, "Desejo atualizado!");
                } else {
                    JOptionPane.showMessageDialog(this, "Desejo não encontrado.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Preço inválido!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um desejo para atualizar.");
        }
    }

    private void removerDesejo() {
        int linha = tabela.getSelectedRow();
        if (linha >= 0) {
            int id = (int) modelo.getValueAt(linha, 0);
            if (controller.removerDesejo(id)) {
                atualizarTabela(controller.listar());
                limparCampos();
                JOptionPane.showMessageDialog(this, "Desejo removido!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um desejo para remover.");
        }
    }

    private void atualizarTabela(List<Desejo> lista) {
        modelo.setRowCount(0);
        for (Desejo d : lista) {
            modelo.addRow(new Object[]{d.getID(), d.getNome(), d.getPreco()});
        }
    }

    private void limparCampos() {
        txtNome.setText("");
        txtPreco.setText("");
    }

    public void executar() {
        setVisible(true);
        atualizarTabela(controller.listar());
    }
}