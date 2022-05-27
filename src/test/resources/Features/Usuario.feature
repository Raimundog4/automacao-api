#language: pt
#encoding: iso-8859-1
@GerenciamentoDeUmUsuarioNaPetstore
Funcionalidade: Gerenciamento de um usuário na Petstore

	@CriaUmUsuarioNaLojaBDDPorExemplo
  Cenário: Criar usuário na loja refletindo o negócio
    Quando crio um usuário
    Então o usuário é salvo no sistema
