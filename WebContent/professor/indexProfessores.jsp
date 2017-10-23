<%@page import="br.ages.crud.util.Util"%>
<%@page import="java.util.ArrayList"%>
<%@page import="br.ages.crud.model.ResumoPonto"%>
<%@page import="br.ages.crud.model.Usuario"%>
<%@page import="br.ages.crud.model.TimePontoDTO"%>
<%@page import="br.ages.crud.util.TimeConverter"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Date"%>
<%@ page import="java.util.HashMap" %>


<jsp:include page="../template/headAlunos.jsp"></jsp:include>


<div class="panel panel-primary">

    <div class="panel-heading text-center">Relatorio Horas</div>

    <jsp:include page="/template/msg.jsp"></jsp:include>

    <div class="panel-body">
        <form id="formListAluno" method="post">
            <div class="form-group row">
                <div class='col-sm-6' id='time1'>
                    <label for="sel1" class="form-label ages">Time:<span
                            class="red">*</span></label>
                    <select class="form-control" id="time" name="time" required>

                        <%
                            HashMap<Integer,Integer> horasEsperadas = (HashMap<Integer,Integer>) request.getAttribute("horasEsperadas");
                            List<TimePontoDTO> listaTimes = (List<TimePontoDTO>) request.getAttribute("listaPontos");
                            String mesString = (String) request.getAttribute("mesString");
                            for (TimePontoDTO time : listaTimes) {
                        %>
                        <option value="<%=time.toString()%>" id="<%=time.getId()%>"><%=time.toString()%></option>

                        <%
                            };
                        %>
                    </select>
                </div>
            </div>
        </form>
        <div class="table-responsive">
            <%for (TimePontoDTO time : listaTimes) {	 %>
            <table id="listaalunos<%=time.getId()%>" class="listaalunos<%=time.getId()%> table table-responsive table-striped table-hover table-condensed"
                   style="display: none;">
                <thead>
                <tr>
                    <th style="text-align: center;">Nome</th>
                    <th style="text-align: center;">Realizadas ate o momento</th>
                    <th style="text-align: center;">Faltam para hoje</th>
                    <th style="text-align: center;">Faltam para o semestre</th>
                </tr>
                </thead>

                <tbody>
                <%

                    for (ResumoPonto usuario : time.getPontos()) {

                        //RESUMIDO
                        int horaTotaldia = usuario.getHoraTotalDiaValido();
                        String horasValidas = TimeConverter.ConvertMinuteToHours(horaTotaldia);
                        String horasAprov = TimeConverter.ConvertMinuteToHours(5400 - horaTotaldia);
                        String horasAprovCem = TimeConverter.ConvertMinuteToHours(7200 - horaTotaldia);

                        //EXTRACLASSE
                        String previstas = TimeConverter.ConvertMinuteToHours(horasEsperadas.get(time.getId()));
                        String realizadasPrevistas = "";
                        String realizadasTotal = "";
                        //float realizadasPrevistasTemp = 100 * (Float.valueOf(horaTotaldia) / Float.valueOf(horaEsperada));
                        //String realizadasPrevistas = TimeConverter.ConvertPorcentagemToString(realizadasPrevistasTemp);
                        //float realizadasTotalTemp = 100 * (Float.valueOf(horaTotaldia) / 3600);
                        //String realizadasTotal = TimeConverter.ConvertPorcentagemToString(realizadasTotalTemp);
                        if ((horasEsperadas.get(time.getId()) - horaTotaldia) > 0)
                            realizadasPrevistas = TimeConverter.ConvertMinuteToHours(horasEsperadas.get(time.getId()) - horaTotaldia);

                        if ((3600 - horaTotaldia) > 0)
                            realizadasTotal = TimeConverter.ConvertMinuteToHours(3600 - horaTotaldia);

                %>
                <tr class="coluna-sh aluno" id="<%=usuario.getIdAluno()%>">
                    <td align="center"><%=usuario.getNomeAluno()%></td>
                    <td align="center" style="text-align: center;"><%=horasValidas%></td>
                    <td align="center" style="text-align: center;"><%=realizadasPrevistas%></td>
                    <td align="center" style="text-align: center;"><%=realizadasTotal%></td>
                </tr>

                <tr style="display: none;"
                    class="alunotitulo<%=usuario.getIdAluno()%>"
                    id="alunotitulo<%=usuario.getIdAluno()%>">
                    <td colspan="4" style="">
                        <table style="width: 100%;"
                               class="table-responsive table-condensed">
                            <tr style="border-bottom-width:1px; border-bottom-style:solid; border-bottom-color:#EEE;">
                                <td>Tipo de horas</td>
                                <td style="text-align: center;">Realizadas</td>
                                <td style="text-align: center;">Previstas até o momento</td>
                                <td style="text-align: center;">Faltam</td>
                                <td style="text-align: center;">Previstas até o fim do semestre</td>
                                <td style="text-align: center;">Faltam</td>
                            </tr>
                            <tr>
                                <td>Extraclasse</td>
                                <td style="text-align: center;"><%=horasValidas%></td>
                                <td style="text-align: center;"><%=previstas%></td>
                                <td style="text-align: center;"><%=realizadasPrevistas%></td>
                                <td style="text-align: center;">60:00</td>
                                <td style="text-align: center;"><%=realizadasTotal%></td>
                            </tr>
                            <tr>
                                <td>Em aula</td>
                                <td style="text-align: center;">-</td>
                                <td style="text-align: center;">-</td>
                                <td style="text-align: center;">-</td>
                                <td style="text-align: center;">-</td>
                                <td style="text-align: center;">-</td>
                            </tr>
                            <tr>
                                <td>Total</td>
                                <td style="text-align: center;">-</td>
                                <td style="text-align: center;">-</td>
                                <td style="text-align: center;">-</td>
                                <td style="text-align: center;">-</td>
                                <td style="text-align: center;">-</td>
                            </tr>

                        </table>
                    </td>
                    <td style="display: none;"></td>
                    <td style="display: none;"></td>
                    <td style="display: none;"></td>
                    <!-- DatePicker me obrigo a fazer essa gambiarra por que não aceita o cosplan :/ -->
                </tr>
                <%
                        };
                    };
                %>
                </tbody>

            </table>

        </div>

    </div>

