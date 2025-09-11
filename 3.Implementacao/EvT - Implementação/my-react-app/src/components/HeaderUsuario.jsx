import React, { useEffect, useState } from 'react';
import { getUsuarioAtual, logoutUsuario } from '../api/usuariosApi';

function HeaderUsuario() {
  const [usuario, setUsuario] = useState(null);

  useEffect(() => {
    getUsuarioAtual()
      .then(setUsuario)
      .catch(() => setUsuario(null));
  }, []);

  const handleLogout = async () => {
    try {
      await logoutUsuario();
      window.location.href = '/';
    } catch {
      alert('Erro ao fazer logout');
    }
  };

  if (!usuario) {
    return (
      <header style={{ padding: 16, background: '#f5f5f5', marginBottom: 24 }}>
        <p>Carregando usuário...</p>
      </header>
    );
  }

  return (
    <header
      style={{
        padding: 16,
        background: '#f5f5f5',
        marginBottom: 24,
        display: 'flex',
        justifyContent: 'space-between',
        alignItems: 'center',
      }}
    >
      <h2 style={{ margin: 0 }}>Bem-vindo, {usuario.nome}!</h2>
      <button
        onClick={handleLogout}
        style={{
          background: '#E53935',
          color: '#fff',
          border: 'none',
          padding: '8px 16px',
          borderRadius: 4,
          cursor: 'pointer',
        }}
      >
        Sair
      </button>
    </header>
  );
}

export default HeaderUsuario;
