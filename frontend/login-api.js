async function loginApi(usuario, senha) {
    let response = await fetch("http://localhost:8080/login/", {
        method: "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            usuario: usuario,
            senha: senha
        })
    });
    let token = await response.text();

    return token;
}
