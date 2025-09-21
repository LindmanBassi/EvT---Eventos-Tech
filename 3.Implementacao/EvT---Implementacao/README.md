# Lp1-e-ADS

## Descrição

Projeto de gerenciamento de eventos acadêmicos, desenvolvido em React (frontend) e Spring Boot (backend).

Utiliza **JPA** para persistência de dados e integração com **API de CEP** para preenchimento automático de endereços.

---

## Estrutura do Projeto

```
/
├── my-react-app/           # Frontend React
│   ├── src/
│   │   ├── api/            # Funções de comunicação com backend (ex: eventosApi, locaisApi)
│   │   ├── components/     # Componentes reutilizáveis (ex: LocaisComponent, EventosComponent)
│   │   ├── pages/          # Telas completas (ex: EventosPage)
│   │   └── App.jsx         # Roteamento principal
│   └── package.json
├── src/                    # Backend Spring Boot
│   ├── main/java/br/com/bassi/trabalho_facu_lp1/
│   │   ├── controller/     # Controllers REST
│   │   ├── service/        # Serviços de negócio
│   │   ├── domain/         # Entidades JPA
│   │   ├── dto/            # DTOs
│   │   └── repositories/   # Repositórios JPA
│   └── ...
└── pom.xml                 # Maven config
```

---

## Funcionalidades

- Cadastro e login de usuários
- Gerenciamento de eventos (criar, editar, deletar)
- Participação em eventos
- Gerenciamento de locais e palestrantes
- Controle de acesso por perfil (ex: gerente)
- Preenchimento automático de endereço via **API de CEP**
- Persistência de dados com **JPA**

---

## Como rodar o projeto

### Backend (Spring Boot)

```bash
cd Lp1-e-ADS
mvn spring-boot:run
```

### Frontend (React)

```bash
cd my-react-app
npm install
npm start
```

---

## Organização do código

- **Pages:** Telas completas (ex: EventosPage)
- **Components:** Componentes reutilizáveis (ex: LocaisComponent)
- **Api:** Funções para comunicação com backend (ex: eventosApi)
- **Handlers:** Funções de manipulação de eventos (dentro dos componentes)
- **Model:** Entidades e DTOs no backend

---

## Colaboradores

- [LindmanBassi]
- [ThMesquita]

---

## Observações

- Para rodar o frontend, o backend deve estar rodando em `http://localhost:8080`
- O projeto utiliza autenticação JWT e controle de permissões
- Utiliza **JPA** para persistência e **API de CEP** para endereços

---
