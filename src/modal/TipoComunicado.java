package modal;

public enum TipoComunicado {
    NOTICIA,
    COMEMORATIVO,
    AVISOS,
    ENTRETERIMENTO;

    public static TipoComunicado getTipoById(Integer opcao) {
        if (opcao == 1) {
            return NOTICIA;
        } else if (opcao == 2) {
            return COMEMORATIVO;
        } else if (opcao == 3) {
            return AVISOS;
        } else {
            return ENTRETERIMENTO;
        }
    }
}
