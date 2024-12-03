async function handleUpdate(event) {
    event.preventDefault(); // Impede o comportamento padrão do formulário

    // Captura o ID diretamente do atributo 'data-id' do formulário
    const form = document.getElementById('partForm');
    const id = form.getAttribute('data-id');

    // Cria um objeto FormData para lidar com arquivos
    const formData = new FormData(form);

    // Converte os dados para JSON (exceto o arquivo)
    const partData = {
        name: formData.get('name') || null,
        amount: parseFloat(formData.get('amount')) || null,
        idSector: parseInt(formData.get('sector')) || null,
    };

    // Obtém o arquivo da imagem separadamente
    const imageFile = formData.get('imageData');
    let imageData = null;

    if (imageFile && imageFile.size > 0) {
        imageData = await imageFile.arrayBuffer(); // Converte o arquivo para um array de bytes
    }

    // Adiciona a imagem em bytes ao objeto de dados
    partData.imageData = imageData ? Array.from(new Uint8Array(imageData)) : null;

    // Faz a requisição PATCH para o backend
try {
    // Monta a URL da requisição


    // Loga a URL no console
        const partId = document.getElementById('partId').value;
        console.log('ID da peça:', partId);
        const requestUrl = `/api/part/${partId}`;
    // Faz a requisição PATCH para o backend
    const response = await fetch(requestUrl, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(partData),
    });

    // Verifica se a resposta foi bem-sucedida
    if (!response.ok) {
        throw new Error('Erro ao atualizar a peça.');
    }

    // Processa a resposta JSON
    const result = await response.json();
    alert('Peça atualizada com sucesso!');
    console.log(result);
} catch (error) {
    console.error(error);
    alert('Ocorreu um erro ao tentar atualizar a peça.');
}
}
