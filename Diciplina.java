package gerenciadorDeEstudos.Gerenciador;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Diciplina implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nome;
    private String dataInicio;
    private List<String> checklist = new ArrayList<>();
    private Set<String> selectedItems = new HashSet<>();
    private int progresso;

    public Diciplina(String nome, String dataInicio) {
        this.nome = nome;
        this.dataInicio = dataInicio;
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

    public Set<String> getSelectedItems() {
        return selectedItems;
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

    public void addSelectedItem(String item) {
        selectedItems.add(item);
    }

    public void removeSelectedItem(String item) {
        selectedItems.remove(item);
    }

    public void limparChecklist() {
        checklist.clear();
        selectedItems.clear();
    }
}
