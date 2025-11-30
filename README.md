# EvT---Eventos-Tech

# Sistema de Gerenciamento de Eventos

Este repositório contém o sistema de gerenciamento de eventos com front-end em **React**, back-end em **Java/Spring Boot** e banco de dados **Aurora and RDS**, totalmente hospedado na **AWS**.

O projeto inclui todo o ciclo de desenvolvimento: requisitos, análise e design, implementação, testes, implantação e gerenciamento de projeto. O **Guia de Implantação** substitui o playbook tradicional, fornecendo instruções detalhadas para replicar o ambiente na nuvem.

---

## 📑 Sumário

- [Estrutura do Repositório](#estrutura-do-repositório)  
- [Conteúdo do Projeto](#conteúdo-do-projeto)  
- [Arquitetura do Sistema](#arquitetura-do-sistema)  
- [Guia de Implantação](#guia-de-implantação)  
  - [Etapa 1 – Criação da VPC](#etapa-1---criação-da-vpc)  
  - [Etapa 2 – Criar Security Groups (SGs)](#etapa-2---criar-security-groups-sgs)  
  - [Etapa 3 – Criar Aurora and RDS](#etapa-3---criar-aurora-and-rds)  
  - [Etapa 4 – Implantar Back-End (Elastic Beanstalk)](#etapa-4---implantar-back-end-elastic-beanstalk)  
  - [Etapa 5 – Criação do Usuário Gerente](#etapa-5---criação-do-usuário-gerente-acesso-inicial-ao-sistema)  
  - [Etapa 6 – Implantar Front-End (S3)](#etapa-6---implantar-front-end-s3-bucket)  
  - [Etapa 7 – Habilitar Acesso SSH e Consultar Logs](#etapa-7---habilitar-acesso-ssh-e-consultar-logs-da-instância-elastic-beanstalk)  
  - [Etapa 8 – Atualizar Back-End](#etapa-8---atualizar-back-end-elastic-beanstalk-com-novo-jar-e-ajustar-cors)  
- [Instruções de Instalação Rápida](#instruções-de-instalação-rápida)  
- [Componentes do Sistema](#componentes-do-sistema)  
- [Cronograma de Implantação](#cronograma-de-implantação)  
- [Contato e Suporte](#contato-e-suporte)  

---

## Estrutura do Repositório

```

.github
│ └── ISSUE_TEMPLATE
│     └── template-padrão.md
│ └── pull_request_template.md
1.Requisitos
│ ├── Casos de Uso
│ │   ├── EvT - Especificações_e_Caso_de_Uso.docx
│ │   ├── EvT - História de Usuário 8_UC 8_Manter Usuário.docx
│ │   ├── História de Usuário 1_UC 1_Cadastrar Conta.docx
│ │   ├── História de Usuário 3_UC 3_Manter Funcionário.docx
│ │   ├── História de Usuário 6_UC 6_Participar de um Evento.docx
│ │   ├── História de Usuário 7_UC 7_Visualizar lista de eventos.docx
│ │   ├── História do Usuário 2_UC 2_Realizar login.docx
│ │   ├── História do Usuário 4_UC 4_Manter local.docx
│ │   ├── História do usuário 5_UC_5_Manter evento.docx
│ │   └── EvT - Visão.docx
2.Analise e Design
│ └── EvT - Modelo Analise e Design.asta
3.Implementacao
│ ├── EvT - BackEnd
│ ├── EvT - FrontEnd
│ └── Evt - Manual do Usuário.mp4
4.Teste
│ ├── .gitkeep
│ ├── EvT - Evidências de Testes.docx
│ ├── EvT - Massa de Teste.sql
│ └── EvT - Roteiro de Teste.xlsx
5.Implantação
│ ├── .gitkeep
│ └── EvT - Guia de Implantação.docx
6.Gerenciamento de Projeto
│ ├── Atas
│ ├── EVT - Planilha de Planejamento e Controle do Projeto.xlsx
│ └── EvT - Checklist_Verificacao_de_Projeto.xlsx
.gitattributes
.gitmodules
README.md

````

---

## Conteúdo do Projeto

### 1. Requisitos
- Documentação de casos de uso e histórias de usuário detalhadas  
- Arquivos de visão geral e especificações do sistema  
- Garantia de cobertura de todos os requisitos funcionais

### 2. Análise e Design
- Modelos de análise e design que descrevem a arquitetura do sistema  

### 3. Implementação
- **Back-end:** código-fonte Java/Spring Boot  
- **Front-end:** código-fonte React  
- **Manual do usuário:** vídeo explicativo (`Evt - Manual do Usuário.mp4`)

### 4. Teste
- Evidências de testes (`EvT - Evidências de Testes.docx`)  
- Massa de teste SQL  
- Roteiro de teste em Excel

### 5. Implantação
- Guia de Implantação detalhado (`EvT - Guia de Implantação.docx`)  
- Arquivos de configuração AWS para replicar o ambiente

### 6. Gerenciamento de Projeto  
- Planilha de planejamento e controle do projeto  
- Checklist de verificação do projeto

---

## Arquitetura do Sistema

<img width="706" height="488" alt="evt-arquitetura" src="https://github.com/user-attachments/assets/46503f44-1c7f-4094-ae05-9928f03be451" />

*Figura 1 – Arquitetura da infraestrutura na AWS.*

Componentes principais:

- **VPC:** rede virtual isolada  
- **Elastic Beanstalk:** hospedagem do back-end  
- **Aurora and RDS (MySQL):** banco de dados Multi-AZ  
- **Amazon S3:** hospedagem do front-end  
- **Security Groups:** regras de acesso e isolamento da rede

---

## Guia de Implantação

### Etapa 1 — Criação da VPC
- Criar rede virtual isolada com sub-redes públicas e privadas  
- Configurar Internet Gateway  

### Etapa 2 — Criar Security Groups (SGs)
- **evt-sg-beanstalk:** acesso HTTP/HTTPS público ao back-end  
- **evt-sg-aurora:** acesso restrito ao Aurora and RDS apenas pelo back-end

### Etapa 3 — Criar Aurora and RDS
- Criar banco de dados Multi-AZ  
- Configurar username, senha, backup e criptografia  
- Integrar com back-end via VPC e SG

### Etapa 4 — Implantar Back-End (Elastic Beanstalk)
- Criar ambiente Java Web  
- Fazer upload do `.jar` da aplicação  
- Configurar variáveis de ambiente para conexão com Aurora and RDS

### Etapa 5 — Criação do Usuário Gerente (Acesso Inicial ao Sistema)
- Inserir manualmente usuário administrador no Aurora and RDS  
- Validar com SELECT no banco

### Etapa 6 — Implantar Front-End (S3 Bucket)
- Criar bucket S3  
- Configurar permissões de leitura pública e CORS  
- Fazer upload da pasta `/build` do React

### Etapa 7 — Habilitar Acesso SSH e Consultar Logs
- Criar Key Pair  
- Associar à instância EC2 do Elastic Beanstalk  
- Acessar logs da aplicação e do NGINX para diagnóstico

### Etapa 8 — Atualizar Back-End (Elastic Beanstalk)
- Gerar novo `.jar` com CORS configurado  
- Subir atualização no Elastic Beanstalk  
- Validar integração com front-end

---

## Instruções de Instalação Rápida

1. Clonar o repositório:

```bash
git clone https://github.com/usuario/projeto.git
cd projeto
````

2. Seguir o **Guia de Implantação** para configurar a AWS
3. Subir o backend:

```bash
mvn clean package -DskipTests
# Upload do .jar no Elastic Beanstalk
```

4. Subir o frontend:

```bash
npm install
npm run build
# Upload da pasta /build para o bucket S3
```

5. Testar funcionalidades principais: cadastro, login, listagem e inscrição em eventos

---

## Componentes do Sistema

| Componente     | Tecnologia           | Função                                                            |
| -------------- | -------------------- | ----------------------------------------------------------------- |
| Back-end       | Java/Spring Boot     | Processamento da lógica de eventos e API REST                     |
| Front-end      | React                | Interface de usuário para gerenciamento e visualização de eventos |
| Banco de dados | Aurora and RDS MySQL | Armazenamento de usuários, eventos e inscrições                   |
| Hospedagem     | Elastic Beanstalk    | Gerenciamento do back-end escalável                               |
| Storage        | Amazon S3            | Hospedagem de arquivos estáticos do front-end                     |
| Rede           | VPC, Security Groups | Isolamento e segurança de acesso                                  |

---

## Cronograma de Implantação

| Atividade                    | Data          | Responsáveis          |
| ---------------------------- | ------------- | --------------------- |
| Criação da VPC               | 04/11/2025    | Maria Clara e Vitória |
| Criação do Aurora and RDS    | 04/11/2025    | Maria Clara e Vitória |
| Teste do banco               | 06/11/2025    | Maria Clara e Vitória |
| Elastic Beanstalk (back-end) | 06–11/11/2025 | Maria Clara e Vitória |
| S3 (front-end)               | 11–24/11/2025 | Maria Clara e Vitória |
| Teste final                  | 25/11/2025    | Maria Clara e Vitória |

---

## Contato e Suporte

* Equipe responsável: Maria Clara e Vitória
* Suporte via logs do Elastic Beanstalk e monitoramento AWS


