import React, { useEffect, useState } from 'react';
import {
  getEventos,
  editarEvento,
  criarEvento,
  deletarEvento,
} from '../api/eventosApi';
import {
  participarEvento,
  getParticipantesDoEvento,
} from '../api/participacoesApi';

function EventosComponent({ usuario, locais, palestrantes }) {
  const [eventos, setEventos] = useState([]);
  const [participantesPorEvento, setParticipantesPorEvento] = useState({});
  const [erro, setErro] = useState('');
  const [sucesso, setSucesso] = useState('');
  const [editandoId, setEditandoId] = useState(null);
  const [eventoEdit, setEventoEdit] = useState(null);
  const [adicionando, setAdicionando] = useState(false);
  const [novoEvento, setNovoEvento] = useState({
    titulo: '',
    descricao: '',
    vagas: '',
    tipoEvento: 'PRESENCIAL',
    localId: '',
    data: '',
    palestranteId: '',
  });

  useEffect(() => {
    getEventos()
      .then((data) => {
        setEventos(data);
        data.forEach((evento) => {
          getParticipantesDoEvento(evento.id)
            .then((usuarios) => {
              setParticipantesPorEvento((prev) => ({
                ...prev,
                [evento.id]: usuarios,
              }));
            })
            .catch(() => {});
        });
      })
      .catch(() => setErro('Não foi possível carregar os eventos.'));
  }, [sucesso]);

  const handleParticipar = async (evento) => {
    if (!usuario) return;
    try {
      await participarEvento(usuario.cpf, evento.titulo);
      setSucesso('Inscrição realizada com sucesso!');
    } catch {
      setErro('Erro ao participar do evento.');
    }
  };

  // Funções para edição
  const handleEditarEvento = (evento) => {
    setEditandoId(evento.id);
    setEventoEdit({
      titulo: evento.titulo,
      descricao: evento.descricao,
      vagas: evento.vagas,
      tipoEvento: evento.tipoEvento,
      localId: evento.local?.id || '',
      data: evento.data ? evento.data.substring(0, 10) : '',
      palestranteId: evento.palestrante?.id || '',
    });
  };

  const handleEventoEditChange = (e) => {
    const { name, value } = e.target;
    setEventoEdit((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSalvarEdicao = async (e) => {
    e.preventDefault();
    try {
      const eventoParaEditar = {
        titulo: eventoEdit.titulo,
        descricao: eventoEdit.descricao,
        vagas: Number(eventoEdit.vagas),
        tipoEvento: eventoEdit.tipoEvento,
        localId:
          eventoEdit.tipoEvento === 'PRESENCIAL'
            ? Number(eventoEdit.localId)
            : null,
        data: eventoEdit.data, // já vem no formato yyyy-MM-dd
        palestranteId: Number(eventoEdit.palestranteId),
        estadoEvento: null,
      };
      await editarEvento(editandoId, eventoParaEditar);
      setSucesso('Evento editado com sucesso!');
      setEditandoId(null);
      setEventoEdit(null);
    } catch {
      setErro('Erro ao editar evento.');
    }
  };

  const handleCancelarEdicao = () => {
    setEditandoId(null);
    setEventoEdit(null);
  };

  // Funções para adicionar evento
  const handleNovoEventoChange = (e) => {
    const { name, value } = e.target;
    setNovoEvento((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleAdicionarEvento = async (e) => {
    e.preventDefault();
    try {
      const eventoParaCriar = {
        titulo: novoEvento.titulo,
        descricao: novoEvento.descricao,
        vagas: Number(novoEvento.vagas),
        tipoEvento: novoEvento.tipoEvento,
        localId:
          novoEvento.tipoEvento === 'PRESENCIAL'
            ? Number(novoEvento.localId)
            : null,
        data: novoEvento.data, // já vem no formato yyyy-MM-dd
        palestranteId: Number(novoEvento.palestranteId),
        estadoEvento: null,
      };
      await criarEvento(eventoParaCriar);
      setSucesso('Evento criado com sucesso!');
      setAdicionando(false);
      setNovoEvento({
        titulo: '',
        descricao: '',
        vagas: '',
        tipoEvento: 'PRESENCIAL',
        localId: '',
        data: '',
        palestranteId: '',
      });
    } catch {
      setErro('Erro ao criar evento.');
    }
  };

  const handleDeletarEvento = async (id) => {
    if (!window.confirm('Tem certeza que deseja deletar este evento?')) return;
    try {
      await deletarEvento(id);
      setSucesso('Evento deletado com sucesso!');
    } catch {
      setErro('Erro ao deletar evento.');
    }
  };

  const isGerente =
    usuario &&
    (usuario.cargo === 'GERENTE' || usuario.cargo?.nome === 'GERENTE');

  return (
    <div className="eventos-container">
      <h3>Lista de Eventos</h3>
      {erro && <p style={{ color: 'red' }}>{erro}</p>}
      {sucesso && <p style={{ color: '#32756F' }}>{sucesso}</p>}

      {isGerente && (
        <>
          <button onClick={() => setAdicionando((v) => !v)}>
            {adicionando ? 'Cancelar' : 'Adicionar Evento'}
          </button>
          {adicionando && (
            <form onSubmit={handleAdicionarEvento} style={{ margin: '16px 0' }}>
              <input
                type="text"
                name="titulo"
                placeholder="Título"
                value={novoEvento.titulo}
                onChange={handleNovoEventoChange}
                required
              />
              <input
                type="text"
                name="descricao"
                placeholder="Descrição"
                value={novoEvento.descricao}
                onChange={handleNovoEventoChange}
                required
              />
              <input
                type="number"
                name="vagas"
                placeholder="Vagas"
                value={novoEvento.vagas}
                onChange={handleNovoEventoChange}
                required
              />
              <input
                type="date"
                name="data"
                value={novoEvento.data}
                onChange={handleNovoEventoChange}
                required
              />
              <select
                name="tipoEvento"
                value={novoEvento.tipoEvento}
                onChange={handleNovoEventoChange}
                required
              >
                <option value="PRESENCIAL">Presencial</option>
                <option value="REMOTO">Remoto</option>
              </select>
              {novoEvento.tipoEvento === 'PRESENCIAL' && (
                <select
                  name="localId"
                  value={novoEvento.localId}
                  onChange={handleNovoEventoChange}
                  required
                >
                  <option value="">Selecione o local</option>
                  {locais.map((local) => (
                    <option key={local.id} value={local.id}>
                      {local.nome} - {local.endereco?.rua},{' '}
                      {local.endereco?.cidade}
                    </option>
                  ))}
                </select>
              )}
              <select
                name="palestranteId"
                value={novoEvento.palestranteId}
                onChange={handleNovoEventoChange}
                required
              >
                <option value="">Selecione o palestrante</option>
                {palestrantes.map((p) => (
                  <option key={p.id} value={p.id}>
                    {p.nome}
                  </option>
                ))}
              </select>
              <button type="submit">Salvar Evento</button>
            </form>
          )}
        </>
      )}

      <ul>
        {eventos.map((evento) => (
          <li key={evento.id} style={{ marginBottom: 8 }}>
            {editandoId === evento.id ? (
              <form onSubmit={handleSalvarEdicao} style={{ marginBottom: 8 }}>
                <input
                  type="text"
                  name="titulo"
                  placeholder="Título"
                  value={eventoEdit.titulo}
                  onChange={handleEventoEditChange}
                  required
                />
                <input
                  type="text"
                  name="descricao"
                  placeholder="Descrição"
                  value={eventoEdit.descricao}
                  onChange={handleEventoEditChange}
                  required
                />
                <input
                  type="number"
                  name="vagas"
                  placeholder="Vagas"
                  value={eventoEdit.vagas}
                  onChange={handleEventoEditChange}
                  required
                />
                <input
                  type="date"
                  name="data"
                  value={eventoEdit.data}
                  onChange={handleEventoEditChange}
                  required
                />
                <select
                  name="tipoEvento"
                  value={eventoEdit.tipoEvento}
                  onChange={handleEventoEditChange}
                  required
                >
                  <option value="PRESENCIAL">Presencial</option>
                  <option value="REMOTO">Remoto</option>
                </select>
                {eventoEdit.tipoEvento === 'PRESENCIAL' && (
                  <select
                    name="localId"
                    value={eventoEdit.localId}
                    onChange={handleEventoEditChange}
                    required
                  >
                    <option value="">Selecione o local</option>
                    {locais.map((local) => (
                      <option key={local.id} value={local.id}>
                        {local.nome} - {local.endereco?.rua},{' '}
                        {local.endereco?.cidade}
                      </option>
                    ))}
                  </select>
                )}
                <select
                  name="palestranteId"
                  value={eventoEdit.palestranteId}
                  onChange={handleEventoEditChange}
                  required
                >
                  <option value="">Selecione o palestrante</option>
                  {palestrantes.map((p) => (
                    <option key={p.id} value={p.id}>
                      {p.nome}
                    </option>
                  ))}
                </select>
                <button type="submit">Salvar</button>
                <button type="button" onClick={handleCancelarEdicao}>
                  Cancelar
                </button>
              </form>
            ) : (
              <>
                <strong>{evento.titulo}</strong> -{' '}
                {evento.data?.substring(0, 10)}
                <br />
                <span>{evento.descricao}</span>
                <br />
                <span>Vagas: {evento.vagas}</span>
                <br />
                <span>Tipo: {evento.tipoEvento}</span>
                <br />
                {evento.tipoEvento === 'PRESENCIAL' &&
                  evento.local &&
                  evento.local.endereco && (
                    <span>
                      Endereço: {evento.local.nome} -{' '}
                      {evento.local.endereco.rua},{' '}
                      {evento.local.endereco.numero},{' '}
                      {evento.local.endereco.bairro},{' '}
                      {evento.local.endereco.cidade} -{' '}
                      {evento.local.endereco.estado}, CEP:{' '}
                      {evento.local.endereco.cep}
                      <br />
                    </span>
                  )}
                {isGerente && (
                  <>
                    <button
                      onClick={() => handleEditarEvento(evento)}
                      style={{ marginRight: 8 }}
                    >
                      Editar
                    </button>
                    <button
                      onClick={() => handleDeletarEvento(evento.id)}
                      style={{ background: '#E53935', color: '#fff' }}
                    >
                      Deletar
                    </button>
                  </>
                )}
                {usuario &&
                  usuario.cargo !== 'GERENTE' &&
                  usuario.cargo?.nome !== 'GERENTE' && (
                    <button
                      onClick={() => handleParticipar(evento)}
                      style={{ background: '#4CAF50', color: '#fff' }}
                      disabled={
                        participantesPorEvento[evento.id] &&
                        participantesPorEvento[evento.id].some(
                          (p) => p.cpf === usuario.cpf,
                        )
                      }
                    >
                      {participantesPorEvento[evento.id] &&
                      participantesPorEvento[evento.id].some(
                        (p) => p.cpf === usuario.cpf,
                      )
                        ? 'Já inscrito'
                        : 'Participar'}
                    </button>
                  )}
              </>
            )}
          </li>
        ))}
      </ul>
    </div>
  );
}

export default EventosComponent;
