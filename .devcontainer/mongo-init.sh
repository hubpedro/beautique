#!/bin/bash
set -e  # Para em caso de erro
set -x  # Modo debug - mostra cada comando

echo "=== Iniciando inicialização do MongoDB ==="

# Aguardar o MongoDB ficar pronto
sleep 3

mongosh --username mongoadmin --password mongopassword --authenticationDatabase admin <<EOF

use ms-beautique-query;

// Limpar usuários existentes primeiro
try {
  db.dropUser("ms-beautique-query")
} catch (err) {
  print("Usuário ms-beautique-query não existia: " + err);
}

try {
  db.dropUser("mssync")
} catch (err) {
  print("Usuário mssync não existia: " + err);
}

// Criar usuário com permissões totais (para o microsserviço que escreve)
db.createUser({
  user: 'mssync',
  pwd: 'mssync',
  roles: [{ role: 'dbOwner', db: 'ms-beautique-query' }]  // CORRIGIDO: role, não roles
});

// Criar usuário com permissão apenas leitura (para segurança)
db.createUser({
  user: 'ms-beautique-query',
  pwd: 'ms-beautique-query',
  roles: [{ role: 'read', db: 'ms-beautique-query' }]  // CORRIGIDO: role, não roles
});

// Verificar se os usuários foram criados
db.getUsers();

EOF

echo "=== Inicialização do MongoDB concluída ==="