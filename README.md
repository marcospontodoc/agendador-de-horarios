# agendador-de-horarios

# API de Agendamento de Horários

## Sobre o Projeto

Esta aplicação consiste em uma API REST desenvolvida em **Java** utilizando **Spring Boot**, com o objetivo de realizar o gerenciamento de agendamentos de horários.

A API permite cadastrar, consultar, alterar e remover agendamentos, além de validar conflitos de horários para um mesmo serviço.

O projeto foi desenvolvido com foco na aplicação dos conceitos de arquitetura em camadas, desenvolvimento de APIs REST, persistência de dados com Spring Data JPA e testes unitários utilizando JUnit 5 e Mockito.

---

# Tecnologias Utilizadas

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- Maven
- Lombok
- JUnit 5
- Mockito
- Docker

---

---

# Funcionalidades

- Cadastro de novos agendamentos
- Consulta de agendamentos por dia
- Alteração de agendamentos existentes
- Exclusão de agendamentos
- Validação para impedir conflitos de horários para o mesmo serviço

---

# Regra de Negócio

Ao cadastrar um novo agendamento, a aplicação verifica se já existe outro agendamento para o mesmo serviço no mesmo horário.

Caso exista, a API retorna uma exceção informando que o horário já está ocupado.

---

# Endpoints

## Criar Agendamento

**POST**

```
/agendamentos
```

### Exemplo de Body

```json
{
  "servico": "Corte",
  "profissional": "João",
  "cliente": "Marcos",
  "telefoneCliente": "85999999999",
  "dataHoraAgendamento": "2026-07-10T14:00:00"
}
```

---

## Buscar Agendamentos por Dia

**GET**

```
/agendamentos?data=2026-07-10
```

---

## Alterar Agendamento

**PUT**

```
/agendamentos
```

### Exemplo de Body

```json
{
  "servico": "Barba",
  "profissional": "Carlos",
  "cliente": "Marcos",
  "telefoneCliente": "85999999999",
  "dataHoraAgendamento": "2026-07-10T15:00:00"
}
```

---

## Excluir Agendamento

**DELETE**

```
/agendamentos
```

### Parâmetros

- `cliente`
- `dataHoraAgendamento`

---

# Testes Unitários

O projeto possui testes unitários desenvolvidos utilizando:

- JUnit 5
- Mockito

Os testes validam a lógica da camada de serviço sem necessidade de conexão com banco de dados ou inicialização do contexto do Spring.

### Cenários testados

- Cadastro de agendamento com sucesso
- Tentativa de cadastro em horário já ocupado
- Busca de agendamentos por dia
- Alteração de agendamento
- Exclusão de agendamento

---

# Docker

O projeto pode ser executado utilizando Docker.

## Construir a imagem

```bash
docker build -t agendamento-api .
```

## Executar o container

```bash
docker run -p 8080:8080 agendamento-api
```

A aplicação ficará disponível em:

```
http://localhost:8080
```

---

# Como Executar Localmente

## 1. Clone o repositório

```bash
git clone <URL_DO_REPOSITORIO>
```

## 2. Acesse a pasta do projeto

```bash
cd agendamento-horario
```

## 3. Compile o projeto

```bash
mvn clean install
```

## 4. Execute a aplicação

```bash
mvn spring-boot:run
```

---

