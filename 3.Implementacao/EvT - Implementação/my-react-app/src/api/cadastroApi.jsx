export async function cadastrarUsuario({ nome, email, senha, cpf }) {
  const res = await fetch('http://localhost:8080/auth/cadastro', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ nome, email, senha, cpf }),
  });
  if (!res.ok) {
    const msg = await res.text();
    throw new Error(msg || 'Erro ao criar conta');
  }
  try {
    return await res.json();
  } catch {
    return true;
  }
}
