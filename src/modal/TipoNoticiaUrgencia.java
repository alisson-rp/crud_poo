package modal;

public enum TipoNoticiaUrgencia {
    BAIXA,
    MEDIA,
    ALTA;

    public static TipoNoticiaUrgencia getTipoById(Integer opcao) {
        if (opcao == 1) {
            return BAIXA;
        } else if (opcao == 2) {
            return MEDIA;
        } else {
            return ALTA;
        }
    }
}
