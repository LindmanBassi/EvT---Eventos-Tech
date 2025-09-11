import React, { useState } from 'react';
import LoginPage from '../components/LoginComponent';
import CadastroPage from '../components/CadastroComponent';

function AuthPage() {
  const [tela, setTela] = useState('login'); // 'login' ou 'cadastro'

  return (
    <div>
      {tela === 'login' ? (
        <>
          <LoginPage />
          <p style={{ textAlign: 'center', marginTop: 16 }}>
            Não tem conta?{' '}
            <button
              style={{
                background: 'none',
                color: '#32756F',
                border: 'none',
                cursor: 'pointer',
                textDecoration: 'underline',
                fontWeight: 'bold',
              }}
              onClick={() => setTela('cadastro')}
            >
              Registrar
            </button>
          </p>
        </>
      ) : (
        <>
          <CadastroPage />
          <p style={{ textAlign: 'center', marginTop: 16 }}>
            Já tem conta?{' '}
            <button
              style={{
                background: 'none',
                color: '#32756F',
                border: 'none',
                cursor: 'pointer',
                textDecoration: 'underline',
                fontWeight: 'bold',
              }}
              onClick={() => setTela('login')}
            >
              Login
            </button>
          </p>
        </>
      )}
    </div>
  );
}

export default AuthPage;
