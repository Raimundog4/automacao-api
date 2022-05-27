#language: pt
#encoding: iso-8859-1
@GerenciamentoDeUmUsuarioNaPetstore
Funcionalidade: Gerenciamento de um usu�rio na Petstore

	@CriaUmUsuarioNaLoja
  Cen�rio: Cria um usu�rio na loja
    Quando fa�o um POST para /user com os seguintes valores:
      | id         |                  10 |
      | username   | raimundog4          |
      | firstName  | Rai                 |
      | lastName   | Jhonson             |
      | email      | raijhonson@oroi.com |
      | password   |            12345678 |
      | phone      |               12345 |
      | userStatus |                   1 |
    Ent�o quando fa�o um GET para /v3/user/raimundog4, o usu�rio criado � retornado

	@CriaUmUsuarioNaLojaDocString
  Cen�rio: Cria um usu�rio na loja usando docstring
    Quando fa�o um POST para /user com a seguinte docstring:
      """json
      {
      "id": 12,
      "username": "theUser",
      "firstName": "John",
      "lastName": "James",
      "email": "john@email.com",
      "password": "12345",
      "phone": "12345",
      "userStatus": 1
      }
      """
    Ent�o quando fa�o um GET para /v3/user/theUser, o usu�rio criado � retornado

	@CriaUmUsuarioNaLojaBDDPorExemplo
  Cen�rio: Criar usu�rio na loja refletindo o neg�cio
    Quando crio um usu�rio
    Ent�o o usu�rio � salvo no sistema
