package modal;

import java.time.LocalDate;

public class Comunicado {
    private int cdComunicado;
    private LocalDate dataCadastro;
    private Setor setor;
    private Usuario responsavel;
    private String Titulo;
    private String descricao;
    private int qtdCurtidas;
    private TipoNoticiaUrgencia tipoUrgencia;
    private TipoComunicado tipoComunicado;

    public int getCdComunicado() {
        return cdComunicado;
    }

    public void setCdComunicado(int cdComunicado) {
        this.cdComunicado = cdComunicado;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Setor getSetor() {
        return setor;
    }

    public void setSetor(Setor setor) {
        this.setor = setor;
    }

    public Usuario getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Usuario responsavel) {
        this.responsavel = responsavel;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getQtdCurtidas() {
        return qtdCurtidas;
    }

    public void setQtdCurtidas(int qtdCurtidas) {
        this.qtdCurtidas = qtdCurtidas;
    }

    public TipoNoticiaUrgencia getTipoUrgencia() {
        return tipoUrgencia;
    }

    public void setTipoUrgencia(TipoNoticiaUrgencia tipoUrgencia) {
        this.tipoUrgencia = tipoUrgencia;
    }

    public TipoComunicado getTipoComunicado() {
        return tipoComunicado;
    }

    public void setTipoComunicado(TipoComunicado tipoComunicado) {
        this.tipoComunicado = tipoComunicado;
    }

    @Override
    public String toString() {
        return "Comunicado{" +
                "cdComunicado=" + cdComunicado +
                ", dataCadastro=" + dataCadastro +
                ", setor=" + setor +
                ", responsavel=" + responsavel +
                ", Titulo='" + Titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", qtdCurtidas=" + qtdCurtidas +
                ", tipoUrgencia=" + tipoUrgencia +
                ", tipoComunicado=" + tipoComunicado +
                '}';
    }
}
