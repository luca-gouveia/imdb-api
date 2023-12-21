# imdb-api

A api está publicada e disponível documentada no endereço abaixo:

https://imdb-api-production-0e39.up.railway.app/swagger-ui/index.html#

O front end da aplicação que usa dos recursos disponibilizados pela api encontra-se no seguite repositório:

https://github.com/luca-gouveia/imdb-app

O frontend também foi disponibilizado para acesso e encontra-se no seguinte endereço:

https://imdb-app-production.up.railway.app/

Obs: O ambiente onde a aplicação está hospedada é bem limitado. A máquina é bem lenta, mas dá para ter uma ideia do projeto

# Para rodar localmente
Em ambos os repositórios, acessar a branch *develop* para rodar os projetos. As branchs master e main tem modificações necessárias para o deploy das aplicações

# Para execuções como administrador, usar o usuário abaixo.

Vale lembrar que o usuário admin só é criado via banco. Todo e qualquer usuário criado pelo sistema, trata-se de um usuário de *ROLE - USER* apenas.
# Usuário
- Admin:
   - admin@admin.com
   - 123

# Api documentada
![image](https://github.com/luca-gouveia/imdb-api/assets/38117857/edc2ca3f-b46f-437e-8f18-1b607cc7bd24)

# Tela principal do front
- listagem dos títulos recém adicionados
- busca por título, gênero, atores e diretor
![image](https://github.com/luca-gouveia/imdb-api/assets/38117857/24e8fd8b-268e-47ee-9b0f-c73734ce0db4)

# Detalhamento do título
- Possibilidade de avaliar
![image](https://github.com/luca-gouveia/imdb-api/assets/38117857/1ce48bef-2129-4d57-a132-5ecddfb87191)

# Usuários ativos
- Somente *users* com a Role ADMIN podem modificar e acessar os usuários.
- O recurso de bloqueio é garantido pela própria api e também há uma camada de autorização do front que lida com a ROLE do usuário logado
![image](https://github.com/luca-gouveia/imdb-api/assets/38117857/7d482380-b29e-435d-b00b-e3ba98120940)

# Um pouco sobre a api
- Ela foi desenvolvida visando atingir o status RESTFUL. Em sua concepção foi determinado o uso semântico dos verbos http, como também o acesso a hipermedia entre os recursos (HATEOAS)
- Há paginação de resultados que orienta melhor quem consome a api

# O que foi elabora e coberto

- Página para a realização do login e logout.
- Página de filmes
- Listagem.
- Cadastro do filme.
- Página de detalhe do filme.
- Cadastro (Somente um usuário administrador poderá realizar esse cadastro)
- Voto (A contagem dos votos será feita por usuário de 0-4 que indica quanto o usuário gostou do filme)
- Listagem (deverá ter filtro por diretor, nome, gênero e/ou atores)
- Detalhe do filme trazendo todas as informações sobre o filme, inclusive a média dos votos
- Edição de usuários e exclusão lógica dos mesmos (Somente ADMIN pode realizar essas ações)


