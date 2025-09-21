import React, { useState } from 'react';
import { loginUsuario } from '../api/loginApi';

function LoginComponent() {
  const [email, setEmail] = useState('');
  const [senha, setSenha] = useState('');
  const [erro, setErro] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    setErro('');
    try {
      await loginUsuario(email, senha);
      window.location.href = '/eventos';
    } catch (err) {
      setErro(err.message || 'Erro ao conectar com o servidor.');
    }
  };

  return (
    <div className="login-container">
      <h2>Login</h2>
      <form onSubmit={handleSubmit}>
        <input
          type="email"
          placeholder="E-mail"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          required
        />
        <input
          type="password"
          placeholder="Senha"
          value={senha}
          onChange={(e) => setSenha(e.target.value)}
          required
        />
        <button type="submit">Entrar</button>
      </form>
      {erro && <p style={{ color: 'red' }}>{erro}</p>}
    </div>
  );
}

export default LoginComponent;
