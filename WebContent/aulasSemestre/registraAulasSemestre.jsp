<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<jsp:include page="../template/head.jsp"></jsp:include>

<div class="panel panel-primary panel-lancamentoHoras">

	<div class="panel-heading text-center">Dias de Aula</div>

	<div class="panel-body ">

		<jsp:include page="/template/msg.jsp"></jsp:include>

		<div class="row">
			<div class="col-sm-6">
				<label class="form-label ages">Turma: </label> <select
					class="form-control" id="turma" name="turma" required>
					<%
						List<String> turmas = new ArrayList<>();
					
						turmas.add("2016/1 - AGES I - 127");
						turmas.add("2016/2 - AGES I - 127");
						turmas.add("2016/2 - AGES II - 127");
					
						for (String turma : turmas) {
					%>
					<option value="<%=turma%>"><%=turma%></option>
					<%
						}
					%>
				</select>
			</div>
		</div>
		<br>
		<form method="post" action="">
			<div class="form-group ">
				<div class="row">
					<div class="col-md-4 ">
						<div id="datepicker1" align="center"></div>
						<div align="center">
							<input class="btn btn-sm" type="button" id="limpar1" value="Limpar">
						</div>
					</div>
					<div class="col-md-4 ">
						<div id="datepicker2" align="center"></div>
						<div align="center">
							<input class="btn btn-sm" type="button" id="limpar2" value="Limpar">
						</div>
					</div>
					<div class="col-md-4 text-center">
						<div id="datepicker3" align="center"></div>
						<div align="center">
							<input class="btn btn-sm" type="button" id="limpar3" value="Limpar">
						</div>
					</div>
				</div>
			</div>
			<div class="form-group">
				<div class="row">
					<div class="col-md-4 ">
						<div id="datepicker4" align="center"></div>
						<div align="center">
							<input class="btn btn-sm" type="button" id="limpar4" value="Limpar">
						</div>
					</div>
					<div class="col-md-4 ">
						<div id="datepicker5" align="center"></div>
						<div align="center">
							<input class="btn btn-sm" type="button" id="limpar5" value="Limpar">
						</div>
					</div>
					<div class="col-md-4 ">
						<div class="panel-btnHoras">
							<input class="btn btn-primary btnHoras" type="submit" value="Salvar">
							<br> 
							<input type="button" class="btn btn-warning btnHoras" id="limpar" value="Limpar todos">
						</div>
					</div>
				</div>
			</div>
		</form>
	</div>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		var semestre = document.getElementById("turma").value;
		semestre = semestre.substring(5, 6);
	
		
		$(function() {
			var ano = new Date().getFullYear();
			var startDate1, endDate1, startDate2, endDate2, startDate3, endDate3, startDate4, endDate4, startDate5, endDate5, ano2;
			if(semestre == 1){
				startDate1 = "1/3/";
				endDate1 = "-04-01T00:00";
				startDate2 = "1/4/";
				endDate2 = "-05-01T00:00";
				startDate3 = "1/5/";
				endDate3 = "-06-01T00:00";
				startDate4 = "1/6/";
				endDate4 = "-07-01T00:00";
				startDate5 = "1/7/";
				endDate5 = "-08-01T00:00";
				ano2 = ano;
			} else{
				startDate1 = "1/8/";
				endDate1 = "-09-01T00:00";
				startDate2 = "1/9/";
				endDate2 = "-10-01T00:00";
				startDate3 = "1/10/";
				endDate3 = "-11-01T00:00";
				startDate4 = "1/11/";
				endDate4 = "-12-01T00:00";
				startDate5 = "1/12/";
				endDate5 = "-01-01T00:00";
				ano2 = ano + 1;
			}
			$('#datepicker1').datepicker({
				format: "d/m/yyyy",
				multidate: true,
				language: "pt-BR",
				daysOfWeekDisabled: [0, 6],
				startDate: startDate1 + ano,
				endDate: new Date(ano + endDate1),
				maxViewMode: "days"
			});
			$('#datepicker2').datepicker({
				format: "d/m/yyyy",
				multidate: true,
				language: "pt-BR",
				daysOfWeekDisabled: [0, 6],
				startDate: startDate2 + ano,
				endDate: new Date(ano + endDate2),
				maxViewMode: "days"
			});
			$('#datepicker3').datepicker({
				format: "d/m/yyyy",
				multidate: true,
				language: "pt-BR",
				daysOfWeekDisabled: [0, 6],
				startDate: startDate3 + ano,
				endDate: new Date(ano + endDate3),
				maxViewMode: "days"
			});
			$('#datepicker4').datepicker({
				format: "d/m/yyyy",
				multidate: true,
				language: "pt-BR",
				daysOfWeekDisabled: [0, 6],
				startDate: startDate4 + ano,
				endDate: new Date(ano + endDate4),
				maxViewMode: "days"
			});
			$('#datepicker5').datepicker({
				format: "d/m/yyyy",
				multidate: true,
				language: "pt-BR",
				daysOfWeekDisabled: [0, 6],
				startDate: startDate5 + ano,
				endDate: new Date(ano2 + endDate5),
				maxViewMode: "days"
			});
		});
		$("#limpar").click( function(){
				$('#datepicker1').data('datepicker').clearDates();
				$('#datepicker2').data('datepicker').clearDates();
				$('#datepicker3').data('datepicker').clearDates();
				$('#datepicker4').data('datepicker').clearDates();
				$('#datepicker5').data('datepicker').clearDates();
				
				//solu��o tempor�ria at� encontrarmos uma forma de limpar as datas do calend�rio
				//sem ir para o m�s do sistema
				$('#datepicker1').data('datepicker').clearDates();
				$('#datepicker2').data('datepicker').clearDates();
				$('#datepicker3').data('datepicker').clearDates();
				$('#datepicker4').data('datepicker').clearDates();
				$('#datepicker5').data('datepicker').clearDates();
		    }
		);
		
		$("#limpar1").click( function(){
			$('#datepicker1').data('datepicker').clearDates();
			$('#datepicker1').data('datepicker').clearDates();
	    });
		
		$("#limpar2").click( function(){
			$('#datepicker2').data('datepicker').clearDates();
			$('#datepicker2').data('datepicker').clearDates();
	    });
		
		$("#limpar3").click( function(){
			$('#datepicker3').data('datepicker').clearDates();
			$('#datepicker3').data('datepicker').clearDates();
	    });
		
		$("#limpar4").click( function(){
			$('#datepicker4').data('datepicker').clearDates();
			$('#datepicker4').data('datepicker').clearDates();
	    });
		
		$("#limpar5").click( function(){
			$('#datepicker5').data('datepicker').clearDates();
			$('#datepicker5').data('datepicker').clearDates();
	    });
	});
</script>


<jsp:include page="../template/foot.jsp"></jsp:include>