import React, { useEffect, useState } from 'react';
import HeaderUsuario from '../components/HeaderUsuario';
import EventosComponent from '../components/EventosComponent';
import LocaisComponent from '../components/LocaisComponent';

function EventosPage() {
  const [usuario, setUsuario] = useState(null);
  const [locais, setLocais] = useState([]);
  const [palestrantes, setPalestrantes] = useState([]);

  // Atualiza lista de locais quando um novo local é criado
  const handleLocalCriado = (localCriado) => {
    setLocais((prev) => [...prev, localCriado]);
  };

  useEffect(() => {
    fetch('http://localhost:8080/usuarios/me', {
      credentials: 'include',
    })
      .then((res) => {
        if (!res.ok) throw new Error('Erro ao buscar usuário');
        return res.json();
      })
      .then((data) => setUsuario(data))
      .catch(() => setUsuario(null));

    fetch('http://localhost:8080/locais', { credentials: 'include' })
      .then((res) => res.json())
      .then((data) => setLocais(data))
      .catch(() => setLocais([]));

    fetch('http://localhost:8080/usuarios', { credentials: 'include' })
      .then((res) => res.json())
      .then((data) =>
        setPalestrantes(
          data.filter(
            (u) => u.cargo === 'GERENTE' || u.cargo === 'FUNCIONARIO',
          ),
        ),
      )
      .catch(() => setPalestrantes([]));
  }, []);

  const isGerente =
    usuario &&
    (usuario.cargo === 'GERENTE' || usuario.cargo?.nome === 'GERENTE');

  return (
    <div>
      <HeaderUsuario />
      {isGerente && (
        <LocaisComponent usuario={usuario} onLocalCriado={handleLocalCriado} />
      )}
      <EventosComponent
        usuario={usuario}
        locais={locais}
        palestrantes={palestrantes}
      />
    </div>
  );
}

export default EventosPage;
