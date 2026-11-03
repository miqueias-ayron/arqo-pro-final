<img width="3527" height="663" alt="logo_github_escuro" src="https://github.com/user-attachments/assets/fb5793c2-658a-4e80-8017-d9362f3ca8b0" /> 


[![License](https://img.shields.io/badge/License-MIT-blue)](LICENSE)
![Java](https://img.shields.io/badge/Java-language-ED8B00?logo=openjdk&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-database-4169E1?logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-image-2496ED?logo=docker&logoColor=white)
![Status](https://img.shields.io/badge/Status-em%20produ%C3%A7%C3%A3o-2cb04a)

# Blackboard
O *Blackboard* é um **LMS**, *Learning Management System* ou sistema de gestão de aprendizagem usado por universidades no mundo todo, inclusive pelo Insper, como ambiente virtual das disciplinas, e este repositório contém uma versão simplificada da plataforma, desenvolvida em Java como projeto final da disciplina de *Arquitetura de Objetos e Times Ágeis* - Prof. Eduardo Felipe Zambom Santana

# Em produção
| | Endereço |
|---|---|
| API | https://blackboard-api-latest.onrender.com |
| Documentação (Swagger) | https://blackboard-api-latest.onrender.com/swagger-ui.html |
| Aplicação web | https://projeto-final-arqob-frontend.vercel.app |
| Repositório do front | https://github.com/miqueias-ayron/projeto-final-arqob-frontend |
| Imagem Docker | [`amiqueias/blackboard-api`](https://hub.docker.com/r/amiqueias/blackboard-api) |

A API roda no Render a partir da imagem do Docker Hub, com banco PostgreSQL. A base de produção tem dados de demonstração; as contas de acesso são pessoais e ficam com o administrador do projeto.

No plano gratuito do Render a API dorme depois de 15 minutos sem uso, e a primeira requisição depois disso pode levar perto de 1 minuto.

# Perfis de acesso
| Perfil | Pode |
|---|---|
| `MASTER` | Cadastrar e editar professores, alunos, cursos, salas, disciplinas, turmas, organizações e eventos; matricular alunos; tudo o que o professor faz, em qualquer turma |
| `PROFESSOR` | Nas turmas das disciplinas que leciona: criar avaliações, lançar notas e frequência, ver matrículas e boletins |
| `ALUNO` | Ver as próprias matrículas e boletins; entrar e sair de organizações; inscrever-se e cancelar inscrição em eventos |

A autenticação é por JWT: `POST /login` devolve o token e `GET /me` informa o perfil do usuário logado.

# Como executar
Pré-requisito: JDK 25 ou superior, com o `JAVA_HOME` apontando para ele. Execute os comandos na raiz do projeto.

Crie o arquivo `.env` a partir do `.env.example` e preencha as variáveis. As variáveis `DB_*` são usadas apenas com PostgreSQL.

```powershell
Copy-Item .env.example .env
```

### PostgreSQL
O banco informado em `DB_URL` precisa existir. As tabelas são criadas e atualizadas ao iniciar a aplicação.

```powershell
.\mvnw spring-boot:run
```

### H2
Usa um banco local salvo na pasta `data/`, sem necessidade de instalar um servidor.

```powershell
.\mvnw spring-boot:run -Dspring-boot.run.profiles=h2
```

Com a aplicação em execução, a API fica disponível em `http://localhost:8081` e a documentação em `http://localhost:8081/swagger-ui.html`.

### Docker
A imagem é gerada pelo `Dockerfile` da raiz: o Maven compila o jar com JDK 25 e a imagem final leva só o JRE, rodando sem root. O `.env` não entra na imagem; as configurações vêm de variáveis de ambiente.

```powershell
docker build -t amiqueias/blackboard-api .
docker run -p 8081:8081 --env-file .env amiqueias/blackboard-api
```

# Variáveis de ambiente
| Variável | Uso |
|---|---|
| `DB_URL` | Endereço JDBC do PostgreSQL, ex.: `jdbc:postgresql://host:5432/banco?sslmode=require` |
| `DB_USERNAME`, `DB_PASSWORD` | Usuário e senha do banco |
| `JWT_SECRET` | Segredo de assinatura dos tokens, com no mínimo 32 caracteres |
| `MASTER_EMAIL`, `MASTER_SENHA` | Usuário master criado na primeira inicialização, se ainda não existir |
| `CORS_ALLOWED_ORIGINS` | Origens do front liberadas, separadas por vírgula; padrão `http://localhost:5173`. A aplicação não sobe se a lista estiver vazia |
| `PORT` | Porta HTTP; padrão `8081`. O Render define a sua |
| `JWT_EXPIRATION` | Opcional: validade do token em milissegundos; padrão `3600000` (1 hora) |

# Deploy
A API é publicada no Render como imagem do Docker Hub. Para publicar uma nova versão depois de um merge na `main`:

```powershell
docker build -t amiqueias/blackboard-api:latest .
docker tag amiqueias/blackboard-api:latest amiqueias/blackboard-api:$(git rev-parse --short HEAD)
docker push amiqueias/blackboard-api:latest
docker push amiqueias/blackboard-api:$(git rev-parse --short HEAD)
```

Em seguida, no painel do Render, use **Manual Deploy > Deploy latest reference** para o serviço puxar a imagem nova. A tag com o hash do commit permite voltar a uma versão anterior.

As variáveis de ambiente da tabela acima ficam cadastradas no Render, em **Environment**; `PORT` é definida pelo próprio Render e o perfil `h2` não deve ser usado em produção.

# Testes da API
A pasta `postman/` tem uma collection com testes de todas as rotas, incluindo as regras de permissão de cada perfil. Ela cria os próprios dados a cada execução e pode rodar contra qualquer ambiente:

```powershell
postman collection run postman/blackboard.postman_collection.json --env-var masterEmail=SEU_MASTER --env-var masterSenha=SUA_SENHA
```

Por padrão ela chama `http://localhost:8081`; para outro endereço, acrescente `--env-var baseUrl=https://...`. Como cria e altera registros, rode contra um banco de teste, não contra a produção.

# Entity-Relationship Diagram
<img width="3920" height="5850" alt="banco_atualizado" src="https://github.com/user-attachments/assets/25b96ae8-944a-40d5-8858-c01dab8d2770" />
