export async function participarEvento(cpfUsuario, tituloEvento) {
  const res = await fetch('http://localhost:8080/participacoes', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    credentials: 'include',
    body: JSON.stringify({ cpfUsuario, tituloEvento }),
  });
  if (!res.ok) throw new Error('Erro ao participar do evento');
  return res.json();
}

export async function getParticipantesDoEvento(eventoId) {
  const res = await fetch(
    `http://localhost:8080/participacoes/evento/${eventoId}/usuarios`,
    {
      credentials: 'include',
    },
  );
  if (!res.ok) throw new Error('Erro ao buscar participantes');
  return res.json();
}