</div>

<jsp:include page="../template/foot.jsp"></jsp:include>
<script>
    function listar() {
        var id =  document.getElementById("idAluno").value;

        document.forms[0].action= 'main?acao=listaPontoHora&id_usuario=' + id;
        document.forms[0].submit();
        winconsole.log(id);
    };
    //	function filtrarData() {
    //		/* var entrada = document.getElementById("dtEntrada").value;
    //		var saida = document.getElementById("dtSaida").value; */
    //		 document.forms[0].action= 'main?acao=listaPontoHora&id_usuario=0';
    //		 document.forms[0].submit();
    //		 winconsole.log(id);
    //	};
</script>
<script>
    var timeSelecionado = 0;
    $(document).ready(function(){
        //ATENCAO, DPS TEM QUE OTIMIZAR PRA UMA FUNCAO
        var id = $(this).find('option:selected').attr('id');
        $(".listaalunos" + id).toggle();
        timeSelecionado=id;
        //
        $('#listaAlunos').dataTable({
            "language": {
                "lengthMenu": "Mostrando _MENU_ registros por página",
                "zeroRecords": "Sem registros - sorry",
                "info": "Mostrando _PAGE_ de _PAGES_ páginas",
                "infoEmpty": "Nenhum registros encontrados!",
                "infoFiltered": "(Filtrado _MAX_ do total deregistros)",
                "search": "Pesquisar",
                "paginate": {
                    "first":      "Primeiro",
                    "last":       "Último",
                    "next":       "Próximo",
                    "previous":   "Anterior"
                },
            },
            "ordering": false
        });
    });

    $("#time").change(function() {
        //JUNTAR COM UMA FUNCAO
        $(".listaalunos" + timeSelecionado).toggle();
        var id = $(this).find('option:selected').attr('id');
        $(".listaalunos" + id).toggle();
        timeSelecionado=id;
        //
    });
</script>
<script type="text/javascript">
    $(function() {
        $('#dataEntrada').datetimepicker({
            locale : 'pt-br',
            format : "DD/MM/YYYY",
            sideBySide : true
        });

        $('#dataSaida').datetimepicker({
            useCurrent : false,
            locale : 'pt-br',
            format : "DD/MM/YYYY",
            sideBySide : true,
            showTodayButton: true
        });

        $("#dataEntrada").on("dp.change", function(e) {
            $('#dataSaida').data("DateTimePicker").minDate(e.date);
            /* alert(document.getElementById('dataSaida').value); */
        });

        $("#dataSaida").on("dp.change", function(e) {
            $('#dataEntrada').data("DateTimePicker").maxDate(e.date);
            /* alert(document.getElementById('dataEntrada').value); */
        });

        //Funcao para expadir a segunda tabela :D
        $(this)
            .on(
                'click',
                'tr.aluno',
                function() {
                    var idCapitulo = $(this).attr("id");
                    var hoje = new Date();

                    //deixar a linha em negrito quando for exibida
                    if((this).style.fontWeight !== "bold")
                        (this).style.fontWeight = "bold";
                    else
                        (this).style.fontWeight = "";
                    //aparecer a tabela individual do aluno
                    $(".alunotitulo" + idCapitulo).toggle();


                });

    });
</script>