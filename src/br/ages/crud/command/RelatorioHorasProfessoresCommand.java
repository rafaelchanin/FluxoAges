package br.ages.crud.command;

import br.ages.crud.bo.AulaBO;
import br.ages.crud.bo.TimePontoDTOBO;
import br.ages.crud.bo.TurmaBO;
import br.ages.crud.bo.UsuarioBO;
import br.ages.crud.exception.NegocioException;
import br.ages.crud.exception.PersistenciaException;
import br.ages.crud.model.TimePontoDTO;
import br.ages.crud.model.Usuario;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.*;

public class RelatorioHorasProfessoresCommand implements Command {
    private String proxima;
    private UsuarioBO usuarioBO;
    private AulaBO aulaBO;
    private List<Usuario> usuarios;
    private TimePontoDTOBO timePontoDTOBO;
    private TurmaBO turmaBO;
    private List<TimePontoDTO> listaPontos;
    private HashMap<Integer,Integer> horasEsperadas;

    @Override
    public String execute(HttpServletRequest request) throws SQLException, ParseException, PersistenciaException {
        timePontoDTOBO = new TimePontoDTOBO();
        usuarioBO = new UsuarioBO();
        aulaBO = new AulaBO();
        usuarios = new ArrayList<>();
        turmaBO = new TurmaBO();
        horasEsperadas = new HashMap<>();

        proxima = "indexProfessores.jsp";
        //proxima = "aulasSemestre/registraAulasSemestre.jsp";

        String dataEntrada, dataSaida;
        Date dataEntradaDate, dataSaidaDate;

        try {


            //int weekNumber2 = data2.get(weekFields.weekOfWeekBasedYear());
            //Depois temos que tirar essa requisicao de usuário :D
            Integer idUsuario = 0;


            //Como tava antes
            listaPontos = timePontoDTOBO.listarTimes();

            LocalDate hoje = LocalDate.now();
            WeekFields weekFields = WeekFields.of(Locale.getDefault());
            int weekNumberHoje = hoje.get(weekFields.weekOfWeekBasedYear());
            int semestre = 0;
            if (hoje.getMonthValue() <=6)
                semestre = 1;
            else
                semestre = 2;
            for(TimePontoDTO time : listaPontos) {
                LocalDate primeiraAula = time.getPrimeiraAula();

                int weekNumberPrimeiraAula = primeiraAula.get(weekFields.weekOfWeekBasedYear());

                int horaEsperada = (weekNumberHoje - weekNumberPrimeiraAula + 1) * 4 * 60;

                horasEsperadas.put(time.getId(),horaEsperada);
            }

            //Sets
            request.setAttribute("listaPontos", listaPontos);
            request.setAttribute("horasEsperadas", horasEsperadas);

        } catch (NegocioException e) {
            e.printStackTrace();
            request.setAttribute("msgErro", e.getMessage());
        }

        return proxima;
    }
}
