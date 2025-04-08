
# PharmaManager

**PharmaManager** é um sistema desktop desenvolvido em **Java** com **JavaFX**, voltado para o gerenciamento de medicamentos e fornecedores em uma farmácia. O sistema permite cadastro, edição, exclusão, listagem, geração de relatórios e exportação de dados em CSV.

## Funcionalidades

- **Cadastro e edição de medicamentos** com campos como nome, descrição, princípio ativo, preço, estoque, validade e indicação de controle especial.
- **Cadastro e edição de fornecedores**, com validação de CNPJ.
- **Listagem de medicamentos e fornecedores** em tabelas com suporte a exclusão e atualização.
- **Relatórios**:
  - Medicamentos próximos ao vencimento (próximos 30 dias)
  - Estoque baixo (menos de 5 unidades)
  - Valor total do estoque por fornecedor
- **Exportação e importação em formato CSV**
- Interface gráfica intuitiva com navegação entre telas

## Pré-requisitos

- Java JDK 17 ou superior   
- IDE recomendada: **IntelliJ IDEA** ou **Eclipse** (com suporte a JavaFX)  

## Como Rodar o Projeto

1. **Clone o repositório ou extraia o arquivo ZIP do projeto:**

```bash
git clone https://github.com/lfernando-cn/pharma-manager-javafx.git
cd pharma-manager-javafx
```

2. **Execute a classe principal:**

```bash
src/main/java/com/pharmamanager/Main.java
```

> Essa classe inicializa a aplicação com o menu principal e navegação para as demais telas.

---

## Como Usar

Após executar o projeto, você terá acesso ao **Menu Principal**, com as seguintes opções:

- **Cadastrar Medicamento**
- **Cadastrar Fornecedor**
- **Listar Medicamentos**
- **Relatórios**

Cada tela permite inserir, visualizar ou modificar os dados com interação simples e direta.

---

## Estrutura do Projeto

- `model/` – Classes de negócio: `Medicamento`, `Fornecedor`
- `controller/` – Controladores das telas JavaFX
- `repository/` – Armazenamento em memória simulando persistência
- `util/` – Utilitários para alertas, validação de CNPJ e manipulação CSV
- `resources/` – Arquivos `.fxml` da interface

---

## Autor

Desenvolvido por Luís Fernando  
GitHub: [https://github.com/lfernando-cn](https://github.com/lfernando-cn)
