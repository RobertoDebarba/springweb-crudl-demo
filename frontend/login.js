function login() {
    let usuario = document.getElementById("usuario").value;
    let senha = document.getElementById("senha").value;
    loginApi(usuario, senha).then(token => {
        if (token === "") { // Usuário incorreto ou erro
            alert("Usuário/senha inválido");
        } else { // OK
            // Salvar o token
            window.localStorage.setItem("token", token);
            // Redirecionar para a tela de pessoas
            document.location.href = 'pessoa.html';
        }
    })
}
