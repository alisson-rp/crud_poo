import modal.Comunicado;
import modal.Setor;
import repository.ComunicadoDAO;
import repository.SetorDAO;
import repository.UsuarioDAO;

import javax.swing.*;

public class AppMain {
    public static void main(String[] args) {
        ChamarMenuPrincipal();
    }

    public static void ChamarMenuPrincipal() {
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
                chamaMenuComunicado();
                break;
            case 3: //SAIR
                System.exit(0);
                break;
        }
    }

    public static void chamaMenuUsuario() {
        String[] opcoes;
        opcoes = new String[]{"Criar", "Vizualizar",  "Voltar"}; //Array com as opções de botões que ira aparecer

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
                //Usuario usuario = chamaCadastroUsuario();
                //if (usuario != null) getUsuarioDAO().salvar(usuario);
                JOptionPane.showMessageDialog(null,"Usario cadastrado com sucesso!!","Aviso", JOptionPane.ERROR_MESSAGE);
                break;
            case 1:
                //processo para update de usuario
                break;
            case 2:
                ChamarMenuPrincipal();
                break;
        }
    }

    public static void chamaMenuSetores() {
        String[] opcoes;
        opcoes = new String[]{"Criar", "Vizualizar",  "Voltar"}; //Array com as opções de botões que ira aparecer

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
                    Setor setor = AppMainCadastro.cadastrarSetor();
//                if (usuario != null) getSetorDAO().salvar(setor);
//                JOptionPane.showMessageDialog(null,"Setor cadastrado com sucesso!!","Aviso", JOptionPane.ERROR_MESSAGE);
                break;
            case 1:
                //processo para update de usuario
                break;
            case 2:
                ChamarMenuPrincipal();
                break;
        }
    }

    public static void chamaMenuComunicado() {
        String[] opcoes;
        opcoes = new String[]{"Criar", "Vizualizar", "Relatorio", "Voltar"}; //Array com as opções de botões que ira aparecer

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
                Comunicado comunicado = AppMainCadastro.cadastrarComunicado();
//                if (comunicado != null) getComunicadoDAO().salvar(usuario);
//                JOptionPane.showMessageDialog(null,"Usario criado com sucesso!!","Aviso", JOptionPane.ERROR_MESSAGE);
                break;
            case 1:
                //processo para update de usuario
                break;
            case 2:
                //processo para visualizar relatorio
                break;
            case 3:
                ChamarMenuPrincipal();
                break;
        }
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

}
