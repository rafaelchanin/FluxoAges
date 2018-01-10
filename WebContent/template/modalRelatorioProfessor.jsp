<%--
  Created by IntelliJ IDEA.
  User: gloff
  Date: 27/10/17
  Time: 11:34
  To change this template use File | Settings | File Templates.
--%>
<script>
    $( document ).ready(function() {

        $('#modalAceitar').on('show.bs.modal', function (event) {
            var botao = $(event.relatedTarget);
            var relatorio = botao.data('relatorio');
            var id = botao.data('id');

            $(this).find('.modal-title').text('Aceitar Relatorio');
            $(this).find('.modal-descricao').text('Você realmente deseja aceitar o relatorio?');

            $('#formAceitar').attr('action', "main?acao=aceitarRelatorio&id_relatorio=" + id);
        });
        $('#modalRecusar').on('show.bs.modal', function (event) {
            var botao = $(event.relatedTarget);
            var relatorio = botao.data('relatorio');
            var id = botao.data('id');

            $(this).find('.modal-title').text('Recusar Relatorio');
            $(this).find('.modal-descricao').text('Você realmente deseja recusar o relatorio?');

            $('#formRejeitar').attr('action', "main?acao=rejeitarRelatorio&id_relatorio=" + id);
        });
        $('#modalVisualizar').on('show.bs.modal', function (event) {
            var botao = $(event.relatedTarget);
            var relatorio = botao.data('relatorio');
            var id = botao.data('id');

            $(this).find('.modal-title').text('Visualizar Relatorio');
            $(this).find('.modal-descricao').text('Você realmente deseja visualizar o relatorio (' + relatorio + ')?');

            $('#formVisualizar').attr('action', "main?acao=visualizarRelatorio&id_relatorio=" + id);
            $('#visualizar').click(function() {
                $('#modalVisualizar').modal('hide');
            });

        });
    });
</script>

<div class="modal fade" id="modalAceitar" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">

            <div class="modal-header modal-ages">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title"></h4>
            </div>

            <div class="modal-body">
                <p class="modal-descricao"></p>
            </div>

            <div class="modal-footer">
                <form action="" method="post" id="formAceitar">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
                    <button type="submit" class="btn btn-primary">Aceitar</button>
                </form>
            </div>

        </div>
    </div>
</div>
<div class="modal fade" id="modalRecusar" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">

            <div class="modal-header modal-ages">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title"></h4>
            </div>

            <div class="modal-body">
                <p class="modal-descricao"></p>
            </div>

            <div class="modal-footer">
                <form action="" method="post" id="formRejeitar">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
                    <button type="submit" class="btn btn-primary">Rejeitar</button>
                </form>
            </div>

        </div>
    </div>
</div>
<div class="modal fade" id="modalVisualizar" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">

            <div class="modal-header modal-ages">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title"></h4>
            </div>

            <div class="modal-body">
                <p class="modal-descricao"></p>
            </div>

            <div class="modal-footer">
                <form action="" method="post" id="formVisualizar">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
                    <button id="visualizar" type="submit" class="btn btn-primary" >Visualizar</button>
                </form>
            </div>

        </div>
    </div>
</div>

