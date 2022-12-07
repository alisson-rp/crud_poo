import modal.*;
import repository.SetorDAO;

import javax.swing.*;
import java.time.LocalDate;

public class AppMainCadastro {


    public static Setor cadastrarSetor() {
        Setor novoSetor = new Setor();
        novoSetor.setNome(JOptionPane.showInputDialog(null,"Nome: ", "Cadastro de Setor", JOptionPane.QUESTION_MESSAGE));

        return novoSetor;
    }

    public static Comunicado cadastrarComunicado() {
        Comunicado novoComunicado = new Comunicado();
        novoComunicado.setDataCadastro(LocalDate.now());

        Setor setor = new Setor();
        novoComunicado.setSetor(setor);

        Usuario reponsavel = new Usuario();
        novoComunicado.setResponsavel(reponsavel);

        novoComunicado.setTitulo(JOptionPane.showInputDialog(null,"Titulo: ", "Cadastro de Setor", JOptionPane.QUESTION_MESSAGE));
        novoComunicado.setDescricao(JOptionPane.showInputDialog(null,"Descrição: ", "Cadastro de Comunicado", JOptionPane.QUESTION_MESSAGE));
        novoComunicado.setTipoUrgencia();//Fazer selects
        novoComunicado.setTipoComunicado();//Fazer selects
        return novoComunicado;
    }

}
