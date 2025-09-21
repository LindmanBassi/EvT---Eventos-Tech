export async function loginUsuario(email, senha) {
  const res = await fetch('http://localhost:8080/auth/login', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    credentials: 'include',
    body: JSON.stringify({ email, senha }),
  });
  if (!res.ok) throw new Error('E-mail ou senha inválidos.');
  return res.json();
}
