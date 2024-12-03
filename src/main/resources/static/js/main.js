
        // Função para alternar entre os formulários
        function toggleForms() {
            const selectedOption = document.getElementById('formSelector').value;
            const partForm = document.getElementById('partForm');
            const sectorForm = document.getElementById('sectorForm');

            // Alterna entre os formulários com base na opção selecionada
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
                form.action = '#'; // Define uma URL inválida se nenhum setor for selecionado
            }
        }

     async function handleSubmit(event) {
         event.preventDefault();  // Impede o envio normal do formulário e a recarga da página

         // Coleta os dados do formulário
         const form = document.getElementById('partForm');
         const formData = new FormData(form);

         // Obtém o ID do setor selecionado no dropdown
         const sectorId = document.getElementById('sector').value;

         if (!sectorId) {
             alert("Por favor, selecione um setor.");
             return;
         }

         try {
             // Envia os dados via AJAX (fetch) com o ID do setor na URL
             const response = await fetch(`/api/part/${sectorId}`, {
                 method: 'POST',
                 body: formData,
             });

             if (response.ok) {
                 // Se o cadastro for bem-sucedido, limpa o formulário
                 form.reset();

                 // Exibe uma mensagem de sucesso
                 alert('Peça cadastrada com sucesso!');
             } else {
                 // Caso ocorra algum erro no cadastro
                 alert('Erro ao cadastrar a peça!');
             }
         } catch (error) {
             console.error('Erro ao enviar dados:', error);
             alert('Erro ao cadastrar a peça!');
         }
     }



        // Faz uma requisição para atualizar a lista de setores
        async function fetchSectors() {
            try {
                const response = await fetch('/api/sector/sectors'); // Endpoint que retorna os setores
                if (!response.ok) {
                    throw new Error('Erro ao buscar setores.');
                }
                const sectors = await response.json();
                populateSectorsDropdown(sectors);
            } catch (error) {
                console.error('Erro ao atualizar setores:', error);
            }
        }

        // Atualiza o dropdown de setores dinamicamente
        function populateSectorsDropdown(sectors) {
            const sectorDropdown = document.getElementById('sector');
            sectorDropdown.innerHTML = '<option value="">Selecione o Setor</option>'; // Limpa as opções existentes

            sectors.forEach(sector => {
                const option = document.createElement('option');
                option.value = sector.id;
                option.textContent = `${sector.shelf} - ${sector.column} - ${sector.row}`;
                sectorDropdown.appendChild(option);
            });
        }

        // Envia o formulário de setor via AJAX e atualiza a lista automaticamente
        async function submitSectorForm(event) {
            event.preventDefault(); // Impede o envio padrão do formulário

            const form = event.target;
            const formData = new FormData(form);

            try {
                const response = await fetch(form.action, {
                    method: form.method,
                    body: new URLSearchParams(formData)
                });

                if (response.ok) {
                    alert('Setor cadastrado com sucesso!');
                    fetchSectors(); // Atualiza a lista de setores após o cadastro
                    form.reset(); // Limpa os campos do formulário
                } else {
                    alert('Erro ao cadastrar o setor.');
                }
            } catch (error) {
                console.error('Erro ao enviar o formulário:', error);
                alert('Erro ao cadastrar o setor.');
            }
        }
