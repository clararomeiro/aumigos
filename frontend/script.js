function showTab(id) {
    document.querySelectorAll('.tab').forEach(t => t.classList.remove('visible'));
    document.getElementById(id).classList.add('visible');
}

/* ================================
   BUSCAR TUTOR
================================ */
async function buscarTutor() {
    const nome = document.getElementById("buscarTutorNome").value;
    const div = document.getElementById("buscarTutorResultado");
    const cadAnimal = document.getElementById("cadAnimalContainer");
    const animaisContainer = document.getElementById("animaisContainer");
    const listaAnimaisForm = document.getElementById("listaAnimaisForm");

    div.innerHTML = "Buscando...";
    cadAnimal.classList.add("hidden");
    animaisContainer.classList.add("hidden");
    listaAnimaisForm.innerHTML = "";

    let resp;
    try {
        resp = await fetch(`/api/tutores?nome=${nome}`);
    } catch (e) {
        div.innerHTML = "Erro de conexão.";
        return;
    }

    if (resp.status == 204) {
        div.innerHTML = "Nenhum tutor encontrado.";
        return;
    } else if (!resp.ok) {
        div.innerHTML = "Erro ao buscar tutor.";
        return;
    }

    const tutor = await resp.json();
    if (!tutor) {
        div.innerHTML = "Nenhum tutor encontrado.";
        return;
    }

    // Exibir dados do tutor
    div.innerHTML = `
        <h4>Resultado:</h4>
        <p><b>Nome:</b> ${tutor.nome}</p>
        <p><b>CPF:</b> ${tutor.cpf}</p>
        <p><b>Email:</b> ${tutor.email}</p>
        <p><b>Endereço:</b> ${tutor.endereco}</p>
    `;

    // Preencher ID do tutor para cadastrar animal
    document.getElementById("aniTutor").value = tutor.cpf;
    cadAnimal.classList.remove("hidden");

    // Renderizar lista de animais com checkboxes
    renderizarListaAnimais(tutor);
}

function renderizarListaAnimais(tutor) {
    const animaisContainer = document.getElementById("animaisContainer");
    const listaAnimaisForm = document.getElementById("listaAnimaisForm");

    if (!tutor.animais || tutor.animais.length === 0) {
        listaAnimaisForm.innerHTML = "Nenhum animal associado.";
        animaisContainer.classList.remove("hidden");
        return;
    }

    listaAnimaisForm.innerHTML = tutor.animais
        .map(a => `
            <label>
                <input type="checkbox" name="animal" value="${a.id}">
                ID ${a.id} - ${a.nome} (${a.especie})
            </label><br>
        `).join("");

    animaisContainer.classList.remove("hidden");
}

async function deletarAnimaisSelecionados() {
    const ids = [...document.querySelectorAll(".chkAnimal:checked")].map(c => c.value);

    for (const id of ids) {
        await fetch(`/api/animais/${id}`, { method: "DELETE" });
    }

    alert("Animais deletados!");
    buscarTutor(); // atualiza página
}

/* ================================
   PAGAMENTO
================================ */
async function realizarPagamento() {
    const tipo = document.getElementById("pagTipo").value;
    const valor = document.getElementById("pagValor").value;

    const r = await fetch(`/payments/pagar`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ tipo, valor })
    });

    const txt = await r.json();
    document.getElementById("pagamentoResultado").innerText = txt.message;
}

/* ================================
   AGENDAR CONSULTA
================================ */
async function agendarConsulta() {
    const animal = document.getElementById("consAnimal").value;
    const vet = document.getElementById("consVet").value;
    const data = document.getElementById("consData").value;

    const r = await fetch(`/api/consultas`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ animal, vet, data })
    });

    const txt = await r.json();
    document.getElementById("consultaResultado").innerText = txt.description;
}

/* ================================
   CADASTRAR VETERINÁRIO
================================ */
async function cadastrarVeterinario() {
    const nome = document.getElementById("vetNome").value;
    const cpf = document.getElementById("vetCpf").value;
    const email = document.getElementById("vetEmail").value;

    const r = await fetch(`/api/veterinarios`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ nome, cpf, email })
    });

    const txt = await r.json();
    document.getElementById("vetResultado").innerText = txt.description;
}

/* ================================
   DELETAR ANIMAL
================================ */
async function deletarAnimaisSelecionados() {
    const checkboxes = document.querySelectorAll("input[name='animal']:checked");
    const result = document.getElementById("delAnimaisResultado");

    if (checkboxes.length === 0) {
        result.innerHTML = "Selecione pelo menos um animal.";
        return;
    }

    result.innerHTML = "Deletando...";

    for (const cb of checkboxes) {
        await fetch(`/api/animais/${cb.value}`, {
            method: "DELETE"
        });
    }

    result.innerHTML = "Animais deletados com sucesso!";

    // Atualizar automaticamente a listagem do tutor
    buscarTutor();
}

/* ================================
   CADASTRAR ANIMAL
================================ */
async function cadastrarAnimal() {
    const tutor = document.getElementById("aniTutor").value;
    const nome = document.getElementById("aniNome").value;
    const especie = document.getElementById("aniEspecie").value;
    const div = document.getElementById("aniResultado");

    const resp = await fetch(`/api/animais`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ tutor, nome, especie })
    });

    if (!resp.ok) {
        div.innerHTML = "Erro ao cadastrar animal.";
        return;
    }

    div.innerHTML = "Animal cadastrado com sucesso!";

    // Buscar tutor atualizado e re-renderizar lista de animais
    const nomeTutor = document.getElementById("buscarTutorNome").value;
    const respTutor = await fetch(`/api/tutores?nome=${nomeTutor}`);

    if (respTutor.ok) {
        const tutorAtualizado = await respTutor.json();
        renderizarListaAnimais(tutorAtualizado);
    }
}

/* ================================
   CADASTRAR TUTOR
================================ */
async function cadastrarTutor() {
    const nome = document.getElementById("tutorNome").value;
    const cpf = document.getElementById("tutorCpf").value;
    const endereco = document.getElementById("tutorEndereco").value;
    const email = document.getElementById("tutorEmail").value;

    const r = await fetch(`/api/tutores`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ nome, cpf, endereco, email })
    });

    const txt = await r.json();
    document.getElementById("tutorResultado").innerText = txt.description;
}
