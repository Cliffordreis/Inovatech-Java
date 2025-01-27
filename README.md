# Inovatech-Java

# Java Inovatech

Este repositório contém os microservices do sistema Inovatech:

## Microservices

### 1. **StatusPedido Service**
- Porta padrão: `8000`
- Descrição: Gerencia os status dos pedidos e envia atualizações.

### 2. **Financeiro Service**
- Porta padrão: `9090`
- Descrição: Processa e gerencia atualizações do financeiro

### 3. **Java-Inovatech**
- Porta padrão: `8080`
- Descrição: Serviço principal 

## Como executar
Cada microservice tem um `pom.xml` independente. Para rodar.

# Sobre:
>  A Inovatech é uma loja especializada na venda de eletrônicos. Esta documentação descreve o funcionamento da loja, incluindo informações sobre produtos, gerenciamento de pedidos e controle de estoque de forma eficiente e segura.
Com um sistema moderno e suporte a diversas funcionalidades, a Inovatech facilita a experiência de compra dos clientes e a gestão operacional da loja.

# Introdução:

> A aplicação facilita a compra de dispositivos móveis, proporcionando uma experiência intuitiva e segura para os clientes.

# Regras de uso:

> O limite de compra está vinculado ao estoque disponível, garantindo que apenas produtos em estoque possam ser adquiridos.

# Realizando compras:

Em poucas etapas, explicaremos como a compra é feita pela Inovatech.

+ ### Fluxo de aprovação do pedido:

1. O pedido é criado na Inovatech.
2. Os dados do pedido são enviados para o microserviço financeiro para aprovação.
3. O microserviço financeiro aprova o pedido e aciona o endpoint correspondente.
4. O microserviço financeiro envia o status "Aprovado" para o microserviço StatusPedido.
5. O microserviço StatusPedido repassa as informações para a Inovatech, onde o status é salvo na tabela StatusCache.

+ ### Fluxo de Atualização do Pedido:

1. O microserviço StatusPedido recebe da transportadora uma atualização do pedido (como "Enviado" ou "Entregue") via endpoint.
2. O microserviço StatusPedido repassa o status atualizado para a Inovatech.
3. A Inovatech salva o novo status na tabela StatusCache.

# Parâmetros da Requisição - EndPoints:

### Financeiro Aprovado:

<pre>
<code>
{
  "pedidoId": 1001,
  "statusDescricao": "Pagamento confirmado"
}
</code>
</pre>

### Financeiro Negado:

<pre>
<code>
{
 "pedidoId": 1001,
 "statusDescricao": "Pagamento negado"
}
</code>
</pre>

### Transportadora (Enviado):

<pre>
<code>
{
 "pedidoId": 1000,
 "statusDescricao": "Pedido enviado"
}
</code>
</pre>

### Transportadora (Entregue):

<pre>
<code>
{
 "pedidoId": 1000,
 "statusDescricao": "Pedido entregue"
}
</code>
</pre>

# Respostas pedidos - MATRIZ:

<table>
  <thead>
    <tr>
      <th>Parâmetro</th>
      <th>Tipo</th>
      <th>Descrição</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>pedidoId</td>
      <td>INT</td>
      <td>Identificador único do pedido.</td>
    </tr>
    <tr>
      <td>statusDescricao</td>
      <td>VARCHAR</td>
      <td>Descrição do status do pedido.</td>
    </tr>
    <tr>
      <td>ultimaAtualizacao</td>
      <td>DATETIME</td>
      <td>Data e hora da última atualização do status.</td>
    </tr>
    <tr>
      <td>Produto_idProduto</td>
      <td>INT</td>
      <td>Identificador único do produto.</td>
    </tr>
    <tr>
      <td>ProdutoDescricao</td>
      <td>VARCHAR</td>
      <td>Nome ou descrição do produto.</td>
    </tr>
    <tr>
      <td>ProdutoValor</td>
      <td>DECIMAL</td>
      <td>Preço do produto.</td>
    </tr>
    <tr>
      <td>ProdutoQuantidade</td>
      <td>INT</td>
      <td>Quantidade disponível em estoque.</td>
    </tr>
    <tr>
      <td>Cliente_idCliente</td>
      <td>INT</td>
      <td>Identificador único do cliente.</td>
    </tr>
    <tr>
      <td>NomeCliente</td>
      <td>VARCHAR</td>
      <td>Nome do cliente que fez o pedido.</td>
    </tr>
    <tr>
      <td>EmailCliente</td>
      <td>VARCHAR</td>
      <td>E-mail do cliente.</td>
    </tr>
    <tr>
      <td>PedidoValor</td>
      <td>DECIMAL</td>
      <td>Valor total do pedido.</td>
    </tr>
    <tr>
      <td>PedidoDataHora</td>
      <td>DATETIME</td>
      <td>Data e hora do pedido.</td>
    </tr>
  </tbody>
</table>

# Dependências Java - Inovatech:

- Spring Boot e Módulos
- Spring Boot
- Spring AMQP (RabbitMQ) - para trabalhar com filas RabbitMQ.
- Spring Security - para autenticação e autorização.
- Spring Data JPA - para persistência e gerenciamento de banco de dados.
- Spring Thymeleaf - para renderizar páginas HTML no servidor.
- Spring Web - para criar APIs RESTful e manipular requisições HTTP.
- Spring DevTools - para acelerar o desenvolvimento com hot reload.

# Banco de Dados

- MySQL Connector - para conexão com o banco de dados MySQL.
- Jackson (Manipulação de JSON)
- Jackson Databind - para serialização e desserialização de objetos JSON.
- Jackson Core - núcleo do Jackson para manipulação de JSON.
- Jackson XML Annotations - suporte a anotações XML.
- Jackson JSR310 - suporte para manipulação de datas com Java 8+ (Date and Time API).
  
# Lombok

- Lombok - para gerar automaticamente métodos como getter, setter, equals, etc., reduzindo código repetitivo.
