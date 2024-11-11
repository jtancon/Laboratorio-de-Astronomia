<?php
session_start();
date_default_timezone_set('America/Sao_Paulo');

$Laboratório = $_SESSION['mesa'];

$numeromesa = intval($Laboratório);

if (!$_COOKIE['token']) {
    header("Location: login.php");
}
header('Cache-Control: no-store, no-cache, must-revalidate, max-age=0');
header('Cache-Control: post-check=0, pre-check=0', false);
header('Pragma: no-cache');

if ($_SERVER['REQUEST_METHOD'] === 'GET' && isset($_GET['refresh']) && $_GET['refresh'] == 1) {
    header('Location: ' . $_SERVER['PHP_SELF']);
    exit;
}

$itensParaCarrinho = json_decode($_POST['itens_para_carrinho'], true);

if ($_SERVER['REQUEST_METHOD'] === 'POST' || isset($itensParaCarrinho)) {
    if (isset($_POST['limpar_carrinho'])) {
        unset($_SESSION['carrinho']);
        header('Location: ' . $_SERVER['PHP_SELF']);
        exit;
    } elseif ((isset($_POST['produto']) && isset($_POST['preco']) && isset($_POST['cod_gruest'])) || isset($itensParaCarrinho)) {


        if (!isset($_SESSION['carrinho'])) {
            $_SESSION['carrinho'] = [];
        }

        foreach ($itensParaCarrinho as $item) {
            $_SESSION['carrinho'][] = $item;
        }

        if (isset($_POST['produto']) && isset($_POST['preco']) && isset($_POST['cod_gruest'])) {
            $produto = $_POST['produto'];
            $preco = $_POST['preco'];
            $cod_gruest = $_POST['cod_gruest'];
            $cod_pro = $_POST['cod_pro'];
            $quantidade = $_POST['quantidade'];

            $_SESSION['carrinho'][] = [
                'produto' => $produto,
                'preco' => $preco,
                'cod_pro' => $cod_pro,
                'cod_gruest' => $cod_gruest,
                'quantidade' => $quantidade
            ];
        }

        header('Location: ' . $_SERVER['HTTP_REFERER']);
        exit();
    } elseif ($_POST['mandar']) {
        header('Location: insercao.php?mesa=' . $Laboratório);
    }
}

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    if (isset($_POST['remover_item'])) {
        $index = $_POST['remover_item'];

        if (isset($_SESSION['carrinho'][$index]) && is_numeric($index) && $index >= 0) {
            if ($_SESSION['carrinho'][$index]['quantidade'] > 1) {
                $_SESSION['carrinho'][$index]['quantidade']--;
            } else {
                unset($_SESSION['carrinho'][$index]);
                $_SESSION['carrinho'] = array_values($_SESSION['carrinho']);
            }

            header('Location: ' . $_SERVER['PHP_SELF']);
        } else {
            header('Location: ' . $_SERVER['HTTP_REFERER']);
        }
        exit;
    }

    if (isset($_POST['adicionar_item'])) {
        $index = $_POST['adicionar_item'];

        if (isset($_SESSION['carrinho'][$index]) && is_numeric($index) && $index >= 0) {
            $_SESSION['carrinho'][$index]['quantidade']++;

            header('Location: ' . $_SERVER['PHP_SELF']);
        } else {
            header('Location: ' . $_SERVER['HTTP_REFERER']);
        }
        exit;
    }
}

if ($_SERVER['REQUEST_METHOD'] === 'POST' && isset($_POST['observacao'])) {
    foreach ($_POST['observacao'] as $index => $observacao) {
        $observacao = trim($observacao);
        $_SESSION['carrinho'][$index]['observacao'] = $observacao;
    }
}
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Laboratório</title>
    <meta charset="UTF-8" name="viewport" content="user-scalable=no">
    <link rel="stylesheet" href="imgs/garcon.css">
    <script src="JS/JSgarcon.js"></script>

</head>
<body>
<header>
    <div style="display: flex; justify-content: flex-start; align-items: center">
        <div style="display: flex; flex-direction: row; align-items: center; height: 100%; justify-content: center">
            <h3>Laboratório </h3>
            <?php
            echo '<h3>' . '. Número: ' . $Laboratório . '</h3>';
            ?>
        </div>
    </div>
</header>


