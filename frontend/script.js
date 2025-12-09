function showTab(id) {
    document.querySelectorAll('.tab').forEach(t => t.classList.remove('visible'));
    document.getElementById(id).classList.add('visible');
}

/* ================================
   BUSCAR TUTOR
================================ */
async function buscarTutor() {
    const cpf = document.getElementById("buscarTutorCpf").value;
    const div = document.getElementById("buscarTutorResultado");
    const cadAnimal = document.getElementById("cadAnimalContainer");
    const animaisContainer = document.getElementById("animaisContainer");
    const listaAnimaisForm = document.getElementById("listaAnimaisForm");

    div.innerHTML = "Buscando...";
    cadAnimal.classList.add("hidden");
    animaisContainer.classList.add("hidden");
    listaAnimaisForm.innerHTML = "";

    if (!cpf) {
        div.innerHTML = "Digite um CPF.";
        return;
    }

    let resp;
    try {
        resp = await fetch(`/api/tutores/${cpf}`);
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

/* ================================
   PAGAMENTO
================================ */
async function realizarPagamento() {
    const consultaId = document.getElementById("pagConsultaSelect").value;
    const tipo = document.getElementById("pagTipo").value;
    const valor = document.getElementById("pagValor").value;
    const div = document.getElementById("pagamentoResultado");

    if (!consultaId) {
        div.innerText = "Selecione uma consulta.";
        div.style.color = "red";
        return;
    }

    try {
        const r = await fetch(`/api/pagamentos`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ 
                consultaId: parseInt(consultaId), 
                tipo: tipo, 
                valor: parseFloat(valor) 
            })
        });

        const txt = await r.json();
        
        if (r.ok) {
            div.innerText = "Pagamento realizado com sucesso!";
            div.style.color = "green";
            carregarDropdownPagamento(); 
            if(typeof carregarConsultas === 'function') carregarConsultas();
        } else {
            div.innerText = "Erro: " + txt.description;
            div.style.color = "red";
        }
    } catch (e) {
        div.innerText = "Erro de conexão.";
    }
}

/* ================================
   CARREGAR DROPDOWN DE PAGAMENTO
================================ */
async function carregarDropdownPagamento() {
    const select = document.getElementById("pagConsultaSelect");
    select.innerHTML = "<option value=''>Carregando...</option>";

    try {
        const resp = await fetch("/api/consultas");
        if (resp.ok) {
            const lista = await resp.json();
            
            const pendentes = lista.filter(c => c.pagamento === null);

            if (pendentes.length === 0) {
                select.innerHTML = "<option value=''>Nenhuma consulta pendente</option>";
                return;
            }

            select.innerHTML = pendentes.map(c => 
                `<option value="${c.id}">
                    Consulta #${c.id} - ${c.data} (${c.animal ? c.animal.nome : 'Pet'})
                </option>`
            ).join("");
        }
    } catch (e) {
        select.innerHTML = "<option value=''>Erro ao carregar</option>";
    }
}

/* ================================
   AGENDAR CONSULTA
================================ */
async function agendarConsulta() {

    const animalId = document.getElementById("consAnimalSelect").value;
    const vetCpf = document.getElementById("consVet").value;
    const data = document.getElementById("consData").value;
    const resultadoDiv = document.getElementById("consultaResultado");
    
    if (!animalId) {
        resultadoDiv.innerText = "Por favor, busque um tutor e selecione um animal.";
        resultadoDiv.style.color = "red";
        return;
    }
    if (!vetCpf) {
        resultadoDiv.innerText = "Por favor, selecione um veterinário.";
        resultadoDiv.style.color = "red";
        return;
    }
    if (!data) {
        resultadoDiv.innerText = "Por favor, selecione uma data.";
        resultadoDiv.style.color = "red";
        return;
    }

    try {
        const r = await fetch(`/api/consultas`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ 
                animal: parseInt(animalId),
                vet: vetCpf, 
                data: data 
            })
        });

        const txt = await r.json();
        
        if (r.ok) {
            resultadoDiv.innerText = "Consulta agendada com sucesso!";
            resultadoDiv.style.color = "green";
            carregarConsultas();
            carregarDropdownPagamento()
        } else {
            resultadoDiv.innerText = "Erro: " + txt.description;
            resultadoDiv.style.color = "red";
        }
    } catch (e) {
        resultadoDiv.innerText = "Erro de conexão.";
        resultadoDiv.style.color = "red";
    }
}

