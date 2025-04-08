# 🛒 Compraz - Sistema de Gestão de Cupons Fiscais

## 📝 Descrição
Compraz é uma aplicação web desenvolvida em Spring Boot para gerenciamento de compras mensais. O sistema permite o controle e organização de compras, oferecendo uma interface RESTful para gerenciar os dados de forma eficiente.

## 🚀 Tecnologias Utilizadas
- Java 17
- Spring Boot 3.4.0
- Spring Data JPA
- MariaDB
- Lombok
- OpenAPI/Swagger
- iText (Manipulação de PDFs)
- PDFBox
- Jsoup

## 🛠️ Pré-requisitos
- Java JDK 17 ou superior
- Maven 3.6.0 ou superior
- MariaDB (para ambiente de produção)
- H2 Database (para testes)

## ⚙️ Configuração do Ambiente

### Instalação
1. Clone o repositório
```bash
git clone https://github.com/marciliojr/compraz.git
```

2. Navegue até o diretório do projeto
```bash
cd compraz
```

3. Execute o projeto
```bash
./mvnw spring-boot:run
```

### Configuração do Banco de Dados
O projeto utiliza MariaDB como banco de dados principal e H2 para testes. As configurações do banco de dados podem ser ajustadas no arquivo `application.properties`.

## 📚 Documentação da API
A documentação da API está disponível através do Swagger UI em http://localhost:sua_porta/swagger-ui.html

## 🏗️ Estrutura do Projeto
src/main/java/com/marciliojr/compraz/
├── controller/ # Controladores REST
├── service/ # Lógica de negócios
├── repository/ # Camada de acesso a dados
├── model/ # Entidades e DTOs
└── infra/ # Configurações e utilitários


## 🔧 Funcionalidades Principais
- Gerenciamento de compras mensais
- Geração de relatórios em PDF
- API RESTful documentada
- Validação de dados
- Suporte a HATEOAS

## �� Testes
Para executar os testes do projeto:
```bash
./mvnw test
```

## 📦 Build
Para gerar o arquivo JAR executável:
```bash
./mvnw clean package
```

## 📄 Licença
Este projeto está sob a licença  MIT license.

## ✒️ Autor
Marcilio Jr - @marciliojr
---
