import React, { useEffect, useState } from 'react';
import {
  getLocais,
  criarLocal,
  editarLocal,
  deletarLocal,
} from '../api/locaisApi';

function LocaisComponent({ usuario, onLocalCriado }) {
  const [locais, setLocais] = useState([]);
  const [novoLocal, setNovoLocal] = useState({
    nome: '',
    capacidade: '',
    endereco: {
      rua: '',
      bairro: '',
      cidade: '',
      estado: '',
      numero: '',
      cep: '',
    },
  });
  const [mostrarNovoLocal, setMostrarNovoLocal] = useState(false);
  const [editandoId, setEditandoId] = useState(null);
  const [localEdit, setLocalEdit] = useState(null);

  useEffect(() => {
    getLocais()
      .then(setLocais)
      .catch(() => setLocais([]));
  }, []);

  const handleNovoLocalChange = (e) => {
    const { name, value } = e.target;
    if (name in novoLocal.endereco) {
      setNovoLocal((prev) => ({
        ...prev,
        endereco: { ...prev.endereco, [name]: value },
      }));
    } else {
      setNovoLocal((prev) => ({ ...prev, [name]: value }));
    }
  };

  const handleLocalEditChange = (e) => {
    const { name, value } = e.target;
    if (name in localEdit.endereco) {
      setLocalEdit((prev) => ({
        ...prev,
        endereco: { ...prev.endereco, [name]: value },
      }));
    } else {
      setLocalEdit((prev) => ({ ...prev, [name]: value }));
    }
  };

  const handleAdicionarLocal = async (e) => {
    e.preventDefault();
    try {
      const localCriado = await criarLocal({
        nome: novoLocal.nome,
        capacidade: Number(novoLocal.capacidade),
        endereco: novoLocal.endereco,
      });
      setLocais((prev) => [...prev, localCriado]);
      setNovoLocal({
        nome: '',
        capacidade: '',
        endereco: {
          rua: '',
          bairro: '',
          cidade: '',
          estado: '',
          numero: '',
          cep: '',
        },
      });
      setMostrarNovoLocal(false);
      if (onLocalCriado) onLocalCriado(localCriado);
    } catch {
      alert('Erro ao criar local');
    }
  };

  const handleEditarLocal = (local) => {
    setEditandoId(local.id);
    setLocalEdit({
      nome: local.nome,
      capacidade: local.capacidade,
      endereco: { ...local.endereco },
    });
  };

  const handleSalvarEdicao = async (e) => {
    e.preventDefault();
    try {
      const localAtualizado = await editarLocal(editandoId, {
        nome: localEdit.nome,
        capacidade: Number(localEdit.capacidade),
        endereco: localEdit.endereco,
      });
      setLocais((prev) =>
        prev.map((l) => (l.id === editandoId ? localAtualizado : l)),
      );
      setEditandoId(null);
      setLocalEdit(null);
    } catch {
      alert('Erro ao editar local');
    }
  };

  const handleCancelarEdicao = () => {
    setEditandoId(null);
    setLocalEdit(null);
  };

  const handleDeletarLocal = async (id) => {
    if (!window.confirm('Tem certeza que deseja deletar este local?')) return;
    try {
      await deletarLocal(id);
      setLocais((prev) => prev.filter((l) => l.id !== id));
    } catch {
      alert('Erro ao deletar local');
    }
  };

  const isGerente =
    usuario &&
    (usuario.cargo === 'GERENTE' || usuario.cargo?.nome === 'GERENTE');

  return (
    <div className="locais-container">
      <h3>Locais</h3>
      <ul>
        {locais.map((local) => (
          <li key={local.id}>
            {editandoId === local.id ? (
              <form className="novo-local-form" onSubmit={handleSalvarEdicao}>
                <input
                  type="text"
                  name="nome"
                  placeholder="Nome do local"
                  value={localEdit.nome}
                  onChange={handleLocalEditChange}
                  required
                />
                <input
                  type="number"
                  name="capacidade"
                  placeholder="Capacidade"
                  value={localEdit.capacidade}
                  onChange={handleLocalEditChange}
                  required
                />
                <input
                  type="text"
                  name="rua"
                  placeholder="Rua"
                  value={localEdit.endereco.rua}
                  onChange={handleLocalEditChange}
                  required
                />
                <input
                  type="text"
                  name="bairro"
                  placeholder="Bairro"
                  value={localEdit.endereco.bairro}
                  onChange={handleLocalEditChange}
                  required
                />
                <input
                  type="text"
                  name="cidade"
                  placeholder="Cidade"
                  value={localEdit.endereco.cidade}
                  onChange={handleLocalEditChange}
                  required
                />
                <input
                  type="text"
                  name="estado"
                  placeholder="Estado"
                  value={localEdit.endereco.estado}
                  onChange={handleLocalEditChange}
                  required
                />
                <input
                  type="text"
                  name="numero"
                  placeholder="Número"
                  value={localEdit.endereco.numero}
                  onChange={handleLocalEditChange}
                  required
                />
                <input
                  type="text"
                  name="cep"
                  placeholder="CEP"
                  value={localEdit.endereco.cep}
                  onChange={handleLocalEditChange}
                  required
                />
                <button type="submit">Salvar</button>
                <button type="button" onClick={handleCancelarEdicao}>
                  Cancelar
                </button>
              </form>
            ) : (
              <>
                <span>
                  <strong>{local.nome}</strong> - {local.endereco?.rua},{' '}
                  {local.endereco?.cidade}
                </span>
                {isGerente && (
                  <>
                    <button onClick={() => handleEditarLocal(local)}>
                      Editar
                    </button>
                    <button
                      className="deletar"
                      onClick={() => handleDeletarLocal(local.id)}
                    >
                      Deletar
                    </button>
                  </>
                )}
              </>
            )}
          </li>
        ))}
      </ul>
      {isGerente && (
        <>
          <button type="button" onClick={() => setMostrarNovoLocal((v) => !v)}>
            {mostrarNovoLocal ? 'Cancelar' : 'Adicionar novo local'}
          </button>
          {mostrarNovoLocal && (
            <div className="novo-local-form">
              <input
                type="text"
                name="nome"
                placeholder="Nome do local"
                value={novoLocal.nome}
                onChange={handleNovoLocalChange}
                required
              />
              <input
                type="number"
                name="capacidade"
                placeholder="Capacidade"
                value={novoLocal.capacidade}
                onChange={handleNovoLocalChange}
                required
              />
              <input
                type="text"
                name="rua"
                placeholder="Rua"
                value={novoLocal.endereco.rua}
                onChange={handleNovoLocalChange}
                required
              />
              <input
                type="text"
                name="bairro"
                placeholder="Bairro"
                value={novoLocal.endereco.bairro}
                onChange={handleNovoLocalChange}
                required
              />
              <input
                type="text"
                name="cidade"
                placeholder="Cidade"
                value={novoLocal.endereco.cidade}
                onChange={handleNovoLocalChange}
                required
              />
              <input
                type="text"
                name="estado"
                placeholder="Estado"
                value={novoLocal.endereco.estado}
                onChange={handleNovoLocalChange}
                required
              />
              <input
                type="text"
                name="numero"
                placeholder="Número"
                value={novoLocal.endereco.numero}
                onChange={handleNovoLocalChange}
                required
              />
              <input
                type="text"
                name="cep"
                placeholder="CEP"
                value={novoLocal.endereco.cep}
                onChange={handleNovoLocalChange}
                required
              />
              <button type="button" onClick={handleAdicionarLocal}>
                Salvar Local
              </button>
            </div>
          )}
        </>
      )}
    </div>
  );
}

export default LocaisComponent;
