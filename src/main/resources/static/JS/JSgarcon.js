function adicionarTodosItensCarrinho() {
    let formulariosProdutos = document.querySelectorAll('.product form');
    let itensParaCarrinho = [];

    formulariosProdutos.forEach(function (formulario) {
        let quantidadeInput = formulario.querySelector('.quant');
        let quantidade = parseInt(quantidadeInput.value, 10);

        console.log('Produto:', formulario.querySelector('input[name="produto"]').value, 'Quantidade:', quantidade); // Log de depuração

        if (quantidade > 0) {
            let produto = formulario.querySelector('input[name="produto"]').value;
            let codPro = formulario.querySelector('input[name="cod_pro"]').value;
            let preco = formulario.querySelector('input[name="preco"]').value;
            let codGruest = formulario.querySelector('input[name="cod_gruest"]').value;

            itensParaCarrinho.push({
                produto: produto, cod_pro: codPro, preco: preco, cod_gruest: codGruest, quantidade: quantidade
            });
        }
    });

    console.log('Itens para Carrinho:', itensParaCarrinho); // Depuração final

    if (itensParaCarrinho.length > 0) {
        let form = document.createElement('form');
        form.method = 'POST';
        form.action = 'garcon.php';

        let inputItens = document.createElement('input');
        inputItens.type = 'hidden';
        inputItens.name = 'itens_para_carrinho';
        inputItens.value = JSON.stringify(itensParaCarrinho);

        form.appendChild(inputItens);
        document.body.appendChild(form);
        form.submit();
    } else {
        alert('Nenhum item selecionado para adicionar ao carrinho.');
    }
}

function mostrapedidos() {
    let ped = document.getElementById('produtos');
    let btns = document.getElementById('botao-container');
    let btn = document.getElementById('btnverpedido');
    let carrinho = document.getElementById('carrinhodiv');
    ped.style.display = 'flex';
    btns.style.display = 'none';
    btn.style.display = 'none';
    carrinho.style.display = 'none';
}

function telabtns() {
    let btns = document.getElementById('200');
    let pedido = document.getElementById('produtos');
    let btnpedido = document.getElementById('btnverpedido');

    btns.style.display = 'flex';
    pedido.style.display = 'none';
    btnpedido.style.display = 'block';
}

function voltartelainicial() {
    let ped = document.getElementById("produtos");
    let car = document.getElementById("carrinhodiv");
    let btns = document.getElementById("botao-container");
    let btn = document.getElementById("btnverpedido");
    let confere = document.getElementById("conferepedido");
    btns.style.display = 'none';
    ped.style.display = 'none';
    confere.style.display = 'none';
    car.style.display = 'flex';
    btns.style.display = 'flex';
    btn.style.display = 'block';
}

function alteraQuantidade(inputId, quantidade) {
    let input = document.getElementById(inputId);
    let btnmais = document.getElementById('mais' + inputId)
    if (quantidade === 1) {
        btnmais.style.backgroundColor = '#e37069';
    } else {
        if (input.value <= 1) {
            btnmais.style.backgroundColor = '#35518c';
        }
    }

    if (input) {
        let valorAtual = parseInt(input.value, 10);
        let novaQuantidade = valorAtual + quantidade;
        novaQuantidade = Math.max(0, novaQuantidade);
        input.value = novaQuantidade;
    }
}

function escondediv(num) {
    let div = document.getElementById(num);
    let envia = document.getElementById("adicionar-todos-carrinho");
    let busca = document.getElementById("botao-container");
    let carrinho = document.getElementById("carrinhodiv");
    let btnverpedido = document.getElementById("btnverpedido");
    let confere = document.getElementById("conferepedido");
    let btnVerPedido = document.getElementById("btnverpedido");

    confere.style.display = 'none';
    if (div.style.display === 'none' || div.style.display === '') {
        div.style.display = 'flex';
        confere.style.display = 'none';
        btnVerPedido.style.display = 'none';
        envia.style.display = "block";
        busca.style.display = "none";
        carrinho.style.display = 'none';
        btnVerPedido.style.display = 'none';
    } else {
        div.style.display = 'none';
        btnVerPedido.style.display = 'none';
        confere.style.display = 'none';
        envia.style.display = "none";
        busca.style.display = "flex";
        carrinho.style.display = 'flex';
        btnVerPedido.style.display = 'block';
    }
}

function mostrabtns(num) {
    console.log("funcmostrabtns")
    let btns = document.getElementById(num);
    let carrinho = document.getElementById("carrinhodiv");


    carrinho.style.display = 'none';
    btns.style.display = 'flex';

}

function fadeInObservacao(index) {
    let inputObs = document.querySelector(`#carrinho-${index} .inputobs`);
    let btn = document.getElementById("btn" + index);

    if (inputObs.style.display === "none") {
        inputObs.style.display = "inline-block";
        btn.style.display = "inline-block";
    } else {
        inputObs.style.display = "none";
        btn.style.display = "none";
    }
}

function adicionarObservacao(index) {
    let inputObs = document.querySelector(`#carrinho-${index} .inputobs`);
    let observacao = inputObs.value.trim();

    let formData = new FormData();
    formData.append('observacao[' + index + ']', observacao);

    fetch('garcon.php', {
        method: 'POST', body: formData
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Erro ao adicionar observação');
            }
            return response.text();
        })
        .then(data => {
            console.log('Observação adicionada com sucesso:');
            window.location.reload();
        })
        .catch(error => {
            console.error('Erro:', error);
        });
}

function toggleObservacao(index) {
    let observacaoDiv = document.getElementById('observacao-' + index);
    let observacaop = document.getElementById('observacaop-' + index);

    if (observacaoDiv.style.display === "none") {
        observacaoDiv.style.display = "flex";
        observacaop.style.display = "none";
    } else {
        observacaoDiv.style.display = "none";
        observacaop.style.display = "block";
    }
}

function mostraconclusao(para) {
    let idpedido = document.getElementById('carrinhodiv');
    let idconc = document.getElementById("conferepedido");
    let btnpedido = document.getElementById("btnverpedido");
    let btns = document.getElementById('botao-container');

    btns.style.display = 'none';
    idpedido.style.display = 'none';
    idconc.style.display = 'flex'
    btnpedido.style.display = 'none';

}

function mudanum(numero) {
    let inp = document.getElementById(numero);
    inp.value = inp.value + 1;
}

window.onload = function () {
    let botaoAdicionarCarrinho = document.getElementById('adicionar-todos-carrinho');
    botaoAdicionarCarrinho.style.display = 'none';
}
