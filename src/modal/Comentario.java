package modal;

import java.time.LocalDate;

public class Comentario extends Entity{
    private Comunicado comunicado;
    private String comentario;
    private Usuario usuario;
    private LocalDate dataComentario;

    public Comunicado getComunicado() {
        return comunicado;
    }

    public void setComunicado(Comunicado comunicado) {
        this.comunicado = comunicado;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LocalDate getDataComentario() {
        return dataComentario;
    }

    public void setDataComentario(LocalDate dataComentario) {
        this.dataComentario = dataComentario;
    }

    @Override
    public String toString() {
        return "ComentarioDAO{" +
                "comunicado=" + comunicado +
                ", comentario='" + comentario + '\'' +
                ", usuario=" + usuario +
                ", dataComentario=" + dataComentario +
                '}';
    }
}
