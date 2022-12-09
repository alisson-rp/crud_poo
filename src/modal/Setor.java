package modal;

public class Setor extends Entity {

    private String nome;

    public Setor(String nome) {
        this.nome = nome;
    }

    public Setor() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Setor{" +
                "nome='" + nome + '\'' +
                '}';
    }
}
