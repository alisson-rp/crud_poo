import modal.Comentario;
import modal.Comunicado;
import modal.Setor;
import modal.Usuario;
import repository.ComentarioDAO;
import repository.ComunicadoDAO;
import repository.SetorDAO;
import repository.UsuarioDAO;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

public class AppMainCadastro {

    public static void vizualizarComentarios(Comunicado comunicado) throws SQLException, ClassNotFoundException {
        try {
            List<Comentario> comments = ComentarioDAO.buscaPorId(comunicado.getId());
            String texto = "";
            for (Comentario comment : comments) {
                texto += "\n" + comment.getUsuario().getUsuario();
                texto += "\n" + comment.getId() + " - " + comment.getComentario() + "\n";
                texto += "-------------------------------------------------------------";
            }

            String[] opcoes;
            opcoes = new String[]{"Operações", "Voltar"};


            int resposta = JOptionPane.showOptionDialog(
                    null
                    , texto
                    , "COMENTÁRIOS - " + comunicado.getTitulo()
                    , JOptionPane.YES_NO_OPTION
                    , JOptionPane.PLAIN_MESSAGE
                    , null
                    , opcoes
                    , "Botao 3"
            );

            switch (resposta) {
                case 0:
                    Comentario comment = AppMain.chamaCastroComentario(comunicado);
                    if (comment != null) AppMain.getCommentDAO().salvar(comment);
                    vizualizarComentarios(comunicado);
                    break;
                case 2:
                    vizualizarComentarios(comunicado);
                    break;
            }
        } catch (Exception e) {
            AppMain.chamaCastroComentario(comunicado);
        }
    }

    public static void vizulizarComunicado(Comunicado comunicado) throws SQLException, ClassNotFoundException {
        try {
            String texto = "";
            texto = "---------------------------- " + comunicado.getTitulo() + " ----------------------------" +
                    "\n " +
                    "\n Por: " + comunicado.getResponsavel().getUsuario() +
                    "\n Setor: " + comunicado.getSetor().getNome() +
                    "\n Tema: " + comunicado.getTipoComunicado() +
                    "\n Data: " + comunicado.getDataCadastro() +
                    "\n \n Urgência/Prioridade: " + comunicado.getTipoUrgencia() +
                    "\n Comunicado : " + comunicado.getDescricao() +
                    "\n \n \n \n Curtidas: " + comunicado.getQtdCurtidas() +
                    "\n";

            String[] opcoes;
            opcoes = new String[]{"Curtir", "Ver comentários", "Voltar"};

            int resposta = JOptionPane.showOptionDialog(
                    null
                    , texto
                    , comunicado.getTitulo()  // Titulo
                    , JOptionPane.YES_NO_OPTION
                    , JOptionPane.PLAIN_MESSAGE
                    , null
                    , opcoes
                    , "Botao 3"
            );

            switch (resposta) {
                case 0:
                    ComunicadoDAO.curtiComentario(comunicado.getId());
                    comunicado.setQtdCurtidas(comunicado.getQtdCurtidas() + 1);
                    vizulizarComunicado(comunicado);
                    break;
                case 1:
                    vizualizarComentarios(comunicado);
                    vizulizarComunicado(comunicado);
                    break;
                case 2:
                    AppMain.ChamarMenuPrincipal();
                    break;
            }
        } catch (Exception e) {
            AppMain.chamaMenuComunicado();
        }
    }


    static void chamaRelatorioComentario(Long id) throws SQLException, ClassNotFoundException {
        List<Comentario> comments = ComentarioDAO.buscaPorId(id);
        ComentariosForm.emitirRelatorioComentario(comments);
    }

    static void chamaRelatorioUsuario() throws SQLException, ClassNotFoundException {
        List<Usuario> usuarios = UsuarioDAO.busca();
        UsuarioForm.emitirRelatorioUsuario(usuarios);
    }

    static void chamaRelatorioSetores() throws SQLException, ClassNotFoundException {
        List<Setor> setores = SetorDAO.busca();
        SetorForm.emitirRelatorioSetores(setores);
    }

    static void chamaRelatorioComunicados() throws SQLException, ClassNotFoundException {
        List<Comunicado> comunicados = ComunicadoDAO.busca();
        ComunicadosForm.emitirRelatorioComunicados(comunicados);
    }
}
