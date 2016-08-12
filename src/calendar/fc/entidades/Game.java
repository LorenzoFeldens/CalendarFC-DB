package calendar.fc.entidades;

public class Game {
    private String titulo;
    private String data;
    private int tCasa;
    private int tFora;
    private int competicao;

    public Game() {
        titulo = "";
        data = "";
        tCasa = 0;
        tFora = 0;
        competicao = 0;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int gettCasa() {
        return tCasa;
    }

    public void settCasa(int tCasa) {
        this.tCasa = tCasa;
    }

    public int gettFora() {
        return tFora;
    }

    public void settFora(int tFora) {
        this.tFora = tFora;
    }

    public int getCompeticao() {
        return competicao;
    }

    public void setCompeticao(int competicao) {
        this.competicao = competicao;
    }
    
    
}
