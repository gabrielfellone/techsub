
# TECH SUB FASE 03

**Pós 2ADJT**

Aluno: Gabriel Fellone  
RM: 350771

---

## Objetivo 

Desenvolver um sistema robusto e eficiente de agendamento e gerenciamento
para serviços de beleza e bem-estar, aplicando os conceitos avançados de arquitetura
de software, desenvolvimento orientado a testes e práticas de código limpo.

---

## Desenho e Documentação do Projeto

- [**Desenho da solução** ](https://miro.com/app/board/uXjVKrPLMaQ=/?share_link_id=624660829579)

---

## Configurar o projeto e testar


O arquivo **init.sql** é a inicialização das tabelas, já com dados criados. Será criado assim que subir o container.
Este arquivo **`init.sql`** esta na raiz do projeto e deve estar no mesmo diretório do arquivo docker-compose.yml


### Subir o container, usar o comando na pasta do **docker-compose.yml** que esta na raiz do projeto:

**`docker-compose up`**


- Swagger:

http://localhost:8080/swagger-ui/index.html#/


---

## Sobre a solução:


**Para verificar o Coverage do projeto**, basta acessar o arquivo: **`/techsub/htmlReport/index.html`** 

Foram criados os testes unitários para melhor cobertura de teste.

Criado também na esteira o step de Continuous Integration (CI) onde todo push nas branchs develop, homol e master será executado os testes.

https://github.com/gabrielfellone/techsub/actions


### Coverage do projeto como todo

| Package                     | Class, %      | Method, %    | Line, %     |
|-----------------------------|---------------|--------------|-------------|
| all classes                 | 80% (12/15)   | 37,2% (29/78)| 17,1% (49/287) |


### Coverage por classes

| Package                                  | Class, %      | Method, %    | Line, %     |
|------------------------------------------|---------------|--------------|-------------|
| com.sub.techsub                         | 100% (1/1)    | 100% (2/2)   | 100% (2/2)   |
| com.sub.techsub.controller               | 100% (4/4)    | 47,1% (8/17) | 30,8% (8/26) |
| com.sub.techsub.entity                   | 0% (0/2)      | 0% (0/2)     | 0% (0/9)     |
| com.sub.techsub.service                  | 100% (5/5)    | 27,5% (11/40)| 7% (12/171)  |
| com.sub.techsub.service.schedules        | 100% (1/1)    | 50% (3/6)    | 64,3% (9/14) |
| com.sub.techsub.utils                    | 50% (1/2)     | 45,5% (5/11) | 27,7% (18/65) |



---


## Arquitetura do Código (Resumida)

- **`controller`**
  Contém as classes responsáveis pelas chamadas de API, camada de aplicação. Inclui endpoints, requests e responses.

- **`entity`**
  Define as classes de entidades e domínios do sistema.

- **`exception`**
  Define exceções personalizadas.

- **`integration`**
  Classe para realizar integração com clients 

- **`repository`**
  Inclui classes/interfaces/entidades relacionadas ao banco de dados.

- **`service`**
  Implementa classes de serviços para manipular os domains e aplicar regras de negócio e schedules

- **`utils`**
  Classes de utilidades para regra de negocio

  
