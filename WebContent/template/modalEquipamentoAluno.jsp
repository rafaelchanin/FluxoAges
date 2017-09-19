
<script>
    $( document ).ready(function() {

        $('#modalEditar').on('show.bs.modal', function (event) {
            var botao = $(event.relatedTarget);
            var nome = botao.data('nome');
            var id = botao.data('id');

            $(this).find('.modal-title').text('Entregar equipamento');
            $(this).find('#modal-descricao').text('Você realmente deseja entregar o equipamento (' + nome + ')?');

            $('#btnClick').on('click', function () {
                var obs = document.getElementById("observacao").value;
                $('#formEditar').attr('action', "main?acao=entregaEquipamento&id=" + id+"&observacao="+obs);
            })
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
                <div class="form-group">
                    <label for="observacao">Observação:</label>
                    <textarea class="form-control" rows="5" id="observacao"></textarea>
                </div>
            </div>

            <div class="modal-footer">
                <form action="" method="post" id="formEditar">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
                    <button id="btnClick" type="submit" class="btn btn-primary">Entregar</button>
                </form>
            </div>

        </div>
    </div>
</div>
	
	