<%--
  Created by IntelliJ IDEA.
  User: gloff
  Date: 20/10/17
  Time: 14:10
  To change this template use File | Settings | File Templates.
--%>
<script>
    $( document ).ready(function() {
        $('#modalEditar').on('show.bs.modal', function (event) {
            var botao = $(event.relatedTarget);
            var relatorio = botao.data('relatorio');
            var id = botao.data('id');

            $(this).find('.modal-title').text('Editar relatorio');
            $(this).find('#modal-descricao').text('Você realmente deseja editar o relatorio do dia (' + relatorio + ')?');

            $('#formEditar').attr('action', "main?acao=relatorioSemanal&id_relatorio=" + id + "&isEdit=true");
        });
    });
</script>

<div class="modal fade" id="modalEditar" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">

            <div class="modal-header modal-ages">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title"></h4>
            </div>

            <div class="modal-body">
                <p id="modal-descricao"></p>
            </div>

            <div class="modal-footer">
                <form action="" method="post" id="formEditar">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
                    <button type="submit" class="btn btn-primary">Editar</button>
                </form>
            </div>

        </div>
    </div>
</div>
