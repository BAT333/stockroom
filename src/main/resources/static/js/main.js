// Função para alternar entre os formulários
function toggleForms() {
    const selectedOption = document.getElementById('formSelector').value;
    const partForm = document.getElementById('partForm');
    const sectorForm = document.getElementById('sectorForm');

    if (selectedOption === 'part') {
        partForm.style.display = 'block';
        sectorForm.style.display = 'none';
        fetchSectors(); // Atualiza a lista de setores ao abrir o formulário de peças
    } else if (selectedOption === 'sector') {
        partForm.style.display = 'none';
        sectorForm.style.display = 'block';
    } else {
        partForm.style.display = 'none';
        sectorForm.style.display = 'none';
    }
}

// Atualiza a URL do formulário de peças com base no setor selecionado
function updateFormAction() {
    const selectElement = document.getElementById('sector');
    const form = document.getElementById('partForm');
    const selectedSectorId = selectElement.value;

    if (selectedSectorId) {
        form.action = `/api/part/${selectedSectorId}`;
    } else {
        form.action = '#';
    }
}

// Função para enviar o formulário de peças via AJAX
async function handleSubmit(event) {
    event.preventDefault();
    const form = document.getElementById('partForm');
    const formData = new FormData(form);
    const sectorId = document.getElementById('sector').value;

    if (!sectorId) {
        alert("Por favor, selecione um setor.");
        return;
    }

    try {
        const response = await fetch(`/api/part/${sectorId}`, {
            method: 'POST',
            body: formData,
        });

        if (response.ok) {
            form.reset();
            alert('Peça cadastrada com sucesso!');
        } else {
            alert('Erro ao cadastrar a peça!');
        }
    } catch (error) {
        console.error('Erro ao enviar dados:', error);
        alert('Erro ao cadastrar a peça!');
    }
}

// Atualiza a lista de setores dinamicamente
async function fetchSectors() {
    try {
        const response = await fetch('/api/sector/sectors');
        if (!response.ok) throw new Error('Erro ao buscar setores');
        const sectors = await response.json();
        populateSectorsDropdown(sectors);
    } catch (error) {
        console.error('Erro ao atualizar setores:', error);
    }
}

function populateSectorsDropdown(sectors) {
    const sectorDropdown = document.getElementById('sector');
    sectorDropdown.innerHTML = '<option value="">Selecione o Setor</option>';

    sectors.forEach(sector => {
        const option = document.createElement('option');
        option.value = sector.id;
        option.textContent = `${sector.shelf} - ${sector.column} - ${sector.row}`;
        sectorDropdown.appendChild(option);
    });
}

// Envia o formulário de setor via AJAX e atualiza a lista automaticamente
async function submitSectorForm(event) {
    event.preventDefault();
    const form = event.target;
    const formData = new FormData(form);

    try {
        const response = await fetch(form.action, {
            method: 'POST',
            body: new URLSearchParams(formData),
        });

        if (response.ok) {
            alert('Setor cadastrado com sucesso!');
            fetchSectors();
            form.reset();
        } else {
            alert('Erro ao cadastrar o setor.');
        }
    } catch (error) {
        console.error('Erro ao enviar formulário:', error);
        alert('Erro ao cadastrar o setor.');
    }
}
