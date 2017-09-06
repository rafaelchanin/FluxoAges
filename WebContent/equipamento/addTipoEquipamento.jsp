<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<jsp:include page="../template/head.jsp"></jsp:include>

<div class="panel panel-primary panel-addUser">

    <div class="panel-heading text-center">Cadastro de Tipo de Equipamentos</div>


    <div class="panel-body">

        <jsp:include page="/template/msg.jsp"></jsp:include>


        <form method="post" action="main?acao=addTipoEquipamento">

            <div class="form-group">
                <label class="form-label ages">Nome: <span class="red">*</span></label>
                <input class="form-control" id="nome" name="nome" value="${param.nome}" type="text" maxlength="120" required>


            </div>
            <p>
                Campos que contém <span class="red">*</span> são obrigatórios
            </p>
            <div class="text-center">
                <input class="btn btn-warning limparUser pull-left" type="reset" value="Limpar">
                <input class="btn btn-primary addUser pull-right" type="submit" value="Cadastrar">
            </div>
        </form>
    </div>
</div>
<script type="text/javascript">

    $(document).ready(function(){
        $('#tipoUsuario').on('change', function() {
            if($('#tipoUsuario').val() == 2)
            {
                if ($('#nome').val() == "" || $('#nome').val() == ".")
                {
                    $('#msgE div').text( 'Informe o nome do Aluno')
                    $('#msgE').show()
                    $('#tipoUsuario').val(1)
                }
                else
                {
                    $("#divUsuarioGitLab").show()
                    $("#usuarioGitLab").val(createUsername())
                }
            }
            else
            {
                $("#divUsuarioGitLab").hide()
                $("#usuarioGitLab").val("")
            }
        })

        $('#nome').blur(function() {
            $('#usuario').val(createUsername())
        })

        $('#matricula').blur(function() {
            $('#senha').val($('#matricula').val())
        })

        function createUsername() {
            var names = $('#nome').val().split(' ')
            var firstName = $('#nome').val().split(' ').slice(0, 1)
            var lastName = $('#nome').val().split(' ').slice(-1)
            var userGitLab = firstName + "." + lastName
            var n_userGitLab = removeDiacritics(userGitLab)

            return n_userGitLab.toLowerCase()
        }

    });


</script>

<jsp:include page="/template/foot.jsp"></jsp:include>
