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
										<div class="col-md-8">
											<div id="datetimepicker1"></div>
										</div>
									</div>
								</div>
								
							</div>
						</div>
					</div>





				<div class="text-center">
					<input class="btn btn-warning limparUser pull-left" type="reset" value="Limpar"> 
					<input class="btn btn-primary addUser pull-right" type="submit" value="Salvar">
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
		
		var maxdate = "'" + date.getFullYear() + "-" + month + "-" + maxDays + "'";
		console.log(maxdate);

		$(function() {
			$('#datetimepicker1').datetimepicker({
				format: 'MM/dd/YYYY',
				minDate: '2016-12-01', 
				maxDate: '2016-12-31',
				inline : true,
				daysOfWeekDisabled: [0, 6],
				showClear: true
				
			});
			$('.picker-switch').prop("disabled", true);
		});
		
	});
</script>


<jsp:include page="../template/foot.jsp"></jsp:include>