/* ================================
   LISTAR CONSULTAS 
================================ */
async function carregarConsultas() {
    const tbody = document.getElementById("tabelaConsultasCorpo");
    tbody.innerHTML = "<tr><td colspan='5'>Carregando...</td></tr>"; 

    try {
        const resp = await fetch("/api/consultas");
        if (!resp.ok) {
            tbody.innerHTML = "<tr><td colspan='5'>Erro ao carregar.</td></tr>";
            return;
        }
        const lista = await resp.json();

        if (lista.length === 0) {
            tbody.innerHTML = "<tr><td colspan='5'>Nenhuma consulta agendada.</td></tr>";
            return;
        }

        tbody.innerHTML = lista.map(c => {
            const statusPagamento = c.pagamento 
                ? `<span style="color: green; font-weight: bold;">PAGO (R$ ${c.pagamento.valor})</span>`
                : `<span style="color: orange; font-weight: bold;">PENDENTE</span>`;

            return `
            <tr>
                <td>${c.id}</td>
                <td>${c.data}</td>
                <td>${c.animal ? c.animal.nome : 'N/A'}</td>
                <td>${c.veterinario ? c.veterinario.nome : 'N/A'}</td>
                <td>${statusPagamento}</td>
            </tr>
            `;
        }).join("");

    } catch (e) {
        console.error(e);
        tbody.innerHTML = "<tr><td colspan='5'>Erro de conexão.</td></tr>";
    }
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

    if (r.ok) {
        carregarVeterinarios(); 

        setTimeout(() => {
            fecharModal('modalCadVet');
            document.getElementById("vetResultado").innerText = "";
            document.getElementById("vetNome").value = "";
            document.getElementById("vetCpf").value = "";
            document.getElementById("vetEmail").value = "";
        }, 1500);
    }
}

async function carregarVeterinarios() {
    const tbody = document.getElementById("tabelaVeterinariosCorpo");
    tbody.innerHTML = "<tr><td colspan='3'>Carregando...</td></tr>";

    try {
        const resp = await fetch("/api/veterinarios");
        if (!resp.ok) {
            tbody.innerHTML = "<tr><td colspan='3'>Erro ao carregar.</td></tr>";
            return;
        }
        const lista = await resp.json();

        if (lista.length === 0) {
            tbody.innerHTML = "<tr><td colspan='3'>Nenhum veterinário cadastrado.</td></tr>";
            return;
        }

        tbody.innerHTML = lista.map(v => `
            <tr>
                <td>${v.nome}</td>
                <td>${v.cpf}</td>
                <td>${v.email}</td>
                <td>
                    <button onclick="deletarVeterinario('${v.cpf}')" style="background: red; color: white; padding: 5px 10px; margin: 0;">Excluir</button>
                </td>
            </tr>
        `).join("");

        carregarDropdownVeterinarios();

    } catch (e) {
        console.error(e);
        tbody.innerHTML = "<tr><td colspan='3'>Erro de conexão.</td></tr>";
    }
}

async function carregarDropdownVeterinarios() {
    const select = document.getElementById("consVet");
    
    try {
        const resp = await fetch("/api/veterinarios");
        if (resp.ok) {
            const lista = await resp.json();
            
            if (lista.length === 0) {
                select.innerHTML = "<option value=''>Nenhum veterinário disponível</option>";
                return;
            }

            select.innerHTML = lista.map(v => 
                `<option value="${v.cpf}">${v.nome} (CPF: ${v.cpf})</option>`
            ).join("");
        }
    } catch (e) {
        select.innerHTML = "<option value=''>Erro ao carregar</option>";
    }
}

