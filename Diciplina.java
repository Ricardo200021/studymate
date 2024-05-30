package gerenciadorDeEstudos.Gerenciador;

import java.util.ArrayList;
import java.util.List;

class Diciplina {
    private String nome;
    private String dataInicio;
    private List<String> checklist;
    private List<String> selectedItems; // Lista de itens selecionados
    private int progresso;

    public Diciplina(String nome, String dataInicio) {
        this.nome = nome;
        this.dataInicio = dataInicio;
        this.checklist = new ArrayList<>();
        this.selectedItems = new ArrayList<>(); // Inicializa a lista de itens selecionados
        this.progresso = 0;
    }

    public String getNome() {
        return nome;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public List<String> getChecklist() {
        return checklist;
    }

    public int getProgresso() {
        return progresso;
    }

    public void setProgresso(int progresso) {
        this.progresso = progresso;
    }

    public void adicionarItemChecklist(String item) {
        checklist.add(item);
    }

    public void limparChecklist() {
        checklist.clear();
        selectedItems.clear(); // Limpa a lista de itens selecionados
    }

    public List<String> getSelectedItems() {
        return selectedItems;
    }

    public void addSelectedItem(String item) {
        if (!selectedItems.contains(item)) {
            selectedItems.add(item);
        }
    }

    public void removeSelectedItem(String item) {
        selectedItems.remove(item);
    }
}
