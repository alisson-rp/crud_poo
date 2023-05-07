import modal.*;
import repository.ComentarioDAO;
import repository.ComunicadoDAO;
import repository.SetorDAO;
import repository.UsuarioDAO;

import javax.swing.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static repository.ComentarioDAO.buscaPorIdED;

public class AppMain {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        ChamarMenuPrincipal();
    }

    public static UsuarioDAO getUsuarioDAO() {
        UsuarioDAO usuarioDAODAO = new UsuarioDAO();
        return usuarioDAODAO;
    }

    public static ComentarioDAO getCommentDAO() {
        ComentarioDAO commentDAO = new ComentarioDAO();
        return commentDAO;
    }

    public static ComunicadoDAO getComunicadoDAO() {
        ComunicadoDAO comunicadoDAO = new ComunicadoDAO();
        return comunicadoDAO;
    }

    public static SetorDAO getSetorDAO() {
        SetorDAO setorDAODAO = new SetorDAO();
        return setorDAODAO;
    }

    public static void ChamarMenuPrincipal() throws SQLException, ClassNotFoundException {
        String[] opcoes;
        opcoes = new String[]{"Usuários", "Setores", "Comunicados", "Sair"};

        int resposta = JOptionPane.showOptionDialog(
                null
                , "Escolha uma opção: "
                , "MENU PRINCIPAL"
                , JOptionPane.YES_NO_OPTION
                , JOptionPane.PLAIN_MESSAGE
                , null
                , opcoes
                , "Botao 3"
        );

        switch (resposta) {
            case 0:
                chamaMenuUsuario();
                break;
            case 1:
                chamaMenuSetores();
                break;
            case 2:
                chamaMenuComunicado();
                break;
            case 3: //SAIR
                System.exit(0);
                break;
        }
    }

    public static void chamaMenuUsuario() throws SQLException, ClassNotFoundException {
        String[] opcoes;
        opcoes = new String[]{"Operações", "Visualizar", "Voltar"};

        int resposta = JOptionPane.showOptionDialog(
                null
                , "Escolha uma opção: "
                , "MENU USUÁRIOS"
                , JOptionPane.YES_NO_OPTION
                , JOptionPane.PLAIN_MESSAGE
                , null
                , opcoes
                , "Botao 3"
        );

        switch (resposta) {
            case 0:
                Usuario usuario = chamaCadastroUsuario();
                if (usuario != null) getUsuarioDAO().salvar(usuario);
                chamaMenuUsuario();
                break;
            case 1:
                AppMainCadastro.chamaRelatorioUsuario();
                break;
            case 2:
                ChamarMenuPrincipal();
                break;
        }
    }

    public static void chamaMenuSetores() throws SQLException, ClassNotFoundException {
        String[] opcoes;
        opcoes = new String[]{"Operações", "Visualizar", "Voltar"};

        int resposta = JOptionPane.showOptionDialog(
                null
                , "Escolha uma opção: "
                , "MENU SETORES"  // Titulo
                , JOptionPane.YES_NO_OPTION
                , JOptionPane.PLAIN_MESSAGE
                , null
                , opcoes
                , "Botao 3"
        );

        switch (resposta) {
            case 0:
                Setor setor = chamaCadastroSetor();
                if (setor != null) getSetorDAO().salvar(setor);
                chamaMenuSetores();
                break;
            case 1:
                AppMainCadastro.chamaRelatorioSetores();
                break;
            case 2:
                ChamarMenuPrincipal();
                break;
        }
    }

    public static void chamaMenuComunicado() throws SQLException, ClassNotFoundException {
        String[] opcoes;
        opcoes = new String[]{"Operações", "Visualizar", "Relatório", "Voltar"};

        int resposta = JOptionPane.showOptionDialog(
                null
                , "Escolha uma opção: "
                , "MENU COMUNICADOS"  // Titulo
                , JOptionPane.YES_NO_OPTION
                , JOptionPane.PLAIN_MESSAGE
                , null
                , opcoes
                , "Botao 3"
        );

        switch (resposta) {
            case 0:
                Comunicado comunicado = chamaCadastroComunicado();
                if (comunicado != null) getComunicadoDAO().salvar(comunicado);
                chamaMenuComunicado();
                break;
            case 1:
                try {
                    Comunicado comunicado1 = selecaoDeComunicado();
                    AppMainCadastro.vizulizarComunicado(comunicado1);
                    break;
                } catch (Exception e) {
                    chamaMenuComunicado();
                }

            case 2:
                AppMainCadastro.chamaRelatorioComunicados();
                break;
            case 3:
                ChamarMenuPrincipal();
                break;
        }
    }

    private static Integer chamaOpcaoCrud() {
        String[] opcao = {"Inserção", "Alteração", "Exclusão", "Voltar"};
        int tipoOpcao = JOptionPane.showOptionDialog(null, "Escolha uma opção: ",
                "OPERAÇÕES ",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcao, opcao[0]);
        return tipoOpcao;
    }

    private static Usuario chamaCadastroUsuario() throws SQLException, ClassNotFoundException {
        Integer opcaoCrud = chamaOpcaoCrud();
        Usuario usuario = null;
        switch (opcaoCrud) {
            case 0: //Inserção
                usuario = cadastroUsuario();
                if (usuario != null) {
                    JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!", "AVISO", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "ERRO! Tente novamente!", "AVISO", JOptionPane.ERROR_MESSAGE);
                    chamaOpcaoCrud();
                }
                break;
            case 1: //Alteração
                try {
                    usuario = selecaoDeUsuario();
                    usuario = editaUsuario(usuario);
                    if (usuario != null) {
                        JOptionPane.showMessageDialog(null, "Usuário alterado com sucesso!", "AVISO", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "ERRO! Tente novamente!", "AVISO", JOptionPane.ERROR_MESSAGE);
                        chamaCadastroUsuario();
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Não há usuários cadastrados!", "AVISO", JOptionPane.INFORMATION_MESSAGE);
                    chamaCadastroUsuario();
                }
                break;
            case 2: //Exclusão
                try {
                    usuario = selecaoDeUsuario();
                    getUsuarioDAO().remover(usuario);
                    usuario = null;
                    JOptionPane.showMessageDialog(null, "Usuário excluido com sucesso!", "AVISO", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Não há cadastros para excluir!", "AVISO", JOptionPane.INFORMATION_MESSAGE);
                    chamaCadastroUsuario();
                }
                break;
            default:
                ChamarMenuPrincipal();
                break;
        }
        return usuario;
    }

    private static Usuario cadastroUsuario() throws SQLException, ClassNotFoundException {
        String usu = JOptionPane.showInputDialog(null, "Digite o nome do usuário: ");
        String email = JOptionPane.showInputDialog(null, "Digite o endereço de e-mail: ");
        String setor = (String) chamaSelecaoSetor();
        try {
            if (usu.length() > 0 && email.length() > 0) {
                Usuario usuario = new Usuario();
                usuario.setUsuario(usu);
                usuario.setEmail(email);
                usuario.setSetor(getSetorDAO().findSetorByNome(setor));
                return usuario;
            }
            return null;
        } catch (Exception e) {
            chamaCadastroSetor();
        }
        return null;
    }

    private static Object chamaSelecaoUsuario() {
        Object[] selectionValues = getUsuarioDAO().findUsuariosInArray();
        String initialSelection = (String) selectionValues[0];
        Object selection = JOptionPane.showInputDialog(null, "Selecione o usuário: ",
                "SELEÇÃO DE USUÁRIO", JOptionPane.QUESTION_MESSAGE, null, selectionValues, initialSelection);
        return selection;
    }

    private static Usuario selecaoDeUsuario() {
        Object[] selectionValues = getUsuarioDAO().findUsuariosInArray();
        String initialSelection = (String) selectionValues[0];
        Object selection = JOptionPane.showInputDialog(null, "Selecione o usuário: ",
                "USUÁRIOS", JOptionPane.QUESTION_MESSAGE, null, selectionValues, initialSelection);
        List<Usuario> usuarios = getUsuarioDAO().buscarPorNome((String) selection);
        return usuarios.get(0);
    }

    private static Usuario editaUsuario(Usuario usuarioEdit) throws SQLException, ClassNotFoundException {
        String usu = JOptionPane.showInputDialog(null, "Digite o nome do usuário: ", usuarioEdit.getUsuario());
        String email = JOptionPane.showInputDialog(null, "Digite o endereço de e-mail: ", usuarioEdit.getEmail());
        String setor = (String) chamaSelecaoSetor();
        try {
            if (usu.length() > 0 && email.length() > 0) {
                Usuario usuario = new Usuario();
                usuario.setUsuario(usu);
                usuario.setEmail(email);
                usuario.setSetor(getSetorDAO().findSetorByNome(setor));
                usuario.setId(usuarioEdit.getId());
                return usuario;
            }
            return null;
        } catch (Exception e) {
            chamaCadastroSetor();
        }
        return null;
    }

    private static Setor chamaCadastroSetor() throws SQLException, ClassNotFoundException {
        Integer opcaoCrud = chamaOpcaoCrud();
        Setor setor = null;
        switch (opcaoCrud) {
            case 0: //Inserção
                setor = cadastroSetor();
                if (setor != null) {
                    JOptionPane.showMessageDialog(null, "Setor cadastrado com sucesso!", "AVISO", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "ERRO! Tente novamente!", "AVISO", JOptionPane.ERROR_MESSAGE);
                    chamaOpcaoCrud();
                }
                break;
            case 1: //Alteração
                try {
                    setor = selecaoDeSetor();
                    setor = editaSetor(setor);
                    if (setor != null) {
                        JOptionPane.showMessageDialog(null, "Setor alterado com sucesso!", "AVISO", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "ERRO! Tente novamente!", "AVISO", JOptionPane.ERROR_MESSAGE);
                        chamaCadastroSetor();
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Não há setores cadastrados", "AVISO", JOptionPane.INFORMATION_MESSAGE);
                    chamaCadastroSetor();
                }
                break;
            case 2: //Exclusão
                try {
                    setor = selecaoDeSetor();
                    getSetorDAO().remover(setor);
                    setor = null;
                    JOptionPane.showMessageDialog(null, "Setor excluído com sucesso!", "AVISO", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Não há cadastros para excluir!", "AVISO", JOptionPane.INFORMATION_MESSAGE);
                    chamaCadastroSetor();
                }
                break;
            default:
                ChamarMenuPrincipal();
                break;
        }
        return setor;
    }

    private static Setor cadastroSetor() throws SQLException, ClassNotFoundException {
        String nome = JOptionPane.showInputDialog(null, "Digite o nome do setor: ");
        try {
            if (nome.length() > 0) {
                Setor setor = new Setor();
                setor.setNome(nome);
                return setor;
            }
            return null;
        } catch (Exception e) {
            chamaCadastroSetor();
        }
        return null;
    }

    private static Object chamaSelecaoSetor() {
        Object[] selectionValues = getSetorDAO().findSetoresInArray();
        String initialSelection = (String) selectionValues[0];
        Object selection = JOptionPane.showInputDialog(null, "Selecione o setor: ",
                "SELEÇÃO DE SETOR", JOptionPane.QUESTION_MESSAGE, null, selectionValues, initialSelection);
        return selection;
    }

    private static Setor selecaoDeSetor() {
        Object[] selectionValues = getSetorDAO().findSetoresInArray();
        String initialSelection = (String) selectionValues[0];
        Object selection = JOptionPane.showInputDialog(null, "Selecione o setor: ",
                "SETORES", JOptionPane.QUESTION_MESSAGE, null, selectionValues, initialSelection);
        List<Setor> setores = getSetorDAO().buscarPorNome((String) selection);
        return setores.get(0);
    }

    private static Setor editaSetor(Setor setorEdit) throws SQLException, ClassNotFoundException {
        String set = JOptionPane.showInputDialog(null, "Digite o nome do setor: ", setorEdit.getNome());
        try {
            if (set.length() > 0) {
                Setor setor = new Setor();
                setor.setNome(set);
                setor.setId(setorEdit.getId());
                return setor;
            }
            return null;
        } catch (Exception e) {
            chamaCadastroSetor();
        }
        return null;
    }

    private static Comunicado chamaCadastroComunicado() throws SQLException, ClassNotFoundException {
        Integer opcaoCrud = chamaOpcaoCrud();
        Comunicado comunicado = null;
        switch (opcaoCrud) {
            case 0: //Inserção
                comunicado = cadastroComunicado();
                if (comunicado != null) {
                    JOptionPane.showMessageDialog(null, "Comunicado cadastrado com sucesso!", "AVISO", JOptionPane.INFORMATION_MESSAGE);
                    chamaCadastroComunicado();
                } else {
                    JOptionPane.showMessageDialog(null, "ERRO! Tente novamente!", "AVISO", JOptionPane.ERROR_MESSAGE);
                    chamaCadastroComunicado();
                }
                break;
            case 1: //Alteração
                try {
                    comunicado = selecaoDeComunicado();
                    comunicado = editaComunicado(comunicado);
                    if (comunicado != null) {
                        JOptionPane.showMessageDialog(null, "Comunicado alterado com sucesso!", "AVISO", JOptionPane.INFORMATION_MESSAGE);
                        chamaCadastroComunicado();
                    } else {
                        JOptionPane.showMessageDialog(null, "ERRO! Tente novamente!", "AVISO", JOptionPane.ERROR_MESSAGE);
                        chamaCadastroComunicado();
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Não há comunicados cadastrados!", "AVISO", JOptionPane.INFORMATION_MESSAGE);
                    chamaCadastroComunicado();
                }
                break;
            case 2: //Exclusão
                try {
                    comunicado = selecaoDeComunicado();
                    getComunicadoDAO().remover(comunicado);
                    comunicado = null;
                    JOptionPane.showMessageDialog(null, "Comunicado excluído com sucesso!!", "AVISO", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Não há cadastros para excluir", "AVISO", JOptionPane.INFORMATION_MESSAGE);
                    chamaCadastroComunicado();
                }
                break;
            default:
                ChamarMenuPrincipal();
                break;
        }
        return comunicado;
    }

    private static Comunicado cadastroComunicado() throws SQLException, ClassNotFoundException {
        String datacrua = JOptionPane.showInputDialog(null, "Digite a data do cadastro: ");
        String setor = (String) chamaSelecaoSetor();
        String resp = (String) chamaSelecaoUsuario();
        String titulo = JOptionPane.showInputDialog(null, "Digite o título: ");
        String desc = JOptionPane.showInputDialog(null, "Digite a descrição: ");
        Integer curtida = 0;
        String TNU = String.valueOf(chamaSelecaoTipoNU());
        String TComunicado = String.valueOf(chamaSelecaoTipoComunicado());

        try {
            if (datacrua.length() > 0 && titulo.length() > 0) {
                Comunicado comunicado = new Comunicado();
                java.sql.Date data = java.sql.Date.valueOf(datacrua);
                comunicado.setDataCadastro(data.toLocalDate());
                comunicado.setSetor(getSetorDAO().findSetorByNome(setor));
                comunicado.setResponsavel(getUsuarioDAO().findUsuarioByNome(resp));
                comunicado.setTitulo(titulo);
                comunicado.setDescricao(desc);
                comunicado.setQtdCurtidas(curtida);
                comunicado.setTipoUrgencia(TipoNoticiaUrgencia.valueOf(TNU));
                comunicado.setTipoComunicado(TipoComunicado.valueOf(TComunicado));
                return comunicado;
            }
            return null;
        } catch (Exception e) {
            chamaCadastroComunicado();
        }
        return null;
    }

    private static Object chamaSelecaoComunicado() {
        Object[] selectionValues = getComunicadoDAO().findComunicadoInArray();
        String initialSelection = (String) selectionValues[0];
        Object selection = JOptionPane.showInputDialog(null, "Selecione o comunicado: ",
                "SELEÇÃO DE COMUNICADO", JOptionPane.QUESTION_MESSAGE, null, selectionValues, initialSelection);
        return selection;
    }

    private static Comunicado selecaoDeComunicado() {
        Object[] selectionValues = getComunicadoDAO().findComunicadoInArray();
        String initialSelection = (String) selectionValues[0];
        Object selection = JOptionPane.showInputDialog(null, "Selecione o comunicado: ",
                "COMUNICADOS", JOptionPane.QUESTION_MESSAGE, null, selectionValues, initialSelection);
        List<Comunicado> comunicados = getComunicadoDAO().buscarPorNome((String) selection);
        return comunicados.get(0);
    }

    private static Comunicado editaComunicado(Comunicado comunicadoEdit) throws SQLException, ClassNotFoundException {

        String data = JOptionPane.showInputDialog(null, "Digite a data do cadastro: ", comunicadoEdit.getDataCadastro());
        String setor = (String) chamaSelecaoSetor();
        String resp = (String) chamaSelecaoUsuario();
        String titulo = JOptionPane.showInputDialog(null, "Digite o título: ", comunicadoEdit.getTitulo());
        String desc = JOptionPane.showInputDialog(null, "Digite a descrição: ", comunicadoEdit.getDescricao());
        Integer curtida = comunicadoEdit.getQtdCurtidas();
        String TNU = String.valueOf(chamaSelecaoTipoNU());
        String TComunicado = String.valueOf(chamaSelecaoTipoComunicado());

        try {
            if (data.length() > 0 && titulo.length() > 0) {
                Comunicado comunicado = new Comunicado();
                comunicado.setDataCadastro(LocalDate.parse(data));
                comunicado.setSetor(getSetorDAO().findSetorByNome(setor));
                comunicado.setResponsavel(getUsuarioDAO().findUsuarioByNome(resp));
                comunicado.setTitulo(titulo);
                comunicado.setDescricao(desc);
                comunicado.setQtdCurtidas(curtida);
                comunicado.setTipoUrgencia(TipoNoticiaUrgencia.valueOf(TNU));
                comunicado.setTipoComunicado(TipoComunicado.valueOf(TComunicado));
                comunicado.setId(comunicadoEdit.getId());
                return comunicado;
            }
            return null;
        } catch (Exception e) {
            chamaCadastroComunicado();
        }
        return null;
    }

    private static TipoComunicado chamaSelecaoTipoComunicado() {
        String codigo = JOptionPane.showInputDialog(
                null,
                "Tipo Comunicado: \n \n 1.  Notícia \n 2.  Comemorativo \n 3.  Avisos \n " +
                        "4.  Entretenimento",
                "COMUNICADO",
                JOptionPane.QUESTION_MESSAGE);

        if (codigo.equals("1")) {
            return TipoComunicado.NOTICIA;
        } else if (codigo.equals("2")) {
            return TipoComunicado.COMEMORATIVO;
        } else if (codigo.equals("3")) {
            return TipoComunicado.AVISOS;
        } else if (codigo.equals("4")) {
            return TipoComunicado.ENTRETERIMENTO;
        } else {
            JOptionPane.showMessageDialog(null, "Código do comunicado inválido!", "ERRO", JOptionPane.ERROR_MESSAGE);
            chamaSelecaoTipoComunicado();
        }
        return null;
    }

    private static TipoNoticiaUrgencia chamaSelecaoTipoNU() {
        String codigo = JOptionPane.showInputDialog(
                null,
                "Tipo Comunicado: \n\n 1.Baixa \n 2.Média \n 3.Alta ",
                "COMUNICADO",
                JOptionPane.QUESTION_MESSAGE);

        if (codigo.equals("1")) {
            return TipoNoticiaUrgencia.BAIXA;
        } else if (codigo.equals("2")) {
            return TipoNoticiaUrgencia.MEDIA;
        } else if (codigo.equals("3")) {
            return TipoNoticiaUrgencia.ALTA;
        } else {
            JOptionPane.showMessageDialog(null, "Tipo de urgência inválido!!", "ERRO", JOptionPane.ERROR_MESSAGE);
            chamaSelecaoTipoNU();
        }
        return null;
    }

    public static Comentario chamaCastroComentario(Comunicado comunicado) throws SQLException, ClassNotFoundException {
        Integer opcaoCrud = chamaOpcaoCrud();
        Comentario comentario = null;

        switch (opcaoCrud) {
            case 0: //Inserção
                comentario = cadastroComentario(comunicado);
                if (comentario != null) {
                    JOptionPane.showMessageDialog(null, "Comentário cadastrado com sucesso!", "AVISO", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "ERRO! Tente Novamente!", "AVISO", JOptionPane.ERROR_MESSAGE);
                    chamaOpcaoCrud();
                }
                break;
            case 1: //Alteração
                try {
                    long coment = Integer.parseInt(JOptionPane.showInputDialog(null, "Digite o número do comentário: "));
                    comentario = buscaPorIdED(coment);
                    comentario = editaComment(comentario);
                    if (comentario != null) {
                        getCommentDAO().salvar(comentario);
                        JOptionPane.showMessageDialog(null, "Comentário alterado com sucesso!", "AVISO", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "ERRO! Tente Novamente!", "AVISO", JOptionPane.ERROR_MESSAGE);
                        chamaCastroComentario(comunicado);
                    }

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Não há comentários cadastrados", "AVISO", JOptionPane.INFORMATION_MESSAGE);
                    chamaCastroComentario(comunicado);
                }
                break;
            case 2: //Exclusão
                try {
                    long coment = Integer.parseInt(JOptionPane.showInputDialog(null, "Digite o número do comentário: "));
                    comentario = buscaPorIdED(coment);
                    getCommentDAO().remover(comentario);
                    comentario = null;
                    JOptionPane.showMessageDialog(null, "Comentário excluído com sucesso!", "AVISO", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Não há cadastros para excluir!", "AVISO", JOptionPane.INFORMATION_MESSAGE);
                    chamaCastroComentario(comunicado);
                }
                break;
            default:
                ChamarMenuPrincipal();
                break;
        }
        return comentario;
    }

    private static Comentario cadastroComentario(Comunicado comunicado) throws SQLException, ClassNotFoundException {
        String comentarioString = JOptionPane.showInputDialog(null, "Digite o comentário: ");

        try {
            if (comentarioString.length() > 0) {
                Comentario comentario1 = new Comentario();
                comentario1.setComunicado(comunicado);
                comentario1.setUsuario(selecaoDeUsuario());
                comentario1.setComentario(comentarioString);
                comentario1.setDataComentario(LocalDate.now());
                return comentario1;
            }
            return null;
        } catch (Exception e) {
            chamaCastroComentario(comunicado);
        }
        return null;
    }

    private static Comentario editaComment(Comentario comentarioEdit) {
        String comentarioString = JOptionPane.showInputDialog(null, "Digite o comentário: ", comentarioEdit.getComentario());

        try {
            if (comentarioString.length() > 0) {
                Comentario comentario1 = new Comentario();
                comentario1.setComentario(comentarioString);
                comentario1.setId(comentarioEdit.getId());

                return comentario1;
            }
            return null;
        } catch (Exception e) {
            editaComment(comentarioEdit);
        }
        return null;
    }
}
