#language: pt
#encoding: iso-8859-1
@GerenciamentoDeUmUsuarioNaPetstore
Funcionalidade: Gerenciamento de um usuário na Petstore

	@CriaUmUsuarioNaLoja
  Cenário: Cria um usuário na loja
    Quando faço um POST para /user com os seguintes valores:
      | id         |                  10 |
      | username   | raimundog4          |
      | firstName  | Rai                 |
      | lastName   | Jhonson             |
      | email      | raijhonson@oroi.com |
      | password   |            12345678 |
      | phone      |               12345 |
      | userStatus |                   1 |
    Então quando faço um GET para /v3/user/raimundog4, o usuário criado é retornado

	@CriaUmUsuarioNaLojaDocString
  Cenário: Cria um usuário na loja usando docstring
    Quando faço um POST para /user com a seguinte docstring:
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
    Então quando faço um GET para /v3/user/theUser, o usuário criado é retornado

	@CriaUmUsuarioNaLojaBDDPorExemplo
  Cenário: Criar usuário na loja refletindo o negócio
    Quando crio um usuário
    Então o usuário é salvo no sistema
