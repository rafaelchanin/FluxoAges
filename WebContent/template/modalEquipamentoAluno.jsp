
<script>
    $( document ).ready(function() {

        $('#modalEditar').on('show.bs.modal', function (event) {
            var botao = $(event.relatedTarget);
            var nome = botao.data('nome');
            var id = botao.data('id');

            $(this).find('.modal-title').text('Entregar equipamento');
            $(this).find('#modal-descricao').text('Você realmente deseja entregar o equipamento (' + nome + ')?');

            $('#formEditar').attr('action', "main?acao=entregaEquipamento&id=" + id);
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
	
	