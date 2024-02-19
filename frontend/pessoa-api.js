async function getPessoas() {
    let response = await fetch("http://localhost:8080/pessoa/", {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': window.localStorage.getItem('token')
        }
    });
    let pessoas = await response.json();

    return pessoas;
}

async function postPessoa(nome, idade) {
    let response = await fetch("http://localhost:8080/pessoa/", {
        method: "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': window.localStorage.getItem('token')
        },
        body: JSON.stringify({
            nome: nome,
            idade: idade
        })
      });
    let pessoa = await response.json();

    return pessoa;
}

async function putPessoa(nome, idade, id) {
    let response = await fetch("http://localhost:8080/pessoa/" + id, {
        method: "PUT",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': window.localStorage.getItem('token')
        },
        body: JSON.stringify({
            nome: nome,
            idade: idade
        })
      });
    let pessoa = await response.json();

    return pessoa;
}

async function deletePessoa(id) {
    let response = await fetch("http://localhost:8080/pessoa/" + id, {
        method: "DELETE",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': window.localStorage.getItem('token')
        }
      });
    let pessoa = await response.json();

    return pessoa;
}

getPessoas().then(pessoas => pessoas.forEach(pessoa => {
        //Adicionar uma linha, duas colunas e os valores na tabela
        let tabela = document.getElementById("tabela-dados");
        let linha = tabela.insertRow();
        let colunaId = linha.insertCell();
        let colunaNome = linha.insertCell();
        let colunaIdade = linha.insertCell();
        let colunaAcoes = linha.insertCell();
        colunaId.innerHTML = pessoa.id;
        colunaNome.innerHTML = pessoa.nome;
        colunaIdade.innerHTML = pessoa.idade;
        colunaAcoes.innerHTML = "<button onclick='clickEditar(this)'>Editar</button><button onclick='clickExcluir(this)' id='btn-delete'>Excluir</button>"
    }))
