<?php
session_start();

if (!$_COOKIE['token']) {
    header('Location: login.php');
    exit();
}
$mesa = isset($_SESSION['mesa']) ? $_SESSION['mesa'] : '';

if (!empty($_SESSION['carrinho'])) {
    $carrinho_json = [];
    foreach ($_SESSION['carrinho'] as $index => $item) {
        $carrinho_json[] = [
            "codigoprodutos" => intval($item['cod_pro']),
            "numeromesa" => intval($mesa),
            "quantidade" => $item['quantidade'],
            "observacao" => isset($item['observacao']) ? $item['observacao'] : ' ',
            "codgruest" => intval($item['cod_gruest'])
        ];
    }
    $json_data = json_encode($carrinho_json);
    echo $json_data;
    $url = 'http://localhost:8080/api/pedido'; // Certifique-se de que o URL está correto
    $options = array(
        'http' => array(
            'header' => "Content-type: application/json\r\n" .
                        "Authorization: Bearer " . $_COOKIE['token'] . "\r\n", // Adiciona o cabeçalho de autorização
            'method' => 'POST',
            'content' => $json_data
        )
    );
    $context = stream_context_create($options);
    $result = file_get_contents($url, false, $context);
    if ($result === FALSE) {
        $error_code = http_response_code();
        echo '
    <link rel="stylesheet" href="CSS/criaficha.css">
    <header>
    <h1> Astra </h1>
    </header>
    <h1> 500 Internal Server Error </h1>
    <p style="text-align: center">Erro na inserção da ficha</p>
    ';
    } else {
        $_SESSION['carrinho'] = [];
        $_SESSION['mesa'] = '';
        header('Location: index.php');
        exit();
    }
} else {
    echo '
     <link rel="stylesheet" href="CSS/criaficha.css">
        <header>
        <h1> Astra </h1>

     </header>
     <h1> Nada para inserir </h1>
     <a href="index.php"><button class="btnenvia">Voltar</button></a>
     ';
}
?>