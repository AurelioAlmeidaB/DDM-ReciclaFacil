# Recicla Fácil

App Android para **agendar a coleta de materiais recicláveis** em casa.

## O problema

Muita gente quer separar o lixo reciclável, mas não sabe como avisar a coleta ou acaba misturando tudo por falta de organização. O **Recicla Fácil** deixa o morador informar o endereço, quais materiais tem para coletar e o melhor período, e mostra um resumo do agendamento.

## O que o app faz

**Tela 1 — Agendamento**
- Imagem das lixeiras da coleta seletiva (azul: papel, vermelho: plástico, verde: vidro, amarelo: metal).
- Campo de texto para o **endereço** (`EditText`).
- **Materiais** para coleta com `CheckBox` (várias escolhas): Papel, Plástico, Vidro e Metal.
- **Período** da coleta com `RadioButton` dentro de um `RadioGroup` (escolha única): Manhã, Tarde e Noite.
- Botão **Agendar coleta**: recupera as escolhas e mostra nos `TextView`s. Se faltar algo, avisa ("Nenhum material selecionado", "Nenhum período selecionado").
- Botão **Ver resumo**: só é liberado quando tudo está preenchido e leva os valores dos `TextView`s para a segunda tela.

**Tela 2 — Resumo (`ResumoActivity`)**
- Recebe os dados com `Intent`/`putExtra` e mostra o resumo do agendamento, com uma dica e o botão **Voltar**.

## Tecnologias

- Kotlin, Android Studio
- View Binding
- Duas Activities com passagem de dados por `Intent`

## Sobre

Atividade de aula de 17/11 da disciplina de **Desenvolvimento para Dispositivos Móveis (DDM)** — IFSP Campus Votuporanga.

**Autores:** Aurelio Almeida e Paulo Mazuque
