document.addEventListener("DOMContentLoaded", function () {
    document.getElementById('cadastroForm').addEventListener('submit', function(event) {
        var senha = document.getElementById('pass').value;
        var confirmacao = document.getElementById('confpass').value;
        var erroMsg = document.getElementById('senhaErro');

        if (senha !== confirmacao) {
            event.preventDefault(); // Impede o envio do formulário
            erroMsg.classList.remove('d-none'); // Mostra a mensagem de erro
        } else {
            erroMsg.classList.add('d-none'); // Oculta a mensagem de erro se estiver tudo certo
        }
    });
});

document.addEventListener('DOMContentLoaded', function() {
    // Seleciona o campo de CPF
    var cpfInput = document.getElementById('cpf');

    // Adiciona um evento de input para a máscara de CPF
    cpfInput.addEventListener('input', function(event) {
        var cpfValue = event.target.value;

        // Remove tudo o que não for número
        cpfValue = cpfValue.replace(/\D/g, '');

        // Aplica a máscara no CPF (111.111.111-11)
        if (cpfValue.length <= 11) {
            cpfValue = cpfValue.replace(/(\d{3})(\d{3})(\d{3})(\d{1})/, '$1.$2.$3-$4');
        }

        // Define o valor formatado de volta no campo de entrada
        event.target.value = cpfValue;
    });
});
