import modal.Comunicado;
import modal.Setor;
import modal.Usuario;
import repository.ComunicadoDAO;
import repository.SetorDAO;
import repository.UsuarioDAO;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

public class AppMain {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        ChamarMenuPrincipal();
    }
    public static UsuarioDAO getUsuarioDAO() {
        UsuarioDAO usuarioDAODAO = new UsuarioDAO();
        return usuarioDAODAO;
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
        opcoes = new String[]{"Usuarios", "Setores", "Comunicados", "Sair"}; //Array com as opções de botões que ira aparecer

        int resposta = JOptionPane.showOptionDialog(
                null
                , "Escolha uma opção:" // Mensagem
                , "Menu"  // Titulo
                , JOptionPane.YES_NO_OPTION // Estilo da caixinha que ira aparecer
                , JOptionPane.PLAIN_MESSAGE
                , null // Icone. Você pode usar uma imagem se quiser, basta carrega-la e passar como referência/Endereço da pasta
                , opcoes// Array de strings com os valores de cada botão.
                , "Botao 3"  // Label do botão Default
        );

        switch (resposta) {
            case 0:
                chamaMenuUsuario();
                break;
            case 1:
                chamaMenuSetores();
                break;
            case 2:
                //chamaMenuComunicado();
                break;
            case 3: //SAIR
                System.exit(0);
                break;
        }
    }

    public static void chamaMenuUsuario() throws SQLException, ClassNotFoundException {
        String[] opcoes;
        opcoes = new String[]{"Operações", "Vizualizar",  "Voltar"}; //Array com as opções de botões que ira aparecer

        int resposta = JOptionPane.showOptionDialog(
                null
                , "Escolha uma opção:" // Mensagem
                , "Menu"  // Titulo
                , JOptionPane.YES_NO_OPTION // Estilo da caixinha que ira aparecer
                , JOptionPane.PLAIN_MESSAGE
                , null // Icone. Você pode usar uma imagem se quiser, basta carrega-la e passar como referência/Endereço da pasta
                , opcoes// Array de strings com os valores de cada botão.
                , "Botao 3"  // Label do botão Default
        );

        switch (resposta) {
            case 0:
                Usuario usuario = chamaCadastroUsuario();
                if (usuario != null) getUsuarioDAO().salvar(usuario);
                chamaMenuUsuario();
                break;
            case 1:
                //processo para update de usuario
                break;
            case 2:
                ChamarMenuPrincipal();
                break;
        }
    }

    public static void chamaMenuSetores() throws SQLException, ClassNotFoundException {
        String[] opcoes;
        opcoes = new String[]{"Operações", "Vizualizar",  "Voltar"}; //Array com as opções de botões que ira aparecer

        int resposta = JOptionPane.showOptionDialog(
                null
                , "Escolha uma opção:" // Mensagem
                , "Menu"  // Titulo
                , JOptionPane.YES_NO_OPTION // Estilo da caixinha que ira aparecer
                , JOptionPane.PLAIN_MESSAGE
                , null // Icone. Você pode usar uma imagem se quiser, basta carrega-la e passar como referência/Endereço da pasta
                , opcoes// Array de strings com os valores de cada botão.
                , "Botao 3"  // Label do botão Default
        );

        switch (resposta) {
            case 0:
                Setor setor = chamaCadastroSetor();
                if (setor != null) getSetorDAO().salvar(setor);
                chamaMenuSetores();
                break;
            case 1:
                //processo para update de usuario
                break;
            case 2:
                ChamarMenuPrincipal();
                break;
        }
    }

