<?php
session_start();

if (!$_COOKIE['token']) {
    header('Location: login.php');
    exit();
}

$mesa = isset($_SESSION['mesa']) ? $_SESSION['mesa'] : '';

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $mesa = isset($_POST['mesa']) ? $_POST['mesa'] : '';
    $url = "http://localhost:8080/api/mesa/$mesa";
    $options = [
        "http" => [
            "header" => "Authorization: Bearer " . $_COOKIE['token'],
            "method" => "POST"
        ]
    ];
    $context = stream_context_create($options);
    $result = file_get_contents($url, false, $context);
    $data = json_decode($result, true);
    header('Location: garcon.php');
    exit();
}


$url = "http://localhost:8080/api/mesa/$mesa";
$options = [
    "http" => [
        "header" => "Authorization: Bearer " . $_COOKIE['token']
    ]
];
$context = stream_context_create($options);
$result = file_get_contents($url, false, $context);

$data = json_decode($result, true);

if (empty($data)) {
    echo '
<link rel="stylesheet" href="imgs/criaficha.css">

<header>
    <h1>Laboratórios</h1>
</header>

<body>
<form action="abremesa.php?mesa=' . $mesa . '" method="post">
<h2>Abrir um novo Laboratório?</h2>
<input type="hidden" name="mesa" value="' . $mesa . '">
<button class="sim" type="submit">Sim</button>
<button class="nao" type="button" onclick="window.location.href=\'index.php\'">Não</button>
</form>
</body>
';

} elseif ($data[0]['numeroMesa'] == $mesa && $data[0]['fechada'] == 'N') {
    header('Location: garcon.php');
    exit();
} elseif ($data[0]['numeroMesa'] == $mesa && $data[0]['fechada'] == 'S') {
    echo '<link rel="stylesheet" href="CSS/criaficha.css">
    <header>
    <h1>Back-End</h1>
</header>   
';
} else {
    echo "Unexpected response";
}

