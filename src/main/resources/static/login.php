<?php
session_start();
date_default_timezone_set('America/Sao_Paulo');

$ch = curl_init();
curl_setopt($ch, CURLOPT_URL, "http://localhost:8080/api/users");
curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
$response = curl_exec($ch);
curl_close($ch);

$users = json_decode($response, true);

// Inicializando o array 'erro' se não existir
if (!isset($_SESSION['erro'])) {
    $_SESSION['erro'] = [];
}

if (empty($users)) {
    $_SESSION['erro'][] = 'Nenhum funcionário cadastrado';
}

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $garcon = isset($_POST['garcon']) ? $_POST['garcon'] : '';
    $senha = isset($_POST['password']) ? $_POST['password'] : '';

    if (empty($garcon)) {
        $_SESSION['erro'][] = 'Selecione um funcionário';
    } elseif (empty($senha)) {
        $_SESSION['erro'][] = 'Digite a senha';
    } else {
        $data = [
            'email' => $garcon,
            'password' => $senha
        ];

        $ch = curl_init('http://localhost:8080/api/users/login');
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($ch, CURLOPT_POST, true);
        curl_setopt($ch, CURLOPT_HTTPHEADER, ['Content-Type: application/json']);
        curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($data));
        $response = curl_exec($ch);
        curl_close($ch);

        $result = json_decode($response, true);

        if (isset($result['token'])) {
            setcookie('token', $result['token'], time() + 60 * 60 * 24, '/');
            header('Location: index.php');
            exit();
        } else {
            $_SESSION['erro'][] = 'Login falhou. Verifique suas credenciais.';
        }
    }
}
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Astraconbar</title>
    <meta charset="UTF-8" name="viewport" content="user-scalable=no">
    <link rel="stylesheet" href="./imgs/index.css">
</head>
<body style="justify-content: center">
<main class="principal">
    <div class="conteudo">
        <h2>Faça seu login</h2><br>
        <?php if (!empty($_SESSION['erro'])): ?>
            <div class="erro">
                <?php foreach ($_SESSION['erro'] as $erro): ?>
                    <p><?= $erro ?></p>
                <?php endforeach ?>
            </div><br>
        <?php endif;
        unset($_SESSION['erro']);
        ?>
        <form action="#" method="post" class="login">
            <div class="input">
                <label for="garcon">Usuário:</label>
                <select name="garcon" id="garcon">
                    <option value="">Selecione um Funcionário</option>
                    <?php
                    foreach ($users as $user) {
                        echo "<option value='{$user['email']}' name='garcon'>{$user['name']}</option>";
                    } ?>
                </select>
            </div>
            <br>
            <div class="input">
                <label for="senha">Senha:</label>
                <input type="password" id="senha" name="password" required style="margin-right: 0">
            </div>
            <br><br>
            <button type="submit" class="btnentrar">Entrar</button>
        </form>
    </div>
</main>
</body>
</html>
