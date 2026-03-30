from locust import HttpUser, task, between
import uuid
import random

class ClienteDaviUser(HttpUser):
    wait_time = between(1, 2)

    headers = {
        "x-api-key": "concessionaria-api-key-2026",
        "Content-Type": "application/json"
    }

    @task
    def criar_e_buscar_cliente(self):
        codigo = str(uuid.uuid4().int)[:11]
        telefone = "43" + str(random.randint(900000000, 999999999))

        payload = {
            "nome": f"Cliente {codigo}",
            "cpf": codigo,
            "telefone": telefone,
            "email": f"cliente{codigo}@teste.com",
            "endereco": f"Rua Teste {codigo}"
        }

        resposta_post = self.client.post(
            "/clientes/novo",
            json=payload,
            headers=self.headers,
            name="POST /clientes/novo"
        )

        if resposta_post.status_code not in [200, 201]:
            print("Erro no POST:", resposta_post.status_code, resposta_post.text)
            return

        try:
            cliente = resposta_post.json()
            cliente_id = cliente.get("id")
        except Exception:
            print("POST não retornou JSON válido:", resposta_post.text)
            return

        if not cliente_id:
            print("POST não retornou id:", resposta_post.text)
            return

        resposta_get = self.client.get(
            f"/clientes/{cliente_id}",
            headers=self.headers,
            name="GET /clientes/{id}"
        )

        if resposta_get.status_code != 200:
            print("Erro no GET:", resposta_get.status_code, resposta_get.text)