async function deletarVeterinario(cpf) {
    if (!confirm("Tem certeza que deseja remover este veterinário?")) return;

    try {
        const r = await fetch(`/api/veterinarios/${cpf}`, {
            method: "DELETE"
        });

        if (r.ok) {
            alert("Veterinário removido!");
            carregarVeterinarios(); 
            carregarDropdownVeterinarios(); 
        } else {
            alert("Erro ao deletar. Verifique se ele não tem consultas agendadas.");
        }
    } catch (e) {
        alert("Erro de conexão.");
    }
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
    
    // Novos campos
    const raca = document.getElementById("aniRaca").value;
    const nascimento = document.getElementById("aniNascimento").value;
    const sexo = document.getElementById("aniSexo").value;
    const peso = document.getElementById("aniPeso").value ? parseFloat(document.getElementById("aniPeso").value) : null;
    const plano = document.getElementById("aniPlano").value ? parseInt(document.getElementById("aniPlano").value) : null;
    const obs = document.getElementById("aniObs").value;

    const div = document.getElementById("aniResultado");

    const resp = await fetch(`/api/animais`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ 
            tutor, nome, especie, 
            raca, nascimento, sexo, 
            peso, plano, obs 
        })
    });

    if (!resp.ok) {
        div.innerHTML = "Erro ao cadastrar animal.";
        div.style.color = "red";
        return;
    }

    div.innerHTML = "Animal cadastrado com sucesso!";
    div.style.color = "green";

    const cpfTutor = document.getElementById("buscarTutorCpf").value;
    const respTutor = await fetch(`/api/tutores/${cpfTutor}`);

    if (respTutor.ok) {
        const tutorAtualizado = await respTutor.json();
        renderizarListaAnimais(tutorAtualizado);
    }
}

async function buscarAnimaisDoTutor() {
    const cpf = document.getElementById("consTutorCpf").value;
    const selectAnimal = document.getElementById("consAnimalSelect");
    const msgDiv = document.getElementById("msgBuscaTutor");

    selectAnimal.innerHTML = "<option value=''>Buscando...</option>";
    selectAnimal.disabled = true;
    msgDiv.innerText = "";
    msgDiv.style.color = "gray";

    if (!cpf) {
        msgDiv.innerText = "Por favor, digite o CPF.";
        msgDiv.style.color = "red";
        selectAnimal.innerHTML = "<option value=''>Aguardando busca...</option>";
        return;
    }

    try {
        const resp = await fetch(`/api/tutores/${cpf}`);
        
        if (resp.status === 404 || resp.status === 204) {
            msgDiv.innerText = "Tutor não encontrado.";
            msgDiv.style.color = "red";
            selectAnimal.innerHTML = "<option value=''>Tutor não encontrado</option>";
            return;
        }

        const tutor = await resp.json();

        if (!tutor.animais || tutor.animais.length === 0) {
            msgDiv.innerText = `Tutor ${tutor.nome} encontrado, mas não possui animais cadastrados.`;
            msgDiv.style.color = "orange";
            selectAnimal.innerHTML = "<option value=''>Sem animais cadastrados</option>";
            return;
        }

        msgDiv.innerText = `Tutor encontrado: ${tutor.nome}`;
        msgDiv.style.color = "green";
        
        selectAnimal.disabled = false;
        selectAnimal.innerHTML = tutor.animais.map(a => 
            `<option value="${a.id}">${a.nome} (${a.especie})</option>`
        ).join("");

    } catch (e) {
        console.error(e);
        msgDiv.innerText = "Erro ao buscar tutor.";
        msgDiv.style.color = "red";
        selectAnimal.innerHTML = "<option value=''>Erro na busca</option>";
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

    if (r.ok) {
        const txt = await r.json();
        document.getElementById("tutorResultado").innerText = txt.description;

        setTimeout(() => {
            fecharModal('modalCadTutor');
            document.getElementById("tutorResultado").innerText = "";
            document.getElementById("tutorNome").value = "";
            document.getElementById("tutorCpf").value = "";
            document.getElementById("tutorEndereco").value = "";
            document.getElementById("tutorEmail").value = "";
        }, 1500);
    }
  
}

/* ================================
   CONTROLE DE MODAIS
================================ */
function abrirModal(id) {
    document.getElementById(id).classList.add("show-modal");
}

function fecharModal(id) {
    document.getElementById(id).classList.remove("show-modal");
    // Opcional: Limpar campos ao fechar
}

window.onclick = function(event) {
    if (event.target.classList.contains('modal-overlay')) {
        event.target.classList.remove("show-modal");
    }
}

carregarDropdownVeterinarios();
carregarDropdownPagamento();
carregarVeterinarios();
carregarConsultas();
