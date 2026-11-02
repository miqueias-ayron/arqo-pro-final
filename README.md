<img width="3527" height="663" alt="logo_github_escuro" src="https://github.com/user-attachments/assets/fb5793c2-658a-4e80-8017-d9362f3ca8b0" /> 


[![License](https://img.shields.io/badge/License-MIT-blue)](LICENSE)
![Java](https://img.shields.io/badge/Java-language-ED8B00?logo=openjdk&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-database-4169E1?logo=postgresql&logoColor=white)
![Status](https://img.shields.io/badge/Status-em%20desenvolvimento-yellow)

# Blackboard
O *Blackboard* é um **LMS**, *Learning Management System* ou sistema de gestão de aprendizagem usado por universidades no mundo todo, inclusive pelo Insper, como ambiente virtual das disciplinasm e este repositório contém uma versão simplificada da plataforma, desenvolvida em Java como projeto final da disciplina de *Arquitetura de Objetos e Times Ágeis* - Prof. Eduardo Felipe Zambom Santana

# Como executar
Pré-requisito: JDK 25 ou superior, com o `JAVA_HOME` apontando para ele. Execute os comandos na raiz do projeto.

Crie o arquivo `.env` a partir do `.env.example` e preencha as variáveis. As variáveis `DB_*` são usadas apenas com PostgreSQL.

```powershell
Copy-Item .env.example .env
```

### PostgreSQL
O banco informado em `DB_URL` precisa existir. As tabelas são criadas ao iniciar a aplicação.

```powershell
.\mvnw spring-boot:run
```

### H2
Usa um banco local salvo na pasta `data/`, sem necessidade de instalar um servidor.

```powershell
.\mvnw spring-boot:run -Dspring-boot.run.profiles=h2
```

Com a aplicação em execução, a API fica disponível em `http://localhost:8081` e a documentação em `http://localhost:8081/swagger-ui.html`.

# Entity-Relationship Diagram
<img width="3920" height="5850" alt="banco_atualizado" src="https://github.com/user-attachments/assets/25b96ae8-944a-40d5-8858-c01dab8d2770" />

