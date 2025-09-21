export async function getLocais() {
  const res = await fetch('http://localhost:8080/locais', {
    credentials: 'include',
  });
  if (!res.ok) throw new Error('Erro ao buscar locais');
  return res.json();
}

export async function criarLocal(local) {
  const res = await fetch('http://localhost:8080/locais', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    credentials: 'include',
    body: JSON.stringify(local),
  });
  if (!res.ok) throw new Error('Erro ao criar local');
  return res.json();
}

export async function editarLocal(id, local) {
  const res = await fetch(`http://localhost:8080/locais/${id}`, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    credentials: 'include',
    body: JSON.stringify(local),
  });
  if (!res.ok) throw new Error('Erro ao editar local');
  return res.json();
}

export async function deletarLocal(id) {
  const res = await fetch(`http://localhost:8080/locais/${id}`, {
    method: 'DELETE',
    credentials: 'include',
  });
  if (!res.ok) throw new Error('Erro ao deletar local');
  return true;
}
