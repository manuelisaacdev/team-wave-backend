# TeamWave - Backend

## 📌 Descrição
O **TeamWave** é uma plataforma de **streaming de música** baseada em **micro serviços**, permitindo que usuários consumam, compartilhem e interajam com conteúdos musicais. Além disso, oferece uma experiência otimizada para **artistas** gerenciarem suas músicas, álbuns e playlists através do **TeamWave Studio**.

Este backend é responsável por fornecer todas as APIs e serviços necessários para o funcionamento da plataforma, garantindo escalabilidade, desempenho e segurança.

## 🚀 Tecnologias Utilizadas
- **🖥️ Backend:** Java com Spring Boot
- **🔗 API Gateway:** GraphQL
- **📦 Microsserviços:** Arquitetura baseada em múltiplos serviços independentes
- **🛠️ Banco de Dados:** PostgreSQL / MongoDB
- **📡 Mensageria:** Apache Kafka
- **🔐 Autenticação e Segurança:** JWT / OAuth2
- **☁️ Deploy & Infra:** Docker, Kubernetes (se aplicável)
- **📜 Logs & Monitoramento:** ELK Stack (Elasticsearch, Logstash, Kibana) / Prometheus / Grafana

## 📂 Estrutura de Microsserviços
O TeamWave possui os seguintes microsserviços:

1. **User Service** - Gerencia usuários, perfis e autenticação.
2. **Location Service** - Gestão de países e regiões.
3. **File Service** - Armazena e gerencia arquivos (músicas, imagens, capas de álbuns, etc.).
4. **Artist Service** - Gerencia informações sobre os artistas.
5. **Musical Genres Service** - Gestão de gêneros musicais.
6. **Media Service** - Controle de músicas e vídeo clips.
7. **Streaming Service** - Serviço responsável pelo streaming de músicas e vídeos.
8. **Album Service** - Gerencia álbuns e suas respectivas faixas.
9. **Playlist Service** - Gerencia playlists criadas pelos usuários.
10. **Reaction Service** - Gerencia curtidas e interações com músicas, álbuns e playlists.
11. **Notification Service** - Gerencia notificações e interações entre usuários.

## 🎯 Funcionalidades Principais
✅ Registro e autenticação de usuários (incluindo artistas)  
✅ Upload e gestão de músicas e vídeos  
✅ Criação e gerenciamento de álbuns e playlists  
✅ Streaming otimizado de músicas e vídeos  
✅ Integração com sistemas de recomendação de conteúdo  
✅ Suporte a curtidas e interações entre usuários  
✅ Notificações para artistas e ouvintes  
✅ Monitoramento de estatísticas e desempenho das músicas  

## 🛠️ Instalação e Execução
### 🔧 Requisitos
- Java 17+
- Maven
- Docker & Docker Compose
- PostgreSQL / MongoDB
- Apache Kafka

### 📥 Passos para rodar localmente
1. Clone o repositório:
   ```sh
   git clone https://github.com/seu-usuario/teamwave-backend.git
   cd teamwave-backend
   ```
2. Configure o ambiente:
   - Crie um arquivo **.env** e defina as variáveis de ambiente necessárias.
3. Suba os serviços necessários com Docker:
   ```sh
   docker-compose up -d
   ```
4. Compile e execute os microsserviços:
   ```sh
   mvn clean install
   mvn spring-boot:run -pl user-service
   # Execute outros serviços conforme necessário
   ```

## 📌 Endpoints Principais
A API segue uma arquitetura **GraphQL** para maior flexibilidade nas consultas. Alguns exemplos de queries:
```graphql
query {
  user(id: "123") {
    name
    email
  }
}
```

Para testes, utilize ferramentas como **Postman** ou **GraphQL Playground**.

## 🤝 Contribuição
1. Fork o repositório
2. Crie uma branch: `git checkout -b minha-feature`
3. Commit suas alterações: `git commit -m 'Adiciona nova funcionalidade'`
4. Envie para o repositório: `git push origin minha-feature`
5. Abra um Pull Request

## 📜 Licença
Este projeto está sob a licença MIT. Consulte o arquivo [`LICENSE`](LICENSE) para mais detalhes.

---

Feito com ❤️ por [Paciência Isaac Manuel](https://github.com/manuelisaacdev).

