package calendar.fc.entidades;

public class Quadro {
    private String nome;
    private String tipo;
    private int idCalendar;
    private String novoNome;

    public Quadro() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getIdCalendar() {
        return idCalendar;
    }

    public void setIdCalendar(int idCalendar) {
        this.idCalendar = idCalendar;
    }

    public String getNovoNome() {
        return novoNome;
    }

    public void setNovoNome(String novoNome) {
        this.novoNome = novoNome;
    }
    
    
}
