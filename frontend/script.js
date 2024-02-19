let isEditando = false;
let indexEditando;

function salvar() {
    if (isEditando) {
      // Nome
      let nome = document.getElementById("input-nome").value;
      // Idade
      let idade = document.getElementById("input-idade").value.trim();
      // ID
      let tdElement = indexEditando.parentNode;
      let trElement = tdElement.parentNode;
      let id = trElement.childNodes[0].innerHTML;

      putPessoa(nome, idade, id).then(pessoa => {
        trElement.childNodes[1].innerHTML = nome;
        trElement.childNodes[2].innerHTML = idade; 
  
        document.getElementById("input-nome").value = "";
        document.getElementById("input-idade").value = "";
      })
    } else {

      // Obter os valores dos campos input
      let nome = document.getElementById("input-nome").value;
      let idade = document.getElementById("input-idade").value;

      // postPessoa(nome, idade).then(function(pessoa) {
      postPessoa(nome, idade).then(pessoa => {
          // Esse código executa quando retornar a promise

          //Adicionar uma linha, duas colunas e os valores na tabela
          let tabela = document.getElementById("tabela-dados");
          let linha = tabela.insertRow();
          let colunaId = linha.insertCell();
          let colunaNome = linha.insertCell();
          let colunaIdade = linha.insertCell();
          let colunaAcoes = linha.insertCell();
          colunaId.innerHTML = pessoa.id;
          colunaNome.innerHTML = nome;
          colunaIdade.innerHTML = idade;
          colunaAcoes.innerHTML = "<button onclick='clickEditar(this)'>Editar</button><button onclick='clickExcluir(this)' id='btn-delete'>Excluir</button>"
      });
    }

    document.getElementById("formNovo").style.display = "none";
}

function clickExcluir(element) {
  console.log(element);

  let tdElement = element.parentNode;
  let trElement = tdElement.parentNode;
  let table = trElement.parentNode;
  let id = trElement.childNodes[0].innerHTML;

  deletePessoa(id).then(() => {
    table.deleteRow(trElement.rowIndex);
  });
  
}

function clickNovo() {
    isEditando= false;

    document.getElementById("input-nome").value = "";
    document.getElementById("input-idade").value = "";

    document.getElementById("formNovo").style.display = "block";
}

function clickEditar(element) {
    isEditando= true; 
    document.getElementById("formNovo").style.display="block";
    
    let tdElement = element.parentNode;
    let trElement = tdElement.parentNode;
    let nome = trElement.childNodes[1].innerHTML;
    let idade = trElement.childNodes[2].innerHTML.trim();
    document.getElementById("input-nome").value = nome;
    document.getElementById("input-idade").value = idade;

    indexEditando = element;
}