<!-- Botões CODGRUEST -->
<div class="botao-container" id="botao-container">
    <?php
    $url = 'http://localhost:8080/api/codgruest';
    $response = file_get_contents($url);
    $data = json_decode($response, true);

    foreach ($data as $row) {
        echo '<button class="btndivs" onclick="escondediv(' . $row['codigo'] . ')"> <img src="imgs/estrela.png" style="height: 130px; width: 130px;"><p style="margin:5px 0 5px 0">' . $row['descricao'] . '</p></button>';
    }

    ?>
</div>

<!-- Mostra os itens para o pedido -->

<?php
echo '<div id="produtos" class="pedidos" id="100" style="display: none">';

$url = 'http://localhost:8080/api/mesa/pedidos/' . $Laboratório;
$response = file_get_contents($url);
$data = json_decode($response, true);

foreach ($data as $item) {
    $nome = $item['descricaoProduto'];
    $quantidade = $item['quantidade'];

    if (isset($itensAgrupados[$nome])) {
        $itensAgrupados[$nome]['QUANTIDADE'] += $quantidade;
    } else {
        $itensAgrupados[$nome] = array(
            'NOME' => $nome,
            'QUANTIDADE' => $quantidade,
        );
    }
}
    echo '<div class="listapedidos">';
    if (!empty($itensAgrupados)) {
        echo '<ul>';
        foreach ($itensAgrupados as $item) {
            echo '<li>' . round($item['QUANTIDADE']) . 'x ' . $item['NOME'] . '</li>';
        }
        echo '</ul>';
        echo '<button class="btnpedido" onclick="voltartelainicial()">Voltar</button>';

    } else {
        echo "<h1>Não há nada inserido na ficha ainda</h1>";
        echo '<button class="btnpedido" onclick="voltartelainicial()"> Voltar </button>';
    }
    echo '</div>';
echo '</div>';


?>
<!-- Mostra as divs de cada COD_GRUEST -->
<?php
$url = 'http://localhost:8080/api/produto';
$response = file_get_contents($url);
$data = json_decode($response, true);

$produtosAgrupados = [];
foreach ($data as $produto) {
    $produtosAgrupados[$produto['cod']][] = $produto;
}

function compararDescricao($a, $b)
{
    return strcmp($a['descricao'], $b['descricao']);
}

foreach ($produtosAgrupados as $cod_gruest => &$produtos) {
    usort($produtos, 'compararDescricao');
}
unset($produtos);

