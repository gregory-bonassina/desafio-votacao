<p align="center">
  <h1 align="center">Sistema de votação para Assembleia</h1>
</p>

- [Sobre o Projeto](#sobre-o-projeto)
- [Funcionalidades](#funcionalidades)
- [Tecnologias](#tecnologias)
- [Pré-Requisitos](#pré-requisitos)
- [Inicializar](#inicializar)
- [Manual da API](#manual-da-api)
- [Algumas Regras](#algumas-regras)
- [Considerações finais](#considerações-finais)
- [Versionamento da API](#versionamento-da-api)

## Sobre o Projeto

Este projeto tem como finalidade didatica implementar um sistema para votação de assembleias por meio de associados.

## Funcionalidades

O sistema permite:

- Cadastrar e consultar associados
- Cadastrar e consultar pautas
- Cadastrar e consultar sessões das pautas
- Registrar votos para sessões ativas
- Consultar CPF Validos ( randomicamente )
- Registrar votos dos associados nas pautas durante as assembleias

## Tecnologias

O sistema foi desenvolvido utilizando as seguintes tecnologias:

| Objetivo | Tecnologia |
| ------ | ------ |
| Linguagem de programação | Java 21 |
| Banco de Dados | PostgreSQL |
| Framework | SpringBoot  |
| Documentação | Swagger  |
| Integração entre APIs | FeignClient  |
| Testes de Integração | MockMvc  |

## Pré-Requisitos

Para começar é preciso que você tenha o Docker instalado na sua máquina.

## Inicializar

Após ter o Docker instalado em sua máquina, certifique-se que ele está inicializado e digite a seguinte linha de comando na raiz do seu projeto.

`````
docker-compose up -d
`````

Isso irá fazer com que suba o serviço de banco de dados em container a aplicação já estará pronta para persistir os dados.

## Manual da API

Você pode realizar as requisições por meio do seu API Client de preferência como Postman, Insomnia, Apidog, ou pode utilizar a interface da Documentação do Swagger, onde estão documentadas todos os endpoints disponíves, ela está disponível no endereço abaixo <br>
[Swagger](http://localhost:8080/swagger-ui/index.html)

`````
http://localhost:8080/swagger-ui/index.html
`````

## Algumas Regras

- Sistema pertmite o cadastro de Associados, onde estes só poderão votar em sessões ativas das pautas.
- Não é permitido cadastrar associados com mesmo cpf
- Não é permitido cadastrar associados com cpf inválido
- Somente é possível ter uma sessão ativa por pauta.
- Somente é possível votar em sessões abertas.
- Cada associado pode votar somente uma vez por sessão
- Pautas poderão ter nova contagem de votos, caso seja aberta uma nova sessão para a mesma


## Considerações finais

Sistema foi criado com intuíto dos associados poderem votar em assembleias.

Foram inseridas validações, teste unitários e de integração para os métodos

Utilizado `Feign Client` para validação aleatória de um CPF, foi criado um endpoint dentro da memsa API para fazer a simulação de chamada para API externa.

Utilizado banco `H2` que é um banco em memória para realização dos testes.

Swagger foi utilizado para fazer a organização e documentação da API.

Nas classes de serviço foram utilizados interfaces, estas foram injetadas nos controllers para proteger e isolar métodos internos que expõem os repositorys.

## Versionamento da API

Optei por utilizar o versionamento da API por URL, esta tem algumas vantagens.

- Visibilidade Direta: Ao incluir a versão na URL, fica imediatamente claro para os usuários qual versão estão acessando, facilitando o entendimento das mudanças feitas na API.
  
- Compatibilidade Segura: Permite que diferentes versões da API operem independentemente, garantindo que mudanças em uma versão não afetem as outras. Isso é útil quando os clientes da API não podem ser atualizados imediatamente.
  
- Gerenciamento de Mudanças Controlado: O versionamento na URL facilita o controle das alterações na API, permitindo que os usuários continuem utilizando versões anteriores até estarem prontos para atualizar.
  
- Adaptação Flexível: O versionamento na URL permite que a API evolua e se adapte às necessidades dos usuários ao longo do tempo, sem causar impactos negativos nos clientes existentes.