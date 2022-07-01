# facom-notes

Alunos:
- Matheus Silva Oliveira
- João Lucas Silva Machado

Visão Geral:
- O software terá a obrigatoriedade de cadastro de usuários com as seguintes informações (nome, email, usuário e senha).
- O software terá uma tela de login para autenticar o usuário, necessitando de (usuário e senha).
- Após autenticação, o usuário poderá criar/alterar/deletar/visualizar notas que serão atreladas ao seu próprio ID(um usuário não pode ver notas de outro usuário).

Papéis:
- O software contará com apenas o papel do próprio usuário

Requisitos funcionais:
- O software conta apenas com usuários comuns, não existe hierarquia de usuários.
- O usuário poderá apenas criar/alterar/deletar/visualizar as suas notas, não podendo interferir em notas criadas por outros usuários.
- O software é de uso exclusivo para armazenamento de notas para lembretes do próprio usuário
- As notas criadas pelo usuário serão salvas em um banco de dados SQLite com as seguintes informações (id, título e descrição).
