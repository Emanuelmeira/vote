## Teste

Documentação da aplicação:
Local: http://localhost:8080/swagger-ui.html
Heroku: https://application-vote.herokuapp.com/swagger-ui.html

Arquitetura simples e funcional
Controller para receber as requisições
Service para aplicar regras de negócios 
Repository para persistências/regaste de informações

Separando responsabilidades e ao mesmo tempo facilitando a mantenabilidade

Validações:
Utilizei bean Validations em DTOs, validando assim algumas regras simples e especificas logo no controller (tudo automático e implícito)

Log:
Usei "LoggerFactory" por ser mais simples e funcional

Testes:
Utilizei alguns exemplos de testes unitários usando Mockito e Junit

Versionamento de API:
Utilizei uma forma antiga porem bem funcional de versionamento, acrescentando na URL "/v1"

Tratamento de erros:
Apliquei uma estrutura onde concentro as exceptions, suas mensagens e HttpStatus, facilitando a manutenção.
Também foi aplicado o @ControllerAdvice

Banco de dados:
Optei pelo banco de dados H2, o mesmo está salvando os dados (não se perde quando se reinicia),
em uma pasta chamada "data", no diretório da aplicação.
Quando a aplicação inicia, é executado o script que está em "src/main/resources/schema.sql", criando toda estrutura necessária

Após iniciar a aplicação, o banco está disponível em: 
Local: http://localhost:8080/h2-console
Heroku: https://application-vote.herokuapp.com/h2-console

OBS: "user name" = "sa" e não precisa de senha, o caminho em JDBC URL é: 
“***:./data/vote”


Utilizei DTOs para alguns métodos do controller, costumo usar para todos os dados que os controllers recebem,
Após validação e processamento utilizo um parse para a entidade do banco. Por esse ser um projeto pequeno, não apliquei essa regra para TUDO


Utilizando a aplicação:
Não existe dependências externas para iniciar a aplicação, nada além de um ambiente Java/Spring
Utilizei: Java11 , Spring 2.5.0, maven 3.6.3 


### Cadastrar Pauta

Para cadastrar uma pauta é necessário um POST, informando um body em JSON:
LOCAL:  http://localhost:8080/api/v1/pauta
Heroku: https://application-vote.herokuapp.com/api/v1/pauta

JSON:
{
    "theme": "Pauta bomba"
}

OBS: é obrigatório informa um theme e tem limite de tamanho entre 3 e 50 caracteres 

### Abrir votação
Para abri um processo de votação, é necessário fazer um POST, informando no path o id da pauta e também informando um body em JSON:
LOCAL: http://localhost:8080/api/v1/pauta/opensession/{pautaId}
JSON: https://application-vote.herokuapp.com/api/v1/pauta/opensession/{pautaId}

{
    "timeInMinutes": 5
}

Esse é o tempo (em minutos) que a pauta ficara aberta para votação, caso não for informado body, então será o tempo padrão de 1min

### Votar

Para votar, é necessário realizar um POST informando na URL o id da pauta e o id do associado, junto com um body ‘SIM/NAO’

Local: http://localhost:8080/api/v1/voto/{associadoId}/{pautaId}
Heroku: https://application-vote.herokuapp.com/api/v1/voto/{associadoId}/{pautaId}

JSON:
{
    "votoType": "SIM"
}

### Obter resultado de votação

Para obter o resultado de uma pauta, basta usar um GET informando o id da pauta
Local: http://localhost:8080/api/v1/pauta/result/{pautaId}
Heroku: https://application-vote.herokuapp.com/api/v1/pauta/result/{pautaId}


O retorno uma mensagem informando se a pauta foi aprovada ou não

OBS: para saber o resultado de uma votação, a pauta precisa ser aberta pra votação e o tempo de votação precisar ter encerrado, para assim ser computado um resultado.
Pauta que não foram abertas não podem ser votadas

