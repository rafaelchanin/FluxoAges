<script>
    $( document ).ready(function() {
        $('#modalExcluir').on('show.bs.modal', function (event) {
            var botao = $(event.relatedTarget);
            var nome = botao.data('nome');
            var id = botao.data('id');

            $(this).find('.modal-title').text('Excluir tipo equipamento');
            $(this).find('#modal-descricao').text('Você realmente deseja excluir o tipo do equipamento (' + nome + ')?');

            $('#formExcluir').attr('action', "main?acao=removerEquipamento&id_equipamento=" + id);
        });


        $('#modalEditar').on('show.bs.modal', function (event) {
            var botao = $(event.relatedTarget);
            var nome = botao.data('nome');
            var id = botao.data('id');

            $(this).find('.modal-title').text('Editar tipo equipamento');
            $(this).find('#modal-descricao').text('Você realmente deseja editar o tipo do equipamento (' + nome + ')?');

            $('#formEditar').attr('action', "main?acao=telaTipoEquipamento&id=" + id + "&isEdit=true");
        });
    });
</script>

<div class="modal fade" id="modalExcluir" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">

            <div class="modal-header modal-danger">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title"></h4>
            </div>

            <div class="modal-body">
                <p id="modal-descricao"></p>
            </div>

            <div class="modal-footer">
                <form action="" method="post" id="formExcluir">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
                    <button type="submit" class="btn btn-danger">Excluir</button>
                </form>
            </div>

        </div>
    </div>
</div>

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

