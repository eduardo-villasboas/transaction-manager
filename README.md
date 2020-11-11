
##### Este projeto expoẽ uma API simples. 

---

##### Tecnologias, bibliotecas.
- Java 11
- Gradle
- Springboot 2.3.5
- Jpa 
- Junit5
- Mockito
- docker
- liquibase(a base de dados é criada automaticamente)
- Java bean validation
- Swagger

##### Para rodar testes pela linha de commando utilize o commando no linux.
```batch
./gradlew clean build test
```
##### Para rodar testes pela linha de commando utilize o commando no windows.
```batch
gradlew.bat clean build test
```
#### Obs: Os testes mockMvc(controller), dependem de uma base de dados limpa para funcionar corretamenente. Os testes não deixam sujeira(dados) no banco.


##### Para executar o banco localmente basta entrar na pasta docker na raiz do projeto e executar o seguinte commando.
```bash
docker-compose up -d
```
##### Obs: O banco esta sendo mapeado para a porta 5431. Mas já está configurada no arquivo de properties

