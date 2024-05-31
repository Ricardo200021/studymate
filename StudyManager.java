package gerenciadorDeEstudos.Gerenciador;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class StudyManager {

    private List<Diciplina> diciplinas = new ArrayList<>();
    private static final String FILE_PATH = "diciplinas.dat";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new StudyManager().createAndShowGui();
            }
        });
    }

    public StudyManager() {
        loadDiciplinas();
    }

    private void createAndShowGui() {
        JFrame frame = new JFrame("StudyMate");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        // Painel Principal
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.YELLOW);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0); // Espaçamento entre os botões
        gbc.anchor = GridBagConstraints.CENTER;

        // Título
        JLabel titleLabel = new JLabel("StudyMate");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLUE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        panel.add(titleLabel, gbc);

        // Botões
        JButton btnAdicionarDiciplina = new JButton("Adicionar diciplina");
        JButton btnListarDiciplinas = new JButton("Listar diciplinas");
        JButton btnAdicionarItemChecklist = new JButton("Adicionar matéria da diciplina");
        JButton btnListarCheckbox = new JButton("Listar matéria");
        JButton btnLimparChecklist = new JButton("Limpar matéria");
        JButton btnDeletarDiciplina = new JButton("Deletar diciplina");

        // Adicionando botões ao painel
        gbc.gridy++;
        panel.add(btnAdicionarDiciplina, gbc);

        gbc.gridy++;
        panel.add(btnListarDiciplinas, gbc);

        gbc.gridy++;
        panel.add(btnAdicionarItemChecklist, gbc);

        gbc.gridy++;
        panel.add(btnListarCheckbox, gbc);

        gbc.gridy++;
        panel.add(btnLimparChecklist, gbc);

        gbc.gridy++;
        panel.add(btnDeletarDiciplina, gbc);

        // Adicionando painel ao frame
        frame.getContentPane().add(panel, BorderLayout.CENTER);

        // Adicionando ações aos botões
        btnAdicionarDiciplina.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                adicionarDiciplina();
            }
        });

        btnListarDiciplinas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                listarDiciplinas();
            }
        });

        btnAdicionarItemChecklist.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                adicionarItemChecklist();
            }
        });

        btnListarCheckbox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                listarCheckbox();
            }
        });

        btnLimparChecklist.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limparChecklist();
            }
        });

        btnDeletarDiciplina.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deletarDiciplina();
            }
        });

        // Exibir o frame
        frame.setVisible(true);
    }

    private void adicionarDiciplina() {
        JTextField nomeField = new JTextField(10);
        JTextField dataInicioField = new JTextField(10);

        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("Diciplina"));
        myPanel.add(nomeField);
        myPanel.add(Box.createHorizontalStrut(15));
        myPanel.add(new JLabel("Início do estudo"));
        myPanel.add(dataInicioField);

        int result = JOptionPane.showConfirmDialog(null, myPanel, "Adicionar Diciplina", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String nome = nomeField.getText();
            String dataInicio = dataInicioField.getText();
            Diciplina diciplina = new Diciplina(nome, dataInicio);
            diciplinas.add(diciplina);
            saveDiciplinas();
            JOptionPane.showMessageDialog(null, "Diciplina adicionada com sucesso!");
        }
    }

    private void listarDiciplinas() {
        if (diciplinas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhuma diciplina adicionada");
            return;
        }

        StringBuilder lista = new StringBuilder();
        for (Diciplina diciplina : diciplinas) {
            lista.append("Diciplina: ").append(diciplina.getNome())
                    .append(", Data de Início: ").append(diciplina.getDataInicio())
                    .append(", Progresso: ").append(diciplina.getProgresso()).append("%\n");
        }

        JOptionPane.showMessageDialog(null, lista.toString());
    }

    private void adicionarItemChecklist() {
        if (diciplinas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhuma diciplina adicionada.");
            return;
        }

        String[] nomesDiciplinas = diciplinas.stream().map(Diciplina::getNome).toArray(String[]::new);
        String diciplinaSelecionada = (String) JOptionPane.showInputDialog(null, "Selecione a diciplina:",
                "Adicionar Item ao Checklist", JOptionPane.QUESTION_MESSAGE, null, nomesDiciplinas, nomesDiciplinas[0]);

        if (diciplinaSelecionada != null) {
            Diciplina materia = diciplinas.stream().filter(m -> m.getNome().equals(diciplinaSelecionada)).findFirst().orElse(null);
            if (materia != null) {
                String item = JOptionPane.showInputDialog("Matéria da diciplina:");
                if (item != null && !item.trim().isEmpty()) {
                    materia.adicionarItemChecklist(item);
                    saveDiciplinas();
                    JOptionPane.showMessageDialog(null, "Matéria adicionada a diciplina com sucesso!");
                }
            }
        }
    }

    private void listarCheckbox() {
        if (diciplinas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhuma diciplina adicionada.");
            return;
        }

        String[] nomesDiciplinas = diciplinas.stream().map(Diciplina::getNome).toArray(String[]::new);
        String diciplinaSelecionada = (String) JOptionPane.showInputDialog(null, "Selecione a diciplina:",
                "Listar matéria", JOptionPane.QUESTION_MESSAGE, null, nomesDiciplinas, nomesDiciplinas[0]);

        if (diciplinaSelecionada != null) {
            Diciplina materia = diciplinas.stream().filter(m -> m.getNome().equals(diciplinaSelecionada)).findFirst().orElse(null);
            if (materia != null) {
                showChecklist(materia);
            }
        }
    }

    private void showChecklist(Diciplina diciplina) {
        JFrame checklistFrame = new JFrame("Matéria - " + diciplina.getNome());
        checklistFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        checklistFrame.setSize(400, 300);
        checklistFrame.setLocationRelativeTo(null);

        JPanel checklistPanel = new JPanel();
        checklistPanel.setLayout(new BoxLayout(checklistPanel, BoxLayout.Y_AXIS));

        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setValue(diciplina.getProgresso());
        progressBar.setStringPainted(true);

        checklistPanel.add(progressBar);

        List<JCheckBox> checkBoxes = new ArrayList<>();
        for (String item : diciplina.getChecklist()) {
            JCheckBox checkBox = new JCheckBox(item);
            checkBox.setSelected(diciplina.getSelectedItems().contains(item)); // Mantém a seleção
            checkBoxes.add(checkBox);
            checkBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (checkBox.isSelected()) {
                        diciplina.addSelectedItem(item);
                    } else {
                        diciplina.removeSelectedItem(item);
                    }
                    int totalItems = checkBoxes.size();
                    int selectedItems = (int) checkBoxes.stream().filter(JCheckBox::isSelected).count();
                    int newProgress = (int) ((selectedItems / (double) totalItems) * 100);
                    diciplina.setProgresso(newProgress);
                    progressBar.setValue(newProgress);
                    if (newProgress == 100) {
                        JOptionPane.showMessageDialog(checklistFrame, "Diciplina concluída! Progresso 100%.");
                    }
                    saveDiciplinas();
                }
            });
            checklistPanel.add(checkBox);
        }

        checklistFrame.getContentPane().add(new JScrollPane(checklistPanel), BorderLayout.CENTER);
        checklistFrame.setVisible(true);
    }

    private void limparChecklist() {
        if (diciplinas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhuma diciplina adicionada.");
            return;
        }

        String[] nomesDiciplinas = diciplinas.stream().map(Diciplina::getNome).toArray(String[]::new);
        String diciplinaSelecionada = (String) JOptionPane.showInputDialog(null, "Selecione a diciplina:",
                "Limpar matéria", JOptionPane.QUESTION_MESSAGE, null, nomesDiciplinas, nomesDiciplinas[0]);

        if (diciplinaSelecionada != null) {
            Diciplina diciplina = diciplinas.stream().filter(m -> m.getNome().equals(diciplinaSelecionada)).findFirst().orElse(null);
            if (diciplina != null) {
                diciplina.limparChecklist();
                diciplina.setProgresso(0);
                saveDiciplinas();
                JOptionPane.showMessageDialog(null, "Matéria deletada com sucesso!");
            }
        }
    }

    private void deletarDiciplina() {
        if (diciplinas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhuma diciplina adicionada.");
            return;
        }

        String[] nomesDiciplinas = diciplinas.stream().map(Diciplina::getNome).toArray(String[]::new);
        String diciplinaSelecionada = (String) JOptionPane.showInputDialog(null, "Selecione a diciplina:",
                "Deletar Diciplina", JOptionPane.QUESTION_MESSAGE, null, nomesDiciplinas, nomesDiciplinas[0]);

        if (diciplinaSelecionada != null) {
            Diciplina diciplina = diciplinas.stream().filter(m -> m.getNome().equals(diciplinaSelecionada)).findFirst().orElse(null);
            if (diciplina != null) {
                diciplinas.remove(diciplina);
                saveDiciplinas();
                JOptionPane.showMessageDialog(null, "Diciplina deletada com sucesso!");
            }
        }
    }

    private void saveDiciplinas() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(diciplinas);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadDiciplinas() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            diciplinas = (List<Diciplina>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
