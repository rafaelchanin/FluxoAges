package br.ages.crud.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

import br.ages.crud.exception.NegocioException;
import br.ages.crud.exception.PersistenciaException;
import br.ages.crud.model.Aula;
import br.ages.crud.model.Periodo;
import br.ages.crud.util.ConexaoUtil;

public class PeriodoDAO {
 private Periodo periodo;
 
 public PeriodoDAO() {
	 
 }
 
 public ArrayList<Periodo> listaPeriodo() throws NegocioException, PersistenciaException, SQLException {
		Connection conexao = null;
		ArrayList<Periodo> periodos = new ArrayList<Periodo>();

		try {
			conexao = ConexaoUtil.getConexao();

			StringBuilder sql = new StringBuilder();
			sql.append(" select id_periodo, horario, hora_inicio, hora_fim, tempo");
			sql.append(" from tb_periodo ");
			
			PreparedStatement statement = conexao.prepareStatement(sql.toString());
			
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Periodo periodo = new Periodo();
				periodo.setId(resultSet.getInt("id_periodo"));
				periodo.setHorario(resultSet.getString("horario"));
				Time horarioInicio = resultSet.getTime("hora_inicio");
				periodo.setHoraInicio(horarioInicio.toString());
				Time horarioFim = resultSet.getTime("hora_fim");
				periodo.setHoraFim(horarioFim.toString());
				periodo.setTempo(resultSet.getInt("tempo"));
				periodos.add(periodo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			conexao.close();
		}

		return periodos;
	}

}
