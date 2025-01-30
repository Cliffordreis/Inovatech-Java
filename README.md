# Inovatech-Java

**OBS: comandos sql para pleno funcionamento disponivel no fim do REDME.md para facilitar simulação do ambiente, pois foi criado pensando nele, então use-o!**

# Java Inovatech

Este repositório contém os microservices do sistema Inovatech:

## Microservices

### 1. **StatusPedido Service**
- Porta padrão: `8000`
- Descrição: Gerencia os status dos pedidos e envia atualizações.

### 2. **Financeiro Service**
- Porta padrão: `9090`
- Descrição: Processa e gerencia atualizações do financeiro.

### 3. **Email Service**
- Porta padrão: `8081`
- Descrição: Recebe e envia dados de criação de contas e pedidos para o email cadastrado.
  
### 4. **Java-Inovatech**
- Porta padrão: `8080`
- Descrição: Serviço principal 

## Como executar
Todos os projetos estão funcionando no **Java 17**, cada microservice tem de ser executado individualmente, assim como execusão de endpoints post nos endereços certos.

Todos os arquivos foram criados no Intellij, com excessão do micro serviço `email` (situação no dispositivo de quem desenvolveu), porém totalmente compatível com o Intellij.

*Serviço principal: Inovatech: http://localhost:8080*

# Sobre:
A Inovatech é uma loja especializada na venda de eletrônicos. Esta documentação descreve o funcionamento da loja, incluindo informações sobre produtos, gerenciamento de pedidos e controle de endpoints.

# Modelo Lógico:
![modelo logico inovatech](https://github.com/user-attachments/assets/04f006f4-a795-4ad7-aacc-311d1b7e1b83)
# Regras de uso:

> O limite de compra está vinculado ao estoque disponível, garantindo que apenas produtos em estoque possam ser adquiridos.
****quantidade de 100 unidades por dispositivo setado pelo arquivo inovatech.sql****

# Realizando compras:

+ ### Fluxo de aprovação do pedido:

1. O pedido é criado na Inovatech.
2. Os dados do pedido são enviados para o microserviço financeiro para aprovação.
3. O microserviço financeiro aprova o pedido através do endpoint correspondente.
4. O microserviço financeiro envia o status "Aprovado" para o microserviço StatusPedido.
5. O microserviço StatusPedido repassa as informações para a Inovatech, onde o status é salvo na tabela StatusCache.

+ ### Fluxo de Atualização do Pedido:

1. O microserviço StatusPedido recebe da transportadora uma atualização do pedido (como "Enviado" ou "Entregue") via endpoint.
2. O microserviço StatusPedido repassa o status atualizado para a Inovatech.
3. A Inovatech salva o novo status na tabela StatusCache.

+ ### Fluxo de envio de emails:

1. O Cadastro ou Pedido é criado no Inovatech.
2. O microserviço email recebe as informações e o encaminha formatado para o email cadastrado.

# Parâmetros da Requisição - EndPoints:

### Financeiro Aprovado:
http://localhost:9090/pagamentos/confirmacao/{pedidoid}

<pre>
<code>
{
  "pedidoId": {pedidoid},
  "statusDescricao": "Pagamento confirmado"
}
</code>
</pre>

### Financeiro Negado:
http://localhost:9090/pagamentos/confirmacao/{pedidoid}
<pre>
<code>
{
 "pedidoId": {pedidoid},
 "statusDescricao": "Pagamento negado"
}
</code>
</pre>

### Transportadora (Enviado):
http://localhost:8000/transportadora/atualizacao/{pedidoid}
<pre>
<code>
{
 "pedidoId": {pedidoid},
 "statusDescricao": "Pedido enviado"
}
</code>
</pre>

### Transportadora (Entregue):
http://localhost:8000/transportadora/atualizacao/{pedidoid}
<pre>
<code>
{
 "pedidoId": {pedidoid},
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
- Spring Security - para autenticação, criptografia das senhas e autorização.
- Spring Data JPA - para persistência e gerenciamento de banco de dados.
- Spring Thymeleaf - para renderizar páginas HTML no servidor.
- Spring Web - para criar APIs RESTful e manipular requisições HTTP.
- Spring DevTools - para acelerar o desenvolvimento com hot reload.
- Java Mail Sender - para envio de emails no spring boot via SMTP.

Permite o envio de e-mails via SMTP.

# Banco de Dados

- MySQL Connector - para conexão com o banco de dados MySQL.
- Jackson (Manipulação de JSON)
- Jackson Databind - para serialização e desserialização de objetos JSON.
- Jackson Core - núcleo do Jackson para manipulação de JSON.
- Jackson XML Annotations - suporte a anotações XML.
- Jackson JSR310 - suporte para manipulação de datas com Java 8+ (Date and Time API).
  
# LomBok

- LomBok - para gerar automaticamente métodos como getter, setter, equals, etc., reduzindo código repetitivo.

# Comandos SQL para pleno funcionamento:
<pre>
<code>
create database inovatechj;
  
- Após criação do db, iniciar o Inovatech para o Hibernate criar as tabelas.
  
insert into produto (produto_descricao,produto_quantidade,produto_valor)
values('Redmi Note 13','100','1299.99'),
('Oppo Find X6 Pro','100','2499.99'),
('Realm Note 50','100','699.99');


</code>
</pre>

# Vídeo demonstrativo:
Vídeo com as principais funcionalidades do sistema, mostrando-o em plena funcionalidade.
https://youtu.be/R2py-SjuNRs
