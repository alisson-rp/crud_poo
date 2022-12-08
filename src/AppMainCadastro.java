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
import java.time.LocalDate;
import java.util.List;

public class AppMainCadastro {


    public static Setor cadastrarSetor() {
        Setor novoSetor = new Setor();
        novoSetor.setNome(JOptionPane.showInputDialog(null, "Nome: ", "Cadastro de Setor", JOptionPane.QUESTION_MESSAGE));

        return novoSetor;
    }

    public static Comunicado cadastrarComunicado() {
        Comunicado novoComunicado = new Comunicado();
        novoComunicado.setDataCadastro(LocalDate.now());

        Setor setor = new Setor();
        novoComunicado.setSetor(setor);

        Usuario reponsavel = new Usuario();
        novoComunicado.setResponsavel(reponsavel);

        novoComunicado.setTitulo(JOptionPane.showInputDialog(null, "Titulo: ", "Cadastro de Setor", JOptionPane.QUESTION_MESSAGE));
        novoComunicado.setDescricao(JOptionPane.showInputDialog(null, "Descrição: ", "Cadastro de Comunicado", JOptionPane.QUESTION_MESSAGE));
        //novoComunicado.setTipoUrgencia();//Fazer selects
        //novoComunicado.setTipoComunicado();//Fazer selects
        return novoComunicado;
    }

    public static void vizulizarComunicado(Comunicado comunicado) throws SQLException, ClassNotFoundException {
        String texto = "";
        texto = "############################ " + comunicado.getTitulo() + " ############################" +
                "\n Por:" + comunicado.getResponsavel().getUsuario() +
                "\n Setor: " + comunicado.getSetor().getNome() +
                "\n Tema: " + comunicado.getTipoComunicado() +
                "\n Data: " + comunicado.getDataCadastro() +
                "\n \n \n Urgencia/Prioridade: " + comunicado.getTipoUrgencia() +
                "\n Comunicado : " + comunicado.getDescricao() +
                "\n \n \n \n Curtidas: " + comunicado.getQtdCurtidas() +
                "\n #####################################################################################";

        String[] opcoes;
        opcoes = new String[]{"Comentar", "Curtir", "Ver comentarios", "Voltar"};

        //JOptionPane.showOptionDialog(null, texto,"",JOptionPane.INFORMATION_MESSAGE);

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
                Comentario comment = AppMain.chamaCastroComentario(comunicado);
                if (comment != null) AppMain.getCommentDAO().salvar(comment);
                vizulizarComunicado(comunicado);
                break;
            case 1:
                ComunicadoDAO.curtiComentario(comunicado.getId());
                vizulizarComunicado(comunicado);
                break;
            case 2:
                chamaRelatorioComentario(comunicado.getId());
                break;
            case 3:
                AppMain.ChamarMenuPrincipal();
                break;
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
