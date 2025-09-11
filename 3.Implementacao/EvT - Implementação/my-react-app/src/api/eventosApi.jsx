export async function getEventos() {
  const res = await fetch('http://localhost:8080/eventos', {
    credentials: 'include',
  });
  if (!res.ok) throw new Error('Erro ao buscar eventos');
  return res.json();
}

export async function editarEvento(id, evento) {
  const res = await fetch(`http://localhost:8080/eventos/${id}`, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    credentials: 'include',
    body: JSON.stringify(evento),
  });
  if (!res.ok) throw new Error('Erro ao editar evento');
  return res.json();
}

export async function criarEvento(evento) {
  const res = await fetch('http://localhost:8080/eventos', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    credentials: 'include',
    body: JSON.stringify(evento),
  });
  if (!res.ok) throw new Error('Erro ao criar evento');
  return res.json();
}

export async function deletarEvento(id) {
  const res = await fetch(`http://localhost:8080/eventos/${id}`, {
    method: 'DELETE',
    credentials: 'include',
  });
  if (!res.ok) throw new Error('Erro ao deletar evento');
  return true;
}
