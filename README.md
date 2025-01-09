# Algoritmo de geração de token
--- 
## Problema a ser resolvido
O objetivo deste projeto é realizar o cálculo de uma "Access Key" gerada por meio de operações matemáticas (XOR) entre valores de entrada fornecidos pelo usuário (um serial de equipamento e um token hexadecimal) e o número “mágico” fixo: 0x99FFAA. Posteriormente, o programa gera um hash criptográfico SHA-1 para verificar a integridade dos dados e exibir uma chave única derivada das entradas.

--- 
## Tecnologia Utilizada
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
Java versão 21.0.5

--- 
## Funcionalidades
##### Entradas do usuario:
> - Um serial (número inteiro entre 0 e 10.000).
> - Um token hexadecimal no formato 0x000000 a 0xFFFFFF.
##### Validação:
> - Verifica se o serial está dentro do intervalo permitido.
> - Garante que o token está no formato correto e dentro do intervalo permitido.
##### Processamento:
> - Realiza a conversão do número inteiro (serial) para uma representacao little-endian.
> - Converte os valores de entrada para arrays de bytes.
> - Realiza operações bit a bit (XOR) entre os valores de entrada e uma chave "mágica"
> - Gera um hash SHA-1 do resultado da operação XOR.
##### Saída:
> - Exibe a chave de acesso derivado da operação XOR.
> - Exibe os 3 primeiros bytes do hash SHA-1.
> - Apresenta ao usuario o resultado em representação hexadecimal.

--- 
## Como compilar o código
> Dica!
Certifique-se de ter o Java Development Kit (JDK) instalado na máquina na versão 21.
Caso nao possua siga o passo a passo abaixo.
1. Execute este comando no terminal para executar a instalação.
```sh
sudo apt install openjdk-21-jdk -y
```
2. Execute este comando para confirmar a versão instalada.
```sh
java -version
```
--- 
# Como utilizar o código
1. O programa solicitará:
- Um serial (digite um número entre 0 e 10.000).
- Um token hexadecimal (no formato 0xFFFFFF
2. O programa processará os dados e exibirá no console:
- A "Access Key" gerada pela operação XOR.
- Os 3 primeiros bytes do hash SHA-1.
### Exemplo de saída
```sh
Enter the serial (0 to 10000): 1234
Enter the hexadecimal token (0x000000 to 0xFFFFFF): 0xADF015

XOR Result (Access Key): 0xE60BBF
SHA1 (output): 0x523DD9
Final: "523DD9"
```
--- 
# Como testar o código
1. Abra o terminal e navegue ate uma pasta vazia.
2. Digite o comando abaixo para clonar o repositório para sua máquina: 
```sh
git clone https://github.com/LucasKerb/Algoritimo_Token.git
```
3. Digite o comando abaixo para acessar o repositório "Algoritimo_Token".
```sh
cd ./Algoritimo_Token
```
4. Dentro desta pasta, existe um arquivo .jar chamado 'TokenGen.jar', para executar este arquivo e testar basta digitar o comando abaixo.
```sh
java -jar TokenGen.jar
```
5. Caso queira executar mais algum teste, basta digitar o comando novamente!
--- 
