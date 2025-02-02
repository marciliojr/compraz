# Changelog - Compraz

## [0.0.1] - Janeiro - 2025
### Adicionado
#### Controllers  
- **`ItemController`**: Implementado endpoints para:
  - Listagem de itens por estabelecimento e período
  - Cálculo de soma de valores unitários
  - Exportação de dados para PDF :contentReference[oaicite:0]{index=0}.
- **`PDFController`**: Criado endpoint para:
  - Upload de arquivos PDF
  - Extração de dados dos PDFs
  - Teste de conexão da API :contentReference[oaicite:1]{index=1}.

#### Serviços  
- **`ItemService`**: Criado serviço para:
  - Listagem de itens
  - Cálculo do valor total por período e estabelecimento :contentReference[oaicite:2]{index=2}.
- **`PDFDataService`**: Implementado processamento de dados extraídos do PDF, persistência no banco de dados e criação automática de estabelecimentos e compras :contentReference[oaicite:3]{index=3}.
- **`PDFGenerationService`**: Criado serviço para geração de relatórios em PDF usando iText :contentReference[oaicite:4]{index=4}.

#### Infraestrutura  
- **`PDFExtractor`**: Criada classe para extração de texto de arquivos PDF utilizando Apache PDFBox :contentReference[oaicite:5]{index=5}.
- **`PDFDataProcessor`**: Implementado processamento de texto extraído de PDFs para identificar itens comprados :contentReference[oaicite:6]{index=6}.
- **`ComprazUtils`**: Adicionadas funções para parsing de datas e sanitização de strings :contentReference[oaicite:7]{index=7}.

#### Modelos de Dados  
- **`Compra`**: Criada entidade representando uma compra, vinculada a um estabelecimento e itens :contentReference[oaicite:8]{index=8}.
- **`Estabelecimento`**: Criada entidade representando um estabelecimento onde as compras são realizadas :contentReference[oaicite:9]{index=9}.
- **`Item`**: Criada entidade representando um item comprado, incluindo quantidade, unidade e valores :contentReference[oaicite:10]{index=10}.
- **`ItemDTO`**: Criado objeto de transferência de dados para representação de itens :contentReference[oaicite:11]{index=11}.

#### Repositórios  
- **`CompraRepository`**: Criado repositório para operações com a entidade `Compra` :contentReference[oaicite:12]{index=12}.
- **`EstabelecimentoRepository`**: Criado repositório para operações com a entidade `Estabelecimento` :contentReference[oaicite:13]{index=13}.
- **`ItemRepository`**: Criado repositório para buscar itens filtrando por estabelecimento e período :contentReference[oaicite:14]{index=14}.

---

Esse changelog reflete o lançamento inicial do **Compraz** com suas principais funcionalidades. 🚀
