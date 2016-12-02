<jsp:include page="../template/head.jsp"></jsp:include>

<div class="panel panel-primary">

	<div class="panel-heading text-center">Dias de Aula</div>

	<div class="panel-body">

		<jsp:include page="/template/msg.jsp"></jsp:include>


		<form method="post" action="">
			<div class="form-group"></div>
			<div class="row">
				<div class="">
					<div style="overflow: hidden;">
						<div class="form-group">
							<div class="row">
								<div class="col-sm-4 center-block">
									<div id="datepicker1"></div>
								</div>
								<div class="col-sm-4 centered">
									<div id="datepicker2"></div>
								</div>
								<div class="col-sm-4 centered">
									<div id="datepicker3"></div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="row">
								<div class="col-sm-4 center-block">
									<div id="datepicker4"></div>
								</div>
								<div class="col-sm-4 centered">
									<div id="datepicker5"></div>
								</div>
								<div class="text-center">
									<input type="button" class="btn btn-warning limparUser pull-left"
										id="limpar" value="Limpar"> <input
										class="btn btn-primary addUser pull-right" type="submit"
										value="Salvar">
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</form>



	</div>

</div>

<script type="text/javascript">
	$(document).ready(function() {
		function leapYear(year){
		    var result; 
		    if (years/400){
		      result = true
		    }
		    else if(years/100){
		      result = false
		    }
		    else if(years/4){
		      result= true
		    }
		    else{
		      result= false
		    }
		    return result
		 }
	
		var date = new Date();
		var maxDays = 3;
	
		//var pra simular o mes de cada tabela
		var month = 8;
		switch(month){
		case 1:
			maxDays = 31;
			break;
		case 2:
			if(leapYear(date.getFullYear())) maxDays = 29;
			else maxDays = 28;
			break;
		case 3: 
			maxDays = 31;
			break;	
		case 4:
			maxDays = 30;
			break;
		case 5:
			maxDays = 31;
			break;	
		case 6:
			maxDays = 30;
			break;
		case 7:
			maxDays = 31;
			break;
		case 8:
			maxDays = 31;
			break;
		case 9:
			maxDays = 30;
			break;
		case 10:
			maxDays = 31;
			break;
		case 11:
			maxDays = 30;
			break;
		case 12:
			maxDays = 31;
			break;
		}
		
		var maxDate = maxDays + "/" + month + "/" + date.getFullYear();
		
		$(function() {
			
			$('#datepicker1').datepicker({
				day: 1,
				month: month-1,
				year: 2016,
				format: "d/m/yyyy",
				multidate: true,
				language: "pt-br",
				daysOfWeekDisabled: [0, 6],
				clearBtn: true,
				startDate: "1/8/2016",
				endDate: maxDate,
				maxViewMode: "days"
			});
			$('#datepicker2').datepicker({
				format: "d/m/yyyy",
				multidate: true,
				language: "pt-br",
				daysOfWeekDisabled: [0, 6],
				clearBtn: true,
				startDate: "1/8/2016",
				endDate: maxDate,
				maxViewMode: "days"
			});
			$('#datepicker3').datepicker({
				format: "d/m/yyyy",
				multidate: true,
				language: "pt-br",
				daysOfWeekDisabled: [0, 6],
				clearBtn: true,
				startDate: "1/8/2016",
				endDate: maxDate,
				maxViewMode: "days"
			});
			$('#datepicker4').datepicker({
				format: "d/m/yyyy",
				multidate: true,
				language: "pt-br",
				daysOfWeekDisabled: [0, 6],
				clearBtn: true,
				startDate: "1/8/2016",
				endDate: maxDate,
				maxViewMode: "days"
			});
			$('#datepicker5').datepicker({
				format: "d/m/yyyy",
				multidate: true,
				language: "pt-br",
				daysOfWeekDisabled: [0, 6],
				clearBtn: true,
				startDate: "1/8/2016",
				endDate: maxDate,
				maxViewMode: "days"
			});
		});
		$("#limpar").click( function(){
				$('#datepicker1').data('datepicker').clearDates();
				$('#datepicker2').data('datepicker').clearDates();
				$('#datepicker3').data('datepicker').clearDates();
				$('#datepicker4').data('datepicker').clearDates();
				$('#datepicker5').data('datepicker').clearDates();
		    }
		);
	});
</script>


<jsp:include page="../template/foot.jsp"></jsp:include>