//    public static void chamaMenuComunicado() throws SQLException, ClassNotFoundException {
//        String[] opcoes;
//        opcoes = new String[]{"Operações", "Vizualizar", "Relatorio", "Voltar"}; //Array com as opções de botões que ira aparecer
//
//        int resposta = JOptionPane.showOptionDialog(
//                null
//                , "Escolha uma opção:" // Mensagem
//                , "Menu"  // Titulo
//                , JOptionPane.YES_NO_OPTION // Estilo da caixinha que ira aparecer
//                , JOptionPane.PLAIN_MESSAGE
//                , null // Icone. Você pode usar uma imagem se quiser, basta carrega-la e passar como referência/Endereço da pasta
//                , opcoes// Array de strings com os valores de cada botão.
//                , "Botao 3"  // Label do botão Default
//        );
//
//        switch (resposta) {
//            case 0:
//                Comunicado comunicado = AppMainCadastro.cadastrarComunicado();
////                if (comunicado != null) getComunicadoDAO().salvar(usuario);
////                JOptionPane.showMessageDialog(null,"Usario criado com sucesso!!","Aviso", JOptionPane.ERROR_MESSAGE);
//                break;
//            case 1:
//                //processo para update de usuario
//                break;
//            case 2:
//                //processo para visualizar relatorio
//                break;
//            case 3:
//                ChamarMenuPrincipal();
//                break;
//        }
//    }



    private static Integer chamaOpcaoCrud() {
        String[] opcao = {"Inserção", "Alteração", "Exclusão","Voltar"};
        int tipoOpcao = JOptionPane.showOptionDialog(null, "Escolha uma opção:",
                "Operação no cadastro: ",
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
                    JOptionPane.showMessageDialog(null,"Usuario cadastrado com sucesso!!","Aviso", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null,"A campos vazios !!!!","Aviso", JOptionPane.ERROR_MESSAGE);
                    chamaOpcaoCrud();
                }
                break;
            case 1: //Alteração
                try {
                    usuario = selecaoDeUsuario();
                    usuario = editaUsuario(usuario);
                    if (usuario != null) {
                        JOptionPane.showMessageDialog(null,"Usuario alterado com sucesso!!","Aviso", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null,"Campo vazio !!!!","Aviso", JOptionPane.ERROR_MESSAGE);
                        chamaCadastroUsuario();
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,"Não temos usuarios cadastrados","Aviso", JOptionPane.INFORMATION_MESSAGE);
                    chamaCadastroUsuario();
                }
                break;
            case 2: //Exclusão
                try {
                    usuario = selecaoDeUsuario();
                    getUsuarioDAO().remover(usuario);
                    usuario = null;
                    JOptionPane.showMessageDialog(null,"Usuario excluido com sucesso!!","Aviso", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,"Não Há nada para excluir","Aviso", JOptionPane.INFORMATION_MESSAGE);
                    chamaCadastroUsuario();
                }
                break;
            default:
                ChamarMenuPrincipal();
                break;
        }
        return usuario;
    }

    private static Usuario cadastroUsuario() {
        String usu = JOptionPane.showInputDialog(null, "Digite o nome do usuario: ");
        String email = JOptionPane.showInputDialog(null, "Digite o endereço de email: ");
        String setor = (String) chamaSelecaoSetor();

        if (usu.length() > 0 && email.length() > 0) {
            Usuario usuario = new Usuario();
            usuario.setUsuario(usu);
            usuario.setEmail(email);
            usuario.setSetor(getSetorDAO().findSetorByNome(setor));
            return usuario;
        }
        return null;
    }

    private static Object chamaSelecaoSetor() {
        Object[] selectionValues = getSetorDAO().findSetoresInArray();
        String initialSelection = (String) selectionValues[0];
        Object selection = JOptionPane.showInputDialog(null, "Selecione o setor: ",
                "cadastor de usuario", JOptionPane.QUESTION_MESSAGE, null, selectionValues, initialSelection);
        return selection;
    }

    private static Usuario selecaoDeUsuario() {
        Object[] selectionValuesUsuario = getUsuarioDAO().findUsuariosInArray();
        String initialSelectionUsuario = (String) selectionValuesUsuario[0];
        Object selectionUsuario = JOptionPane.showInputDialog(null, "Selecione o usuario?",
                "Usuario", JOptionPane.QUESTION_MESSAGE, null, selectionValuesUsuario, initialSelectionUsuario);
        List<Usuario> usuarios = getUsuarioDAO().buscarPorNome((String) selectionUsuario);
        return usuarios.get(0);
    }

    private static Usuario editaUsuario(Usuario usuarioEdit) {
        String usu = JOptionPane.showInputDialog(null, "Digite o nome do usuario: ",usuarioEdit.getUsuario());
        String email = JOptionPane.showInputDialog(null, "Digite o endereço de email: ",usuarioEdit.getEmail());
        String setor = (String) chamaSelecaoSetor();

        if (usu.length() > 0 && email.length() > 0) {
            Usuario usuario = new Usuario();
            usuario.setUsuario(usu);
            usuario.setEmail(email);
            usuario.setSetor(getSetorDAO().findSetorByNome(setor));
            usuario.setId(usuarioEdit.getId());
            return usuario;
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
                    JOptionPane.showMessageDialog(null,"Setor cadastrado com sucesso!!","Aviso", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null,"Campo vazio !!!!","Aviso", JOptionPane.ERROR_MESSAGE);
                    chamaOpcaoCrud();
                }
                break;
            case 1: //Alteração
                try {
                    setor = selecaoDeSetor();
                    setor = editaSetor(setor);
                    if (setor != null) {
                        JOptionPane.showMessageDialog(null,"Setor alterado com sucesso!!","Aviso", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null,"Campo vazio !!!!","Aviso", JOptionPane.ERROR_MESSAGE);
                        chamaCadastroSetor();
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,"Não temos setores cadastrados","Aviso", JOptionPane.INFORMATION_MESSAGE);
                    chamaCadastroSetor();
                }
                break;
           case 2: //Exclusão
                try {
                    setor = selecaoDeSetor();
                    getSetorDAO().remover(setor);
                    setor = null;
                    JOptionPane.showMessageDialog(null,"Setor excluido com sucesso!!","Aviso", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,"Não Há nada para excluir","Aviso", JOptionPane.INFORMATION_MESSAGE);
                    chamaCadastroSetor();
                }
                break;
            default:
                ChamarMenuPrincipal();
                break;
        }
        return setor;
    }

    private static Setor cadastroSetor() {
        String nome = JOptionPane.showInputDialog(null, "Digite o nome do setor: ");

        if (nome.length() > 0) {
            Setor setor = new Setor();
            setor.setNome(nome);
            return setor;
        }
        return null;
    }

    private static Setor selecaoDeSetor() {
        Object[] selectionValuesSetor = getSetorDAO().findSetoresInArray();
        String initialSelectionUsuario = (String) selectionValuesSetor[0];
        Object selectionUsuario = JOptionPane.showInputDialog(null, "Selecione o setor?",
                "Setores", JOptionPane.QUESTION_MESSAGE, null, selectionValuesSetor, initialSelectionUsuario);
        List<Setor> setores = getSetorDAO().buscarPorNome((String) selectionUsuario);
        return setores.get(0);
    }

    private static Setor editaSetor(Setor setorEdit) {
        String set = JOptionPane.showInputDialog(null, "Digite o nome do usuario: ",setorEdit.getNome());

        if (set.length() > 0) {
            Setor setor = new Setor();
            setor.setNome(set);
            return setor;
        }
        return null;
    }
}
