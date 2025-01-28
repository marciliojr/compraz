# 🛒 Compraz - Sistema de Gestão de Cupons Fiscais

O **Compraz** é um sistema para gerenciar notas fiscais e compras realizadas em estabelecimentos. Ele permite extrair dados de cupons fiscais em PDF, armazená-los em um banco de dados e consultá-los através de uma API REST.

## 📌 Funcionalidades

- 📂 Upload de cupons fiscais em PDF e extração de dados;
- 📊 Armazenamento de compras, estabelecimentos e itens no banco de dados;
- 🔎 Consultas por período e estabelecimento;
- 🏪 Cadastro e gerenciamento de estabelecimentos;
- 📄 API REST para acesso e consulta de dados.

## 🏗️ Arquitetura do Projeto

O projeto segue uma **arquitetura em camadas**, utilizando **Spring Boot** e **JPA**. As principais camadas são:

- **Model**: Representação das entidades `Compra`, `Estabelecimento` e `Item`.
- **DTO**: Objetos de transferência de dados (`ItemDTO`).
- **Repository**: Interfaces para acesso ao banco de dados com Spring Data JPA.
- **Service**: Regras de negócio e processamento de dados dos cupons fiscais.
- **Controller**: Exposição de endpoints REST.


## 🛠️ Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3+**
- **Spring Data JPA**
- **Hibernate**
- **Apache PDFBox** (extração de texto de PDFs)
- **Maven** (gerenciamento de dependências)
- **Banco de Dados H2/MariaDB** (configuração ajustável)

## 🚀 Como Executar o Projeto

### 1️⃣ Clonar o Repositório

```sh
git clone https://github.com/seu-usuario/compraz.git
cd compraz

2️⃣ Configurar o Banco de Dados
Defina as configurações do banco de dados no arquivo application.properties:

spring.datasource.url=jdbc:h2:mem:compraz
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update

3️⃣ Construir e Rodar a Aplicação
mvn clean install
mvn spring-boot:run

📝 Melhorias Futuras
📌 Integração com APIs de consulta de notas fiscais eletrônicas (NFC-e).
📊 Dashboard interativo para visualização de gastos.
📱 Aplicação mobile para consulta rápida de compras.
🤝 Contribuições
Fique à vontade para contribuir com melhorias! Faça um fork do repositório, crie uma branch com suas alterações e envie um pull request. 🚀

📌 Desenvolvido por Marcíliojr
📧 Entre em contato: @marciliojr
