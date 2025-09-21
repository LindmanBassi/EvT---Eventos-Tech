import React, { useState } from 'react';
import { cadastrarUsuario } from '../api/cadastroApi';

function CadastroComponent() {
  const [nome, setNome] = useState('');
  const [email, setEmail] = useState('');
  const [senha, setSenha] = useState('');
  const [confirmarSenha, setConfirmarSenha] = useState('');
  const [cpf, setCpf] = useState('');
  const [erro, setErro] = useState('');
  const [sucesso, setSucesso] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    setErro('');
    setSucesso('');

    if (senha !== confirmarSenha) {
      setErro('As senhas não coincidem.');
      return;
    }

    try {
      await cadastrarUsuario({ nome, email, senha, cpf });
      setSucesso('Conta criada com sucesso! Faça login.');
      setNome('');
      setEmail('');
      setSenha('');
      setConfirmarSenha('');
      setCpf('');
    } catch (err) {
      setErro('Erro ao criar conta. Tente outro e-mail.');
    }
  };

  return (
    <div className="login-container">
      <h2>Criar Conta</h2>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          placeholder="Nome"
          value={nome}
          onChange={(e) => setNome(e.target.value)}
          required
        />
        <input
          type="email"
          placeholder="E-mail"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          required
        />
        <input
          type="text"
          placeholder="CPF"
          value={cpf}
          onChange={(e) => setCpf(e.target.value)}
          required
        />
        <input
          type="password"
          placeholder="Senha"
          value={senha}
          onChange={(e) => setSenha(e.target.value)}
          required
        />
        <input
          type="password"
          placeholder="Confirmar Senha"
          value={confirmarSenha}
          onChange={(e) => setConfirmarSenha(e.target.value)}
          required
        />
        <button type="submit">Registrar</button>
      </form>
      {erro && <p style={{ color: '#E53935' }}>{erro}</p>}
      {sucesso && <p style={{ color: '#32756F' }}>{sucesso}</p>}
    </div>
  );
}

export default CadastroComponent;
