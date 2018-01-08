package br.ages.crud.command;

import br.ages.crud.bo.*;
import br.ages.crud.exception.NegocioException;
import br.ages.crud.exception.PersistenciaException;
import br.ages.crud.model.AlunoPonto;
import br.ages.crud.model.ResumoPonto;
import br.ages.crud.model.TimePontoDTO;
import br.ages.crud.model.Usuario;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.*;

public class HorasAlunoCommand implements Command {
    private String proxima;
    private UsuarioBO usuarioBO;
    private AulaBO aulaBO;
    private List<Usuario> usuarios;
    private AlunoPonto alunoPonto;
    private AlunoPontoBO alunoPontoBO;
    private TurmaBO turmaBO;
    private List<AlunoPonto> listaPontos;
    private HashMap<Integer,Integer> horasEsperadas;

    @Override
    public String execute(HttpServletRequest request) throws SQLException, ParseException, PersistenciaException {
        alunoPonto = new AlunoPonto();
        usuarioBO = new UsuarioBO();
        aulaBO = new AulaBO();
        usuarios = new ArrayList<>();
        turmaBO = new TurmaBO();
        horasEsperadas = new HashMap<>();
        alunoPontoBO = new AlunoPontoBO();

        proxima = "aluno/horasAluno.jsp";
        //proxima = "aulasSemestre/registraAulasSemestre.jsp";

        String dataEntrada, dataSaida;
        Date dataEntradaDate, dataSaidaDate;

        try {


            //int weekNumber2 = data2.get(weekFields.weekOfWeekBasedYear());
            //Depois temos que tirar essa requisicao de usuário :D
            Integer idUsuario = 0;
            Usuario aluno = (Usuario) request.getSession().getAttribute("usuarioSessao");

            //Como tava antes
            listaPontos = alunoPontoBO.listarPonto(aluno.getIdUsuario());

            LocalDate hoje = LocalDate.now();
            WeekFields weekFields = WeekFields.of(Locale.getDefault());
            int weekNumberHoje = hoje.get(weekFields.weekOfWeekBasedYear());
            int semestre = 0;
            if (hoje.getMonthValue() <=6)
                semestre = 1;
            else
                semestre = 2;

            LocalDate primeiraAula = listaPontos.get(0).getPrimeiraAula();
            int weekNumberPrimeiraAula = primeiraAula.get(weekFields.weekOfWeekBasedYear());
            int horaEsperada = (weekNumberHoje - weekNumberPrimeiraAula + 1) * 4 * 60;
            if (horaEsperada > 3600) horaEsperada = 3600 ;


            //Sets
            request.setAttribute("listaPonto", listaPontos);
            request.setAttribute("horaEsperada", horaEsperada);

        } catch (NegocioException e) {
            e.printStackTrace();
            request.setAttribute("msgErro", e.getMessage());
        }

        return proxima;
    }
}