foreach ($produtosAgrupados as $cod_gruest => $produtos) {
    echo '<div class="product-group product-group' . $cod_gruest . '" id="' . $cod_gruest . '">';
    echo '<button class="btnpedido" id="btn' . $cod_gruest . '" onclick="escondediv(' . $cod_gruest . ')" style="align-content: center">Voltar a tela de categorias</button>';
    foreach ($produtos as $produto) {
        $produtoId = 'produto_' . $produto['codigoProduto'] . '_' . $cod_gruest;
        echo '<div class="product">';
        echo '<form action="" class="produto" method="post" onkeydown="return event.key != ' . "'Enter'" . ';">';
        echo '<input type="hidden" name="produto" value="' . $produto['descricao']. '">';
        echo '<input type="hidden" name="cod_pro" value="' . $produto['codigoProduto'] . '">';
        echo '<input type="hidden" name="preco" value="' . number_format($produto['preco'], 2, ",") . '">';
        echo '<input type="hidden" name="cod_gruest" value="' . $cod_gruest . '">';
        echo '<input type="button" class="btnquant" id="mais' . $produtoId . '" name="mais" onclick="alteraQuantidade(\'' . $produtoId . '\', 1)" value="+">';
        echo '<input name="quantidade" class="quant" id="' . $produtoId . '" value="0" min="0">';
        echo '<input type="button" class="btnquant" name="menos" onclick="alteraQuantidade(\'' . $produtoId . '\', -1)" value="-">';
        echo "<p style='font-size: 45px'>" . $produto['descricao'] . "</p>";
        echo '</form>';
        echo '</div>';
    }
    echo '</div>';
}
echo '<button class="btn-flutuante" style="display:none;" id="adicionar-todos-carrinho" onclick="adicionarTodosItensCarrinho()">Enviar pedido de Compra</button>'; ?>
<!-- Div para o carrinho -->
<div class="carrinho" id="carrinhodiv" style="display: flex">
    <?php
    if (empty($_SESSION['carrinho'])) {
        echo '<form action="" method="post" class="formcarrinho" id="carrinhoform" onkeydown="return event.key != \'Enter\';">';
    } else {
        echo '<form action="" method="post" class="formcarrinho" id="carrinhoform" onkeydown="return event.key != \'Enter\';">';
    }
    ?>

    <?php
    if (!empty($_SESSION['carrinho'])) {
        echo '<a href="insercao.php?mesa=' . $Laboratório . '" style="text-decoration: none"><button type="button" class="btnenvia" name="mandarpedido" value="mandar">Enviar o pedido</button></a>
        <div class="btnlimpaconfere">
        <button type="submit" class="btnlimpacarrinho" name="limpar_carrinho" value="Limpar pedido">Limpar pedido</button>
        <button type="button" class="btnverificapedido" name="mandarpedido" value="Verificarpedido" class="btnsmanda" onclick="mostraconclusao()">Conferir o pedido</button>
        </div>
        <hr style="width: 80vw; border: solid #7e7a7a 3px; margin: 20px 0 20px">
        <div style="width: 80vw; height: auto; display: flex; flex-direction: column; align-items: center;">';
        foreach ($_SESSION['carrinho'] as $index => $item) {
            echo '<div class="carrinhoitem" id="carrinho-' . $index . '">';
            echo '<div class="divitembtn">';
            echo "<button type='button' class='btnplus' onclick='toggleObservacao(" . $index . ")'><b>≡</b></button>";
            echo "<p style='font-size: 40px'> {$item['produto']}</p>";
            if ($item['quantidade'] > 1) {
                echo "<button type='submit' class='btnquant' name='remover_item' value='{$index}'><b>-</b></button>";
            } else {
                echo "<button type='submit' style='height:55px; background-color: #ff4655; width: auto; padding: 0 5px 0 5px; font-size: 25px' class='btnquant' name='remover_item' value='{$index}'>Remover item</button>";
            }
            echo "<p style='font-size: 43px'> {$item['quantidade']}</p>";
            echo "<button type='submit' class='btnquant' name='adicionar_item' value='{$index}'><b>+</b></button>";
            echo "<br>";
            echo '</div>';

            $observacao = isset($item['observacao']) ? $item['observacao'] : '';
            if ($observacao != null) {
                echo '<p class="obsp" id="observacaop-' . $index . '">' . $observacao . '</p>';
            }
            echo '<div style="display: none; flex-direction: row; width: 80vw; justify-content: flex-start; padding-left: 100px;" id="observacao-' . $index . '">';

            echo '<input type="text" style="display: flex" class="inputobs" name="observacao[' . $index . ']" placeholder="Digite a observação" class="input-observacao" value="' . htmlspecialchars($observacao) . '" onsubmit="hideMobileKeyboardOnEnter(event)">';
            if ($observacao != null) {
                echo '<button type="button" class="btnobs" onclick="adicionarObservacao(' . $index . ')">Mudar Observação</button>';
            } else {
                echo '<button type="button" class="btnobs" onclick="adicionarObservacao(' . $index . ')">Adicionar Observação</button>';
            }
            echo '</div>';
            echo '</div>';
        }
    }

    ?>
    <input type="hidden" name="refresh" value="1">
    </form>
</div>
</div>


<button class='btnpedido' onclick="mostrapedidos()" id="btnverpedido" id="btnverprodutos" style="display: flex">Ver os tópicos selecionados
</button>
<div class="pedidoconfere" id="conferepedido" style="display: none">
    <form action="insercao.php?mesa=<?php echo $Laboratório ?>" method="post">
        <?php
        if (!empty($_SESSION['carrinho'])) {
            foreach ($_SESSION['carrinho'] as $index => $item) {
                echo '<div class="carrinhoitem" id="carrinho-' . $index . '">';
                echo "<li style='font-size: 35px;'>{$item['quantidade']} x {$item['produto']} - $ {$item['preco']} - {$item['cod_gruest']} - cod {$item['cod_pro']}</li>";
                //echo "<li style='font-size: 45px;'>{$item['quantidade']} x {$item['produto']}</li>";
                if ($item['observacao'] != "") {
                    echo "<p style='font-size: 35px'> Observação: {$item['observacao']}</p>";
                }
            }
        } else {
            echo "<h1>Não há nada inserido na ficha ainda</h1>";
        }
        echo '<button type="submit" name="mandarpedido" value="Verificarpedido" class="btnenvia">Fazer a inserção na ficha</button>';
        echo '<button type="button" class="btnpedido" onclick="voltartelainicial()">Voltar para tela do pedido';

        ?>
    </form>
</div>
</body>
</html>