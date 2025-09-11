export async function getUsuarioAtual() {
  const res = await fetch('http://localhost:8080/usuarios/me', {
    credentials: 'include',
  });
  if (!res.ok) throw new Error('Erro ao buscar usuário');
  return res.json();
}

export async function getUsuarios() {
  const res = await fetch('http://localhost:8080/usuarios', {
    credentials: 'include',
  });
  if (!res.ok) throw new Error('Erro ao buscar usuários');
  return res.json();
}

export async function logoutUsuario() {
  const res = await fetch('http://localhost:8080/auth/logout', {
    method: 'POST',
    credentials: 'include',
  });
  if (!res.ok) throw new Error('Erro ao fazer logout');
  return true;
}
